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
6. The command prompt will display your current working context:
   * `> ` means no trip is currently open
   * `[Trip Name] > ` means you are working within that trip

### Date Format
* Dates must be entered in `YYYY-MM-DD` format (e.g., `2026-05-01`).
* Invalid formats will be rejected and you will be prompted to re-enter.

### Time Format
* Time must be entered in 24-hour format `HH:MM` (e.g., `14:30`).

### Notes
* TravelTrio uses precise date and time validation to ensure consistency.
* All activities must fall within the trip’s start and end dates.

## Features 
Unlike traditional CLI apps that force you to type long, complicated command strings, TravelTrio guides you step-by-step. Simply type the base command, and the app will prompt you for the required details!

1. **Trip Management**
    Manage multiple travel plans in one place.
   * Create Trips: Use `addtrip` to define a trip name and its duration. The app automatically validates that your start date occurs before your end date. 
   * Track Multiple Itineraries: View all your saved journeys with `listtrip` and see a high-level spending summary for each.
   * Focus on a Trip: Use `opentrip` to select a "working" trip. Once a trip is open:
     * All activity and budget commands will apply specifically to that itinerary  
     * The command prompt will display the trip name (e.g., `[Japan Trip] >`) for clarity
   * Flexible Data Sharing:
     * Export: Save a specific trip to a formatted `.txt` file using `exporttrip` to share with friends. 
     * Import: Seamlessly merge a trip file shared by another TravelTrio user using `importtrip`.

2. **Smart Itinerary Planning**
    Build a detailed schedule for your open trip.
   * Automatic Sorting: No matter what order you add them, `listactivity` displays your schedule chronologically.
   * Date Validation: The app ensures every activity falls within the start and end dates of your selected trip.
   * Easy Updates: Use `editactivity` to change locations or times without re-typing the entire entry. Leave a field blank to keep its current value.
   * Conflict Detection:
     * When using `addactivity`, TravelTrio automatically checks for time overlaps between activities.
     * If a conflict is detected, the activity will not be added.
     * This ensures that your itinerary remains realistic and executable.

   **Example:** If you already have an activity from `10:00` to `12:00`, adding another activity from `11:00` to `13:00` will be rejected.

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

## Feature Commands

### 1. Trip Management

#### 1.1 Adding a Trip
Adds a new trip to user's travel planner
* Format: `addtrip`
    * After entering the command, the application will prompt users for inputs:
        * `Trip name`
        * Start date in `YYYY-MM-DD` format
        * End date in `YYYY-MM-DD` format
* Example usage:
  ```text
  > addtrip
  Trip name: Japan Winter Trip
  Start Date (YYYY-MM-DD): 2026-12-01
  End Date (YYYY-MM-DD): 2026-12-30
  ```
  Expected result:
  ```text
  ===========================================================
  New trip added:
  Japan Winter Trip (2026-12-01 to 2026-12-30)
  ===========================================================
  ```
<br>

#### 1.2 Listing all Trips
Displays all trips
* Format: `listtrip`

* Expected result:
  ```text
  ===========================================================
  Trips:
  1. Japan Winter Trip
     Start: 2026-12-01
     End:   2026-12-30

  2. Summer Trip at Bali
     Start: 2026-06-01
     End:   2026-06-30
  ===========================================================
  ```
<br>

#### 1.3 Open a Trip to edit
Sets a trip as the active working trip, to allow users to add edit the opened trip's itinerary.

* Format: `opentrip`
  * After entering the command, the application will prompt users for inputs:
    * `Enter the index of the trip to open: `
    
* Example usage:
    ```text
    ===========================================================
    Trips:
    1. Japan Winter Trip           (Total Spent: $60.00)
       Start: 2026-12-01
       End:   2026-12-30
    
    2. Summer Trip at Bali
       Start: 2026-06-01
       End:   2026-06-30
    ===========================================================
    Enter the index of the trip to open:  1
    ```

* Expected result:
    ```text
    Opened trip: Japan Winter Trip (2026-12-01 to 2026-12-30)
    [Opened: Japan Winter Trip] > 
    ```
<br>

### 1.4 Exporting a Trip
Exports a specific trip and all its activity and budget details into a text file. This file can then be shared with friends or used as a backup.

* Format: `exporttrip`
  * After entering the command, the application will prompt users for inputs:
    * `Enter the index of the trip to export: `
    * `Enter the file name to save as (e.g., JapanTrip.txt): `

* Example usage:
    ```text
    > exporttrip
    ===========================================================
    Trips:
    1. Japan Trip           (Total Spent: $0.00)
       Start: 2026-04-04
       End:   2026-04-04
       ===========================================================
       Enter the index of the trip to export: 1
       Enter the file name to save as (e.g., JapanTrip.txt): JapanTrip.txt
    ```

