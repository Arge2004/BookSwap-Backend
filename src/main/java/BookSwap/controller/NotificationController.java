package BookSwap.controller;

import BookSwap.model.entity.Notification;
import BookSwap.model.entity.User;
import BookSwap.service.INotification;
import BookSwap.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class NotificationController {

    @Autowired
    private INotification notificationService;

    @PostMapping(path = "notification")
    public Notification create(@RequestBody Notification notification) { return notificationService.save(notification); }

    @PutMapping(path = "notification")
    public Notification update(@RequestBody Notification notification) { return notificationService.save(notification); }

    @DeleteMapping(path = "notification/{id}")
    public void delete(@PathVariable Integer id) {
        notificationService.delete(notificationService.findById(id));
    }

    @GetMapping(path = "notification/{id}")
    public Notification findById(@PathVariable Integer id) {
        return notificationService.findById(id);
    }

    @GetMapping(path = "notifications")
    public List<Notification> findAll(){
        return notificationService.findAll();
    }

}
