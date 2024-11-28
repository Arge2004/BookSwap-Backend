package BookSwap.controller;
import BookSwap.fireBase.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @PostMapping("/upload-files")
    public ResponseEntity<?> uploadFiles(@RequestParam("demo[]") List<MultipartFile> files) {
        try {
            List<String> urls = firebaseStorageService.uploadFiles(files);

            Map<String, Object> response = new HashMap<>();
            response.put("urls", urls);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al subir archivos");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(error);
        }
    }
}