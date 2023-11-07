package com.example.noteme;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AddEntry extends AppCompatActivity {
    EditText address, latitude, longitude;
    Button saveEntry, discardEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        address = findViewById(R.id.address);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        saveEntry = findViewById(R.id.button_save);
        discardEntry = findViewById(R.id.button_second);
        saveEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocationToDatabase();
            }
        });

        discardEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEntry.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Location discarded", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveLocationToDatabase() {
        String locationAddress = address.getText().toString();
        String locationLatitude = latitude.getText().toString();
        String locationLongitude = longitude.getText().toString();

        if (!locationAddress.isEmpty()) {
            EntryModel entryModel = new EntryModel(locationAddress, locationLatitude, locationLongitude);

            // Add note to database
            LocationDatabase db = new LocationDatabase(AddEntry.this);
            db.addEntry(entryModel);

            // Navigate back to MainActivity
            Intent intent = new Intent(AddEntry.this, MainActivity.class);
            startActivity(intent);

            // Display success message
            Toast.makeText(getApplicationContext(), "Location saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }
}