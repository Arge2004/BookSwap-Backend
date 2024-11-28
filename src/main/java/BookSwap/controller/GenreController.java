package BookSwap.controller;

import BookSwap.model.entity.Genre;
import BookSwap.service.IGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class GenreController {

    @Autowired
    private IGenre genreService;

    @GetMapping(path = "genre/{id}")
    public Genre findById(@PathVariable Integer id) {
        return genreService.findById(id);
    }

    @GetMapping(path = "genres")
    public List<Genre> findAll(){
        return genreService.findAll();
    }

    @GetMapping(path = "genresWithBooks")
    public List<Genre> findGenresWithBooks(){
        return genreService.findGenresWithBooks();
    }

}
