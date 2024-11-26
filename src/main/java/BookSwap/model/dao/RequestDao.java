package BookSwap.model.dao;

import BookSwap.model.entity.Copy;
import BookSwap.model.entity.Request;
import BookSwap.model.entity.Usage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestDao extends CrudRepository<Request, Integer> {

    List<Request> findByOfferedCopiesListContains(Copy copy);
    List<Request> findByRequestedCopiesListContains(Copy copy);
    boolean existsByOfferedCopiesList_IdOrRequestedCopiesList_IdAndStatus_Active(Integer offeredCopyId, Integer requestedCopyId, boolean active);
}
