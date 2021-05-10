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
import java.util.concurrent.ExecutionException;

@RestController
public class DatabaseController {
    private static Firestore database = null;
    public static Firestore getDatabase() throws IOException {
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
            if (document.getString("email").equals(email)) {
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
            if (document.getString("email").equals(email)) {
                if (document.getString("password").equals(password)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void read(Firestore db) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println("User: " + document.getId());
            System.out.println("email: " + document.getString("email"));
            System.out.println("password: " + document.getString("password"));
            System.out.println("is_admin: " + document.getBoolean("is_admin"));
            if (document.contains("hotel_admin")) {
                System.out.println("hotel_admin: " + document.getLong("hotel_admin"));
            }
        }
    }

    public static void addUser(Firestore db, User newUser)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(Integer.toString(newUser.getId_user()));
        Map<String, Object> data = new HashMap<>();
        data.put("name", newUser.getName());
        data.put("email", newUser.getEmail());
        data.put("password", newUser.getPassword());
        data.put("is_admin", newUser.isAdmin());
        if (newUser.isAdmin()) {
            data.put("hotel_admin", newUser.getHotel_admin());
        }
        ApiFuture<WriteResult> result = docRef.set(data);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    public static void main(String[] args) {

    }
}
