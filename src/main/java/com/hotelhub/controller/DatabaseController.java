package com.hotelhub.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.RestController;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class DatabaseController {
    private static int no_users;
    // Use a service account
    private static Firestore initDBConnection() throws IOException {
        InputStream serviceAccount = new FileInputStream("key.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        return FirestoreClient.getFirestore();
    }

    public static void read(Firestore db) throws ExecutionException, InterruptedException {
        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        int i = 0;
        for (QueryDocumentSnapshot document : documents) {
            i++;
            System.out.println("User: " + document.getId());
            System.out.println("email: " + document.getString("email"));
            System.out.println("password: " + document.getString("password"));
            System.out.println("is_admin: " + document.getBoolean("is_admin"));
            if (document.contains("hotel_admin")) {
                System.out.println("hotel_admin: " + document.getLong("hotel_admin"));
            }
        }
        no_users = i;
    }

    public static void addUser(Firestore db, String email, String password, boolean is_admin, int hotel_admin)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(Integer.toString(no_users + 1));
        // Add document data with an additional field ("middle")
        Map<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("is_admin", is_admin);
        if (is_admin) {
            data.put("hotel_admin", hotel_admin);
        }
        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Firestore db = initDBConnection();
        read(db);
        //addUser(db, "test3@email.com", "test3", false, 0);
    }
}
