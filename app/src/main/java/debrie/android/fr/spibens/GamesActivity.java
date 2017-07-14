package debrie.android.fr.spibens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by lamipaul on 4/25/17.
 */

public class GamesActivity extends AppCompatActivity{
    private DatabaseReference gamesRef;
    private ArrayAdapter<String> games;
    private boolean starred;
    boolean done=false;
    private DatabaseReference SubscribeRef;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.happyh, menu);
        if(!done) {
            SubscribeRef = FirebaseDatabase.getInstance().getReference("membersList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("sports");
            SubscribeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().equals(true)) {
                        starred = true;
                        invalidateOptionsMenu();
                        done = true;
                    } else {
                        starred = false;
                        invalidateOptionsMenu();
                        done = true;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        if(starred){
            menu.getItem(0).setIcon(R.drawable.ic_action_star);
            starred=true;
        }else{
            menu.getItem(0).setIcon(R.drawable.ic_action_star_border);
            starred=false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        gamesRef = FirebaseDatabase.getInstance().getReference("gamesList");

        System.out.println(gamesRef.toString());
        games = new ArrayAdapter<String>(this, R.layout.activitylist);



        gamesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("child added");
                Map<String, Object> game = (Map<String, Object>) dataSnapshot.getValue();
                games.add(game.get("sport").toString()+" : "+game.get("team1").toString()+" vs "+game.get("team2").toString()+"\n"+game.get("location").toString()+" - "+game.get("date").toString());
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
//        System.out.println(games.toString());
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(games);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite420:
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                if (!starred) {
                    SubscribeRef.setValue(true);
                    item.setIcon(R.drawable.ic_action_star);
                    fm.subscribeToTopic("Sports");
                    Toast.makeText(getApplicationContext(), "Subscribed to Sports", Toast.LENGTH_SHORT).show();
                }
                else{
                    SubscribeRef.setValue(false);
                    item.setIcon(R.drawable.ic_action_star_border);
                    fm.unsubscribeFromTopic("Sports");
                    Toast.makeText(getApplicationContext(), "Unsubscribed from Sports", Toast.LENGTH_SHORT).show();
                }
                starred = !starred;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}



class Game {

    private String team1;
    private String team2;
    private Date date;
    private String location;
    private String sport;


    public Game(Map<String, Object> userMap){
        team1 = userMap.get("team1").toString();
        team2 = userMap.get("team2").toString();
        date = (Date) userMap.get("date");
        location = userMap.get("location").toString();
        sport = userMap.get("sport").toString();
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("team1",team1);
        userMap.put("team2",team2);
        userMap.put("date",date);
        userMap.put("location",location);
        userMap.put("sport",sport);
        return userMap;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getSport() {
        return sport;
    }
}

