package debrie.android.fr.spibens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private ArrayList<String> games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        gamesRef = FirebaseDatabase.getInstance().getReference("gamesList");




        gamesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Game game = new Game((Map<String, Object>) dataSnapshot.getValue());
                games.add(game.getSport()+" : "+game.getTeam1()+" vs "+game.getTeam2()+"\n"+game.getLocation()+" - "+game.getDate().toString());
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

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.activitylist, (String[]) games.toArray()));
    }

}



class Game {

    private String team1;
    private String team2;
    private Date date;
    private String location;
    private String sport;


    public Game(Map<String, Object> userMap){
        team1 = userMap.get("name").toString();
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

