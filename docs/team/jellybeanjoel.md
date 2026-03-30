# Joel (JellybeanJoel) - Project Portfolio Page

**TravelTrio** is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI).
It helps organized and thrifty travelers manage complex trip itineraries, track activity-based budgets, and handle currency exchange rates within a single, lightweight interface.

Given below are my contributions to the project.

### **New Feature: Advanced Activity Management and Trip Sharing**
* The Edit feature supports partial updates, allowing users to modify specific fields (like time or location) without retyping the entire activity.
* The Delete feature safely extracts activities from the itinerary while maintaining strict data integrity.
* Created a `ExportTripCommand` to save a single trip to a standalone `.txt` file and an `ImportTripCommand` to seamlessly parse and merge shared files back into the user's active `TripList`. This maximised code reuse by leveraging on the existing `Storage` parsing logic.

### **Enhancements to Existing Features**
* Help Command: Designed and implemented the `HelpCommand`, providing a centralized and easily updatable reference guide directly within the CLI to smoothly onboard new users and serve as a quick reference for power users.
* UI Error Recovery: Replace the data-entry flow in the `Ui` and `CommandProcessor` classes to utilize robust `while(true)` loops and custom parsers (`promptDate`, `promptTime`). This improved the user experience by allowing users to simply re-type a specific faulty field instead of forcing them to restart an entire multi-step command from scratch.

### Code Contributed
[RepoSense Link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=jellybeanjoel&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=JellybeanJoel&tabRepo=AY2526S2-CS2113-F09-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### **Project Management**

### **Documentation**
* **User Guide:**
    * Authored a document to illustrate the step-by-step process on how to use the application.

* **Developer Guide:** 
    * Authored the comprehensive design document for the Edit Activity feature.
    * Created the PlantUML Sequence Diagram to visually illustrate the step-by-step interaction between the `Ui` prompting, `CommandProcessor` routing, and internal `ActivityList` mutation.

### **Community**
* PR Reviews: Reviewed and approved Pull Requests (PRs), focusing on identifying logic inconsistencies and naming convention errors, ensuring the codebase remained cohesive and readable.
