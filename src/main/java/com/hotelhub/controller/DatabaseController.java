package com.hotelhub.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.hotelhub.model.Hotel;
import com.hotelhub.model.Room;
import com.hotelhub.model.User;
import org.springframework.web.bind.annotation.RestController;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class DatabaseController {

    private static Firestore database = null;

    public static Firestore getDatabase()
            throws IOException, ExecutionException, InterruptedException {
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

    public static boolean searchUser(Firestore db, String email)
            throws ExecutionException, InterruptedException {
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

    public static boolean checkCredentials(Firestore db, String email, String password)
            throws ExecutionException, InterruptedException {
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

    public static User getUser(Firestore db, String email, String password)
            throws ExecutionException, InterruptedException {
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
                if (document.getBoolean("isAdmin") != null) {
                    doc_is_admin = Objects.requireNonNull(document.getBoolean("isAdmin"));
                }

                int doc_hotel_admin = 0;
                if (document.getLong("hotel_admin") != null) {
                    doc_hotel_admin = Objects.requireNonNull(document.getLong("hotel_admin")).intValue();
                }

                if (doc_password.equals(password)) {
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
        data.put("isAdmin", newUser.isAdmin());
        data.put("user_id", docRef.getId());

        if (newUser.isAdmin()) {
            data.put("hotel_admin", newUser.getHotel_admin());
        }

        docRef.set(data);
    }

    public static boolean hasHotel(Firestore db, String hotel_id)
            throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> query = db.collection("hotels").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id))
                return true;
        }

        return false;
    }

    public static List<Room> getAllRooms(Firestore db, String hotel_id)
            throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> query = db.collection("rooms").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Room> rooms = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (hotel_id.equals(doc_hotel_id)) {
                String room_id = document.getString("room_id");
                assert room_id != null;

                int price = 0;
                if (document.getLong("price") != null) {
                    price = Objects.requireNonNull(document.getLong("price")).intValue();
                }

                String name = document.getString("name");
                assert name != null;

                int no_of_people = 0;
                if (document.getLong("no_of_people") != null) {
                    no_of_people = Objects.requireNonNull(document.getLong("no_of_people")).intValue();
                }

                String type = document.getString("type");
                assert type != null;

                rooms.add(new Room(room_id, price, name, hotel_id, no_of_people, type));
            }
        }

        return rooms;
    }

    public static List<Hotel> getAllHotels(Firestore db)
            throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> query = db.collection("hotels").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Hotel> hotels = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String hotel_id = document.getString("hotel_id");
            assert hotel_id != null;

            String location = document.getString("location");
            assert location != null;

            String name = document.getString("name");
            assert name != null;

            String photo = document.getString("photo");
            assert photo != null;

            hotels.add(new Hotel(hotel_id, location, name, photo));
        }

        return hotels;
    }

    public static boolean searchHotel(Firestore db, Hotel hotel) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("hotels").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            String doc_name = document.getString("name");
            assert doc_name != null;
            if (doc_name.equals(hotel.getName())) {
                String doc_loc = document.getString("location");
                assert doc_loc != null;
                if (doc_loc.equals(hotel.getLocation()))
                    return true;
            }
        }

        return false;
    }

    public static void addHotel(Firestore db, Hotel newHotel, String user_id) {
        DocumentReference docRef = db.collection("hotels").document();
        Map<String, Object> data = new HashMap<>();
        data.put("name", newHotel.getName());
        data.put("location", newHotel.getLocation());
        data.put("photo", newHotel.getPhoto());
        String hotel_id = docRef.getId();
        data.put("hotel_id", hotel_id);

        docRef.set(data);

        DocumentReference docRefAdmin = db.collection("users").document(user_id);
        docRefAdmin.update("hotel_admin", hotel_id);
    }

    public static void deleteHotel(Firestore db, String hotel_id) {
        ApiFuture<WriteResult> writeResult = db.collection("hotels").document(hotel_id).delete();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Firestore database = getDatabase();
    }


}
