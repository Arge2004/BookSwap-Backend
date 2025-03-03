package BookSwap.service.impl;

import BookSwap.model.dao.GenreDao;
import BookSwap.model.entity.Genre;
import BookSwap.service.IGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreImpl implements IGenre {

    @Autowired
    private GenreDao genreDao;

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Integer id) {
        return genreDao.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public List<Genre> findAll() {
        Iterable<Genre> genresIterable = genreDao.findAll();
        List<Genre> genresList = new ArrayList<>();
        genresIterable.forEach(genresList::add);
        return genresList;
    }

    @Override
    @Transactional
    public List<Genre> findGenresWithBooks() {
        Iterable<Genre> genresIterable = genreDao.findAll();
        List<Genre> genresWithBooks = new ArrayList<>();
        for (Genre genre : genresIterable) {
            if (genre.getBooksList() != null && !genre.getBooksList().isEmpty()) {
                genresWithBooks.add(genre);
            }
        }
        return genresWithBooks;
    }

}
