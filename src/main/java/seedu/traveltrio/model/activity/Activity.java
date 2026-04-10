package seedu.traveltrio.model.activity;

import seedu.traveltrio.TravelTrioException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Activity {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    private String name;
    private String location;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private LocalDate endDate;
    private String remark = "";


    public Activity(String name, String location, String date, String start, String end) throws TravelTrioException {
        this.name = name;
        this.location = location;
        this.date = parseDate(date);
        this.start = parseTime(start, "Start time");
        this.end = parseTime(end, "End time");

        if (this.start != null && this.end != null) {
            if (!this.end.isAfter(this.start)) {
                // end <= start means activity crosses midnight into the next day
                this.endDate = this.date != null ? this.date.plusDays(1) : null;
            } else {
                this.endDate = this.date;
            }
        }
    }

    private LocalDate parseDate(String date) throws TravelTrioException {
        if (date == null || date.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TravelTrioException("Invalid date format: '" + date
                    + "'. Please use YYYY-MM-DD.");
        }
    }

    private LocalTime parseTime(String time, String fieldName) throws TravelTrioException {
        if (time == null || time.isBlank()) {
            return null;
        }
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new TravelTrioException("Invalid " + fieldName + " format: '" + time
                    + "'. Please use HH:mm.");
        }
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
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    public void setDate(String date) throws TravelTrioException {
        this.date = parseDate(date);
    }

    public String getStart() {
        return start != null ? start.format(TIME_FORMATTER) : null;
    }

    public void setStart(String start) throws TravelTrioException {
        this.start = parseTime(start, "Start time");
    }

    public String getEnd() {
        return end != null ? end.format(TIME_FORMATTER) : null;
    }

    public void setEnd(String end) throws TravelTrioException {
        this.end = parseTime(end, "End time");
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public LocalTime getLocalStart() {
        return start;
    }

    public LocalTime getLocalEnd() {
        return end;
    }

    public boolean overlapsWith(Activity other) {
        if (this.date == null || other.date == null) {
            return false;
        }
        if (this.start == null || this.end == null
                || other.start == null || other.end == null) {
            return false;
        }
        LocalDate thisEndDate = this.endDate != null ? this.endDate : this.date;
        LocalDate otherEndDate = other.endDate != null ? other.endDate : other.date;

        LocalDateTime thisStart = LocalDateTime.of(this.date, this.start);
        LocalDateTime thisEnd = LocalDateTime.of(thisEndDate, this.end);
        LocalDateTime otherStart = LocalDateTime.of(other.date, other.start);
        LocalDateTime otherEnd = LocalDateTime.of(otherEndDate, other.end);

        return thisStart.isBefore(otherEnd) && otherStart.isBefore(thisEnd);
    }

    @Override
    public String toString() {
        String result = name + "\n";
        result += " Location: " + (location != null ? location : "---") + "\n";
        result += " Date: " + (date != null ? date : "---") + "\n";
        result += " Time: " + (start == null || end == null ? "---" : (start + " to " + end));

        return result;
    }

    public String formatForDisplay() {
        String result = name + "\n";
        result += "   Location: " + (location != null ? location : "---") + "\n";
        result += "   Date: " + (date != null ? date : "---") + "\n";
        result += "   Time: " + (start == null || end == null ? "---" : (start + " to " + end));

        if (remark != null && !remark.isBlank()) {
            result += "\n   Remark: " + remark;
        }

        return result;
    }

    public String formatForTableRow(int index) {
        String activityName = name != null ? name : "---";
        String activityLocation = location != null ? location : "---";
        String activityDate = date != null ? date.format(DATE_FORMATTER) : "---";
        String activityTime = (start == null || end == null)
                ? "---"
                : start.format(TIME_FORMATTER) + " to " + end.format(TIME_FORMATTER);
        String activityRemark = (remark != null && !remark.isBlank())
                ? remark
                : "-";

        return String.format("%-3d | %-20s | %-15s | %-12s | %-18s | %-20s",
                index, activityName, activityLocation, activityDate, activityTime, activityRemark);
    }
}
