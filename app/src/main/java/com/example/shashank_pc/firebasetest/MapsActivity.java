package com.example.shashank_pc.firebasetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseStorage storage;
    private StorageReference ref;
    private Bitmap img;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        storage = FirebaseStorage.getInstance();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        marker= mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        ref= storage.getReference().child("Profile pics/9701420818.jpg");

        img=null;
/*
        Upload
        StorageReference ref2 = storage.getReference().child("Profile pics/city.jpg");

        Bitmap dr_image= BitmapFactory.decodeResource(getResources(),R.drawable.future_city);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        dr_image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask= ref2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


*/

/*        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getApplicationContext(),"Running",Toast.LENGTH_SHORT).show();
                try {
                    img = Glide.with(getApplicationContext())
                            .using(new FirebaseImageLoader())
                            .load(ref)
                            .asBitmap()
                            .into(120, 120).get();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }



            }
        };

        Handler handler= new Handler();
        handler.postDelayed(runnable,1000);
*/



        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Toast.makeText(getApplicationContext(),"Downloaded",Toast.LENGTH_SHORT).show();


                try
                {
                    img = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    Bitmap small_img = Bitmap.createScaledBitmap(img, 120, 120, false);

                    Bitmap round_img= getRoundedRectBitmap(small_img);

                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(round_img));

                    //Bitmap round_img= getCircleBitmap(img);
                    marker.setAnchor(0.5f,0.5f);

                }
                catch (Exception e)
                {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

                Toast.makeText(getApplicationContext(), exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



/*  Code for putting image im marker
       if(img!=null) {
            Bitmap small_img = Bitmap.createScaledBitmap(img, 120, 120, false);

//        Bitmap round_img= getRoundedRectBitmap(small_img);
            //      Bitmap round_img= getCircleBitmap(img);

            marker.setIcon(BitmapDescriptorFactory.fromBitmap(small_img));
        }

//        Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.future_city);

*/

    }


    public static Bitmap getRoundedRectBitmap(Bitmap bitmap) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(50, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }



}
