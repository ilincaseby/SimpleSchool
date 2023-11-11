package ro.schooldata.SimpleSchool.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;

import java.util.List;

public interface IElevService {
    public ResponseEntity<?> authElev(LoginRequest loginRequest,
                                          AuthenticationManager authenticationManager,
                                          PasswordEncoder encode,
                                          JwtUtils jwtUtils);
    public ResponseEntity<?> signupElev(SignupRequest signupRequest,
                                        AuthenticationManager authenticationManager,
                                        PasswordEncoder encoder,
                                        JwtUtils jwtUtils);
    public Elev getADefaultElev();
    public List<Elev> returnAll();

    public void createElev(Elev elev);

    public void updateElev(Long id, Elev elev);


    public ResponseEntity<?> deleteElev(Long id);

    public Elev getElevById(Long id);
}
