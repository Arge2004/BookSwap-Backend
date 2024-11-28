package BookSwap.controller;

import BookSwap.model.entity.City;
import BookSwap.model.entity.Usage;
import BookSwap.service.ICity;
import BookSwap.service.IUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CityController {

    @Autowired
    private ICity cityService;

    @GetMapping(path = "city/{id}")
    public City findById(@PathVariable Integer id) {
        return cityService.findById(id);
    }

    @GetMapping(path = "cities")
    public List<City> findAll(){
        return cityService.findAll();
    }

}
