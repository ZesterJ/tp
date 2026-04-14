# Jian Wei (ZesterJ) - Project Portfolio Page

**TravelTrio** is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI).  
It helps organized and thrifty travelers manage complex trip itineraries, track activity-based budgets, and handle currency exchange rates within a single, lightweight interface.

Given below are my contributions to the project.

---

### **New Feature: Trip Management System**

- **What it does:**  
  Allows users to create new trips and list all existing trips through CLI commands.

- **Justification:**  
  Forms the core foundation of the application, enabling users to manage multiple travel plans in an organized manner.

- **Highlights:**  
  - Supports dynamic creation and storage of multiple trips  
  - Enables users to quickly view all planned trips  

- **Implementation:**  
  - Designed and implemented an abstract `Trip` and `TripList` class to serve as the base for different trip types  
  - Implemented commands for creating (`addtrip`) and listing (`listtrip`) trips  
  - Integrated trip storage with the application's data persistence layer  

---

### **Core Classes: Trip Model and Command Structure**

- **What it does:**  
  Provides the foundational model classes and command structure for trip management functionality.

- **Justification:**  
  Establishes a clean, extensible architecture for managing trips with proper separation of concerns between model and command layers.

- **Highlights:**  
  - `Trip` class encapsulates trip details (name, dates) with associated `ActivityList`, `BudgetList`, and `PackingList`  
  - `TripList` manages multiple trips with operations for add, remove, get, and keyword search  
  - `TripCommand` abstract base class provides common structure for all trip-related commands  
  - Supports file export via `toFileFormat()` and list display via `formatForList()` methods  

- **Implementation:**  
  - Created `Trip.java` model class with fields for name, start date, end date, and composition of activity/budget/packing lists  
  - Implemented `TripList.java` with `ArrayList<Trip>` storage and methods: `add()`, `remove()`, `get()`, `findTrips()`, `contains()`  
  - Designed `TripCommand.java` as abstract base class with `tripList` field and abstract `execute()` method  
  - Implemented `AddTripCommand.java` with validation for non-empty name, date presence, and start-before-end logic  
  - Implemented `ListTripCommand.java` to display all trips with formatted output including total spent when budget exists  

---

### **New Feature: Schedule Conflict Detection**

- **What it does:**  
  Detects and prevents overlapping activities within the same trip.

- **Justification:**  
  Helps users avoid scheduling conflicts and ensures a realistic, executable itinerary.

- **Highlights:**  
  - Automatically checks for time overlaps when adding activities  
  - Provides feedback to users when conflicts are detected  

- **Implementation:**  
  - Utilized Java's `LocalDate` and `LocalTime` classes for accurate and reliable time representation  
  - Implemented conflict-checking logic by comparing activity time intervals  
  - Integrated validation into the activity creation workflow  

---

### **New Feature: Next Activity Command**

- **What it does:**  
  Displays the next upcoming activity in a trip based on the current date and time.

- **Justification:**  
  Helps users quickly identify what activity is coming up next without manually scanning through the entire itinerary.

- **Highlights:**  
  - Filters activities to find those scheduled after the current time  
  - Returns the earliest upcoming activity in a formatted display  

- **Implementation:**  
  - Used `LocalDateTime` to compare activity start times against the current time  
  - Iterates through all activities to find the one with the earliest future start time  
  - Integrated with the activity display formatting for consistent output  

---

### **New Feature: Budget Chart Command**

- **What it does:**  
  Generates a visual bar chart showing budget usage percentage for each activity in a trip.

- **Justification:**  
  Provides users with an at-a-glance view of how much of their budget has been consumed for each activity, enabling better financial tracking during travel.

- **Highlights:**  
  - Displays budget usage as a visual bar chart (e.g., `[#####-----] 50%`)  
  - Shows percentage calculation for each activity's budget vs actual spending  
  - Skips activities without assigned budgets  

- **Implementation:**  
  - Calculated percentage as `(spent / total) * 100` for each activity budget  
  - Created a 10-segment bar representation using `#` for filled and `-` for empty segments  
  - Integrated with the `Budget` and `ActivityList` models to retrieve spending data  

---

### **New Feature: Activity Remarks**

