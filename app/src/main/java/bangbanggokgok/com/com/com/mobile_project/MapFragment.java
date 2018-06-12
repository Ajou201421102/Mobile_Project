package bangbanggokgok.com.com.com.mobile_project;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by User on 2018-06-07.
 */

public class MapFragment extends AppCompatActivity implements OnMapReadyCallback{

    private Button button;
    private MapView mapView = null;
    private ImageView imageView;
    private String urlList;
    private String titleNode;
    private String placeNode;
    private String realmNameNode;
    private String startDateNode;
    private String priceNode;
    private String endDateNode;
    private String GPS_x;
    private String GPS_y;
    private String seqNode;

    private String current_uid;
    GoogleMap googleMap;
    private TextView party_name;
    private TextView party_realmName;
    private TextView party_location;
    private TextView party_date;
    private TextView party_price;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mdatabaseReference;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_details_map);
        party_name = findViewById(R.id.party_name);
        party_realmName = findViewById(R.id.party_realmName);
        party_location = findViewById(R.id.party_location);
        party_date = findViewById(R.id.party_date);
        party_price = findViewById(R.id.party_price);
        imageView = findViewById(R.id.party_poster);
        button = findViewById(R.id.save);

        mAuth = FirebaseAuth.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mfirebaseDatabase.getReference();
        current_uid = mAuth.getCurrentUser().getUid().toString();

        Intent intent = getIntent();
        urlList = intent.getStringExtra("imgUrl");
        titleNode = intent.getStringExtra("title");;
        placeNode = intent.getStringExtra("place");;
        realmNameNode = intent.getStringExtra("realmName");;
        startDateNode = intent.getStringExtra("startDate");;
        priceNode = intent.getStringExtra("price");;
        endDateNode = intent.getStringExtra("endDate");;
        GPS_x = intent.getStringExtra("gpsX");;
        GPS_y = intent.getStringExtra("gpsY");;
        seqNode = intent.getStringExtra("seq");

        party_name.setText(titleNode);
        party_realmName.setText(realmNameNode);
        party_location.setText(placeNode);
        party_date.setText(startDateNode+ " ~ " + endDateNode);
        party_price.setText(priceNode);
        mapView = findViewById(R.id.map);

        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        new UploadImage().execute(urlList);


    }
    public void onClick(View v){
        final Activity activity = this;
        mdatabaseReference.child("Users").child(current_uid).child("Seq").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("저장","저장");
                Boolean check = true;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(seqNode)) {
                        Toast.makeText(activity, "이미 저장되어있습니다.", Toast.LENGTH_SHORT).show();
                        check = false;
                        break;
                    } else {
                        check = true;
                    }
                }
                if (check) {
                    InfoDTO infoDTO = new InfoDTO(urlList, titleNode, placeNode, realmNameNode, startDateNode, priceNode, endDateNode, GPS_x, GPS_y, seqNode);
                    mdatabaseReference.child("Users").child(current_uid).child("Seq").child(seqNode).setValue(infoDTO);
                    Toast.makeText(activity, "저장되었습니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
    public class UploadImage extends AsyncTask<String,Bitmap,Bitmap>{
        Bitmap bitmap = null;
        Bitmap resized = null;
        @Override
        protected Bitmap doInBackground(String... urls) {
            URL url = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream in = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                while (height > 150) {
                    resized = Bitmap.createScaledBitmap(bitmap, (width * 150) / height, 150, true);
                    height = resized.getHeight();
                    width = resized.getWidth();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resized;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        if(!GPS_x.equals("")||!GPS_y.equals("")) {
            LatLng Center = new LatLng(Double.parseDouble(GPS_y), Double.parseDouble(GPS_x));
            googleMap.addMarker(new MarkerOptions().position(Center));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(Center));
        }
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(14));
    }

}
