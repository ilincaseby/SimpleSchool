package ro.schooldata.SimpleSchool.Classes;

import jakarta.persistence.*;
import ro.schooldata.SimpleSchool.Classes.Elev;

import java.util.List;

@Entity
@Table(name = "materii")
public class Materie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nume_materie")
    private String name;

    @ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tabela_note", joinColumns = @JoinColumn(name = "materie_id"))
    @Column(name = "note")
    private List<Integer> note;


    @ManyToOne
    @JoinColumn(name = "elev_id") // specificați numele coloanei care reprezintă legătura către Elev
    private Elev elev;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getNote() {
        return note;
    }

    public void setNote(List<Integer> note) {
        this.note = note;
    }

    public Elev getElev() {
        return elev;
    }

    public void setElev(Elev elev) {
        this.elev = elev;
    }

    public Materie() {
    }

    public Materie(String name, List<Integer> note, Elev elev) {
        this.name = name;
        this.note = note;
        this.elev = elev;
    }
}
