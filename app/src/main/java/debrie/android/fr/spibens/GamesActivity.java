package debrie.android.fr.spibens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private boolean starred = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        gamesRef = FirebaseDatabase.getInstance().getReference("gamesList");

        System.out.println(gamesRef.toString());
        games = new ArrayAdapter<String>(this, R.layout.activitylist);

        final ImageButton s = (ImageButton)findViewById(R.id.imageButton2);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                if (!starred) {
                    s.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_star));
                    fm.subscribeToTopic("Sports");
                    Toast.makeText(getApplicationContext(), "Subscribed to Sport Events", Toast.LENGTH_LONG).show();

                }
                else{
                    s.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_star_border));
                    fm.unsubscribeFromTopic("Sports");
                    Toast.makeText(getApplicationContext(), "Unsubscribed from Sport Events", Toast.LENGTH_LONG).show();

                }
                starred = !starred;
            }
        });


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

