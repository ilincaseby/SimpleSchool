package ro.schooldata.SimpleSchool.Classes;

import java.util.Set;

public interface User {
    Long getId();
    String getUserName();
    String getEmail();
    String getPassword();
    Set<Role> getRoles();
}