- **What it does:**  
  Allows users to add custom remarks or notes to individual activities.

- **Justification:**  
  Enables users to record important details, reminders, or context for activities (e.g., "Book tickets in advance", "Meeting point: Gate 5").

- **Highlights:**  
  - Supports adding remarks to any existing activity by index  
  - Remarks are displayed alongside activity details in listings  

- **Implementation:**  
  - Added `remark` field to the `Activity` class with getter/setter methods  
  - Updated `formatForDisplay()` and `formatForTableRow()` to include remark information  
  - Implemented `AddRemarkCommand` to handle remark assignment with validation  

---

### **New Feature: Packing List Management**

- **What it does:**  
  Provides a complete packing list system for users to track items they need to pack for their trip.

- **Justification:**  
  Helps users stay organized by maintaining a checklist of items to pack, ensuring nothing is forgotten before departure.

- **Highlights:**  
  - Add new items to the packing list  
  - List all items with packed/unpacked status indicators (`[X]` or `[ ]`)  
  - Mark items as packed/unpacked  
  - Delete items from the packing list  

- **Implementation:**  
  - Designed `PackingItem` class with name and packed status fields  
  - Implemented `PackingList` class to manage collection of packing items with file serialization support  
  - Created command classes: `AddItemCommand`, `ListItemCommand`, `CheckItemCommand`, `DeleteItemCommand`  
  - Integrated packing list storage with the trip data persistence layer  

---

### **Enhancements to Existing Features**

- **Activity Listing by Timestamp**  
  - Improved activity listing by sorting activities based on their scheduled date and time  
  - Enhances readability and usability of itineraries  

- **Standardized Date and Time Handling**  
  - Adopted Java’s `LocalDate` and `LocalTime` across the application  
  - Ensures consistency and reduces errors in time-based operations  

- **Project Structure Refactoring**  
  - Restructured folders to improve code modularity and maintainability  
  - Separated concerns (e.g., commands, model, storage) for better scalability  

- **Active Trip Context in Command Prompt**
  -  Enhanced the command prompt to display the currently opened trip (e.g., `[Trip Name] >`)  
  - Provides clear visibility of the active working context  
  - Reduces user errors when managing multiple trips  
  - Dynamically updates the prompt based on application state (e.g., opening or closing a trip) 

- **Activity Table Display with Remarks**
  - Enhanced activity table rows to include remark column  
  - Updated formatting logic to display remarks consistently in listings  

---

### **Code Contributed**
[RepoSense Link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=zesterj&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=ZesterJ&tabRepo=AY2526S2-CS2113-F09-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

- Contributed across core modules including `Trip`, activity scheduling, budget management, packing list system, and parser logic.
- Key files: `Trip.java`, `TripList.java`, `TripCommand.java`, `AddTripCommand.java`, `ListTripCommand.java`, `NextActivityCommand.java`, `BudgetChartCommand.java`, `AddRemarkCommand.java`, `PackingList.java`, `PackingItem.java`, `AddItemCommand.java`, `ListItemCommand.java`, `CheckItemCommand.java`, `DeleteItemCommand.java`

---

### **Project Management**

- Assisted in organizing project structure and improving codebase maintainability  
- Contributed to integration of features across multiple components  

---

### **Documentation**

* **User Guide:**
  - Documented commands for trip creation, listing, and activity scheduling  
  - Provided examples for handling scheduling conflicts  
  - Added documentation for `next` command to find upcoming activities  
  - Documented `budgetchart` command with visual bar chart examples  
  - Added remarks feature documentation with usage examples  
  - Documented complete packing list management commands (`additem`, `listitem`, `checkitem`, `deleteitem`)  

* **Developer Guide:**
  - Documented implementation of schedule conflict detection logic  
  - Explained design of `Trip` abstraction and usage of `LocalDate` and `LocalTime`  
  - Documented the `NextActivityCommand` implementation and time comparison logic  
  - Explained `BudgetChartCommand` visual bar generation algorithm  
  - Documented the packing list system architecture including `PackingList` and `PackingItem` classes  
  - Added remarks integration into the `Activity` model class  

---

### Community

- Collaborated with team members during feature integration  
- Provided feedback on code structure and design decisions  
