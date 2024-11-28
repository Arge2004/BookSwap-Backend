package BookSwap.controller;

import BookSwap.model.entity.Usage;
import BookSwap.model.entity.User;
import BookSwap.service.IUsage;
import BookSwap.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UsageController {

    @Autowired
    private IUsage usageService;

    @GetMapping(path = "usage/{id}")
    public Usage findById(@PathVariable Integer id) {
        return usageService.findById(id);
    }

    @GetMapping(path = "usages")
    public List<Usage> findAll(){
        return usageService.findAll();
    }

}
