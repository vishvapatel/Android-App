package com.example.vnspv.driverapp;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DrieverActivity extends AppCompatActivity {
  FirebaseDatabase database;
  DatabaseReference ref;
Button showlocation;
private  static final int requestforpermission=2;
String permission= Manifest.permission.ACCESS_FINE_LOCATION;
drivergpstracker dgps;
TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driever);
        try{
            if (ActivityCompat.checkSelfPermission(this,permission)!= MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{permission},requestforpermission);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        showlocation=(Button)findViewById(R.id.getlocationbtn);
        showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dgps=new drivergpstracker(DrieverActivity.this);
                location=(TextView)findViewById(R.id.location);
                if(dgps.cangetlocation()){
                   double latitude= dgps.islatitude() ;
                   double longitude=dgps.islongitude();
                   location.setText("Latitude :"+latitude+" \n "+"Longitude :"+longitude);
                   database= FirebaseDatabase.getInstance();
                   ref=database.getReference("Location");
                   ref.setValue(latitude+","+longitude);
                }else{
                    dgps.showalert();
                }
            }
        });
    }


}
