package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.schooldata.SimpleSchool.Classes.ERole;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Classes.Profesor;
import ro.schooldata.SimpleSchool.Classes.User;
import ro.schooldata.SimpleSchool.Services.IElevService;
import ro.schooldata.SimpleSchool.Services.IProfesorService;
import ro.schooldata.SimpleSchool.Services.ProfesorService;

import java.util.List;

@RestController
public class SimpleGets {
    IProfesorService profesorService;
    IElevService elevService;
    public SimpleGets(IProfesorService profesorService, IElevService elevService) {
        this.elevService = elevService;
        this.profesorService = profesorService;
    }
    @GetMapping(path = "/hello")
    public String firstMethod() {
        return "Hello friend, this is my first project!";
    }
    @GetMapping(path = "/getAllPeople")
    public List<Elev> getAll() {
        List<Elev> lst = elevService.returnAll();
        System.out.println(lst.size());
        return lst;
    }
    @GetMapping(path = "/getAllPeopleT")
    public List<Profesor> getAllT() {
        return profesorService.returnAll();
    }
    @GetMapping(path = "/getAllAdmin")
    public List<Profesor> getAllA() {
        return profesorService.returnAll().stream()
                .filter(a -> a.getRoles().stream().filter(c -> c
                        .getName().equals(ERole.ROLE_ADMIN)).toList().size() != 0).toList();
    }
}
