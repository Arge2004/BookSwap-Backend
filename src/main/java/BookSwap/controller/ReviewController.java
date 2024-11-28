package BookSwap.controller;

import BookSwap.model.entity.Review;
import BookSwap.model.entity.Status;
import BookSwap.service.IReview;
import BookSwap.service.IStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ReviewController {

    @Autowired
    private IReview reviewService;

    @PostMapping(path = "review")
    public Review create(@RequestBody Review review) { return reviewService.save(review); }

    @PutMapping(path = "review")
    public Review update(@RequestBody Review review) { return reviewService.save(review); }

    @DeleteMapping(path = "review/{id}")
    public void delete(@PathVariable Integer id) {
        reviewService.delete(reviewService.findById(id));
    }

    @GetMapping(path = "review/{id}")
    public Review findById(@PathVariable Integer id) {
        return reviewService.findById(id);
    }

    @GetMapping(path = "reviews")
    public List<Review> findAll(){
        return reviewService.findAll();
    }

}
