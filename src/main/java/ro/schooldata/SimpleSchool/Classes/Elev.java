package ro.schooldata.SimpleSchool.Classes;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "elevi")
public class Elev implements User{
    @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nume_elev")
    private String lastName;

    @Column(name = "prenume_elev")
    private String firstName;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getEmail() {
        return this.getEmail_p();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "numar_de_telefon")
    private String tel;

    @Column(name = "email_parinte")
    @Email(message = "Invalid email address")
    private String email_p;

    @Column(name = "CNP")
    private String cnp;

    @Column(name = "cod_matricol")
    private String cod;

    @Column(name = "clasa")
    private String clasa;

    @OneToMany(mappedBy = "elev", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Materie> materii = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profesor-elev", joinColumns = @JoinColumn(name = "elev_fk"), inverseJoinColumns = @JoinColumn(name = "profesor_fk"))
    private List<Profesor> profesori = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "elev_roles",
            joinColumns = @JoinColumn(name = "elev_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Profesor> getProfesori() {
        return profesori;
    }

    public void setProfesori(List<Profesor> profesori) {
        this.profesori = profesori;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getClasa() {
        return clasa;
    }

    public void setClasa(String clasa) {
        this.clasa = clasa;
    }

    public Set<Materie> getMaterii() {
        return materii;
    }

    public void setMaterii(Set<Materie> materii) {
        this.materii = materii;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail_p() {
        return email_p;
    }

    public void setEmail_p(String email_p) {
        this.email_p = email_p;
    }

    public String getCNP() {
        return cnp;
    }

    public void setCNP(String cnp) {
        this.cnp = cnp;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }


    public Elev() {
    }

    public Elev(String lastName, String firstName, String tel,
                String email_p, String cnp, String cod, String userName,
                String password) {
        this.userName = userName;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.tel = tel;
        this.email_p = email_p;
        this.cnp = cnp;
        this.cod = cod;
    }
}















