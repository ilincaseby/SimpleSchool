package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;
import ro.schooldata.SimpleSchool.Services.IProfesorService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    IProfesorService profesorService;

    public AuthController(AuthenticationManager authenticationManager,
                          PasswordEncoder encoder, JwtUtils jwtUtils,
                          IProfesorService profesorService){
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.profesorService = profesorService;
    }

    @PostMapping("/profesor/signin")
    public ResponseEntity<?> loginProfesor(@Valid @RequestBody LoginRequest loginRequest) {
        return profesorService.authProfesor(loginRequest, authenticationManager, encoder, jwtUtils);
    }

    @PostMapping("/profesor/signup")
    public ResponseEntity<?> registerProfesor(@Valid @RequestBody SignupRequest signupRequest) {
        return profesorService.signupProfesor(signupRequest, authenticationManager, encoder, jwtUtils);
    }
    @GetMapping("/profesor/wow")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String getTest() {
        return "Ai reusit bro!!!";
    }
}
