package BookSwap.model.dao;

import BookSwap.model.entity.Exchange;
import BookSwap.model.entity.Usage;
import org.springframework.data.repository.CrudRepository;

public interface ExchangeDao extends CrudRepository<Exchange, Integer> {


}
