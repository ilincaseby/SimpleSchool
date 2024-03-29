package ro.schooldata.SimpleSchool.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.schooldata.SimpleSchool.Classes.Elev;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "materii")
public class Materie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nume_materie")
    private String name;

    @ManyToMany(mappedBy = "materii")
    @JsonIgnore
    private List<Elev> elevi;

    @OneToMany(mappedBy = "materie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ElevMaterieT> elevMaterieTList;

    public Materie(List<Elev> elevi, List<ElevMaterieT> elevMaterieTList) {
        this.elevi = elevi;
        this.elevMaterieTList = elevMaterieTList;
    }
}
