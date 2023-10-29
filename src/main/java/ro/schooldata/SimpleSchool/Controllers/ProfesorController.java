package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.schooldata.SimpleSchool.Classes.MyIntegerClass;
import ro.schooldata.SimpleSchool.Classes.PasswordClass;
import ro.schooldata.SimpleSchool.Classes.Profesor;
import ro.schooldata.SimpleSchool.Classes.SubjectClass;
import ro.schooldata.SimpleSchool.Services.IProfesorService;

import java.util.List;

@Controller
@RequestMapping(path = "api/v1/profesori")
public class ProfesorController {
    private final IProfesorService profesorService;
    public ProfesorController(IProfesorService profesorService) {
        this.profesorService = profesorService;
    }
    @GetMapping(path = "/returnAll")
    public List<Profesor> returnAllTeachers() {
        return profesorService.returnAll();
    }
    @GetMapping(path = "{id}")
    public Profesor returnTeacher(@PathVariable Long id) {
        return profesorService.getProfesorById(id);
    }
    @PostMapping(path = "/add")
    public void createTeacher(@RequestBody Profesor profesor) {
        profesorService.createProfesor(profesor);
    }
    @PutMapping(path = "/updateC/{id}")
    public void updateTeacher(@PathVariable Long id, @RequestBody Profesor profesor) {
        profesorService.updateProfesor(id, profesor);
    }
    @PutMapping(path = "/updateP")
    public void updatePassword(@RequestBody PasswordClass passwordO) {
        profesorService.changePassword(passwordO.getPasswordOld(), passwordO.getPasswordNew());
    }
    @DeleteMapping(path = "/delete/{id}")
    public void deleteTeacher(@RequestBody Long id) {
        profesorService.deleteProfesor(id);
    }
    @PutMapping(path = "/addGrade/{id}")
    public void gradeToStudent(@PathVariable Long id, @RequestBody MyIntegerClass grade) {
        profesorService.putGradeToElev(id, grade.getValue());
    }
    @PutMapping(path = "/assignStudent/{id}")
    public void assignStudent(@PathVariable Long id) {
        profesorService.addAnElev(id);
    }
    @PutMapping(path = "/renounceStudent/{id}")
    public void renounceStudent(@PathVariable Long id) {
        profesorService.deleteAnElev(id);
    }
    @PostMapping(path = "/subjects/{id}/add")
    public void addASubject(@PathVariable Long id, @RequestBody SubjectClass subject) {
        profesorService.addASubject(id, subject.getSubject());
    }
    @DeleteMapping(path = "/subjects/{id}/delete")
    public void deleteASubject(@PathVariable Long id, @RequestBody SubjectClass subject) {
        profesorService.deleteASubject(id, subject.getSubject());
    }
}
