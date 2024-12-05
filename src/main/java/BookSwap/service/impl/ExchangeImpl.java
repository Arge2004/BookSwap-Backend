package BookSwap.service.impl;

import BookSwap.model.dao.ExchangeDao;
import BookSwap.model.dao.UsageDao;
import BookSwap.model.entity.Exchange;
import BookSwap.model.entity.Usage;
import BookSwap.service.IExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeImpl implements IExchange {

    @Autowired
    private ExchangeDao exchangeDao;

    @Transactional
    public Exchange save(Exchange exchange) {
        return exchangeDao.save(exchange);
    }

    @Transactional(readOnly = true)
    public Exchange findById(Integer id) {
        return exchangeDao.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Exchange exchange) {
        exchangeDao.delete(exchange);
    }

    @Transactional
    public List<Exchange> findAll() {
        Iterable<Exchange> exchangesIterable = exchangeDao.findAll();
        List<Exchange> exchangesList = new ArrayList<>();
        exchangesIterable.forEach(exchangesList::add);
        return exchangesList;
    }

    @Transactional
    public Exchange updateExchangePartial(Integer id, Map<String, Object> updates) {
        Exchange exchange = exchangeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exchange not found"));

        // Actualizar solo los campos proporcionados
        updates.forEach((key, value) -> {
            switch (key) {
                case "requester_confirm":
                    exchange.setRequester_confirm((Boolean) value);
                    break;
                case "askedFor_confirm":
                    exchange.setAskedFor_confirm((Boolean) value);
                    break;
                default:
                    throw new IllegalArgumentException("Field " + key + " not recognized");
            }
        });

        return exchangeDao.save(exchange);
    }
}
