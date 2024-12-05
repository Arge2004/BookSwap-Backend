package BookSwap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.print.attribute.standard.Copies;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_status")
    @ManyToOne
    private Status status;

    @Column(name = "created_at")
    private Date created_at;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "offered_copies",
            joinColumns = @JoinColumn(name = "id_request", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_copy", referencedColumnName = "id")
    )
    private List<Copy> offeredCopiesList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "requested_copies",
            joinColumns = @JoinColumn(name = "id_request", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_copy", referencedColumnName = "id")
    )
    private List<Copy> requestedCopiesList;
}
