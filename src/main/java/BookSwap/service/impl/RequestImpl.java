package BookSwap.service.impl;

import BookSwap.emailService.EmailService;
import BookSwap.model.dao.CopyDao;
import BookSwap.model.dao.RequestDao;
import BookSwap.model.dao.StatusDao;
import BookSwap.model.dao.UserDao;
import BookSwap.model.entity.Copy;
import BookSwap.model.entity.Request;
import BookSwap.model.entity.Status;
import BookSwap.model.entity.User;
import BookSwap.service.IRequest;
import io.swagger.v3.core.util.ReflectionUtils;
import jakarta.mail.MessagingException;
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
    @Autowired
    private EmailService emailService;
    @Autowired
    private StatusDao statusDao;
    @Autowired
    private CopyDao copyDao;

    @Transactional
    public Request save(Request request) {
        Request savedRequest = requestDao.save(request);

        System.out.println("Request saved: " + savedRequest.getId());



        // Obtener info del usuario que hizo la solicitud

        Copy offeredCopy = copyDao.findById(request.getOfferedCopiesList().get(0).getId())
                .orElseThrow(() -> new IllegalArgumentException("Copia ofrecida no encontrada"));

        User userOffered = userDao.findById(offeredCopy.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Obtener info del usuario al que se le hizo la solicitud

        Copy requestedCopy = copyDao.findById(request.getRequestedCopiesList().get(0).getId())
                .orElseThrow(() -> new IllegalArgumentException("Copia solicitada no encontrada"));

        User userRequested = userDao.findById(requestedCopy.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Enviar correo de notificación
        try{
            emailService.sendExchangePendingNotification(userRequested.getEmail(), userOffered.getUsername());
        }
        catch (MessagingException e){
            System.out.println("Error sending email: " + e.getMessage());
        }

        return savedRequest;
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
            // Agregar solicitudes de copias ofrecidas y solicitada
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

                            // Buscar el status en la base de datos
                            Status newStatus = statusDao.findById(statusId)
                                    .orElseThrow(() -> new IllegalArgumentException("Status no encontrado con ID: " + statusId));

                            // Acción si el status es 3
                            if (newStatus.getId() == 3) {
                                // Obtener info del usuario que hizo la solicitud
                                String requesterEmail = request.getOfferedCopiesList().get(0).getUser().getEmail();

                                // Obtener info del usuario al que se le hizo la solicitud
                                String userName = request.getRequestedCopiesList().get(0).getUser().getUsername();

                                // Enviar correo de notificación
                                try {
                                    emailService.sendExchangeRejectedNotification(requesterEmail, userName);
                                } catch (MessagingException e) {
                                    System.out.println("Error sending email: " + e.getMessage());
                                }
                            }

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
