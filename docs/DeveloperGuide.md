# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


### Add Activity to Itinerary feature
**Implementation**<br>
The `addactivity` feature is facilitated by `AddActivityCommand`. It allows the user to create a new `Activity` and add it to the `ActivityList` of the currently opened `Trip`.

The feature mainly involves the following classes:
- AddActivityCommand — adds a new Activity into the activity list.
- Activity — represents a single activity with fields such as name, location, date, start time, and end time.
- ActivityList — stores all Activity objects belonging to a trip.
- Trip — owns the corresponding ActivityList.

The `AddActivityCommand` receives the target `ActivityList` of the currently opened `Trip` and the `Activity` to be added. 
When `AddActivityCommand#execute()` is called, the activity is appended to the list and a success message is returned.

Given below is an example usage scenario and how the add activity mechanism behaves at each step.

Step 1. The user opens a trip, for example Japan Trip. The opened Trip contains an `ActivityList`, which may initially be empty.

Step 2. The user executes an `addactivity` command with the relevant activity details, such as activity name, location, date, and time.

Step 3. The application parses the user input and constructs a new Activity object containing the specified details.

Step 4. The application creates an `AddActivityCommand`, passing in the current trip’s ActivityList and the newly created Activity.

Step 5. The user command is executed through `AddActivityCommand#execute()`. The command calls `ActivityList#add(activity)`, causing the new `activity` to be stored in the list.

Step 6. A success message is returned to the user, showing that the activity has been successfully added.

If the command input is invalid, or if no trip is currently opened, the command will not be executed successfully and no activity will be added.

**Sequence Diagram:**

The following sequence diagram shows how an operation to add an activity goes:
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
<puml src="diagrams/SetBudgetSequenceDiagram.puml" />

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ...              | I want to ...                                                                 | So that I can ...                                                    |
|--------|-----------------------|-------------------------------------------------------------------------------|----------------------------------------------------------------------|
|v1.0| new user              | see usage instructions                                                        | refer to them when I forget how to use the application               |
|v1.0| Organized user        | view all acitivities that I had added to the activity list at once            | see all of the activities i had planned for my trip                  |
|v1.0| Organized user        | add activities to a trip with details (e.g., activity name, time and location | append new activity to my list of activities in the itinerary        |
|v2.0| Thrifty User          | record my actual spending for planned activities                              | easily track and compare my actual expenses against my planned budget |


## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
