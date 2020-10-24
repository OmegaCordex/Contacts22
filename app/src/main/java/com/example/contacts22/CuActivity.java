package com.example.contacts22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterCu adapterCu;
    FirebaseFirestore firebaseFirestore;
    ArrayList<CommunityUnit> arrayList;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cu);


        recyclerView = findViewById(R.id.recCuId);
        firebaseFirestore = FirebaseFirestore.getInstance();
        arrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterCu = new AdapterCu(this, arrayList);
        firebaseAuth = FirebaseAuth.getInstance();

        String userEmail = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        assert userEmail != null;
        final String brName = userEmail.substring(userEmail.indexOf(".") + 1);

        firebaseFirestore.collection("branchName").document(brName).collection("cuName").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d :list){

                                CommunityUnit categories = d.toObject(CommunityUnit.class);

                                arrayList.add(categories);
                            }

                            adapterCu.notifyDataSetChanged();

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
//        mainAdapter.setOnItemClickListener(new MainAdapter.OnListItemClick() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//                Toast.makeText(MainActivity.this, arrayList.get(position).getCatName(), Toast.LENGTH_SHORT).show();
//
//                startActivity(new Intent(MainActivity.this, SetsActivity.class));
//
//            }
//        });


    }


}