package BookSwap.controller;

import BookSwap.model.entity.Copy_image;
import BookSwap.service.ICopy_image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class Copy_imageController {

    @Autowired
    private ICopy_image copy_imageService;

    @PostMapping(path = "copy_image")
    public Copy_image create(@RequestBody Copy_image copy_image) {
        return copy_imageService.save(copy_image);
    }

    @PutMapping(path = "copy_image")
    public Copy_image update(@RequestBody Copy_image copy_image) {
        return copy_imageService.save(copy_image);
    }

    @DeleteMapping(path = "copy_image/{id}")
    public void delete(@PathVariable Integer id) {
        copy_imageService.delete(copy_imageService.findById(id));
    }

    @GetMapping(path = "copy_image/{id}")
    public Copy_image findById(@PathVariable Integer id) {
        return copy_imageService.findById(id);
    }

    @GetMapping(path = "copy_images")
    public List<Copy_image> findAll(){
        return copy_imageService.findAll();
    }

}
