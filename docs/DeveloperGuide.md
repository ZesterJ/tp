# Developer Guide

## Acknowledgements

- The command-step-prompting logic was inspired by the [AB3](https://seedu.org/addressbook-level3/) project architecture.
- UML diagrams generated with [PlantUML](https://plantuml.com/).
- AI was used for trivial debugging.

## Design
TravelTrio follows a multi-layered architecture, separating concerns into four main components. This design follows the Separation of Concerns (SoC) principle, ensuring that changes in the user interface do not affect the core data logic or storage mechanisms.
**Architecture Diagram**:
![img.png](diagrams/ArchitectureDiagram.png)
- `UI`: Handles all user interactions, input parsing (initial stage), and printing formatted feedback.
- `Logic`: Contains the `Command` classes. It processes the user's intent, validates constraints, and manipulates the Model. 
- `Model`: Holds the data structures (Trips, Activities, etc.) and enforces business rules (e.g., conflict detection for overlapping activities). 
- `Storage`: Manages file I/O operations, ensuring data persists in the hierarchical `.txt` format. It is responsible for translating the in-memory `Model` into a storable format.

### Architecture 

### Model 
- The core logic of TravelTrio is built around a hierarchical model where Trip serves as the aggregate root. This ensures that itinerary, financial, and checklist data are strictly encapsulated.
- **Class diagram:**
![img.png](diagrams/ModelClassDiagram.png)

Key Structural Components:
- `Trip`: The central entity. It owns an `ActivityList` (itinerary), `BudgetList` (financials), and `PackingList` (checklist).
- `Storage`: Uses a dependency-based relationship to reconstruct the model hierarchy from local text files.
- `BudgetList`: Uses a `Map<Activity, Budget>` to maintain a 1-to-1 association between scheduled events and their allocated funds.

Object Diagram
- The following diagram illustrates an example of the system state when a user is managing trips. It demonstrates how instances of `Activity`, `Budget`, and `PackingItem` are linked together under a specific Trip instance.
![img.png](diagrams/ModelObjectDiagram.png)
- Scenario Details:
  - Trip Management: The user has one trip in their TripList. The "Japan Trip 2024" is currently open. 
  - Itinerary Planning: The open trip contains three activities: "Mount Fuji Day Trip", "Tokyo Tower Visit" and "Sushi Making Class". 
  - Financial Tracking: 
    - A budget of $300.00 is specifically linked to the Mount Fuji Day Trip activity. 
    - A budget of $100.00 is linked to the Tokyo Tower Visit activity.
    - Actual expense is also tracked for both activities, with $280.00 for the Mount Fuji Day Trip activity and $85.00 for the Tokyo Tower Visit activity.
    - No budget is set for the Sushi Making Class activity.
  - Packing Progress: The packing list for the current trip includes 
    - "Camera" (already packed) 
    - "Winter Jacket" (not yet packed)
    - "Travel Adapter" (not yet packed) and
    - "Passport" (already packed)

### Command Hierarchy
Taking trip commands as an example, in the diagram below, we see that all trip-related actions inherit from an abstract TripCommand class.
![img.png](diagrams/CommandTripClassDiagram.png)
Key Design Features:
- Inheritance: Each specific action (e.g., `AddTripCommand`, `OpenTripCommand`) extends the base `TripCommand`. They all implement the `execute()` method, allowing the application to run any trip command without knowing its specific type at runtime.

External Dependencies:
- TripList: Most commands interact with the `TripList` to add or remove trip data.
- Storage: The `ExportTripCommand` and `ImportTripCommand` specifically maintain an association with the `Storage` component. This allows them to handle the complex task of reading from or writing to external `.txt` files independently of the main application loop.

This hierarchy is also observed in the other command classes (e.g., `ActivityCommand`, `BudgetCommand`)

### Packing List Component
**Implementation**<br>
The packing list feature allows users to create and manage a checklist of items to pack for their trip. Each item can be marked as packed or unpacked to track packing progress.

The feature mainly involves the following classes:
- `PackingList` — Stores and manages all `PackingItem` objects for a trip.
- `PackingItem` — Represents a single item with a name and packed status.
- `AddItemCommand` — Adds a new item to the packing list.
- `ListItemCommand` — Displays all items in the packing list with their status.
- `CheckItemCommand` — Marks an item as packed.
- `DeleteItemCommand` — Removes an item from the packing list.
- `Trip` — Owns the `PackingList` as part of its data structure.

The `PackingList` maintains an `ArrayList<PackingItem>` to store items. Each `PackingItem` has a `name` (String) and `isPacked` (boolean) field. New items are initially marked as unpacked (`isPacked = false`).

The `PackingItem#toFileFormat()` method serializes items as `"1|name"` for packed items and `"0|name"` for unpacked items, allowing the storage component to persist packing data.

Given below is an example usage scenario and how the packing list mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened `Trip` contains a `PackingList`, which may initially be empty.

Step 2. The user executes an `additem` command and is prompted to enter the item name.

Step 3. The application collects the user input, creates a new `PackingItem` with the provided name (initially marked as unpacked), and creates an `AddItemCommand`.

Step 4. The user command is executed through `AddItemCommand#execute()`. The command validates that the item name is not empty, then calls `PackingList#addItem(item)` to add the item to the list.

Step 5. A success message is returned to the user, showing that the item has been added.

Step 6. The user can type `listitems` to view all packing items with their packed/unpacked status displayed as `[X]` for packed and `[ ]` for unpacked.

Step 7. The user can type `checkitem` and select an item index to mark it as packed. The `CheckItemCommand#execute()` calls `PackingItem#markPacked()` on the selected item.

Step 8. The user can type `deleteitem` to remove an item from the list. The `DeleteItemCommand#execute()` validates the index and calls `PackingList#remove(index)`.

If the command input is invalid (such as an out-of-bounds index or empty item name), or if no trip is currently opened, the command will not be executed successfully.

**Sequence Diagram:**

The following sequence diagram shows how an operation to add a packing item goes:
![img.png](diagrams/AddItemSequenceDiagram.png)

## Implementation

### Add Activity to Itinerary feature
**Implementation**<br>
The `addactivity` feature is implemented using `AddActivityCommand`. Its purpose is to add a new `Activity` into the `ActivityList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- AddActivityCommand — adds a new Activity into the activity list.
- Activity — represents a single activity with fields such as name, location, date, start time, and end time.
- ActivityList — stores all Activity objects belonging to a trip.
- Trip — owns the corresponding ActivityList.

When the user invokes `addactivity`, the system first collects the required input fields from the user. These include the activity title, location, date, start time, and end time. The input is validated before command execution. In particular:
- the activity date must fall within the currently opened trip’s date range
- the end time must not be earlier than the start time

After validation succeeds, the system constructs an `AddActivityCommand` object with the current trip’s `ActivityList` and the activity details provided by the user.

`AddActivityCommand#execute()` is called which appends the activity to the list and a success message is returned.


**Given below is an example usage scenario and how the add activity mechanism behaves at each step.**

Step 1. The user opens a trip, for example Japan Trip. The opened Trip contains an `ActivityList`, which may initially be empty.

Step 2. The user executes an `addactivity` command with the relevant activity details, such as activity name, location, date, and time.

Step 3. The application parses the user input and constructs a new Activity object containing the specified details.

Step 4. The application creates an `AddActivityCommand`, passing in the current trip’s ActivityList and the newly created Activity.

Step 5. The user command is executed through `AddActivityCommand#execute()`. The command calls `ActivityList#add(activity)`, causing the new `activity` to be stored in the list.

Step 6. A success message is returned to the user, showing that the activity has been successfully added.

If the command input is invalid, or if no trip is currently opened, the command will not be executed successfully and no activity will be added.

**Sequence Diagram:**

- The following sequence diagram is a simplified version that shows the success path of add activity operation, assuming that the user gives the correct inputs:
![img.png](diagrams/AddActivitySequenceDiagram.png)



### Edit Activity feature 
**Implementation**<br>
The `editactivity` feature is facilitated by `EditActivityCommand`. It allows the user to modify the details of an existing `Activity` within the `ActivityList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- EditActivityCommand — updates the specified fields of an existing Activity. 
- Activity — represents a single activity whose fields (name, location, date, start time, end time) are being updated. 
- ActivityList — stores all Activity objects belonging to a trip and is used to retrieve the target Activity.
- Trip — owns the corresponding ActivityList.

The `EditActivityCommand` receives the target `ActivityList` of the currently opened `Trip`, the index of the activity to edit and the new details to be updated.
When `EditActivityCommand#execute()` is called, the target activity is retrieved, its specified fields are updated via setter methods, and a success message is returned.

Given below is an example usage scenario and how the edit activity mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened `Trip` contains an `ActivityList` with existing activities.

Step 2. The user executes an `editactivity` command and is prompted to provide the index of the activity to edit, along with new details such as a new title, location, date, or time.

Step 3. The application collects the user input, capturing the index and any new field values provided (leaving blank inputs as unchanged).

Step 4. The application creates an `EditActivityCommand`, passing in the current trip’s `ActivityList`, the target index, and the newly provided details.

Step 5. The user command is executed through `EditActivityCommand#execute()`. The command retrieves the target `Activity` from the list using the provided index and calls its respective setter methods for any non-empty fields.

Step 6. A success message is returned to the user, showing that the activity has been successfully updated.

If the command input is invalid (such as an out-of-bounds index), or if no trip is currently opened, the command will not be executed successfully and the activity will remain unchanged.

**Sequence Diagram:**

The following sequence diagram shows how an operation to edit an activity goes:
![img.png](diagrams/EditActivitySequenceDiagram.png)

### Add Trip feature
**Implementation**<br>
The `addtrip` feature is facilitated by `AddTripCommand`. It allows the user to create a new `Trip` and add it to the `TripList`.

The feature mainly involves the following classes:
- AddTripCommand — adds a new Trip into the trip list.
- Trip — represents a single trip with fields such as name, start date, and end date.
- TripList — stores all Trip objects created by the user.

The `AddTripCommand` receives the shared `TripList` and the trip details (name, start date, end date) to be added. 
When `AddTripCommand#execute()` is called, the command validates the inputs, creates a new `Trip`, adds it to the list, and returns a success message.

Given below is an example usage scenario and how the add trip mechanism behaves at each step.

Step 1. The user starts the application. The application loads an existing `TripList`, which may initially be empty.

Step 2. The user executes an `addtrip` command with the relevant trip details, such as trip name, start date, and end date.

Step 3. The application collects the user input through the `Ui` and passes it to the `CommandProcessor`.

Step 4. The application creates an `AddTripCommand`, passing in the current `TripList` and the provided trip details.

Step 5. The user command is executed through `AddTripCommand#execute()`. The command validates that the name is not empty, the dates are provided, and that the start date is not later than the end date. A new `Trip` object is then created.

Step 6. The command calls `TripList#add(trip)`, causing the new `trip` to be stored in the list.

Step 7. A success message is returned to the user, showing that the trip has been successfully added.

If the command input is invalid, the command will not be executed successfully and no trip will be added.

**Sequence Diagram:**

The following sequence diagram shows how an operation to add a trip goes:
![img.png](diagrams/AddTripSequenceDiagram.png)

### Set Budget feature
**Implementation**<br>

The `setbudget` feature is facilitated by `SetBudgetCommand`. It allows the user to add a budget for an existing `Activity` within the `ActivityList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- SetBudgetCommand — adds a new Budget for the specified Activity.
- Budget — represents the budget object for an activity.
- BudgetList — stores all Budget objects for activities in a trip.
- Activity — represents a single activity for which the budget is set.
- ActivityList — stores all Activity objects belonging to a trip.
- Trip — owns the corresponding ActivityList and BudgetList.
- CommandProcessor — handles the user input, ensures a trip is open, and orchestrates the creation of the command.
- Ui — handles all interactions with the user, such as prompting for the activity index and budget amount.
- Storage — handles all saving and loading of txt file, allowing for saving of data across sessions. 

The `SetBudgetCommand` receives the target `BudgetList`, `ActivityList`, the specific `Activity`, and the budget amount.
When `SetBudgetCommand#execute()` is called, a new `Budget` is created and added to the `BudgetList` for the activity, and a success message is returned.

Given below is an example usage scenario and how the set budget mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened Trip contains an `ActivityList` with existing activities and a `BudgetList` which may be empty.

Step 2. The user executes a `setbudget` command, followed by seeing the list of all current activities.

Step 3. The application prompts the user to select an activity from the list and enter the budget amount.

Step 4. The application creates a `SetBudgetCommand`, passing in the current trip’s `BudgetList`, `ActivityList`, the selected `Activity`, and the budget amount.

Step 5. The user command is executed through `SetBudgetCommand#execute()`. The command creates a new `Budget` object and calls `BudgetList#addBudget(activity, budget)` to store it.

Step 6. A success message is returned to the user, showing that the budget has been successfully added.

If the command input is invalid, or if no trip is currently opened, the command will not be executed successfully and no budget will be added.

**Sequence Diagram:**

The following sequence diagram shows how an operation to set budget goes:
![img.png](diagrams/SetBudgetSequenceDiagram.png)

### Storage Component
**Implementation**<br>
The `Storage` component handles the data persistence of the application. It is responsible for saving the TripList to a 
local text file and reconstructing those objects when the application restarts.

The feature mainly involves the following classes:
- `Storage` — The main logic class that manages file I/O operations and hierarchical parsing.
- `TripList` — The top-level container that is populated during the loading process.
- `Trip` / `Activity` / `Budget` — Model classes that provide `toFileFormat()` methods to convert objects into storable strings.
- `Scanner` — Used to parse the text file line-by-line within a try-with-resources block.

The `Storage#load()` method implements a **State-Aware Parser**. Because the text file is hierarchical (Activities belong to Dates, which belong to Trips), 
the component maintains internal context variables (`currentTrip`, `currentDate`, `lastActivity`) during execution to ensure child objects are linked to the correct parent objects.

Given below is an example usage scenario and how the storage mechanism behaves during the loading phase.

Step 1. The user starts the application. The `Main` class initializes `Storage` with a file path (e.g., `data/traveltrio.txt`) and calls `load()`.

Step 2. `Storage#load()` checks if the file exists. If not, it returns an empty `TripList`. If it exists, it calls the private `parseFileToTripList(file)` method.

Step 3. Inside the parser, a `Scanner` is opened. As it reads the file line-by-line, it detects a **`Trip:`** header and calls `loadTripDetails(...)`. This creates a new `Trip` object and immediately checks the next line for **`Exchange Rate:`** metadata to set the trip's financial context.

Step 4. When a line starting with **`=== Date:`** is found, the `currentDate` string is updated. This acts as a header for all activities that follow.

Step 5. When a **`Title:`** line is found, the parser calls `loadActivityDetails(...)`. This helper method "consumes" the next three lines (Location, Start Time, and End Time) to construct an `Activity` object, which is then added to the `currentTrip`.

Step 6. If a **`Budget set:`** line is found, the parser calls `loadBudgetDetails(...)`. It reads the total budget and actual expenses and links them to the `lastActivity` using an **assertion** to ensure no budget data is orphaned.

Step 7. Once the end of the file is reached, the fully populated `TripList` is returned to the `Main` controller.

If the parser encounters a line it does not recognize (and is not a known summary line like `Total Budget:`), it triggers a `ui.showError` to alert the user of potential file corruption.
If a line is encountered that does not match any expected prefix or formatting, a `TravelTrioException` is thrown, alerting the user to the specific line that requires manual correction.

**Sequence Diagram:**

The following sequence diagram shows how the `Storage` component interacts with the `Scanner` and `Model` classes to load data:
![img.png](diagrams/StorageSequenceDiagram.png)

### Add Packing Item feature 
**Implementation**<br>
The `additem` feature is facilitated by `AddItemCommand`. It allows the user to add a new `PackingItem` into the `PackingList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- `AddItemCommand` — adds a new `PackingItem` into the packing list.
- `PackingItem` — represents a single packing item with a name and packed status.
- `PackingList` — stores all `PackingItem` objects belonging to a trip.
- `Trip` — owns the corresponding `PackingList`.

The `AddItemCommand` receives the target `PackingList` and the item name to be added. 
When `AddItemCommand#execute()` is called, the command validates that the item name is not empty, creates a new `PackingItem`, adds it to the list, and returns a success message.

Given below is an example usage scenario and how the add packing item mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened `Trip` contains a `PackingList`, which may initially be empty.

Step 2. The user executes an `additem` command and is prompted to enter the item name.

Step 3. The application collects the user input through the `Ui` and passes it to the `CommandProcessor`.

Step 4. The application creates an `AddItemCommand`, passing in the current trip's `PackingList` and the provided item name.

Step 5. The user command is executed through `AddItemCommand#execute()`. The command validates that the name is not empty or null, trims whitespace, creates a new `PackingItem` object (initially marked as unpacked), and calls `PackingList#addItem(item)`.

Step 6. A success message is returned to the user, showing that the item has been successfully added.

If the command input is invalid (empty or null item name), the command will not be executed successfully and no item will be added.

**Sequence Diagram:**

The following sequence diagram shows how an operation to add a packing item goes:
![img.png](diagrams/AddItemSequenceDiagram.png)

### List Packing Items feature 
**Implementation**<br>
The `listitems` feature is facilitated by `ListItemCommand`. It allows the user to view all `PackingItem` objects in the `PackingList` of the currently opened `Trip`, along with their packed/unpacked status.

The feature mainly involves the following classes:
- `ListItemCommand` — retrieves and formats all items from the packing list for display.
- `PackingList` — stores all `PackingItem` objects and provides access to them.
- `PackingItem` — represents a single item whose status is displayed.
- `Trip` — owns the corresponding `PackingList`.

The `ListItemCommand` receives the target `PackingList`. When `ListItemCommand#execute()` is called, it checks if the list is empty. If not, it iterates through all items and formats them with their status (`[X]` for packed, `[ ]` for unpacked) and index numbers.

Given below is an example usage scenario and how the list packing items mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened `Trip` contains a `PackingList` with existing items.

Step 2. The user executes a `listitems` command.

Step 3. The application creates a `ListItemCommand`, passing in the current trip's `PackingList`.

Step 4. The user command is executed through `ListItemCommand#execute()`. The command checks if the list is empty. If empty, it returns a message indicating the packing list is empty.

Step 5. If the list contains items, the command builds a formatted string displaying each item with its index and status (e.g., "1. [X] Passport", "2. [ ] Winter Jacket").

Step 6. The formatted list is displayed to the user.

**Sequence Diagram:**

The following sequence diagram shows how an operation to list packing items goes:
![img.png](diagrams/ListItemSequenceDiagram.png)

### Check Packing Item feature 
**Implementation**<br>
The `checkitem` feature is facilitated by `CheckItemCommand`. It allows the user to mark an existing `PackingItem` as packed in the `PackingList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- `CheckItemCommand` — marks a specified `PackingItem` as packed.
- `PackingItem` — represents a single packing item whose status is being updated.
- `PackingList` — stores all `PackingItem` objects and is used to retrieve the target item.
- `Trip` — owns the corresponding `PackingList`.

The `CheckItemCommand` receives the target `PackingList` and the 1-based index of the item to mark as packed. When `CheckItemCommand#execute()` is called, it validates the index, retrieves the target `PackingItem`, calls `markPacked()` on it, and returns a success message.

Given below is an example usage scenario and how the check packing item mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened `Trip` contains a `PackingList` with existing items.

Step 2. The user executes a `checkitem` command and is prompted to enter the index of the item to mark as packed.

Step 3. The application collects the user input, capturing the index.

Step 4. The application creates a `CheckItemCommand`, passing in the current trip's `PackingList` and the target index.

Step 5. The user command is executed through `CheckItemCommand#execute()`. The command validates that the index is within bounds (1 to list size), retrieves the target `PackingItem` using `PackingList#get(index - 1)`, and calls `PackingItem#markPacked()`.

Step 6. A success message is returned to the user, showing that the item has been marked as packed.

If the command input is invalid (such as an out-of-bounds index), or if no trip is currently opened, the command will not be executed successfully and the item will remain unchanged.

**Sequence Diagram:**

The following sequence diagram shows how an operation to check a packing item goes:
![img.png](diagrams/CheckItemSequenceDiagram.png)

### Delete Packing Item feature 
**Implementation**<br>
The `deleteitem` feature is facilitated by `DeleteItemCommand`. It allows the user to remove an existing `PackingItem` from the `PackingList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- `DeleteItemCommand` — removes a specified `PackingItem` from the packing list.
- `PackingItem` — represents a single packing item that is being removed.
- `PackingList` — stores all `PackingItem` objects and is used to retrieve and remove the target item.
- `Trip` — owns the corresponding `PackingList`.

The `DeleteItemCommand` receives the target `PackingList` and the 1-based index of the item to delete. When `DeleteItemCommand#execute()` is called, it validates the index, retrieves the target item's name for the success message, removes it from the list, and returns a confirmation message.

Given below is an example usage scenario and how the delete packing item mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened `Trip` contains a `PackingList` with existing items.

Step 2. The user executes a `deleteitem` command and is prompted to enter the index of the item to delete.

Step 3. The application collects the user input, capturing the index.

Step 4. The application creates a `DeleteItemCommand`, passing in the current trip's `PackingList` and the target index.

Step 5. The user command is executed through `DeleteItemCommand#execute()`. The command validates that the index is within bounds (1 to list size), retrieves the target `PackingItem` using `PackingList#get(index - 1)`, captures its name, and calls `PackingList#remove(index - 1)`.

Step 6. A success message is returned to the user, showing that the item has been removed.

If the command input is invalid (such as an out-of-bounds index), or if no trip is currently opened, the command will not be executed successfully and no item will be removed.

**Sequence Diagram:**

The following sequence diagram shows how an operation to delete a packing item goes:
![img.png](diagrams/DeleteItemSequenceDiagram.png)

## Product scope
### Target user profile

TravelTrio is built for users who prefer a fast, keyboard-driven interface over a traditional GUI.
Our core users include:
- The Organized User — Someone who needs to see all their travel plans in one centralized list and wants to append new activities quickly without navigating complex menus.
- The Thrifty User — A traveler who is conscious of their budget and wants to track actual spending against planned estimates in real-time.
- The Tech-Savvy Planner — A user (likely a student or developer) who is comfortable with a Command Line Interface (CLI) and values a lightweight, offline-first application.
- The Frequent Traveler — Someone managing multiple trips who needs a reliable way to switch between different itineraries and export/import data for backup.

### Value proposition

TravelTrio provides a high-speed, distraction-free environment for itinerary management. It is intuitive and bridges the gap between a messy notes app and a complex spreadsheet by offering the following benefits:
- Specific commands to handle trips, activities, and budgets separately but cohesively.
- Instant calculation of remaining budgets and expenses, specifically catering to the "Thrifty User."
- Easy sharing of trips through the export/import feature, allowing "Organized Users" to share plans with friends.

## User Stories

| Version | As a ...                  | I want to ...                                           | So that I can ...                                              |
|---------|---------------------------|---------------------------------------------------------|----------------------------------------------------------------|
| v1.0    | New user                  | see usage instructions                                  | refer to them when I forget how to use the application         |
| v1.0    | Frequent traveler         | create a new trip with a name and date range            | keep my different trips organized separately                   |
| v1.0    | Planner                   | open a specific trip to manage its details              | focus on planning one trip at a time                           |
| v1.0    | Organized user            | view a list of all my planned trips                     | see an overview of all my upcoming travels                     |
| v1.0    | Planner                   | edit an existing activity's details                     | correct mistakes or update plans as they change                |
| v1.0    | Planner                   | delete a trip that I no longer need                     | remove outdated or cancelled trips from my list                |
| v1.0    | Planner                   | add an activity with name, date, time, and location     | build a detailed itinerary for each day of my trip             |
| v1.0    | Planner                   | delete an activity from my itinerary                    | remove cancelled or rescheduled events                         |
| v1.0    | Organized user            | view my itinerary in chronological order                | see the flow of my planned activities                          |
| v2.0    | Traveler                  | export my trip details to a text file                   | share my itinerary with friends or family                      |
| v2.0    | Traveler                  | import a shared trip from a text file                   | use itineraries created by others as a template                |
| v2.0    | Budget-conscious traveler | allocate a budget for each activity                     | plan my expected spending per event                            |
| v2.0    | Budget-conscious traveler | record actual spending for each activity                | track how much I'm actually spending vs my plan                |
| v2.0    | Loyal user                | save data and exit the application                      | preserve my trip plans for the next session                    |
| v2.0    | International traveler    | set a foreign exchange rate                             | calculate expenses accurately in my home currency              |
| v2.0    | Budget-conscious traveler | compare budget vs actual spending chronologically       | see where I'm overspending during my trip                      |
| v2.0    | Budget-conscious traveler | view a summary of total trip budget and remaining funds | make informed decisions about adding new expenses              |
| v2.1    | Time-conscious traveler   | see the next upcoming activity                          | know what I need to prepare for next                           |
| v2.1    | Budget-conscious traveler | set a daily spending limit                              | avoid spending all my money in the first few days              |
| v2.1    | Visual learner            | see a visual chart of budget usage                      | quickly identify which activities are using most of my budget  |
| v2.1    | Forgetful traveler        | add items to a packing list                             | remember everything I need to bring                            |
| v2.1    | Organized traveler        | view my packing list and packing progress               | see what I've already packed and what's left                   |
| v2.1    | Prepared traveler         | mark items as packed                                    | track my packing progress before departure                     |
| v2.1    | Organized traveler        | remove items from my packing list                       | delete items I decided not to bring                            |
| v2.1    | Detail-oriented user      | add remarks or notes to an activity                     | store extra information like meeting points or contact numbers |

## Non-Functional Requirements

- The software must function as a lightweight, offline-first application.
- The application must save data hierarchically to a local text file (e.g., data/traveltrio.txt) allowing data to persist across sessions.
- The application must operate through a Command Line Interface (CLI) rather than a traditional GUI.

## Glossary

| Term               | Definition                                                                                                                                                             |
|--------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Aggregate Root     | A cluster of associated objects treated as a single unit. `Trip` is the root for its itinerary and finances.                                                           | 
| CLI                | Command Line Interface. A text-based user interface.                                                                                                                   | 
| Command Pattern    | A design pattern encapsulating all information needed to perform an action into an object.                                                                             | 
| Conflict Detection | Logic ensuring no two activities in the same trip occupy the same time slot.                                                                                           |
| Persistence        | The characteristic of data outliving the execution of the program (achieved via `.txt` storage).                                                                       |
| SoC                | Separation of Concerns. A design principle for separating a program into distinct sections (UI, Logic, Model, Storage) so each addresses a separate concern.           |
| State-Aware Parser | A storage logic that tracks the current context (e.g., which trip is being read) while processing a file.                                                              |
| Trip               | The top-level data entity in TravelTrio. It acts as the container for all activities, budgets, and packing items associated with a single travel plan.                 |
| Activity           | A specific scheduled event within a trip itinerary, containing location, date, and time metadata. It is the primary unit for budget allocation and conflict detection. |
| PackingItem        | A single item in the packing list with a name and packed status. Displayed as `[X] name` when packed and `[ ] name` when unpacked.                                     |
| PackingList        | A collection of PackingItem objects belonging to a trip. Supports add, list, check (mark as packed), and delete operations.                                            |

## Instructions for manual testing

### Initial Setup

#### Option 1: Run from JAR (Recommended for End-User Testing)
1. Build the application using Gradle: `./gradlew shadowJar` (on macOS/Linux) or `gradlew.bat shadowJar` (on Windows)
2. Navigate to `build/libs/` and locate `traveltrio.jar`
3. Copy `traveltrio.jar` to a clean test directory (e.g., `test_home/`)
4. Open a terminal, navigate to `test_home/`, and run: `java -jar traveltrio.jar`
5. The application will create a `data/` folder automatically if it doesn't exist

#### Option 2: Run from Source (for Development Testing)
1. Open the project in your IDE or terminal
2. Run: `./gradlew run` (on macOS/Linux) or `gradlew.bat run` (on Windows)
3. The application will start and use the existing `data/traveltrio.txt` file

### Loading Sample Data

#### Quick Sample Data (Non-emptying Existing Data)
**Note:** If you want to preserve existing test data, skip to the "Complete Test Scenarios" section instead.

#### Resetting to Fresh State
1. Locate the `data/traveltrio.txt` file (next to the `.jar` or in the project root)
2. Delete it or rename it to `traveltrio_backup.txt`
3. Restart the application — it will create a fresh `data/traveltrio.txt` with no data
4. You can now follow the test scenarios below to populate it gradually

#### Pre-loading Sample Data (Alternative)
1. Stop the application (press `Ctrl+C` or type `exit`)
2. Create or replace `data/traveltrio.txt` with the following sample content:
```
***************************************************************************
Trip: Japan Trip | From: 2026-03-15 | To: 2026-03-22 | 
Total Budget: 3000.00 | Remaining Budget: 1250.00 | Exchange Rate: 1.50
***************************************************************************

=== Date: 2026-03-15 ===
---------------------------------------------------------------------------
Title: Flight to Tokyo
    Location: SFO Airport
    Start Time: 09:00
    End Time:   22:00
      Budget set: 800.00
      Actual Expense: 800.00
---------------------------------------------------------------------------

Title: Check into Hotel
    Location: Tokyo Hotel
    Start Time: 23:00
    End Time:   23:30
      Budget set: 200.00
      Actual Expense: 200.00
---------------------------------------------------------------------------

=== Date: 2026-03-16 ===
---------------------------------------------------------------------------
Title: Visit Senso-ji Temple
    Location: Asakusa
    Start Time: 09:00
    End Time:   11:00
      Budget set: 50.00
      Actual Expense: 45.00
---------------------------------------------------------------------------

Title: Lunch at Tsukiji Market
    Location: Tsukiji
    Start Time: 12:00
    End Time:   13:00
      Budget set: 30.00
      Actual Expense: 28.00
---------------------------------------------------------------------------

Packing List:
---------------------------------------------------------------------------
1|Passport
0|Winter Jacket
0|Travel Adapter
1|Camera
---------------------------------------------------------------------------

***************************************************************************
Trip: Paris Escape | From: 2026-05-01 | To: 2026-05-05 | 
Total Budget: 2000.00 | Remaining Budget: 2000.00 | Exchange Rate: 1.00
***************************************************************************

Packing List:
---------------------------------------------------------------------------
0|Umbrella
1|Walking Shoes
0|Guidebook
---------------------------------------------------------------------------
```
3. Restart the application and verify that both trips appear when you type `listtrip`

### Complete Test Scenarios

#### Scenario 1: Trip Management
1. **Add a new trip:** Type `addtrip` and follow the prompts to create a trip (e.g., "Summer Beach Vacation", 2026-06-01, 2026-06-10)
2. **List all trips:** Type `listtrip` — verify your new trip appears in the list
3. **Open a trip:** Type `opentrip` and select the trip you just created
4. **Expected output:** The application should confirm which trip is now open

#### Scenario 2: Activity Management
1. Ensure a trip is open (from Scenario 1)
2. **Add an activity:** Type `addactivity` and provide details (e.g., "Beach Day", "Sandy Beach", 2026-06-01, 09:00, 17:00)
3. **Add another activity:** Type `addactivity` again without overlapping times (e.g., "Swimming Lesson", "Beach", 2026-06-01, 18:00, 20:00)
   - **Expected:** Application should accept both activities without conflict warning (different activities on same day are allowed)
4. **Add another activity:** Type `addactivity` again with overlapping times (e.g., "Sunbathing", "Beach", 2026-06-01, 15:00, 16:00)
   - **Expected:** Application rejects the new activity and shows a conflict warning (activity timings are not allowed to overlap)
5. **List activities:** Type `listactivity` — verify both activities appear in chronological order
6. **Edit an activity:** Type `editactivity`, select an activity, and modify its location or time
7. **Delete an activity:** Type `deleteactivity` and remove one activity — verify it's gone when you list again

#### Scenario 3: Budget Management (if activity has no budget set)
1. Ensure a trip is open with at least one activity
2. **Set budget:** Type `setbudget`, select an activity, and enter a budget amount (e.g., 150.00)
3. **Set expense (below budget):** Type `setexpense`, select the same activity, and enter an amount less than the budget (e.g., 100.00)
   - **Expected:** Confirmation that expense is recorded successfully
4. **Set expense (warning level):** Type `setexpense` again, entering an amount that brings total to 90% or more of budget (e.g., 40.00 more = 140.00 total)
   - **Expected:** Warning message that expense is approaching budget limit
5. **Attempt to exceed budget:** Type `setexpense` and try to enter an amount that would exceed the budget
   - **Expected:** Application should reject the expense and prompt you to increase the budget first

#### Scenario 4: Currency Conversion
1. Ensure a trip is open
2. **Set currency rate:** Type `setcurrency` and enter a foreign exchange rate (e.g., 1.50 for JPY to your home currency)
3. **Check trip info:** Type `listtrip` — verify the exchange rate is stored for the trip
4. **Set expense in foreign currency:** Type `setexpense` on an activity with budget set, and when prompted for currency, choose the foreign currency option
   - Enter a foreign amount (e.g., 10000 JPY)
   - **Expected:** The application converts to home currency (e.g., 6666.67 in home currency) and records the expense

#### Scenario 5: Budget Summary and Expense Tracking
1. Ensure a trip is open with multiple activities that have budgets and expenses set
2. **View budget summary:** Type `budgetsummary`
   - **Expected:** Displays total budget, total spent, remaining budget, and overspending alerts (if any)
3. **View expense list:** Type `listexpense`
   - **Expected:** Shows a chronological table of expenses grouped by date, with daily totals and daily limit warnings

#### Scenario 6: Daily Spending Limit
1. Ensure a trip is open
2. **Set daily limit:** Type `setdailylimit` and enter a daily spending cap (e.g., 200.00)
3. **Add activities with budgets for multiple days**
4. **Try to exceed daily limit:** Type `setexpense` and attempt to log an expense that would breach the daily limit
   - **Expected:** Application rejects the expense and alerts you to the daily limit breach
5. **Verify limit tracking:** Type `listexpense` to see daily totals with limit indicators

#### Scenario 7: Packing List Management
1. Ensure a trip is open (from Scenario 1)
2. **Add a packing item:** Type `additem` and enter an item name (e.g., "Passport")
   - **Expected:** Confirmation that the item was added
3. **Add more items:** Type `additem` again to add more items (e.g., "Winter Jacket", "Travel Adapter", "Camera")
4. **List packing items:** Type `listitems`
   - **Expected:** All items are displayed with their status as `[ ]` (unpacked), e.g., "1. [ ] Passport"
5. **Mark an item as packed:** Type `checkitem` and enter the index of an item (e.g., 1 for Passport)
   - **Expected:** Confirmation that the item was marked as packed
6. **Verify status change:** Type `listitems` again
   - **Expected:** The checked item now shows `[X]`, e.g., "1. [X] Passport"
7. **Delete an item:** Type `deleteitem` and enter the index of an item to remove
   - **Expected:** Confirmation that the item was removed
8. **Verify deletion:** Type `listitems` to confirm the item is no longer in the list
9. **Test invalid index:** Type `checkitem` or `deleteitem` with an out-of-range index
   - **Expected:** Application shows an error message about invalid index

#### Scenario 8: Data Persistence
1. Complete several operations (create trips, add activities, set budgets, log expenses)
2. Type `exit` to save and close the application
3. Restart the application (re-run the `.jar` or `./gradlew run`)
4. Type `listtrip` and verify all your data is still present
   - **Expected:** All trips, activities, budgets, and expenses persist

#### Scenario 9: Trip Export and Import
1. Ensure a trip is open
2. **Export trip:** Type `exporttrip` and follow the prompt to save the trip to a `.txt` file (e.g., `exported_trip.txt`)
3. **Verify export file:** Navigate to the directory and confirm `exported_trip.txt` was created with the expected format
4. **Import trip (to same or different setup):** Type `importtrip` and select the exported file
   - **Expected:** The trip is merged into the trip list with all activities and budgets intact

#### Scenario 10: Error Handling & Edge Cases
1. **Invalid date format:** Type `addtrip` and enter a date in wrong format (e.g., "01/01/2026" instead of "2026-01-01")
   - **Expected:** Application rejects and re-prompts for correct format
2. **Activity outside trip dates:** Ensure trip is open (dates 2026-03-15 to 2026-03-22) and type `addactivity` with a date outside this range (e.g., 2026-04-01)
   - **Expected:** Application rejects the activity
3. **No trip open:** Close/delete the open trip and try to add an activity
   - **Expected:** Application prompts to open a trip first
4. **Negative budget/expense:** Try to set a negative budget or expense
   - **Expected:** Application rejects negative values

#### Scenario 11: Help and Navigation
1. Type `help` to view the command summary
2. **Expected:** All major commands are listed with descriptions
3. Type `opentrip` without an open trip to re-prompt
4. Navigate through various commands and verify the step-by-step guidance is clear and helpful

### Cleanup After Testing
- Delete the `data/traveltrio.txt` file to reset to a clean state for the next test run
- Optionally delete the entire `data/` folder if you want a completely fresh start
