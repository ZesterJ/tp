# Xian Yang (WengXianYang) - Project Portfolio Page

**TravelTrio** is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI).
It helps organized and thrifty travelers manage complex trip itineraries, track activity-based budgets, and handle currency exchange rates within a single, lightweight interface.

Given below are my contributions to the project.

### **New Feature: Added the ability to open a trip (`opentrip`)**
**What it does:** Allows the user to select and open an existing trip to manage its specific activities and budget.

**Justification:** This feature is essential for context-switching, allowing users to clearly focus their actions (like adding activities or logging expenses) on one specific trip at a time without data overlap or confusion.

**Highlights:** This command acts as a critical prerequisite state for most itinerary and budget management commands, integrating deeply with the application's core navigation and logic.

### **New Feature: Added the ability to delete a trip (`deletetrip`)**
**What it does:** Allows the user to permanently remove a trip and all of its associated data from the master trip list.

**Justification:** Users need a way to manage their workspace by cleaning up cancelled, completed, or mistakenly created travel plans to keep the application organized.

**Highlights:** Requires safe state handling to ensure that removing a trip safely clears all nested data (activities, budgets, expenses) and handles edge cases, such as preventing the user from operating on a trip that has just been deleted.

### **New Feature: Added trip budget allocation (`budget`, `setbudget`)**
**What it does:** Allows the user to set the total allowable expenditure for a trip in the base currency (`setbudget`) and assign or adjust specific budget allocations (`budget`).

**Justification:** Financial planning is a crucial part of travel. These features allow thrifty travelers to establish a financial ceiling and allocate funds proactively to prevent overspending.

**Highlights:** Forms the foundational data structure for all expense tracking in the application. It required robust validation to handle flexible user inputs and integrates closely with the activity list.

### **New Feature: Added the ability to view a budget summary (`budgetsummary`)**
**What it does:** Displays a comprehensive, high-level overview of the selected trip's financial status, detailing current spend, remaining budget, and exchange status.

**Justification:** Users need a quick snapshot of their overall financial health during a trip to make informed, real-time spending decisions without having to manually calculate totals.

**Highlights:** This feature dynamically aggregates and calculates data across all individual activities and logged expenses, providing accurate, real-time financial insights in a formatted summary.

### **New Feature: Added the ability to track cross-currency expenses (`setcurrency`)**
**What it does:** Allows the user to update the currency exchange rate for the trip, enabling automatic cross-currency expense tracking. User can choose to input in foreign currency when setting budget and expense.

**Justification:** International travelers frequently deal with multiple currencies. Automating the conversion removes the need for manual math, reducing friction and calculation errors for the user.

**Highlights:** Seamlessly integrates with the expense logging system to automatically convert foreign expenditures into the user's base currency, ensuring all budget summaries remain accurate regardless of where the money was spent.

### **Enhancement: Added intuitive removal of financial limits**
**What it does:** Allows the user to easily remove a set budget by updating it to `$0`, and remove a daily spending limit by setting it to `0`.

**Justification:** Provides users with the flexibility to undo financial constraints or adapt to changing travel plans where strict limits are no longer necessary.

**Highlights:** Enhances the overall user experience by utilizing intuitive, natural inputs (zero values) to trigger deletion states, avoiding the need to bloat the CLI with separate "delete budget" or "delete limit" commands.

### **Enhancement: Added the ability to remove a daily spending limit**
**What it does:** Allows the user to remove an existing daily spending limit for a trip entirely by setting the limit value to `0`.

**Justification:** Provides users with the flexibility to adapt to changing travel plans where strict daily financial constraints are no longer necessary.

**Highlights:** Seamlessly integrates with the `setdailylimit` command logic, ensuring that removing the limit correctly updates the `listexpense` and `budgetsummary` tracking UI without breaking existing expense calculations.

### Code Contributed
[RepoSense Link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=wengxianyang&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=WengXianYang&tabRepo=AY2526S2-CS2113-F09-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### **Project Management**
* Reveiwed and merged pull requests.
* Added some testcases for easier CI/CD

### **Documentation**
**User Guide: **
  * Added details for Delete trip feature
  * Added details for Set Currency feature
  * Added details for Budget management, including:
    * set budget for a trip
    * view budget summary (total spent, remaining, exchange rate)
    * add expenses and track category budgets
    * update currency exchange rates for total currency conversions

**Developer Guide: **
  * Added new user stories:
  * Added manual testing instructions for budget features:
  * Added implementation note for Set Budget feature:

### **Community**
* Collaborated with team members during feature integration to minimize merge conflicts
