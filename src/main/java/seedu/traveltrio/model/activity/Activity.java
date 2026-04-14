package seedu.traveltrio.model.activity;

import seedu.traveltrio.TravelTrioException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents an activity in a trip itinerary.
 * Contains information about the activity's name, location, date, time, and optional remarks.
 * Supports conflict detection for overlapping activity schedules.
 */
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

    /**
     * Constructs an Activity with the specified details.
     *
     * @param name the name of the activity
     * @param location the location where the activity takes place
     * @param date the date of the activity (YYYY-MM-DD format)
     * @param start the start time of the activity (HH:mm format)
     * @param end the end time of the activity (HH:mm format)
     * @throws TravelTrioException if the date or time format is invalid
     */
    public Activity(String name, String location, String date, String start, String end) throws TravelTrioException {
        this.name = name;
        this.location = location;
        this.date = parseDate(date);
        this.start = parseTime(start, "Start time");
        this.end = parseTime(end, "End time");
        updateEndDate();
    }

    /**
     * Recalculates the end date of the activity. If the end time is before or equal to
     * the start time, it assumes the activity crosses midnight into the next day.
     */
    private void updateEndDate() {
        if (this.start != null && this.end != null) {
            if (!this.end.isAfter(this.start)) {
                // end <= start means activity crosses midnight into the next day
                this.endDate = this.date != null ? this.date.plusDays(1) : null;
            } else {
                this.endDate = this.date;
            }
        } else {
            this.endDate = this.date;
        }
    }

    /**
     * Parses a date string into a LocalDate object.
     *
     * @param date the date string to parse
     * @return the parsed LocalDate, or null if the input is blank
     * @throws TravelTrioException if the date format is invalid
     */
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

    /**
     * Parses a time string into a LocalTime object.
     *
     * @param time the time string to parse
     * @param fieldName the name of the field being parsed (for error messages)
     * @return the parsed LocalTime, or null if the input is blank
     * @throws TravelTrioException if the time format is invalid
     */
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

    /**
     * Returns the name of this activity.
     *
     * @return the activity name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this activity.
     *
     * @param name the new activity name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the location of this activity.
     *
     * @return the activity location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of this activity.
     *
     * @param location the new activity location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the date of this activity as a formatted string.
     *
     * @return the date in YYYY-MM-DD format, or null if not set
     */
    public String getDate() {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }

    /**
     * Sets the date of this activity.
     *
     * @param date the new date in YYYY-MM-DD format
     * @throws TravelTrioException if the date format is invalid
     */
    public void setDate(String date) throws TravelTrioException {
        this.date = parseDate(date);
        updateEndDate();
    }

    /**
     * Returns the start time of this activity as a formatted string.
     *
     * @return the start time in HH:mm format, or null if not set
     */
    public String getStart() {
        return start != null ? start.format(TIME_FORMATTER) : null;
    }

    /**
     * Sets the start time of this activity.
     *
     * @param start the new start time in HH:mm format
     * @throws TravelTrioException if the time format is invalid
     */
    public void setStart(String start) throws TravelTrioException {
        this.start = parseTime(start, "Start time");
        updateEndDate();
    }

    /**
     * Returns the end time of this activity as a formatted string.
     *
     * @return the end time in HH:mm format, or null if not set
     */
    public String getEnd() {
        return end != null ? end.format(TIME_FORMATTER) : null;
    }

    /**
     * Sets the end time of this activity.
     *
     * @param end the new end time in HH:mm format
     * @throws TravelTrioException if the time format is invalid
     */
    public void setEnd(String end) throws TravelTrioException {
        this.end = parseTime(end, "End time");
        updateEndDate();
    }

    /**
     * Sets the remark for this activity.
     *
     * @param remark the remark text to add
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Returns the remark for this activity.
     *
     * @return the remark text
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Returns the start time as a LocalTime object.
     *
     * @return the start time, or null if not set
     */
    public LocalTime getLocalStart() {
        return start;
    }

    /**
     * Returns the end time as a LocalTime object.
     *
     * @return the end time, or null if not set
     */
    public LocalTime getLocalEnd() {
        return end;
    }

    /**
     * Returns the start date and time combined as a LocalDateTime.
     *
     * @return the start date and time, or null if either is not set
     */
    public LocalDateTime getStartDateTime() {
        if (date == null || start == null) {
            return null;
        }
        return LocalDateTime.of(date, start);
    }

    /**
     * Checks if this activity overlaps with another activity.
     * Compares the time intervals of both activities to detect conflicts.
     *
     * @param other the other activity to check for overlap
     * @return true if the activities overlap, false otherwise
     */
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

    /**
     * Formats this activity for detailed display with all fields.
     * Includes the remark if one is set.
     *
     * @return a formatted string with the activity details
     */
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

    /**
     * Formats this activity for display in a table row.
     * Used in the itinerary listing command.
     *
     * @param index the 1-based index of the activity in the list
     * @return a formatted string representing one row in the activity table
     */
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
