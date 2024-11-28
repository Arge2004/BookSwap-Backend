package BookSwap.controller;

import BookSwap.model.entity.Notification;
import BookSwap.model.entity.Notification_template;
import BookSwap.service.INotification;
import BookSwap.service.INotification_template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class Notification_templateController {

    @Autowired
    private INotification_template notification_templateService;

    @GetMapping(path = "notification_template/{id}")
    public Notification_template findById(@PathVariable Integer id) {
        return notification_templateService.findById(id);
    }

    @GetMapping(path = "notification_templates")
    public List<Notification_template> findAll(){
        return notification_templateService.findAll();
    }

}
