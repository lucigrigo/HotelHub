package com.hotelhub.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.hotelhub.model.User;
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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
public class DatabaseController {
    private static Firestore database = null;

    public static Firestore getDatabase() throws IOException, ExecutionException, InterruptedException {
        if (database != null) {
            return database;
        }
        InputStream serviceAccount = new FileInputStream("key.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        database = FirestoreClient.getFirestore();
        return database;
    }

    public static boolean searchUser(Firestore db, String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            String doc_email = document.getString("email");
            assert doc_email != null;
            if (doc_email.equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkCredentials(Firestore db, String email, String password) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            String doc_email = document.getString("email");
            assert doc_email != null;
            if (doc_email.equals(email)) {
                String doc_password = document.getString("password");
                assert doc_password != null;
                return doc_password.equals(password);
            }
        }
        return false;
    }

    public static User getUser(Firestore db, String email, String password) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            String doc_email = document.getString("email");
            assert doc_email != null;
            if (doc_email.equals(email)) {
                String doc_password = document.getString("password");
                assert doc_password != null;

                boolean doc_is_admin = false;
                if (document.getBoolean("is_admin") != null) {
                    doc_is_admin = Objects.requireNonNull(document.getBoolean("is_admin"));
                }

                int doc_hotel_admin = 0;
                if (document.getLong("hotel_admin") != null) {
                    doc_hotel_admin = Objects.requireNonNull(document.getLong("hotel_admin")).intValue();
                }

                if(doc_password.equals(password)) {
                    return new User(document.getId(), document.getString("name"),
                            doc_email, doc_password, doc_is_admin, doc_hotel_admin);
                }
            }
        }
        return null;
    }

    public static void addUser(Firestore db, User newUser)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document();
        Map<String, Object> data = new HashMap<>();
        data.put("name", newUser.getName());
        data.put("email", newUser.getEmail());
        data.put("password", newUser.getPassword());
        data.put("is_admin", newUser.isAdmin());
        data.put("user_id", docRef.getId());
        if (newUser.isAdmin()) {
            data.put("hotel_admin", newUser.getHotel_admin());
        }
        docRef.set(data);
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Firestore database = getDatabase();
    }
}
