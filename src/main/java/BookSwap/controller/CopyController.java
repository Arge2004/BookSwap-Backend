package BookSwap.controller;

import BookSwap.model.entity.Copy;
import BookSwap.service.ICopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CopyController {

    @Autowired
    private ICopy copyService;

    @PostMapping(path = "copy")
    public Copy create(@RequestBody Copy copy) {
        return copyService.save(copy);
    }

    @PutMapping(path = "copy")
    public Copy update(@RequestBody Copy copy) {
        return copyService.save(copy);
    }

    @DeleteMapping(path = "copy/{id}")
    public void delete(@PathVariable Integer id) {
        copyService.delete(copyService.findById(id));
    }

    @GetMapping(path = "copy/{id}")
    public Copy findById(@PathVariable Integer id) {
        return copyService.findById(id);
    }

    @GetMapping(path = "copies")
    public List<Copy> findAll(){
        return copyService.findAll();
    }

    @GetMapping(path = "copies/book/{id_book}")
    public List<Copy> findByBook(@PathVariable Integer id_book){
        return copyService.findByBook(id_book);
    }

    @GetMapping(path = "copies/user/{id_user}")
    public List<Copy> findByUser(@PathVariable String id_user){
        return copyService.findByUser(id_user);
    }

}