* Expected result:
    ```text
    ===========================================================
    Successfully exported 'Japan Trip' to JapanTrip.txt
    ===========================================================
    ```
<br>

### 1.5 Importing a Trip
Imports a trip schedule and its budget details from a provided text file into your TravelTrio application.

* Format: `importtrip`
  * After entering the command, the application will prompt users for inputs:
    * `Enter the file name to import (e.g., SharedTrip.txt): `

* Example usage:
    ```text
    > importtrip
    Enter the file name to import (e.g., SharedTrip.txt): JapanTrip.txt
    ```

* Expected result:
    ```text
    ===========================================================
    Successfully imported new trip: 
    Japan Trip (2026-04-04 to 2026-04-04)
    ===========================================================
    ```
<br>


### 2. Itinerary Management
*Note: a trip has to be "opened" by the user, in order to perform the following commands*

#### 2.1 Adding an Activity
Adds a new activity to the current trip's itinerary*
* Format: `addactivity`
    * After entering the command, the application will prompt the user for the required activity details.
    * Prompt user for inputs:
        * `Activity Title:`
        * `Location: `
        * Start date in `yyyy-MM-dd` format
        * End date in `yyyy-MM-dd` format
* Example usage:
    ```text
    > addactivity
    Activity Title: Hiking
    Location: Mount Fuji
    Date (YYYY-MM-DD): 2026-12-10
    Start Time (HH:MM): 09:00
    End Time (HH:MM): 13:00
    ```
* Expected result:
    ```text
    ===========================================================
    Activity added to Japan Winter Trip:
    
    Hiking
    Location: Mount Fuji
    Date: 2026-12-10
    Time: 09:00 to 13:00
    ===========================================================
    ```
<br>

#### 2.2 Listing all Activities (shows itinerary)
Displays all activities in the itinerary of the opened trip
* Format: `listactivity`

* Expected result:
    ```text
     
    No  | Activity                  | Location        | Date         | Time
    -------------------------------------------------------------------------------------
    1   | Hiking                    | Mount Fuji      | 2026-12-10   | 09:00 to 13:00    
    2   | Swimming                  | Hotel           | 2026-12-10   | 19:00 to 20:00
    ```
<br>

#### 2.3 Editing an Ectivity
Edits one or more fields of an existing activity. Only specified fields are updated; unchanged fields remain as-is.
* Format: `editactivity`
    * After entering the command, the application will prompt the user for the required activity details.
    * Prompt users for inputs:
        * `Enter the index of the activity to edit:`
        * Leave any field blank to keep current values.
        * `New Title:`
        * `New Location:`
        * `New Date (YYYY-MM-DD):`
        * `New Start Time (HH:MM):`
        * `New End Time (HH:MM):`

* Example usage:
    ```text
    > editactivity
    Enter the index of the activity to edit: 2
    Leave any field blank to keep current values.
    New Title: Night Swim
    New Location:
    New Date (YYYY-MM-DD): 2026-12-11
    New Start Time (HH:MM): 16:00
    New End Time (HH:MM): 17:00
    ```
* Expected result:
    ```text
    ===========================================================
    Activity updated:
    
    Night Swim
    Location: Hotel
    Date: 2026-12-11
    Time: 16:00 to 17:00
    ===========================================================
    ```
<br>

#### 2.4 Deleting an Activity
Removes an activity from the itinerary.
* Format: `deleteactivity`
* After entering the command, the application will prompt the user for the required activity details.
    * Prompt users for inputs:
        * `Enter the index of the activity to delete:`

* Example usage:
    ```text
    > deleteactivity
  
    Itinerary for Japan Winter Trip:
    No  | Activity                  | Location        | Date         | Time              
    -------------------------------------------------------------------------------------
    1   | Hiking                    | Mount Fuji      | 2026-12-10   | 09:00 to 13:00    
    2   | Night Swim                | Hotel           | 2026-12-11   | 16:00 to 17:00    
    3   | Firework display          | Hokkaido        | 2026-12-13   | 19:00 to 20:00
    
  Enter the index of the activity to delete:  3
    ```
* Expected result:
    ```text
    Activity deleted:

    Firework display
    ```
<br>


### 3. Budget Management
*Note: budget is assigned for an activity. Thus, the activity has to be created first before setting the activity budget.*
*Note: All monetory values displayed are of the home currency of the user.*

#### 3.1 Adding a Budget for an Activity
Records the expected or planned cost of an activity. To change the budget for a specific activity, the same command is used with the new budget amount. To delete a budget, simply use the same command and set the budget for the activity to 0.

