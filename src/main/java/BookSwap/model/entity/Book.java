package BookSwap.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "id_author")
    @ManyToOne
    private Author author;

    @JoinColumn(name = "id_language")
    @ManyToOne
    private Language language;

    @Column(name = "title")
    private String title;

    @Column(name = "published_year")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate published_year;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture")
    private String picture;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "genresxbook",
            joinColumns = @JoinColumn(name = "id_book", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_genre", referencedColumnName = "id")
    )
    private List<Genre> genresList;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Copy> copiesList;

}
