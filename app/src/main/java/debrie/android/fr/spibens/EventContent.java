package debrie.android.fr.spibens;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EventContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<EventItem> ITEMS = new ArrayList<EventItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, EventItem> ITEM_MAP = new HashMap<String, EventItem>();



    private static void addItem(EventItem item) {
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
     * A dummy item representing a piece of content.
     */
    public static class EventItem {
        private String name;
        private String date;
        private String location;
        private String description;
        private String id;

        public EventItem(String name, String date, String location, String description, String id) {
            this.name = name;
            this.date = date;
            this.location = location;
            this.description = description;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getLocation() {
            return location;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
