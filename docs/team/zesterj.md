# Jian Wei (ZesterJ) - Project Portfolio Page

TravelTrio is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI).  
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

---

### **Code Contributed**
[RepoSense Link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=zesterj&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=ZesterJ&tabRepo=AY2526S2-CS2113-F09-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

- Contributed across core modules including `Trip`, activity scheduling, and parser logic.

---

### **Project Management**

- Assisted in organizing project structure and improving codebase maintainability  
- Contributed to integration of features across multiple components  

---

### **Documentation**

* **User Guide:**
  - Documented commands for trip creation, listing, and activity scheduling  
  - Provided examples for handling scheduling conflicts  

* **Developer Guide:**
  - Documented implementation of schedule conflict detection logic  
  - Explained design of `Trip` abstraction and usage of `LocalDate` and `LocalTime`  

---

### **Community**

- Collaborated with team members during feature integration  
- Provided feedback on code structure and design decisions  
