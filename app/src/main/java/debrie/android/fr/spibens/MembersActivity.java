package debrie.android.fr.spibens;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MembersActivity extends AppCompatActivity {

    private DatabaseReference membersRef;
    private ArrayAdapter<String> members;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.members, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar_members);
        setSupportActionBar(mytoolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);



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
        

        GridView grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(members);

        // TODO: Map clicked item's position to user's UID
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MembersActivity.this, ProfileActivity.class);
//                i.putExtra("id", id+1);
                startActivity(i);
            }
        });


    }


}
