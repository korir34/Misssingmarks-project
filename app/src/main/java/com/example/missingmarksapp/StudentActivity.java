package com.example.missingmarksapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.missingmarksapp.Models.Marks;
import com.example.missingmarksapp.Models.StudentData;
import com.example.missingmarksapp.databinding.ActivityStudentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    ActivityStudentBinding binding;
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ChatAdapter adapter;
    private List<ChatMessage> messageList = new ArrayList<>();
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString().trim();
                String regNumber = binding.RegNumber.getText().toString().trim();
                String unitName = binding.UnitName.getText().toString().trim();
                String unitCode = binding.UnitCode.getText().toString().trim();
                String examDate = binding.txtDate.getText().toString().trim();

                if (binding.editTextName.getText().toString().isEmpty() || binding.RegNumber.getText().toString().isEmpty() || binding.UnitName.getText().toString().isEmpty() || binding.UnitCode.toString().isEmpty() || binding.txtDate.toString().isEmpty()) {
                    Toast.makeText(StudentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String currentUserId = currentUser.getUid();

// Create a StudentData object
                StudentData studentData = new StudentData(binding.editTextName.getText().toString(),
                        binding.RegNumber.getText().toString(),
                        binding.UnitName.getText().toString(),
                        binding.UnitCode.getText().toString());
// Get reference to the Firebase Database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("studentData");

// Push the data to Firebase
                databaseReference.push().setValue(studentData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Clear input fields after successful submission
                                binding.editTextName.setText("");
                                binding.RegNumber.setText("");
                                binding.UnitName.setText("");
                                binding.UnitCode.setText("");
                                binding.txtDate.setText("");
                                Toast.makeText(StudentActivity.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure
                                Toast.makeText(StudentActivity.this, "Failed to submit data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }

            });



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        currentUserId = currentUser.getUid();

        RecyclerView recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(messageList, currentUserId);
        recyclerViewChat.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("chats").child("lecturer");

        findViewById(R.id.btnSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMessage message = snapshot.getValue(ChatMessage.class);
                if (message != null) {
                    messageList.add(message);
                    adapter.notifyItemInserted(messageList.size() - 1);
                    recyclerViewChat.scrollToPosition(messageList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void sendMessage() {
        String messageText = ((EditText) findViewById(R.id.editTextMessage)).getText().toString();

        if (!messageText.isEmpty()) {
            ChatMessage message = new ChatMessage(currentUserId, messageText, System.currentTimeMillis());
            databaseReference.push().setValue(message)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the input field after sending message
                            ((EditText) findViewById(R.id.editTextMessage)).setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure
                            Toast.makeText(StudentActivity.this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
