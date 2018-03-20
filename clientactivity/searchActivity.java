package com.example.vnspv.clientactivity;

import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class searchActivity extends AppCompatActivity {
String val=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("Location");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                val=dataSnapshot.getValue(String.class);
                TextView textview=(TextView)findViewById(R.id.loctext);
                textview.setText(val);
                String [] sept=val.split(",");
                String latipos=sept[0].trim();
                String longipos=sept[1].trim();
                double dlat=Double.parseDouble(latipos);
                double dlong=Double.parseDouble(longipos);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void buttonclicked(View view) {
        Intent i=new Intent(searchActivity.this,MapsActivity.class);
        i.putExtra("local",val);
        startActivity(i);
    }
}
