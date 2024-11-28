package BookSwap.controller;

import BookSwap.model.entity.Editorial;
import BookSwap.model.entity.Usage;
import BookSwap.service.IEditorial;
import BookSwap.service.IUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class EditorialController {

    @Autowired
    private IEditorial editorialService;

    @GetMapping(path = "editorial/{id}")
    public Editorial findById(@PathVariable Integer id) {
        return editorialService.findById(id);
    }

    @GetMapping(path = "editorials")
    public List<Editorial> findAll(){
        return editorialService.findAll();
    }

}
