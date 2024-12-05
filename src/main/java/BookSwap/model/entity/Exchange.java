package BookSwap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "exchanges")
public class Exchange {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_request")
    @OneToOne
    private Request request;

    @Column(name = "requester_confirm", columnDefinition = "boolean")
    private boolean requester_confirm;

    @Column(name = "asked_for_confirm", columnDefinition = "boolean")
    private boolean askedFor_confirm;

    @JoinColumn(name = "id_status")
    @ManyToOne
    private Status status;

    @Column(name = "exchange_date")
    private Date exchange_date;

    @OneToMany(mappedBy = "exchange", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviewsList;

}
