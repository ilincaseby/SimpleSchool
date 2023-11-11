package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.schooldata.SimpleSchool.Classes.ConnTeacherStudent;
import ro.schooldata.SimpleSchool.Classes.User;
import ro.schooldata.SimpleSchool.Security.jwt.JwtUtils;
import ro.schooldata.SimpleSchool.Security.services.UserDetailsImpl;
import ro.schooldata.SimpleSchool.Services.IElevService;
import ro.schooldata.SimpleSchool.Services.IProfesorService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/management")
public class GradeManagementController {
    AuthenticationManager authenticationManager;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    IProfesorService profesorService;

    IElevService elevService;
    public GradeManagementController(AuthenticationManager authenticationManager,
                          PasswordEncoder encoder, JwtUtils jwtUtils,
                          IProfesorService profesorService,
                          IElevService elevService){
        this.elevService = elevService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.profesorService = profesorService;
    }

    @PutMapping("/admin/establishConnection")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> establishConnTS(@Valid @RequestBody ConnTeacherStudent connTeacherStudent) {
        return profesorService.connectAStudent(connTeacherStudent.getIdT(), connTeacherStudent.getIdS());
    }

    @PutMapping("/admin/deleteConnection")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteConnTS(@Valid @RequestBody ConnTeacherStudent connTeacherStudent) {
        return profesorService.deleteConnection(connTeacherStudent.getIdT(), connTeacherStudent.getIdS());
    }

    @PutMapping("/teacher/establishConnection/{id}")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> establishConn(@PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profesorService.connectAStudent(userDetails.getId(), id);
    }

    @PutMapping("/teacher/deleteConnection/{id}")
    public ResponseEntity<?> deleteConn(@PathVariable Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return profesorService.deleteConnection(userDetails.getId(), id);
    }
}
