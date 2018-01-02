package com.example.shashank_pc.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    TextView textView;
    Button button;
    EditText editText;
    boolean flag;
    int count=0;

    TextView textView2;
    Button button2;
    EditText editText2;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        textView2 = (TextView) findViewById(R.id.textView2);
        button2 = (Button) findViewById(R.id.button2);
        editText2 = (EditText) findViewById(R.id.editText2);

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Message");
        flag=false;
        count=0;

        List<FirebaseDatabase> DB= new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("DB").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if(e!=null)
                            return;

                for(DocumentChange dc : documentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Toast.makeText(getApplicationContext(),dc.getDocument().getId(),Toast.LENGTH_SHORT).show();
                            break;
                        case MODIFIED:

                            break;
                        case REMOVED:
                            Toast.makeText(getApplicationContext(),dc.getDocument().getId(),Toast.LENGTH_SHORT).show();
                            break;
                    }
            }
        }});





  /*      reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getApplicationContext(),dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();
                count+=2;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();


//                textView.setText(text);


//                Map<String,String> map = new HashMap<>();

//                reference.child(Integer.toString(count)).setValue(" ");



                //          Intent openMaps= new Intent();                  //If latitude and longitude are valid, create Intent openMaps
                //         openMaps.setClass(getApplicationContext(),MapsActivity.class);      //set Class as MapsActivity (Google maps activity)
                //         startActivity(openMaps);
            }
        });

 /*       FirebaseDatabase db2 = FirebaseDatabase.getInstance("https://testproject-5baad-5ffa4.firebaseio.com/");
        final DatabaseReference ref2 = db2.getReference("Message");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                textView.setText((String)dataSnapshot.child("1").getValue());
                Toast.makeText(getApplicationContext(),(String)dataSnapshot.child("1").getValue(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textView2.setText((String)dataSnapshot.child("1").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




//New database reference testproject-5baad-5ffa4



        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();


//                textView.setText(text);


                Map<String,String> map = new HashMap<>();
                map.put("1",text);
                map.put("2",text);
                reference.setValue(map);



      //          Intent openMaps= new Intent();                  //If latitude and longitude are valid, create Intent openMaps
       //         openMaps.setClass(getApplicationContext(),MapsActivity.class);      //set Class as MapsActivity (Google maps activity)
       //         startActivity(openMaps);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText2.getText().toString();


//                textView.setText(text);


                Map<String,String> map = new HashMap<>();
                map.put("1",text);

                ref2.setValue(map);
            }
        });

        */


    }
}
