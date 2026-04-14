# Wai Yan (wy208) - Project Portfolio Page

**TravelTrio** is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI). 
It helps organized and thrifty travelers manage complex trip itineraries, track activity-based budgets, and handle currency exchange rates within a single, lightweight interface.

Given below are my contributions to the project.

### **New Feature: State-Aware Storage System** (PR #70)
* Automatically persists all Trip, Activity, and Budget data to a local `.txt` file and reconstructs hierarchical relationships upon startup.
* This is the core utility for v1.0. It ensures data persistence and allows for complex relationship mapping (e.g., linking a specific Budget to a specific Activity) that a standard flat-file parser cannot handle.
* Implemented a Context-Tracking Parser that maintains internal state variables (`currentTrip`, `lastActivity`) to correctly map nested data.
* Developed a Robust Error-Handling mechanism using `TravelTrioException` to prevent the application from crashing when encountering corrupted or manually edited text files.
* Designed the file format to be Human-Readable, allowing advanced users to perform quick manual edits in a standard text editor.

### **Enhancements to Existing Features**
* UI Class Refinement: Standardized the CLI output format to include borders and clear headers, improving the "Scannability" of long itinerary lists for the user. (PR #66)
* Command Processor Integration: Optimized the logic between the functions parsing commands in `Ui` and `Command` classes to ensure that the `Storage` object is updated immediately after any data-modifying command. (PR #66)
* Java Logging: Integrated `java.util.logging` to record background file I/O operations, which assisted the team in debugging cross-platform file access issues. (PR #70)

### Code Contributed
[RepoSense Link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=wy208&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=wy208&tabRepo=AY2526S2-CS2113-F09-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### **Project Management**
* Managed release `v1.0` on Github

### **Documentation**
* User Guide:
    * Added FAQ on Manual Editing of data file to warn users of the risk of corrupting the file.
    * Updated several sections to resolve inconsistencies identified during the Practical Exam Dry-run (PE-D)
* Developer Guide:
    * Created Architecture Diagram, Class Diagrams and Object Diagrams to illustrate the internal design and runtime state of TravelTrio.
    * Authored the Storage Component section, detailing the architectural design of the state-aware parser.
    * Created UML Sequence Diagrams using PlantUML to visualize the hierarchical loading process and object creation during startup.

### **Community**
* PR Reviews: Reviewed and approved Pull Requests (PRs), focusing on identifying logic inconsistencies and naming convention errors, ensuring the codebase remained cohesive and readable.