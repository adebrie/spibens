package debrie.android.fr.spibens;

import android.app.SearchManager;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchActivity extends AppCompatActivity {

    DatabaseReference eventsRef, membersRef;
    DatabaseReference mRef;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.search_listview);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            performSearch(query);
        }
    }

    public void performSearch(final String query){

        eventsRef = FirebaseDatabase.getInstance().getReference("eventsList");
        membersRef = FirebaseDatabase.getInstance().getReference("membersList");

        // Query MEMBERS
        Query q = membersRef.orderByKey();
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot c : dataSnapshot.getChildren()){
                    // If any record matches the query
                    if(c.child("name").getValue().toString().contains(query)) {
                        Toast.makeText(getApplicationContext(), "FOUND "+c.child("name").getValue().toString()+"  :  "+c.getValue().toString(), Toast.LENGTH_LONG).show();
                        System.out.println("FOUD "+c.child("name").getValue().toString()+"  :  "+c.getValue().toString());
//                        CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_dropdown_item_1line, StoresData.filterData(query));
//                        listView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Query EVENTS
        Query q2 = eventsRef.orderByKey();
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot c : dataSnapshot.getChildren()){
                    // If any record matches the query
                    if (c.child("name").getValue().toString().contains(query)) {
                        System.out.println("FOUD "+c.child("name").getValue().toString()+"  :  "+c.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}