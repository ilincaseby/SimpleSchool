package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.web.bind.annotation.*;
import ro.schooldata.SimpleSchool.Classes.Elev;
import ro.schooldata.SimpleSchool.Services.IElevService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/elevi")
public class ElevController {
    private final IElevService elevService;

    public ElevController(IElevService elevService) {
        this.elevService = elevService;
    }

    @GetMapping
    public Elev getADefaultElev() {
        return elevService.getADefaultElev();
    }
    @GetMapping(path = "/all")
    public List<Elev> returnAll() {
        return elevService.returnAll();
    }
    @PostMapping(path = "/add")
    public void createElev(@RequestBody Elev elev) {
        elevService.createElev(elev);
    }
    @PutMapping(path = "{id}")
    public void updateElev(@PathVariable Long id, @RequestBody Elev elev) {
        elevService.updateElev(id, elev);
    }
    @DeleteMapping(path = "{id}")
    public void deleteElev(@PathVariable Long id) {
        elevService.deleteElev(id);
    }
    @GetMapping(path = "{id}")
    public Elev getElevById(@PathVariable Long id) {
        return elevService.getElevById(id);
    }

}
