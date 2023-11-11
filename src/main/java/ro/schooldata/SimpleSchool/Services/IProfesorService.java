package ro.schooldata.SimpleSchool.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.schooldata.SimpleSchool.Classes.Profesor;
import ro.schooldata.SimpleSchool.Payload.Request.LoginRequest;
import ro.schooldata.SimpleSchool.Payload.Request.SignupRequest;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;

import java.util.List;

public interface IProfesorService {
    public ResponseEntity<?> authProfesor(LoginRequest loginRequest,
                                          AuthenticationManager authenticationManager,
                                          PasswordEncoder encode,
                                          JwtUtils jwtUtils);
    public ResponseEntity<?> signupProfesor(SignupRequest signupRequest,
                                            AuthenticationManager authenticationManager,
                                            PasswordEncoder encode,
                                            JwtUtils jwtUtils);
    public List<Profesor> returnAll(); //done

    public void deleteStudent(Long id);

    public void createProfesor(Profesor profesor); // done
    public void updateProfesor(Long id, Profesor profesor); // done
    public void deleteProfesor(Long id); // done
    public Profesor getProfesorById(Long id); // done
    public void changePassword(String passwordOld, String passwordNew); // done
    public void putGradeToElev(Long id, int grade); // done
    public void addAnElev(Long id); // done
    public void deleteAnElev(Long id); // done

    public void deleteASubject(Long id, String materie); // done
    public void addASubject(Long id, String materie); // done

    public void disconnect();
    public void connect(String userName, String password);
}
