package BookSwap.controller;

import BookSwap.model.entity.Language;
import BookSwap.model.entity.Notification;
import BookSwap.service.ILanguage;
import BookSwap.service.INotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class LanguageController {

    @Autowired
    private ILanguage languageService;

    @PostMapping(path = "language")
    public Language create(@RequestBody Language language) { return languageService.save(language); }

    @PutMapping(path = "language")
    public Language update(@RequestBody Language language) { return languageService.save(language); }

    @DeleteMapping(path = "language/{id}")
    public void delete(@PathVariable Integer id) {
        languageService.delete(languageService.findById(id));
    }

    @GetMapping(path = "language/{id}")
    public Language findById(@PathVariable Integer id) {
        return languageService.findById(id);
    }

    @GetMapping(path = "languages")
    public List<Language> findAll(){
        return languageService.findAll();
    }

}
