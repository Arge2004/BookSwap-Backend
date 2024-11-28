package BookSwap.controller;

import BookSwap.model.entity.Notification;
import BookSwap.model.entity.Status;
import BookSwap.service.INotification;
import BookSwap.service.IStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StatusController {

    @Autowired
    private IStatus statusService;

    @PostMapping(path = "status")
    public Status create(@RequestBody Status status) { return statusService.save(status); }

    @PutMapping(path = "status")
    public Status update(@RequestBody Status status) { return statusService.save(status); }

    @DeleteMapping(path = "status/{id}")
    public void delete(@PathVariable Integer id) {
        statusService.delete(statusService.findById(id));
    }

    @GetMapping(path = "status/{id}")
    public Status findById(@PathVariable Integer id) {
        return statusService.findById(id);
    }

    @GetMapping(path = "statuses")
    public List<Status> findAll(){
        return statusService.findAll();
    }

}
