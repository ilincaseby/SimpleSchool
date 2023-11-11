package ro.schooldata.SimpleSchool.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Classes.Materie;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;

import java.util.List;
import java.util.Set;

public interface IElevService {
    public ResponseEntity<?> authElev(LoginRequest loginRequest,
                                          AuthenticationManager authenticationManager,
                                          PasswordEncoder encode,
                                          JwtUtils jwtUtils);
    public ResponseEntity<?> signupElev(SignupRequest signupRequest,
                                        AuthenticationManager authenticationManager,
                                        PasswordEncoder encoder,
                                        JwtUtils jwtUtils);
    public Set<Materie> getGrades(Long id);
    public Elev getADefaultElev();
    public List<Elev> returnAll();

    public void createElev(Elev elev);

    public void updateElev(Long id, Elev elev);


    public ResponseEntity<?> deleteElev(Long id);

    public Elev getElevById(Long id);
}
