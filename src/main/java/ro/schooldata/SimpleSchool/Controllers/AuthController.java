package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;
import ro.schooldata.SimpleSchool.Security.services.UserDetailsImpl;
import ro.schooldata.SimpleSchool.Services.IElevService;
import ro.schooldata.SimpleSchool.Services.IProfesorService;

import javax.validation.Valid;
import java.security.PublicKey;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    IProfesorService profesorService;

    IElevService elevService;

    public AuthController(AuthenticationManager authenticationManager,
                          PasswordEncoder encoder, JwtUtils jwtUtils,
                          IProfesorService profesorService,
                          IElevService elevService){
        this.elevService = elevService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.profesorService = profesorService;
    }

    @PostMapping("/profesor/signin")
    public ResponseEntity<?> loginProfesor(@Valid @RequestBody LoginRequest loginRequest) {
        return profesorService.authProfesor(loginRequest, authenticationManager, encoder, jwtUtils);
    }
    @PostMapping("/elev/signin")
    public ResponseEntity<?> loginElev(@Valid @RequestBody LoginRequest loginRequest) {
        return elevService.authElev(loginRequest, authenticationManager, encoder, jwtUtils);
    }
    @PostMapping("/profesor/signup")
    public ResponseEntity<?> registerProfesor(@Valid @RequestBody SignupRequest signupRequest) {
        return profesorService.signupProfesor(signupRequest, authenticationManager, encoder, jwtUtils);
    }
    @PostMapping("/elev/signup")
    public ResponseEntity<?> registerElev(@Valid @RequestBody SignupRequest signupRequest) {
        return elevService.signupElev(signupRequest, authenticationManager, encoder, jwtUtils);
    }
    @PostMapping("/admin/signupElev")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addAStudent(@Valid @RequestBody SignupRequest signupRequest) {
        return elevService.signupElev(signupRequest, authenticationManager, encoder, jwtUtils);
    }
    @PostMapping("/admin/deleteElev/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return elevService.deleteElev(id);
    }

    @PostMapping("/admin/addTeacher")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTeacher(@Valid @RequestBody SignupRequest signupRequest) {
        return profesorService.signupProfesor(signupRequest, authenticationManager, encoder, jwtUtils);
    }

    @PostMapping("/high/deleteMYSLF")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> deleteSelfHigh() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext().getAuthentication()
                .getPrincipal();
        return profesorService.deleteTeacher(userDetails.getId());
    }
}
