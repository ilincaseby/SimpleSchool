package ro.schooldata.SimpleSchool.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "elevmateriet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElevMaterieT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "elev_id")
    @JsonIgnore
    Elev elev;

    @ManyToOne
    @JoinColumn(name = "materie_id")
    Materie materie;

    @ElementCollection
    @CollectionTable(
            name = "note_atribuite",
            joinColumns = @JoinColumn(name = "entity_id")
    )
    @OrderColumn(name = "element_order")
    @Column(name = "note")
    private List<Integer> note = new ArrayList<>();

    public ElevMaterieT(Elev elev, Materie materie, List<Integer> note) {
        this.elev = elev;
        this.materie = materie;
        this.note = note;
    }
}
