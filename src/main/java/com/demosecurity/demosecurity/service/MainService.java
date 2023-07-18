package com.demosecurity.demosecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Service;

import com.demosecurity.demosecurity.entities.Account;
import com.demosecurity.demosecurity.handlerexc.DocumentNotFoundException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class MainService {
    public String createData(Account account) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collections = dbFirestore.collection("account").document(account.getName()).set(account);
        return collections.get().getUpdateTime().toString();
    }

    public Account getData(String name) throws InterruptedException, ExecutionException, DocumentNotFoundException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("account").document(name);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Account acc;
        if (document.exists()) {
            acc = document.toObject(Account.class);
            return acc;
        } else {
            throw new DocumentNotFoundException("Document " + name + " not found!");
        }
    }

    public List<Account> getAllData() throws InterruptedException, ExecutionException, DocumentNotFoundException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Account acc = new Account();
        // DocumentReference documentReference =
        // dbFirestore.collection("account").get();
        ApiFuture<QuerySnapshot> future = dbFirestore.collection("account").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Account> allData = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            acc = document.toObject(Account.class);
            allData.add(acc);
        }
        return allData;
    }
}
