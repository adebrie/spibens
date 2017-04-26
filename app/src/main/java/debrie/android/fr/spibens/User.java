package debrie.android.fr.spibens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lamipaul on 4/16/17.
 */

public class User {
    private String name;
    private ArrayList<String> skills;
    private String lab;
    private String email;
    private String room;
    private ArrayList<String> languages;
    private String worksOn;

    public User(Map<String, Object> userMap){
        name = userMap.get("name").toString();
        lab = userMap.get("lab").toString();
        email = userMap.get("email").toString();
        room = userMap.get("room").toString();
        worksOn = userMap.get("worksOn").toString();
        skills = (ArrayList) userMap.get("skills");
        languages = (ArrayList) userMap.get("languages");
    }
    public User(){

    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("name",name);
        userMap.put("skills", skills);
        userMap.put("lab",lab);
        userMap.put("email",email);
        userMap.put("room",room);
        userMap.put("languages",languages);
        userMap.put("worksOn",worksOn);

        return userMap;
    }

    public String getName() {
        return name;
    }


    public ArrayList<String> getSkills() {
        return skills;
    }

    public String getLab() {
        return lab;
    }

    public String getEmail() {
        return email;
    }

    public String getRoom() {
        return room;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public String getWorksOn() {
        return worksOn;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setWorksOn(String worksOn) {
        this.worksOn = worksOn;
    }


}
