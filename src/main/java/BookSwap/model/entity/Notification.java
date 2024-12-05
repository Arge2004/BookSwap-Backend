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
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_request")
    @ManyToOne
    private Request request;

    @JoinColumn(name = "id_user")
    @ManyToOne
    private User user;

    @JoinColumn(name = "id_notification_template")
    @ManyToOne
    private Notification_template notification_template;

    @Column(name = "read_status")
    private String read_status;

    @Column(name = "created_at")
    private Date created_at;

}
