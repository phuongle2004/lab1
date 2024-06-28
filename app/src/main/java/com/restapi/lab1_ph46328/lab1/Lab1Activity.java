package com.restapi.lab1_ph46328.lab1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.restapi.lab1_ph46328.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;



public class Lab1Activity extends AppCompatActivity {
    Button btnAdd, btnUpdate, btnDelete, btnDisplay;
    TextView tvResult;
    String id =  "";
    ToDo toDo = null;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAdd = findViewById(R.id.btn_add);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnDisplay = findViewById(R.id.btn_display);
        tvResult = findViewById(R.id.tv_result);
        db = FirebaseFirestore.getInstance();

        btnAdd.setOnClickListener(v -> {addData(tvResult);});
        btnUpdate.setOnClickListener(v -> {updateData(tvResult);});
        btnDelete.setOnClickListener(v -> {deleteData(tvResult);});
        btnDisplay.setOnClickListener(v -> {SelectDataFromFirebase(tvResult);});
    }

    public void addData(TextView tvResult) {
        id = UUID.randomUUID().toString();
        toDo = new ToDo(id, "title 1", "content 1");
        HashMap<String, Object> mapTodo = toDo.convertHashMap();
        db.collection("TODO").document(id)
                .set(mapTodo)//doi tuong can insert
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tvResult.setText("Them thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
    }

    public void updateData(TextView tvResult) {
        id = "1cce6f40-50c1-4a23-bc5a-9626120a6f75";
        toDo = new ToDo(id, "sua title 1", "sua content 1");
        db.collection("TODO").document(toDo.getId())
                .update(toDo.convertHashMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tvResult.setText("Sua thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
    }

    public void deleteData(TextView tvResult) {
        id = "7f707ddd-ed10-4a8d-aab2-3e975dce0fd5";
        db.collection("TODO").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tvResult.setText("Xoa thanh cong");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
    }

    String strResult = "";

    public ArrayList<ToDo> SelectDataFromFirebase(TextView tvResult) {
        ArrayList<ToDo> list = new ArrayList<>();
        db.collection("TODO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            strResult = "";
                            //doc theo tung dong du lieu
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ToDo toDo1 = document.toObject(ToDo.class);
                                strResult += "Id: " + toDo1.getId() + "\n" + "Title: " + toDo1.getTitle() + "\n" + "Content: " + toDo1.getContent()+  "\n";
                                list.add(toDo1);
                            }
                            //hien thi ket qua
                            tvResult.setText(strResult);
                        } else {
                            tvResult.setText("Doc du lieu that bai");
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        tvResult.setText(e.getMessage());
                    }
                });
        return list;
    }
}