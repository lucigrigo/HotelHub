package com.hotelhub.controller;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.hotelhub.model.*;
import org.springframework.web.bind.annotation.RestController;

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

                String doc_hotel_admin = document.getString("hotel_admin");
                assert doc_hotel_admin != null;

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

    public static boolean searchHotel(Firestore db, Hotel hotel)
            throws ExecutionException, InterruptedException {
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

    public static void deleteHotel(Firestore db, String admin_id, String hotel_id)
            throws ExecutionException, InterruptedException {
        db.collection("hotels").document(hotel_id).delete();
        DocumentReference docRef = db.collection("users").document(admin_id);
        Map<String, Object> updates = new HashMap<>();
        updates.put("hotel_admin", FieldValue.delete());
        docRef.update(updates);
    }

    public static boolean checkRoomInHotel(Firestore db, Room room)
            throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> query = db.collection("rooms").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            String doc_name = document.getString("name");
            assert doc_name != null;

            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(room.getHotel_id()) && doc_name.equals(room.getName()))
                return true;
        }

        return false;
    }

    public static void addRoom(Firestore db, Room room) {
        DocumentReference docRef = db.collection("rooms").document();
        Map<String, Object> data = new HashMap<>();

        data.put("room_id", docRef.getId());
        data.put("price", room.getPrice());
        data.put("name", room.getName());
        data.put("hotel_id", room.getHotel_id());
        data.put("no_of_people", room.getNo_of_people());
        data.put("type", room.getType());

        docRef.set(data);
    }

    public static boolean hasRoom(Firestore db, String room_id)
            throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> query = db.collection("rooms").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            String doc_room_id = document.getString("room_id");
            assert doc_room_id != null;

            if (doc_room_id.equals(room_id))
                return true;
        }

        return false;
    }

    public static void deleteRoom(Firestore db, String room_id) {
        db.collection("rooms").document(room_id).delete();
    }

    public static boolean searchBooking(Firestore db, String booking_id)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("bookings").document(booking_id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        return document.exists();
    }

    public static Booking createBooking(Firestore db, String user_id, Room room, Date start_date, Date end_date) {
        DocumentReference docRef = db.collection("bookings").document();
        Map<String, Object> data = new HashMap<>();

        data.put("booking_id", docRef.getId());
        data.put("room_id", room.getRoom_id());
        data.put("hotel_id", room.getHotel_id());
        data.put("user_id", user_id);
        data.put("approved", false);
        data.put("to_be_canceled", false);
        data.put("start_date", start_date);
        data.put("end_date", end_date);

        docRef.set(data);

        return new Booking(docRef.getId(), room.getRoom_id(), room.getHotel_id(), user_id,
                false, false, start_date, end_date);
    }

    public static void clientCancelBooking(Firestore db, String booking_id)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("bookings").document(booking_id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        boolean doc_approved = Objects.requireNonNull(document.getBoolean("approved"));
        if (doc_approved) {
            docRef.update("to_be_canceled", true);
        } else {
            docRef.delete();
        }

    }

    public static boolean hasBooking(Firestore db, String booking_id)
            throws IOException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("bookings").document(booking_id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            boolean doc_approved = false;
            if (document.getBoolean("approved") != null) {
                doc_approved = Objects.requireNonNull(document.getBoolean("approved"));
            }

            return !doc_approved;
        }

        return false;
    }

    public static void approveBooking(Firestore db, String booking_id) {
        DocumentReference docRef = db.collection("bookings").document(booking_id);
        docRef.update("approved", true);
    }

    public static boolean hasApprovedBooking(Firestore db, String booking_id)
            throws IOException, ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("bookings").document(booking_id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            boolean doc_to_be_canceled = false;
            if (document.getBoolean("to_be_canceled") != null) {
                doc_to_be_canceled = Objects.requireNonNull(document.getBoolean("to_be_canceled"));
            }

            return !doc_to_be_canceled;
        }

        return false;
    }

    public static void adminCancelBooking(Firestore db, String booking_id) {
        db.collection("bookings").document(booking_id).delete();
    }

    public static List<Hotel> getLocationHotels(Firestore db, String location)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("hotels").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Hotel> hotels = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_location = document.getString("location");
            assert doc_location != null;

            if (doc_location.equals(location)) {
                String hotel_id = document.getString("hotel_id");
                assert hotel_id != null;

                String name = document.getString("name");
                assert name != null;

                String photo = document.getString("photo");
                assert photo != null;

                hotels.add(new Hotel(hotel_id, location, name, photo));
            }
        }
        return hotels;
    }

    public static List<Room> getHotelRooms(Firestore db, String hotel_id, int no_of_people, String type)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("rooms").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Room> rooms = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                int doc_no_of_people = 0;
                if (document.getLong("no_of_people") != null)
                    doc_no_of_people = Objects.requireNonNull(document.getLong("no_of_people")).intValue();

                String doc_type = document.getString("type");
                assert doc_type != null;

                String room_id = document.getString("room_id");
                assert room_id != null;

                int price = 0;
                if (document.getLong("price") != null) {
                    price = Objects.requireNonNull(document.getLong("price")).intValue();
                }

                String name = document.getString("name");
                assert name != null;

                if (no_of_people < 1 && type == null) {
                    rooms.add(new Room(room_id, price, name, hotel_id, doc_no_of_people, doc_type));
                } else if (no_of_people >= 1) {
                    if (type != null) {
                        if (type.equals(doc_type) && no_of_people == doc_no_of_people) {
                            rooms.add(new Room(room_id, price, name, hotel_id, doc_no_of_people, doc_type));
                        }
                    } else if (no_of_people == doc_no_of_people) {
                        rooms.add(new Room(room_id, price, name, hotel_id, doc_no_of_people, doc_type));
                    }
                } else {
                    if (type.equals(doc_type)) {
                        rooms.add(new Room(room_id, price, name, hotel_id, doc_no_of_people, doc_type));
                    }
                }
            }
        }
        return rooms;
    }

    public static List<Booking> getHotelConfirmedBookings(Firestore db, String hotel_id)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("bookings").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Booking> bookings = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                String booking_id = document.getString("booking_id");
                assert booking_id != null;

                String room_id = document.getString("room_id");
                assert room_id != null;

                String user_id = document.getString("user_id");
                assert user_id != null;

                boolean to_be_canceled = false;
                if (document.getBoolean("to_be_canceled") != null) {
                    to_be_canceled = Objects.requireNonNull(document.getBoolean("to_be_canceled"));
                }

                Date start_date = document.getDate("start_date");
                assert start_date != null;

                Date end_date = document.getDate("end_date");
                assert end_date != null;

                boolean approved = false;
                if (document.getBoolean("approved") != null) {
                    approved = Objects.requireNonNull(document.getBoolean("approved"));
                }

                if (approved) {
                    bookings.add(new Booking(booking_id, room_id, hotel_id,
                            user_id, true, to_be_canceled, start_date, end_date));
                }
            }
        }
        return bookings;
    }

    public static List<Booking> getHotelToDeleteBookings(Firestore db, String hotel_id)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("bookings").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Booking> bookings = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                String booking_id = document.getString("booking_id");
                assert booking_id != null;

                String room_id = document.getString("room_id");
                assert room_id != null;

                String user_id = document.getString("user_id");
                assert user_id != null;

                boolean to_be_canceled = false;
                if (document.getBoolean("to_be_canceled") != null) {
                    to_be_canceled = Objects.requireNonNull(document.getBoolean("to_be_canceled"));
                }

                Date start_date = document.getDate("start_date");
                assert start_date != null;

                Date end_date = document.getDate("end_date");
                assert end_date != null;

                boolean approved = false;
                if (document.getBoolean("approved") != null) {
                    approved = Objects.requireNonNull(document.getBoolean("approved"));
                }

                if (to_be_canceled) {
                    bookings.add(new Booking(booking_id, room_id, hotel_id,
                            user_id, approved, true, start_date, end_date));
                }
            }
        }
        return bookings;
    }

    public static List<Booking> getUserConfirmedBookings(Firestore db, String user_id)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("bookings").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Booking> bookings = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_user_id = document.getString("user_id");
            assert doc_user_id != null;

            if (doc_user_id.equals(user_id)) {
                String booking_id = document.getString("booking_id");
                assert booking_id != null;

                String room_id = document.getString("room_id");
                assert room_id != null;

                String hotel_id = document.getString("hotel_id");
                assert hotel_id != null;

                boolean to_be_canceled = false;
                if (document.getBoolean("to_be_canceled") != null) {
                    to_be_canceled = Objects.requireNonNull(document.getBoolean("to_be_canceled"));
                }

                Date start_date = document.getDate("start_date");
                assert start_date != null;

                Date end_date = document.getDate("end_date");
                assert end_date != null;

                boolean approved = false;
                if (document.getBoolean("approved") != null) {
                    approved = Objects.requireNonNull(document.getBoolean("approved"));
                }

                if (approved) {
                    bookings.add(new Booking(booking_id, room_id, hotel_id,
                            user_id, true, to_be_canceled, start_date, end_date));
                }
            }
        }
        return bookings;
    }

    public static List<Booking> getUserPendingBookings(Firestore db, String user_id)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("bookings").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Booking> bookings = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_user_id = document.getString("user_id");
            assert doc_user_id != null;

            if (doc_user_id.equals(user_id)) {
                String booking_id = document.getString("booking_id");
                assert booking_id != null;

                String room_id = document.getString("room_id");
                assert room_id != null;

                String hotel_id = document.getString("hotel_id");
                assert hotel_id != null;

                boolean to_be_canceled = false;
                if (document.getBoolean("to_be_canceled") != null) {
                    to_be_canceled = Objects.requireNonNull(document.getBoolean("to_be_canceled"));
                }

                Date start_date = document.getDate("start_date");
                assert start_date != null;

                Date end_date = document.getDate("end_date");
                assert end_date != null;

                boolean approved = false;
                if (document.getBoolean("approved") != null) {
                    approved = Objects.requireNonNull(document.getBoolean("approved"));
                }

                if (to_be_canceled) {
                    bookings.add(new Booking(booking_id, room_id, hotel_id,
                            user_id, approved, true, start_date, end_date));
                }
            }
        }
        return bookings;
    }

    public static Room getRoomById(Firestore db, String room_id)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("rooms").document(room_id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        int no_of_people = 0;
        if (document.getLong("no_of_people") != null)
            no_of_people = Objects.requireNonNull(document.getLong("no_of_people")).intValue();

        String type = document.getString("type");
        assert type != null;

        String hotel_id = document.getString("hotel_id");
        assert hotel_id != null;

        int price = 0;
        if (document.getLong("price") != null) {
            price = Objects.requireNonNull(document.getLong("price")).intValue();
        }

        String name = document.getString("name");
        assert name != null;

        return new Room(room_id, price, name, hotel_id, no_of_people, type);
    }

    private static List<String> getUnavailableRooms(Firestore db, String hotel_id, Date start_date, Date end_date)
            throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("bookings").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<String> unavailableRooms = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                Date doc_start_date = document.getDate("start_date");
                assert doc_start_date != null;

                Date doc_end_date = document.getDate("end_date");
                assert doc_end_date != null;

                String room_id = document.getString("room_id");
                assert room_id != null;

                if (doc_end_date.after(start_date) || doc_start_date.before(end_date)) {
                    unavailableRooms.add(room_id);
                }
            }
        }
        return unavailableRooms;
    }

    public static List<Room> getAvailableRooms(Firestore db, String hotel_id, Date start_date, Date end_date)
            throws ExecutionException, InterruptedException {
        List<String> unavailableRooms = getUnavailableRooms(db, hotel_id, start_date, end_date);
        ApiFuture<QuerySnapshot> query = db.collection("rooms").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Room> availableRooms = new ArrayList<>();
        Map<String, Boolean> roomTypes = new HashMap<>();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                String room_id = document.getString("room_id");
                assert room_id != null;

                if (!unavailableRooms.contains(room_id)) {
                    String type = document.getString("type");
                    assert type != null;
                    if (!roomTypes.containsKey(type)) {
                        roomTypes.put(type, true);

                        int no_of_people = 0;
                        if (document.getLong("no_of_people") != null)
                            no_of_people = Objects.requireNonNull(document.getLong("no_of_people")).intValue();

                        int price = 0;
                        if (document.getLong("price") != null) {
                            price = Objects.requireNonNull(document.getLong("price")).intValue();
                        }

                        String name = document.getString("name");
                        assert name != null;

                        availableRooms.add(new Room(room_id, price, name, hotel_id, no_of_people, type));
                    }
                }
            }
        }

        return availableRooms;
    }

    public static Hotel getHotelById(Firestore db, String hotel_id)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("hotels").document(hotel_id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        String location = document.getString("location");
        assert location != null;

        String name = document.getString("name");
        assert name != null;

        String photo = document.getString("photo");
        assert photo != null;

        return new Hotel(hotel_id, location, name, photo);
    }

    public static void main(String[] args)
            throws IOException, ExecutionException, InterruptedException {
        Firestore database = getDatabase();
    }

    public static List<Facility> getAllFacilities(Firestore db)
            throws IOException, ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("facilities").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Facility> facilities = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {
            String facility_id = document.getString("facility_id");
            assert facility_id != null;

            String hotel_id = document.getString("hotel_id");
            assert hotel_id != null;

            String name = document.getString("name");
            assert name != null;

            int price = 0;
            if (document.getLong("price") != null) {
                price = Objects.requireNonNull(document.getLong("price")).intValue();
            }

            String photo = document.getString("photo");
            assert photo != null;

            facilities.add(new Facility(facility_id, hotel_id, name, price, photo));
        }

        return facilities;
    }

    public static List<Facility> getFacilitiesByHotel(Firestore db, String hotel_id)
            throws IOException, ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("facilities").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<Facility> facilities = new ArrayList<>();

        for (QueryDocumentSnapshot document : documents) {

            String doc_hotel_id = document.getString("hotel_id");
            assert hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                String facility_id = document.getString("facility_id");
                assert facility_id != null;

                String name = document.getString("name");
                assert name != null;

                int price = 0;
                if (document.getLong("price") != null) {
                    price = Objects.requireNonNull(document.getLong("price")).intValue();
                }

                String photo = document.getString("photo");
                assert photo != null;

                facilities.add(new Facility(facility_id, hotel_id, name, price, photo));
            }
        }

        return facilities;
    }

    public static boolean hasFacility(Firestore db, String facility_id)
            throws IOException, ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("facilities").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            String doc_facility_id = document.getString("facility_id");
            assert doc_facility_id != null;

            if (doc_facility_id.equals(facility_id))
                return true;
        }

        return false;
    }

    public static void deleteFacility(Firestore db, String facility_id) {
        db.collection("facilities").document(facility_id).delete();
    }

    public static Room getFirstRoom(Firestore db, String hotel_id)
            throws IOException, ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("rooms").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        for (QueryDocumentSnapshot document : documents) {
            String doc_hotel_id = document.getString("hotel_id");
            assert doc_hotel_id != null;

            if (doc_hotel_id.equals(hotel_id)) {
                String type = document.getString("type");
                assert type != null;

                int no_of_people = 0;
                if (document.getLong("no_of_people") != null) {
                    no_of_people = Objects.requireNonNull(document.getLong("no_of_people")).intValue();
                }

                if (no_of_people == 2 && type.equals("premium")) {
                    int price = 0;
                    if (document.getLong("price") != null) {
                        price = Objects.requireNonNull(document.getLong("price")).intValue();
                    }

                    String room_id = document.getString("room_id");
                    assert room_id != null;

                    String name = document.getString("name");
                    assert name != null;

                    return new Room(room_id, price, name, hotel_id, no_of_people, type);
                }
            }
        }

        return null;
    }
}
