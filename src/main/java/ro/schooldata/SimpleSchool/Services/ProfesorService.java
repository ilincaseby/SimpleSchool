package ro.schooldata.SimpleSchool.Services;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.schooldata.SimpleSchool.Classes.*;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Payload.Response.JwtResponse;
import ro.schooldata.SimpleSchool.Payload.Response.MessageResponse;
import ro.schooldata.SimpleSchool.Repositories.ElevRepository;
import ro.schooldata.SimpleSchool.Repositories.MaterieRepository;
import ro.schooldata.SimpleSchool.Repositories.ProfesorRepository;
import ro.schooldata.SimpleSchool.Repositories.RoleRepository;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;
import ro.schooldata.SimpleSchool.Security.services.UserDetailsImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfesorService implements IProfesorService {

    private final ProfesorRepository profesorRepository;
    private final MaterieRepository materieRepository;

    private RoleRepository roleRepository;
    private RoleService roleService;

    private final ElevRepository elevRepository;
    public ProfesorService(RoleService roleService, ProfesorRepository profesorRepository,
                           MaterieRepository materieRepository, ElevRepository elevRepository,
                            RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.profesorRepository = profesorRepository;
        this.materieRepository = materieRepository;
        this.elevRepository = elevRepository;
    }


    @Override
    public ResponseEntity<?> authProfesor(LoginRequest loginRequest, AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils) {
        Profesor profesor = profesorRepository.findByUserName(loginRequest.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("Profesor not found by username!"));
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequest.
                        getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().
                map(item -> item.getAuthority()).
                        collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    @Transactional
    public ResponseEntity<?> signupProfesor(SignupRequest signupRequest, AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils) {
        roleService.verifyAndAdapt();

        if (profesorRepository.existsByUserName(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This username already exists in the database"));
        }
        if (profesorRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("This email is already in use"));
        }
        Profesor profesor = new Profesor(signupRequest.getFirstName(),
                signupRequest.getLastName(), signupRequest.getMaterie(),
                signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()),
                signupRequest.getEmail());

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                    .orElseThrow(() -> new RuntimeException("Role is not found"));
            roles.add(teacherRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role is not found"));
                        roles.add(adminRole);
                        break;

                    default:
                        Role profesorRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                .orElseThrow(() -> new RuntimeException("Role is not found"));
                        roles.add(profesorRole);
                        break;
                }
            });
        }

        profesor.setRoles(roles);
        profesorRepository.save(profesor);
        return ResponseEntity.ok(new MessageResponse("Account successfully created!"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> connectAStudent(Long idT, Long idS) {
        Profesor profesor = profesorRepository.findById(idT)
                .orElse(null);
        Elev elev = elevRepository.findById(idS)
                .orElse(null);
        if (profesor == null || elev == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Student or teacher not found by the id provided"));
        }
        if (elev.getProfesori().stream().filter(a -> a.getMaterie().equals(profesor.getMaterie())).findFirst().orElse(null) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Student already has a teacher on this subject"));
        }
        if (elev.getMaterii().stream().filter(a -> a.getName().equals(profesor.getMaterie())).findFirst().orElse(null) == null) {
            elev.getMaterii().add(new Materie(profesor.getMaterie(), new ArrayList<>(), elev));
        }
        elev.getProfesori().add(profesor);
        profesor.getElevi().add(elev);
        profesorRepository.save(profesor);
        elevRepository.save(elev);
        return ResponseEntity
                .ok(new MessageResponse("Operation completed!"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteConnection(Long idT, Long idS) {
        Profesor profesor = profesorRepository.findById(idT)
                .orElse(null);
        Elev elev = elevRepository.findById(idS)
                .orElse(null);
        if (profesor == null || elev == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Student or teacher not found by the id provided"));
        }
        if (elev.getProfesori().stream().filter(a -> a.getMaterie().equals(profesor.getMaterie())).findFirst().orElse(null) == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Student has no teacher on this subject"));
        }
        elev.getProfesori().removeIf(a -> a.getId() == idT);
        profesor.getElevi().removeIf(a -> a.getId() == idS);
        elevRepository.save(elev);
        profesorRepository.save(profesor);
        return ResponseEntity
                .ok(new MessageResponse("Connection deleted successfully"));
    }


    @Override
    @Transactional
    public ResponseEntity<?> deleteTeacher(Long id) {
        Profesor profesor = profesorRepository.findById(id).orElse(null);
        if (profesor == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Teacher not found with this id"));
        }
        List<Elev> list = elevRepository.findAll();
        for (Elev elev : list) {
            if (elev.getProfesori().stream().filter(a -> a.getId() == id).findFirst().orElse(null) != null) {
                elev.getProfesori().removeIf(a -> a.getId() == id);
                elevRepository.save(elev);
            }
        }
        profesorRepository.delete(profesor);
        return ResponseEntity
                .ok(new MessageResponse("Delete operation done!"));
    }

    @Override
    public List<Profesor> returnAll() {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {
        List<Profesor> profesors = profesorRepository.findAll();
        profesors.forEach(profesor -> {
            int size = profesor.getElevi().size();
            profesor.getElevi().removeIf(elev -> elev.getId().equals(id));
            if (size > profesor.getElevi().size()) {
                profesorRepository.save(profesor);
            }
        });
    }

    @Override
    public void createProfesor(Profesor profesor) {

    }

    @Override
    public void updateProfesor(Long id, Profesor profesor) {

    }

    @Override
    public void deleteProfesor(Long id) {

    }

    @Override
    public Profesor getProfesorById(Long id) {
        return null;
    }

    @Override
    public void changePassword(String passwordOld, String passwordNew) {

    }

    @Override
    public void putGradeToElev(Long id, int grade) {

    }

    @Override
    public void addAnElev(Long id) {

    }

    @Override
    public void deleteAnElev(Long id) {

    }

    @Override
    public void deleteASubject(Long id, String materie) {

    }

    @Override
    public void addASubject(Long id, String materie) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void connect(String userName, String password) {

    }
}
