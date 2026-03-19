package seedu.traveltrio.model;

public class Activity {
    private String name;
    private String location;
    private String date;
    private String start;
    private String end;


    public Activity(String name, String location, String date, String start, String end) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        String result = name + "\n";
        result += "📍 Location: " + (location != null ? location : "---") + "\n";
        result += "📆 Date: " + (date != null ? date : "---") + "\n";
        result += "🕛 Time: " + (start == null || end == null ? "---" : (start + " to " + end));

        return result;
    }

    public String formatForList() {
        String result = name + "\n";
        result += "   📍 Location: " + (location != null ? location : "---") + "\n";
        result += "   📆 Date: " + (date != null ? date : "---") + "\n";
        result += "   🕛 Time: " + (start == null || end == null ? "---" : (start + " to " + end));

        return result;
    }
}
