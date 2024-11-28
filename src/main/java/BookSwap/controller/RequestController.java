package BookSwap.controller;

import BookSwap.model.entity.Copy;
import BookSwap.model.entity.Notification;
import BookSwap.model.entity.Request;
import BookSwap.model.entity.User;
import BookSwap.service.INotification;
import BookSwap.service.IRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class RequestController {

    @Autowired
    private IRequest requestService;

    @PostMapping(path = "request")
    public Request create(@RequestBody Request request) { return requestService.save(request); }

    @PutMapping(path = "request")
    public Request update(@RequestBody Request request) { return requestService.save(request); }

    @DeleteMapping(path = "request/{id}")
    public void delete(@PathVariable Integer id) {
        requestService.delete(requestService.findById(id));
    }

    @GetMapping(path = "request/{id}")
    public Request findById(@PathVariable Integer id) {
        return requestService.findById(id);
    }

    @GetMapping(path = "requests")
    public List<Request> findAll(){
        return requestService.findAll();
    }

    @GetMapping(path = "requests/user/{id}")
    public List<Request> RequestsUser(@PathVariable String id) {
        return requestService.RequestsUser(id);
    }

    @GetMapping(path = "requests/validate-copy/{id}")
    public ResponseEntity<Boolean> validateCopyInRequest(@PathVariable Integer id) {
        Copy copy = new Copy();
        copy.setId(id); // Crear una instancia de Copy con el ID proporcionado
        boolean isInRequest = requestService.isCopyInActiveRequest(copy);
        return ResponseEntity.ok(isInRequest);
    }

    @PatchMapping(path = "request/{id}")
    public ResponseEntity<Request> partialUpdate(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Request updatedRequest = requestService.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedRequest);
    }

}
