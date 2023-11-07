package com.example.noteme;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;


import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView showAddress, showLatitude, showLongitude;
    Button deleteNote, saveChanges, discardChanges;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        deleteNote = findViewById(R.id.delete);
        saveChanges = findViewById(R.id.save);
        discardChanges = findViewById(R.id.undo);

        showAddress = findViewById(R.id.editAddress);
        showLatitude = findViewById(R.id.editLatitude);
        showLongitude = findViewById(R.id.editLongitude);

        LocationDatabase db = new LocationDatabase(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);

        EntryModel entryModel = db.getEntry(id);

        showAddress.setText((entryModel.getAddressInput()));
        showLatitude.setText((entryModel.getLatitudeInput()));
        showLongitude.setText((entryModel.getLongitudeInput()));
        Toast.makeText(getApplicationContext(), "Opening " + showAddress.getText().toString(), Toast.LENGTH_SHORT).show();
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationDatabase db = new LocationDatabase(getApplicationContext());
                Intent intent = getIntent();
                id = intent.getIntExtra("ID", 0);
                db.deleteEntry(id);
                Toast.makeText(getApplicationContext(), showAddress.getText().toString()+" deleted", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocationToDatabase();
                LocationDatabase db = new LocationDatabase(getApplicationContext());
                Intent intent = getIntent();
                id = intent.getIntExtra("ID", 0);
                db.deleteEntry(id);
                Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        discardChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Changes discarded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void saveLocationToDatabase() {
        String editAddress = showAddress.getText().toString();
        String editLatitude = showLatitude.getText().toString();
        String editLongitude = showLongitude.getText().toString();

        if (!editAddress.isEmpty()) {
            // Create a new NoteModel object with selected color
            EntryModel entryModel = new EntryModel(editAddress, editLatitude, editLongitude);

            // Add note to database
            LocationDatabase db = new LocationDatabase(DetailActivity.this);
            db.addEntry(entryModel);

            // Navigate back to MainActivity
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);

            // Display success message
            Toast.makeText(getApplicationContext(), "Changes made", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
