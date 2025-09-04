package com.example.termproject;
// RemoveBookActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemoveBookActivity extends AppCompatActivity {

    private EditText isbnEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_book);

        isbnEditText = findViewById(R.id.isbnEditText);

        Button removeBookButton = findViewById(R.id.removeBookButton);
        removeBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeBook();
            }
        });
    }
    private void removeBook() {
        String isbn = isbnEditText.getText().toString().trim();
        if (!isbn.isEmpty()) {
            DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");
            booksRef.orderByChild("isbn").equalTo(isbn).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        DataSnapshot bookSnapshot = snapshot.getChildren().iterator().next();
                        bookSnapshot.getRef().removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@NonNull DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    Toast.makeText(RemoveBookActivity.this, "Book removed successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RemoveBookActivity.this, "Failed to remove book: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(RemoveBookActivity.this, "Book with ISBN " + isbn + " not found", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RemoveBookActivity.this, "Error querying the database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }}
}
