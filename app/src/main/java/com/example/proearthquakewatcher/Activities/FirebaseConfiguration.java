package com.example.proearthquakewatcher.Activities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FirebaseConfiguration {

    private static DatabaseReference FirebaseReference;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseStorage storage;
    private static com.google.firebase.storage.StorageReference StorageReference;


    public static DatabaseReference getFirebase() {
        if (FirebaseReference == null) {
            FirebaseReference = FirebaseDatabase.getInstance().getReference();
        }

        return FirebaseReference;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }

    public static FirebaseStorage getFirebaseStorage() {
        if (storage == null) {
            storage = FirebaseStorage.getInstance();
        }

        return storage;
    }

    public static StorageReference getFirebaseStorageReference() {
        if (StorageReference == null) {
            StorageReference = FirebaseStorage.getInstance().getReference();
        }

        return StorageReference;
    }
}
