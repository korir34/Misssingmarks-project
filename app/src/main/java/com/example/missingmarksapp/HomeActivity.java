package com.example.missingmarksapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.missingmarksapp.Models.Marks;
import com.example.missingmarksapp.Models.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private EditText name, reg, year, marks;
    private DatabaseReference studentDataReference;
    private DatabaseReference chatReference;
    private RecyclerView recyclerViewStudentData;
    private RecyclerView recyclerViewChat;
    private StudentDataAdapter studentDataAdapter;
    private ChatAdapter chatAdapter;
    private List<StudentData> studentDataList = new ArrayList<>();
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView for student data
        name = findViewById(R.id.editTextStudentName);
        reg = findViewById(R.id.editTextStudentRegNo);
        year = findViewById(R.id.editTextStudentStudyYear);
        marks = findViewById(R.id.editTextStudentMarks);

        recyclerViewStudentData = findViewById(R.id.recyclerViewResults);
        recyclerViewStudentData.setLayoutManager(new LinearLayoutManager(this));
        studentDataAdapter = new StudentDataAdapter(studentDataList);
        recyclerViewStudentData.setAdapter(studentDataAdapter);


        // Initialize RecyclerView for chat
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatMessageList, FirebaseAuth.getInstance().getCurrentUser().getUid());
        recyclerViewChat.setAdapter(chatAdapter);

        // Get current user ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }

        // Initialize Firebase Database references
        studentDataReference = FirebaseDatabase.getInstance().getReference().child("studentData");
        chatReference = FirebaseDatabase.getInstance().getReference().child("chats").child("lecturer");

        // Fetch student data from Firebase
        fetchStudentData();

        // Listen for new chat messages
        listenForChatMessages();

        // Send message button click listener
        Button btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        Button btnSendMarks = findViewById(R.id.btnSendMarks);
        btnSendMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmarks(name, reg, year, marks);
                Toast.makeText(HomeActivity.this, "Marks successfully submitted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchStudentData() {
        studentDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        StudentData studentData = data.getValue(StudentData.class);
                        if (studentData != null) {
                            studentDataList.add(studentData);
                        }
                    }
                }
                studentDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(HomeActivity.this, "Failed to fetch student data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listenForChatMessages() {
        chatReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                if (chatMessage != null) {
                    chatMessageList.add(chatMessage);
                    chatAdapter.notifyItemInserted(chatMessageList.size() - 1);
                    recyclerViewChat.scrollToPosition(chatMessageList.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
                Toast.makeText(HomeActivity.this, "Failed to fetch chat messages: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        String messageText = editTextMessage.getText().toString().trim();

        if (!messageText.isEmpty()) {
            // Create ChatMessage object
            ChatMessage chatMessage = new ChatMessage(currentUserId, messageText, System.currentTimeMillis());

            // Push the message to Firebase
            chatReference.push().setValue(chatMessage)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Clear the input field after sending message
                            editTextMessage.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle failure
                            Toast.makeText(HomeActivity.this, "Failed to send message: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void sendmarks( EditText name, EditText reg, EditText year, EditText marks){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Marks marks1 = new Marks(name.getText().toString(), reg.getText().toString(), year.getText().toString(), marks.getText().toString());

        String key = ref.push().getKey();

        ref.child("Marks").child(key).setValue(marks1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HomeActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
