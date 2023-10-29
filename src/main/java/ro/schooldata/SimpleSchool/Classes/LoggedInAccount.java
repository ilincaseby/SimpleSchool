package ro.schooldata.SimpleSchool.Classes;

public class LoggedInAccount {
    private static LoggedInAccount instance;
    private boolean isLoggedIn;
    private Profesor profesor;
    private boolean isAdmin;
    private LoggedInAccount(){
        isLoggedIn = false;
        profesor = null;
        isAdmin = false;
    }

    public static LoggedInAccount getInstance() {
        if (instance == null) {
            instance = new LoggedInAccount();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
