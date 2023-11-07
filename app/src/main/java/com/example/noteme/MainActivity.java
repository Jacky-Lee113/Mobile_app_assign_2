package com.example.noteme;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.noteme.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    private SearchView searchView;
    Adapter adapter;
    List<EntryModel> entryModelList;
    List<EntryModel> filteredList;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerview);

        LocationDatabase noteDatabase = new LocationDatabase(this);
        entryModelList = noteDatabase.getEntry();
        filteredList = new ArrayList<>(entryModelList);
        entryModelList = noteDatabase.getEntry();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(this, entryModelList);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(this);
        if (entryModelList.size() < 1) {    //Only inputs coordinates if database is empty (only runs once when app is first downloaded and ran)
            String locAddress = "Unknown address";
            String locLatitude = "Unknown latitude";
            String locLongitude = "Unknown longitude";
            double[] latitude = new double[50];
            double[] longitude = new double[50];
            int x = 0;

            InputStream is = this.getResources().openRawResource(R.raw.latlongval);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            String address = "";
            while (true) {  //Adds file input values into geocoder to get address
                try {
                    if (!(x < 50 && (line = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    latitude[x] = Double.parseDouble(parts[0]);
                    longitude[x] = Double.parseDouble(parts[1]);
                    address = getAddressFromCoordinates(latitude[x], longitude[x]);
                    locAddress = address;   //Finding address from coordinates
                    locLatitude = String.valueOf(latitude[x]);  //Loading first number to database
                    locLongitude = String.valueOf(longitude[x]);    //Loading second number to database
                    EntryModel entryModel = new EntryModel(locAddress, locLatitude, locLongitude);
                    LocationDatabase db = new LocationDatabase(this);
                    db.addEntry(entryModel);
                    Log.d("Addresses", "Address: " + address);
                    x++;
                }
            }
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            recreate();
        }
            binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, AddEntry.class));
                }
            });
        }
    private String getAddressFromCoordinates(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String addressString = "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                // Extract street number, street name, and country
                String streetNumber = address.getSubThoroughfare();
                String streetName = address.getThoroughfare();
                String country = address.getCountryName();

                // Build the address string with street number, street name, and country
                if (streetNumber != null) {
                    addressString += streetNumber + " ";
                }
                if (streetName != null) {
                    addressString += streetName + ", ";
                }
                if (country != null) {
                    addressString += country;
                }

                if (addressString.isEmpty()) {
                    addressString = "Address not found";
                }
            } else {
                addressString = "Address not found";
            }
        } catch (IOException e) {
            e.printStackTrace();
            addressString = "Error retrieving address";
        }

        return addressString;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<EntryModel> filteredList = new ArrayList<>();
        for (EntryModel entryModel: entryModelList) {
            String name =  entryModel.getAddressInput().toLowerCase();
            if (name.contains(newText)) {
                filteredList.add(entryModel);
            }
        }
        adapter.setFilter(filteredList);
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        LocationDatabase noteDatabase = new LocationDatabase(this);
        entryModelList = noteDatabase.getEntry();
        adapter.notifyDataSetChanged(); // Notify the adapter about the data change
    }
}