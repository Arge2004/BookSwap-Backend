package BookSwap.service;

import BookSwap.model.entity.Exchange;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;

public interface IExchange {

    Exchange save(Exchange exchange) throws MessagingException;

    Exchange findById(Integer id);

    void delete(Exchange exchange);

    List<Exchange> findAll();

    Exchange updateExchangePartial(Integer id, Map<String, Object> updates);
}
