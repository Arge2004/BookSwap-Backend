package BookSwap.service.impl;

import BookSwap.model.dao.RequestDao;
import BookSwap.model.dao.UserDao;
import BookSwap.model.entity.Copy;
import BookSwap.model.entity.Request;
import BookSwap.model.entity.Status;
import BookSwap.model.entity.User;
import BookSwap.service.IRequest;
import io.swagger.v3.core.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RequestImpl implements IRequest {

    @Autowired
    private RequestDao requestDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public Request save(Request request) {
        return requestDao.save(request);
    }

    @Transactional(readOnly = true)
    public Request findById(Integer id) {
        return requestDao.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Request request) {
        requestDao.delete(request);
    }

    @Transactional
    public List<Request> findAll() {
        Iterable<Request> requestsIterable = requestDao.findAll();
        List<Request> requestsList = new ArrayList<>();
        requestsIterable.forEach(requestsList::add);
        return requestsList;
    }

    @Override
    public List<Request> RequestsUser(String id) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Obtener las copias asociadas al usuario
        List<Copy> userCopies = user.getCopiesList();

        // Buscar las solicitudes relacionadas con esas copias
        List<Request> requests = new ArrayList<>();
        for (Copy copy : userCopies) {
            // Agregar solicitudes de copias ofrecidas y solicitadas
            requests.addAll(requestDao.findByOfferedCopiesListContains(copy));
            requests.addAll(requestDao.findByRequestedCopiesListContains(copy));
        }

        // Eliminar duplicados si la misma solicitud aparece dos veces
        return requests.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public boolean isCopyInActiveRequest(Copy copy) {
        // Verificar si la copia está en las solicitudes ofrecidas o solicitadas
        return requestDao.findByOfferedCopiesListContains(copy).size() > 0 ||
                requestDao.findByRequestedCopiesListContains(copy).size() > 0;
    }

    @Override
    public Request partialUpdate(Integer id, Map<String, Object> updates) {
        Request request = requestDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Request no encontrado con ID: " + id));

        // Validar y actualizar campos específicos
        updates.forEach((key, value) -> {
            switch (key) {
                case "status":
                    if (value instanceof Map) {
                        Map<String, Object> statusMap = (Map<String, Object>) value;
                        if (statusMap.containsKey("id")) {
                            Integer statusId = (Integer) statusMap.get("id");
                            Status newStatus = new Status();
                            newStatus.setId(statusId);
                            request.setStatus(newStatus);
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Campo no soportado para actualización parcial: " + key);
            }
        });

        return requestDao.save(request);
    }


}
