package com.demosecurity.demosecurity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
public class DemosecurityApplication {

	public static void main(String[] args) throws IOException {
		File file = new ClassPathResource("serviceKey.json").getFile();
		// ClassLoader classLoader = DemosecurityApplication.class.getClassLoader();

		// File file = new
		// File(Objects.requireNonNull(classLoader.getResource("serviceKey.json")).getFile());
		FileInputStream serviceAccount = new FileInputStream(
				file.getAbsolutePath());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		// FirebaseApp.initializeApp(options);
		if (FirebaseApp.getApps().isEmpty()) { // <--- check with this line
			FirebaseApp.initializeApp(options);
		}

		SpringApplication.run(DemosecurityApplication.class, args);
	}

}
