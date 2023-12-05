package com.example.invernadero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    DatabaseReference mydb; // DatabaseReference object representing the reference to the Firebase database location.
    TextView Temperatura, Humedad; // TextView objects used to display temperature and humidity in the UI.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("FirebaseData", "onCreate called"); // Log message to be printed in the Android console
        Temperatura = findViewById(R.id.Temp);
        Humedad = findViewById(R.id.Hum);
        mydb = FirebaseDatabase.getInstance().getReference().child("Sensor"); // Initializing mydb with the reference to the "Sensor" location in the Firebase database.

        try {
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("FirebaseData", "onDataChange called");
                    if (Temperatura == null || Humedad == null) {
                        Log.e("FirebaseData", "TextViews not initialized");
                        return;
                    }
                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("temp").exists() && dataSnapshot.child("hum").exists()) {
                            String tempData = dataSnapshot.child("temp").getValue(String.class);
                            String humData = dataSnapshot.child("hum").getValue(String.class);
                            if (tempData != null && humData != null) {
                                Temperatura.setText(tempData);
                                Humedad.setText(humData);
                            } else {
                                // Handle the case where values are null
                            }
                        } else {
                            // Handle the case where 'temp' or 'hum' do not exist
                        }
                    } else {
                        Log.d("FirebaseData", "DataSnapshot does not exist");
                        // You can print a log message or perform error handling actions here
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseData", "onCancelled: " + error.getMessage());
                    // Logging error messages or performing error handling actions in case of cancellation
                }
            });
        } catch (Exception e) {
            // This block is designed to catch and log any exceptions
            Log.e("FirebaseData", "Error: " + e.getMessage());
        }
    }
}



