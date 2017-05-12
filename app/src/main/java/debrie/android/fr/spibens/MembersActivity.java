package debrie.android.fr.spibens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

public class Members extends AppCompatActivity {

    private DatabaseReference membersRef;
    private ArrayAdapter<String> members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        membersRef = FirebaseDatabase.getInstance().getReference("membersList");
        System.out.println(membersRef.toString());


        members = new ArrayAdapter<String>(this, R.layout.activitylist);

        membersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String, Object> usermap = (Map<String, Object>) dataSnapshot.getValue();

                members.add(usermap.get("name").toString());

                System.out.println(usermap.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        System.out.println(members.toString());
//        ListIterator  iterator = members.listIterator();
//        while(iterator.hasNext()){
//            ImageView patrick = (ImageView) findViewById(R.id.patrickProfile);
//            patrick.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(Members.this, ProfileActivity.class);
//                    startActivity(i);
//                }
//            });
//        }


        GridView grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(members);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Members.this, ProfileActivity.class);
                i.putExtra("id", id+1);
                startActivity(i);
            }
        });


    }


}