* Format: `setbudget`
    * After entering the command, the application will prompt the user for inputs:
        * `Enter the index of the activity to add a budget for:`
        * `Enter budget amount ($):`
      
* Example usage:
    ```text
    > setbudget
  
    Itinerary for Japan Winter Trip:
    No  | Activity                  | Location        | Date         | Time
    -------------------------------------------------------------------------------------
    1   | Hiking                    | Mount Fuji      | 2026-12-10   | 09:00 to 13:00    
    2   | Night Swim                | Hotel           | 2026-12-11   | 16:00 to 17:00   
  
    Enter the index of the activity to add a budget for:  1
    Enter budget amount ($):  200
    ```
* Expected result:
    ```text
    Added budget for Hiking: $200.00
    ```
<br>

#### 3.2 Viewing Budget Summary
Displays a comprehensive summary comparing your planned spending against your total budget.
* Format: `budgetsummary`

* Expected result:
    ```text
    ===========================================================
    Total trip budget: $220.00
    Total expense: $0.00
    Total remaining budget: $220.00
    
    Budget Breakdown:
    1. Hiking
       Total: $200.00 | Spent: $0.00 | Remaining: $200.00
    2. Night Swim
       Total: $20.00 | Spent: $0.00 | Remaining: $20.00
  
    ===========================================================
    ```
<br>


### 4. Expense Tracking

#### 4.1 Set the Actual Expense for an Activity
Records the actual amount spent on an activity. This allows users to compare the actual expense against the planned budget for each activity in budgetsummary.
*Note: A budget must be added for an activity before its expense can be set.*
*Note: The user needs to specify if home currency or foreign currency is being input. 1 for foreign currency and 0 for home currency*

* Format: `setexpense`
  * After entering the command, the application will prompt the user for inputs:
    * `Enter the activity index to set actual spending`
    * `Is the amount in foreign currency?`
    * `Enter amount spent ($):`
    
* Example usage:
    ```text
    Itinerary for Japan Winter Trip:
    No  | Activity                  | Location        | Date         | Time              
    -------------------------------------------------------------------------------------
    1   | Hiking                    | Mount Fuji      | 2026-12-10   | 09:00 to 13:00    
    2   | Night Swim                | Hotel           | 2026-12-11   | 16:00 to 17:00  
    
    Enter the activity index to set actual spending (or type 'exit' to cancel):  1
    Is the amount in foreign currency? (1 for Yes, 0 for No):  0
    Enter amount spent ($):  50
    ```
* Expected result:
    ```text
    Expense set for activity: Hiking. 
    Actual spending: $50.00
    ```
<br>

#### 4.2 Listing all Activity Expenses
Lists the actual expenses recorded for all activities in the currently opened trip. This allows users to review the spending for each activity and view the total expense incurred for the trip.

* Format: `listexpense`

* Expected result:
    ```text
    ===========================================================
    Expense Comparison for Japan Winter Trip:
    (Daily Limit: Not set)
    
    Date       | Activity           | Actual expense
    -----------------------------------------------------------
    2026-12-10 | Hiking             |     $50.00
    2026-12-11 | Night Swim         |      $0.00
    2026-12-18 | Road cycling       |     $10.00
    
    -----------------------------------------------------------
    Total expense: $60.00
    ===========================================================
    ```
<br>

### 4.3 Set a Daily Expense Limit for a Trip
Sets a daily spending limit for the currently opened trip. This allows users to manage their daily expenses more effectively and compare their total daily spending against the limit.

* Format: setdailylimit
  * After entering the command, the application will prompt the user for inputs:
    * Enter daily spending limit to set:

* Example usage:
    ```text
    Enter daily spending limit to set ($): 500
    ```
* Expected result: 
    ```text
    Daily spending limit has been set to $500.00.
    ``` 
<br>


## FAQ

**Q:** How do I transfer my data to another computer? 

**A:** You have two options! For individual trips, you can use the `exporttrip` command to generate a shareable `.txt` file, transfer that file via email or USB, and use `importtrip` on the other computer.
Alternatively, to transfer all your trips at once, simply copy the `traveltrio.txt` file located in the `data` folder next to your `.jar` application and move it to the new computer.

**Q:** What happens if I type a date incorrectly?

**A:** TravelTrio is forgiving! If you type `2026/05/01` instead of `2026-05-01`, the app will simply tell you the format is invalid and ask you for the date again without losing the rest of the information you already typed.

**Q:** Can I edit the `traveltrio.txt` file manually? 

**A:** Yes, but be careful! The file must be formatted in a certain way so that the data is able to load the next session. 
You must keep the exact prefixes (like `Trip:` or `Title:`) and avoid adding empty lines.

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
