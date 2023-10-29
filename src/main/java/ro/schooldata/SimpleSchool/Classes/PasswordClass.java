package ro.schooldata.SimpleSchool.Classes;

public class PasswordClass {
    private String passwordOld;
    private String passwordNew;

    public PasswordClass(String passwordOld, String passwordNew) {
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }
}
