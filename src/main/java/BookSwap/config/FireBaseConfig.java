package BookSwap.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class FireBaseConfig {

    @Bean
    public Storage storage() throws IOException {
        FirebaseOptions options;
        String firebaseCredentials = System.getenv("FIREBASE_CREDENTIALS");

        if (firebaseCredentials != null) {
            // Configuración para Heroku usando variable de entorno
            InputStream serviceAccount = new ByteArrayInputStream(firebaseCredentials.getBytes());
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("bookswap-8eb14.firebasestorage.app")
                    .build();
        } else {
            // Configuración local usando archivo
            Resource resource = new ClassPathResource("firebase/bookswap-8eb14-firebase-adminsdk-litgf-6edd026b55.json");
            InputStream serviceAccount = resource.getInputStream();
            options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("bookswap-8eb14.firebasestorage.app")
                    .build();
        }

        // Inicializar Firebase solo si no está inicializado
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        // Retornar el bean de Storage
        return StorageClient.getInstance().bucket().getStorage();
    }
}