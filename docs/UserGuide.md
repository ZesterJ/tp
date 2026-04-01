# TravelTrio User Guide

## Introduction
**TravelTrio** is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI). It helps organized and thrifty travelers manage complex trip itineraries, track activity-based budgets, and handle currency exchange rates within a single and lightweight environment.
Whether you are planning a weekend getaway or a month-long backpacking trip, TravelTrio ensures your schedule is conflict-free and your wallet is happy.

## Quick Start
1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `traveltrio.jar` from [traveltrio.jar](../build/libs/traveltrio.jar). 
3. Copy the file to the folder you want to use as the home folder for your travel plans.
4. Open a command terminal (e.g., Command Prompt on Windows, Terminal on macOS), navigate to the folder where you placed the `.jar` file, and run the following command: `java -jar traveltrio.jar`.
5. You should see the TravelTrio welcome logo. Type `help` and press Enter to see the list of available commands to get started!

### Date Format
- Dates must be entered in `YYYY-MM-DD` format (e.g., `2026-05-01`).
- Invalid formats will be rejected and you will be prompted to re-enter.

### Time Format
- Time must be entered in 24-hour format `HH:MM` (e.g., `14:30`).

### Notes
- TravelTrio uses precise date and time validation to ensure consistency.
- All activities must fall within the trip’s start and end dates.

## Features 
Unlike traditional CLI apps that force you to type long, complicated command strings, TravelTrio guides you step-by-step. Simply type the base command, and the app will prompt you for the required details!

1. **Trip Management**
    Manage multiple travel plans in one place.
   * Create Trips: Use `addtrip` to define a trip name and its duration. The app automatically validates that your start date occurs before your end date. 
   * Track Multiple Itineraries: View all your saved journeys with `listtrip` and see a high-level spending summary for each.
   * Focus on a Trip: Use `opentrip` to select a "working" trip. Once a trip is open, all activity and budget commands will apply specifically to that itinerary. 
   * Flexible Data Sharing:
     * Export: Save a specific trip to a formatted `.txt` file using `exporttrip` to share with friends. 
     * Import: Seamlessly merge a trip file shared by another TravelTrio user using `importtrip`.

2. **Smart Itinerary Planning**
    Build a detailed schedule for your open trip.
   * Automatic Sorting: No matter what order you add them, `listactivity` displays your schedule chronologically.
   * Date Validation: The app ensures every activity falls within the start and end dates of your selected trip.
   * Easy Updates: Use `editactivity` to change locations or times without re-typing the entire entry. Leave a field blank to keep its current value.
   * Conflict Detection:
  - When using `addactivity`, TravelTrio automatically checks for time overlaps between activities.
  - If a conflict is detected, the activity will not be added.
  - This ensures that your itinerary remains realistic and executable.

    **Example:**
   If you already have an activity from `10:00` to `12:00`, adding another activity from `11:00` to `13:00` will be rejected.

3. **Financial Tracking & Budgeting**
   Keep your travel expenses under control with integrated finance tools.
   * Activity-Based Budgeting: Use `setbudget` to allocate specific funds for individual activities. 
   * Expense Logging: Record what you actually spent on an activity with `setexpense`.
   * Overspending Alerts: 
     * Budget Warnings: The app warns you if an activity's expense reaches 90% of its allocated budget.
     * Strict Limits: You cannot log an expense that exceeds the individual activity's budget without adjusting the budget first.
   * Daily Spending Limits: Use `setdailylimit` to cap your total spending per day. TravelTrio will block expenses that would cause you to exceed this daily threshold. 
   * Currency Conversion: Planning an international trip? Use `setcurrency` to define an exchange rate. When setting expenses, you can choose to enter the amount in a foreign currency, and TravelTrio will convert it to your home currency automatically for easy budget calculation.

4. **Financial Insights**
   * Budget Summary: Get a birds-eye view of your total trip budget, total spent, and total remaining funds with `budgetsummary`.
   * Chronological Expense List: Use `listexpense` to see a table of your daily spending. It groups expenses by date and compares them against your daily limit.

5. **Data Persistence**
   * Auto-Save: TravelTrio automatically saves your data to `./data/traveltrio.txt` every time you successfully execute a command and when you `exit`.
   * Corruption Protection: The app uses robust internal logging and assertions to ensure that even if you make a mistake, your data remains consistent.

## FAQ

**Q:** How do I transfer my data to another computer? 

**A:** You have two options! For individual trips, you can use the `exporttrip` command to generate a shareable `.txt` file, transfer that file via email or USB, and use `importtrip` on the other computer.
Alternatively, to transfer all your trips at once, simply copy the `traveltrio.txt` file located in the `data` folder next to your `.jar` application and move it to the new computer.

**Q:** What happens if I type a date incorrectly?

**A:** TravelTrio is forgiving! If you type `2026/05/01` instead of `2026-05-01`, the app will simply tell you the format is invalid and ask you for the date again without losing the rest of the information you already typed.

## Command Summary

| Category     | Command          | Description                                         |
|:-------------|:-----------------|:----------------------------------------------------|
| **Trip**     | `addtrip`        | Create a new trip.                                  |
|              | `listtrip`       | View a list of all your planned trips.              |
|              | `opentrip`       | Open a specific trip to manage it.                  |
|              | `deletetrip`     | Permanently delete a trip.                          |
|              | `exporttrip`     | Export a trip to a text file for sharing.           |
|              | `importtrip`     | Import a shared trip from a text file.              |
| **Activity** | `addactivity`    | Add an event to the open trip.                      |
|              | `listactivity`   | View the itinerary for the open trip.               |
|              | `editactivity`   | Modify details of an existing activity.             |
|              | `deleteactivity` | Remove an activity from the itinerary.              |
| **Budget**   | `setbudget`      | Allocate funds for an activity.                     |
|              | `setexpense`     | Log actual spending for an activity.                |
|              | `setcurrency`    | Set the foreign exchange rate.                      |
|              | `setdailylimit`  | Set a maximum daily spending limit.                 |
|              | `budgetsummary`  | View total trip budget and remaining funds.         |
|              | `listexpense`    | Compare budget vs. actual spending chronologically. |
| **General**  | `help`           | View this built-in help guide.                      |
|              | `exit`           | Save data and close the application.                |
