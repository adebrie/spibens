package debrie.android.fr.spibens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberContent {

    /**
     * An array of sample (Member) items.
     */
    public static final List<MemberItem> ITEMS = new ArrayList<MemberItem>();
    public static final Map<String, MemberItem> ITEM_MAP = new HashMap<String, MemberItem>();


    private static void addItem(MemberItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A Member item representing a piece of content.
     */
    public static class MemberItem {
        private String name;
        private String studies;
        private String section;
        private String workson;
        private String room;
        private String lab;
        private String email;
        private String startyear;
        private String id;

        public MemberItem(String name, String studies, String section, String workson, String room, String lab, String startyear, String email, String id) {
            this.name = name;
            this.studies = studies;
            this.section = section;
            this.workson = workson;
            this.room = room;
            this.lab = lab;
            this.email=email;
            this.startyear = startyear;
            this.id = id;
        }


        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getStudies() {
            return studies;
        }

        public String getSection() {
            return section;
        }

        public String getWorkson() {
            return workson;
        }

        public String getRoom() {
            return room;
        }

        public String getLab() {
            return lab;
        }

        public String getEmail() {
            return email;
        }

        public String getStartyear() {
            return startyear;
        }

        @Override
        public String toString() {
            return name+studies+section+workson+room+lab+id+email+startyear;
        }
    }
}
