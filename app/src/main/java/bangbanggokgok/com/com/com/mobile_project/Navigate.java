package bangbanggokgok.com.com.com.mobile_project;

import android.app.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 2018-06-07.
 */

public class Navigate extends Fragment implements mainMethod{
    TextView email;
    TextView name;
    ImageView close;
    FirebaseAuth mAuth;
    FirebaseDatabase mfirebaseDatabase;
    DatabaseReference mdatabaseReference;
    mainMethod mainMethod;
    String current_uid;
    ArrayList<String> info;
    public void setListener(mainMethod mainMethod){
        this.mainMethod = mainMethod;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mfirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        current_uid = mAuth.getCurrentUser().getUid().toString();
        View view = inflater.inflate(R.layout.navigate,container,false);
        view.setClickable(true);
        email = view.findViewById(R.id.email_info);
        name = view.findViewById(R.id.name_info);
        close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMethod.CloseNavigate();
            }
        });
        mdatabaseReference.child("Users").child(current_uid).child("INFO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if(snapshot.getKey().equals("email"))
                            email.setText(snapshot.getValue().toString());
                        else
                            name.setText(snapshot.getValue().toString());
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }

    @Override
    public void CloseNavigate() {

    }
}
