package ro.schooldata.SimpleSchool.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleGets {
    @GetMapping(path = "/hello")
    public String firstMethod() {
        return "Hello friend, this is my first project!";
    }
}
