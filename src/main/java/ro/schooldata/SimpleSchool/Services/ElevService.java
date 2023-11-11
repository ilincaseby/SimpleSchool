package ro.schooldata.SimpleSchool.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.schooldata.SimpleSchool.Classes.ERole;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Classes.Role;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Payload.Response.JwtResponse;
import ro.schooldata.SimpleSchool.Payload.Response.MessageResponse;
import ro.schooldata.SimpleSchool.Repositories.ElevRepository;
import ro.schooldata.SimpleSchool.Classes.LoggedInAccount;
import ro.schooldata.SimpleSchool.Repositories.ProfesorRepository;
import ro.schooldata.SimpleSchool.Repositories.RoleRepository;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;
import ro.schooldata.SimpleSchool.Security.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElevService implements IElevService {

    private final ElevRepository elevRepository;
    private RoleRepository roleRepository;
    private RoleService roleService;
    private IProfesorService profesorService;
    private LoggedInAccount acc = LoggedInAccount.getInstance();
    public ElevService(ElevRepository elevRepository,
                       RoleRepository roleRepository,
                       RoleService roleService,
                       IProfesorService profesorService) {
        this.profesorService = profesorService;
        this.elevRepository = elevRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<?> authElev(LoginRequest loginRequest, AuthenticationManager authenticationManager, PasswordEncoder encode, JwtUtils jwtUtils) {
        Elev elev = elevRepository.findByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Elev not found by this username!"));
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    @Override
    @Transactional
    public ResponseEntity<?> signupElev(SignupRequest signupRequest, AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils) {
        roleService.verifyAndAdapt();
        if (elevRepository.existsByUserName(signupRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Username already in use!"));
        }
        Elev elev = new Elev(signupRequest.getLastName(), signupRequest.getFirstName(),
                signupRequest.getTel(), signupRequest.getEmail(), signupRequest.getCnp(),
                signupRequest.getCod(), signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()));
        // Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(ERole.ROLE_ELEV)
                        .orElseThrow(() -> new RuntimeException("Role not found!"));
        roles.add(role);
        elev.setRoles(roles);
        elevRepository.save(elev);
        return ResponseEntity.ok(new MessageResponse("Student account created successfully"));
    }

    @Override
    public Elev getADefaultElev() {
        return null;
        //return new Elev("Popescu", "Andrei", "0788888888", "email@example.com", "0000000000", "000000");
    }

    @Override
    public List<Elev> returnAll() {
        return elevRepository.findAll();
    }

    @Override
    public void createElev(Elev elev) {
        List<Elev> elevP = elevRepository.findAllByCnp(elev.getCNP());
        if (elevP.size() != 0) {
            throw new IllegalStateException(String.format("Elevul deja exista in sistem pe baza CNP-ului: %s", elev.getCNP()));
        }
        elevRepository.save(elev);
    }

    @Override
    public void updateElev(Long id, Elev elev) {
        Elev elev1 = elevRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Nu exista in baza de date elevul cu id-ul %ld", id)));
        elev1.setCNP(elev.getCNP());
        elev1.setCod(elev.getCod());
        elev1.setEmail_p(elev.getEmail_p());
        elev1.setTel(elev.getTel());
        elev1.setFirstName(elev.getFirstName());
        elev1.setLastName(elev.getLastName());
        elev1.setClasa(elev.getClasa());
        elevRepository.save(elev1);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteElev(Long id) {
        Elev elev = elevRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("Student not found by id!"));
        // update all teacher about this kid
        profesorService.deleteStudent(id);
        elevRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Account deleted successfully!"));
    }

    @Override
    public Elev getElevById(Long id) {
        Elev elev = elevRepository.findById(id).orElseThrow(() -> new IllegalStateException(String.format("Nu exista elevul cu id-ul %ld", id)));
        return elev;
    }
}
