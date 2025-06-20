package com.cu.productmanagementsystem.service;

import com.cu.productmanagementsystem.model.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final String COLLECTION_NAME = "products";

    public String addProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        product.setPid(UUID.randomUUID().toString());
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(product.getPid()).set(product);
        return future.get().getUpdateTime().toString();
    }

    public List<Product> getAllProducts() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream().map(doc -> doc.toObject(Product.class)).collect(Collectors.toList());
    }

    public Product getProductById(String pid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(pid);
        DocumentSnapshot doc = docRef.get().get();
        return doc.exists() ? doc.toObject(Product.class) : null;
    }

    public String updateProduct(String pid, Product product) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        product.setPid(pid);
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(pid).set(product);
        return future.get().getUpdateTime().toString();
    }

    public String deleteProduct(String pid) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(pid).delete();
        return future.get().getUpdateTime().toString();
    }
}
