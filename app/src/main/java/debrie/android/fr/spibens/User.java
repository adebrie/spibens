package debrie.android.fr.spibens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by lamipaul on 4/16/17.
 */

public class User {
    private String name;
    private String picId;
    private ArrayList<String> skillList;
    private String lab;
    private String email;
    private int room;
    private ArrayList<String> languages;
    private String worksOn;

    public User(String name, String picId, ArrayList<String> skillList, String lab, String email, int room, ArrayList<String> languages, String worksOn) {
        this.name = name;
        this.picId = picId;
        this.skillList = skillList;
        this.lab = lab;
        this.email = email;
        this.room = room;
        this.languages = languages;
        this.worksOn = worksOn;
    }

    public User(Map<String, Object> userMap){
        name = userMap.get("name").toString();
        picId = userMap.get("picId").toString();
        lab = userMap.get("lab").toString();
        email = userMap.get("email").toString();
        room = (int) userMap.get("room");
        worksOn = userMap.get("worksOn").toString();
        skillList = (ArrayList) userMap.get("skillList");
        languages = (ArrayList) userMap.get("languages");
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("name",name);
        userMap.put("skillList",skillList);
        userMap.put("picId",picId);
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

    public String getPicId() {
        return picId;
    }

    public ArrayList<String> getSkillList() {
        return skillList;
    }

    public String getLab() {
        return lab;
    }

    public String getEmail() {
        return email;
    }

    public int getRoom() {
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

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public void setSkillList(ArrayList<String> skillList) {
        this.skillList = skillList;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setWorksOn(String worksOn) {
        this.worksOn = worksOn;
    }


}
