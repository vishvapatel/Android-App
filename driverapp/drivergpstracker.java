package com.example.vnspv.driverapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by vnspv on 18-3-18.
 */

public class drivergpstracker extends Service implements LocationListener {

    private final Context context;
    boolean gpsenabled=false;
    boolean networkenabled=false;
    boolean cangetlocation=false;
    Location loc;
    double latitude;
    double longitude;
    private static final long min_dist=10;
    private static final long min_time=1000*60*1;
    protected LocationManager locationmanager;
    public drivergpstracker(Context context){
        this.context=context;
getlocation();
    }
    public Location getlocation(){
        try{
   locationmanager=(LocationManager) context.getSystemService(LOCATION_SERVICE);
   gpsenabled=locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
   networkenabled=locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
   if(!gpsenabled && !networkenabled){

   }else{
       this.cangetlocation=true;
       if(networkenabled){
           if(ActivityCompat.checkSelfPermission((Activity)context,Manifest.permission.ACCESS_FINE_LOCATION )!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission((Activity)context,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

               return null;
           }
           locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,min_time,min_dist,this);
           if(locationmanager!=null){
               loc=locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
               if(loc!=null){
                   latitude=loc.getLatitude();
                   longitude=loc.getLongitude();
               }
           }
       }
       if(gpsenabled){
           if(loc==null){
               locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,min_time,min_dist,this);
               if(locationmanager!=null){
                   loc=locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                   if(loc!=null){
                       latitude=loc.getLatitude();
                       longitude=loc.getLongitude();
                   }
               }
           }
       }
   }
        }catch (Exception e){
            e.printStackTrace();
        }
        return loc;
    }

    public void stopusinggps(){
        if(locationmanager!=null){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            locationmanager.removeUpdates(drivergpstracker.this);
        }
    }
    public double islatitude(){
        if(loc!=null){
            latitude=loc.getLatitude();
        }
        return latitude;
    }
    public double islongitude(){
        if(loc!=null){
            longitude=loc.getLongitude();
        }
        return longitude;
    }
    public boolean cangetlocation(){
        return this.cangetlocation;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void showalert()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setTitle("GPS in settings");

        alert.setMessage("GPS is not enabled"+" Please enable it "+" Go to the settings menu ");
        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int k) {
                Intent i=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(i);
            }
        });
   alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
       @Override
       public void onClick(DialogInterface dialogInterface, int m) {
           dialogInterface.cancel();
       }
   });
   alert.show();
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
