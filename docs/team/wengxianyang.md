# Xian Yang (WengXianYang) - Project Portfolio Page

TravelTrio is a desktop travel management application optimized for users who prefer a Command Line Interface (CLI).
It helps organized and thrifty travelers manage complex trip itineraries, track activity-based budgets, and handle currency exchange rates within a single, lightweight interface.

Given below are my contributions to the project.

### **New features**
- Added `opentrip` command: opens an existing trip for activity/budget management.
- Added `deletetrip` command: removes a trip from the trip list permanently.
- Added `budget` command: assigns or adjusts budget information for a trip.
- Added `budgetsummary` command: displays current spend, remaining budget, and exchange status for the selected trip.
- Added `setbudget` command: sets the total allowable expenditure for a trip in the base currency.

### **Enhancements to Existing Features**
- Added ability to remove the budget when set to $0
- Added `setcurrency` command: updates the currency exchange rate for cross-currency expense tracking so that no manual conversion is needed.

### Code Contributed
[RepoSense Link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=wengxianyang&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=WengXianYang&tabRepo=AY2526S2-CS2113-F09-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### **Project Management**
* Reveiwed and merged pull requests.
* Added some testcases for easier CI/CD

### **Documentation**
* **User Guide: **

- Added details for Budget management, including:
  - set budget for a trip
  - view budget summary (total spent, remaining, exchange rate)
  - add expenses and track category budgets
  - update currency exchange rates for total currency conversions

* **Developer Guide: **
- Added new user stories:
- Added manual testing instructions for budget features:
- Added implementation note for Set Budget feature:

### **Community**
- Collaborated with team members during feature integration to minimize merge conflicts
