package BookSwap.service;

import BookSwap.model.entity.Copy;
import BookSwap.model.entity.Request;
import BookSwap.model.entity.Usage;
import BookSwap.model.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface IRequest {

    Request save(Request request);

    Request findById(Integer id);

    void delete(Request request);

    List<Request> findAll();

    List<Request> RequestsUser(String id);

    public boolean isCopyInActiveRequest(Copy copy);

    Request partialUpdate(Integer id, Map<String, Object> updates);
}
