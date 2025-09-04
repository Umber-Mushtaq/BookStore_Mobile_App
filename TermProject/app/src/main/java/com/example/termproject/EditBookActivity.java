package com.example.termproject;
// EditBookActivity.java
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

public class EditBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, isbnEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        isbnEditText = findViewById(R.id.isbnEditText);

        Button editBookButton = findViewById(R.id.editBookButton);
        editBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBook();
            }
        });
    }

    private void editBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String isbn = isbnEditText.getText().toString().trim();

        if (!title.isEmpty() && !author.isEmpty() && !isbn.isEmpty()) {
            // Update the existing book with new information
            DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("books");

            // Query the database to find the book with the specified ISBN
            booksRef.orderByChild("isbn").equalTo(isbn).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // The book with the specified ISBN is found, update its details
                        DataSnapshot bookSnapshot = snapshot.getChildren().iterator().next();
                        bookSnapshot.getRef().child("title").setValue(title);
                        bookSnapshot.getRef().child("author").setValue(author);
                        // You might not want to update the ISBN, as it's often considered a unique identifier
                        // bookSnapshot.getRef().child("isbn").setValue(isbn);

                        Toast.makeText(EditBookActivity.this, "Book edited successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Book with the specified ISBN not found
                        Toast.makeText(EditBookActivity.this, "Book with ISBN " + isbn + " not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error during database query
                    Toast.makeText(EditBookActivity.this, "Error querying the database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
