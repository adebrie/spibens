import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lamipaul on 4/16/17.
 */

public class Game {

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
}

