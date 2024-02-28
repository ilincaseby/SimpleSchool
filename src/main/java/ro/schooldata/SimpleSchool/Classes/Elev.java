package ro.schooldata.SimpleSchool.Classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "elevi")
@Getter
@Setter
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

    @Column(name = "email_parinte")
    @Email(message = "Invalid email address")
    private String email_p;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getEmail() {
        return this.getEmail_p();
    }

    @Column(name = "numar_de_telefon")
    private String tel;



    @Column(name = "CNP")
    private String cnp;

    @Column(name = "cod_matricol")
    private String cod;

    @Column(name = "clasa")
    private String clasa;

    @ManyToMany
    @JoinTable(name = "materie-elev", joinColumns = @JoinColumn(name = "elev_fk"), inverseJoinColumns = @JoinColumn(name = "materie_fk"))
    private Set<Materie> materii = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profesor-elev", joinColumns = @JoinColumn(name = "elev_fk"), inverseJoinColumns = @JoinColumn(name = "profesor_fk"))
    private List<Profesor> profesori = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "elev_roles",
            joinColumns = @JoinColumn(name = "elev_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "elev", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElevMaterieT> elevMaterieTList;

    public String getCNP() {
        return cnp;
    }

    public void setCNP(String cnp) {
        this.cnp = cnp;
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















