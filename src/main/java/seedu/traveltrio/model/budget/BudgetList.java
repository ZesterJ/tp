package seedu.traveltrio.model.budget;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

/**
 * Represents a collection of budgets for a trip. A BudgetList object acts as the
 * central financial manager, tracking individual activity budgets, overall trip totals,
 * exchange rates, and daily spending limits.
 */
public class BudgetList {

    private final Map<Activity, Budget> budgets;
    private double totalTripBudget;
    private double totalTripExpense;
    private Double dailySpendingLimit;
    private double exchangeRate;

    /**
     * Initializes an empty BudgetList. Sets all totals to zero, leaves the daily
     * spending limit unset, and defaults the exchange rate to 1.0.
     */
    public BudgetList() {
        this.budgets = new HashMap<>();
        this.totalTripBudget = 0;
        this.dailySpendingLimit = null; /// no limit set
        this.exchangeRate = 1.0; // Default exchange rate
    }

    /**
     * Calculates the total remaining budget for the entire trip by summing the remaining
     * balances of all individual activity budgets.
     *
     * @return The total unspent budget across all activities.
     */
    public double getTotalRemainingTripBudget() {
        double totalRemaining = 0;
        for (Budget b : budgets.values()) {
            totalRemaining += b.getRemainingBudget();
        }
        return totalRemaining;
    }

    /**
     * Adds a new budget for a specific activity or updates an existing one.
     * Automatically adjusts the overall trip budget to reflect the addition or change.
     *
     * @param activity The activity the budget is assigned to.
     * @param budget The budget object containing the financial allocation.
     */
    public void addBudget(Activity activity, Budget budget) {
        if (budgets.containsKey(activity)) {
            Budget existingBudget = budgets.get(activity);
            totalTripBudget -= existingBudget.getActivityBudget();
        } 
        budgets.put(activity, budget);
        totalTripBudget += budget.getActivityBudget();
    }

    /**
     * Removes the budget associated with a specific activity. Automatically deducts
     * the removed budget's allocated amount and actual expenses from the overall trip totals.
     *
     * @param activity The activity whose budget should be removed.
     */
    public void removeBudget(Activity activity) {
        Budget activityBudget = budgets.get(activity);
        if (activityBudget == null){
            return;
        }
        totalTripBudget -= activityBudget.getActivityBudget();
        totalTripExpense -= activityBudget.getActualExpense();

        budgets.remove(activity);
    }

    /**
     * Retrieves the budget associated with a specific activity.
     *
     * @param activity The activity to search for.
     * @return The Budget object assigned to the activity, or null if none exists.
     */
    public Budget getBudget(Activity activity) {
        return budgets.get(activity);
    }

    /**
     * Returns the total allocated budget for the entire trip.
     *
     * @return The overall trip budget.
     */
    public double getTotalTripBudget() {
        return totalTripBudget;
    }

    /**
     * Retrieves the map of all activities and their corresponding budgets.
     *
     * @return A map linking Activity objects to their Budget objects.
     */
    public Map<Activity, Budget> getBudgets() {
        return budgets;
    }

    /**
     * Returns the current currency exchange rate for the trip.
     *
     * @return The exchange rate multiplier.
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets a new currency exchange rate for the trip.
     *
     * @param exchangeRate The new exchange rate multiplier.
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * Records the actual expense for a specific activity and updates the overall trip expenses.
     * Validates that the activity has an existing budget and that the new expense does not
     * violate the daily spending limit.
     *
     * @param activity The activity the expense is tied to.
     * @param newExpense The monetary amount spent.
     * @throws TravelTrioException If no budget exists for the activity, or if the expense exceeds the daily limit.
     */
    public void setExpense(Activity activity, double newExpense) throws TravelTrioException {
        assert activity != null : "Activity should not be null";
        Budget budget = budgets.get(activity);
        if (budget == null) {
            throw new TravelTrioException("You must add a budget for this activity first.");
        }
        if (willExceedDailyLimit(activity, newExpense)) {
            throw new TravelTrioException("This will result in daily spending limit exceeded.\n" +
                    "Please either set a lower expense amount or set a higher daily limit.");
        }

        double oldExpense = budget.getActualExpense();
        budget.setActualExpense(newExpense);

        //update trip's total expense
        double expenseIncrease = newExpense - oldExpense;
        totalTripExpense = totalTripExpense + expenseIncrease;
    }

    /**
     * Returns the total actual expenses incurred across the entire trip.
     *
     * @return The total trip expense.
     */
    public double getTotalTripExpense() {
        return totalTripExpense;
    }

    /**
     * Checks whether a daily spending limit has been established for the trip.
     *
     * @return True if a daily limit is set, false otherwise.
     */
    public boolean hasDailyLimitSet(){
        return !(dailySpendingLimit == null || dailySpendingLimit == 0);
    }

    /**
     * Sets a maximum allowed spending limit per day.
     *
     * @param limit The monetary threshold for daily expenses.
     * @throws TravelTrioException If the provided limit is negative.
     */
    public void setDailySpendingLimit(double limit) throws TravelTrioException {
        if (limit < 0) {
            throw new TravelTrioException("Daily spending limit cannot be negative.");
        }
        this.dailySpendingLimit = limit;
    }

    /**
     * Retrieves the current daily spending limit.
     *
     * @return The daily spending limit, or null if it has not been set.
     */
    public Double getDailySpendingLimit() {
        return dailySpendingLimit;
    }

    /**
     * Calculates the total expenses incurred on a specific calendar date.
     *
     * @param date The specific LocalDate to calculate expenses for.
     * @return The sum of all expenses tied to activities occurring on the specified date.
     */
    public double getTotalExpenseForDate(LocalDate date) {
        double total = 0;
        for (Map.Entry<Activity, Budget> entry : budgets.entrySet()) {
            Activity activity = entry.getKey();
            Budget budget = entry.getValue();

            if (activity.getDate() != null
                    && LocalDate.parse(activity.getDate()).equals(date)) {
                total += budget.getActualExpense();
            }
        }
        return total;
    }

    /**
     * Evaluates whether updating an activity's expense will cause the total spending for that
     * specific day to exceed the daily spending limit.
     *
     * @param activity The activity being modified.
     * @param newExpense The proposed new expense amount.
     * @return True if the update will exceed the daily limit, false otherwise.
     */
    public boolean willExceedDailyLimit(Activity activity, double newExpense) {
        if (!hasDailyLimitSet() || activity.getDate() == null) {
            return false;
        }

        Budget existingBudget = budgets.get(activity);
        double oldExpense = existingBudget == null ? 0 : existingBudget.getActualExpense();

        double currentDayTotal = getTotalExpenseForDate(LocalDate.parse(activity.getDate()));
        double newDayTotal = currentDayTotal - oldExpense + newExpense;

        return newDayTotal > dailySpendingLimit;
    }

    /**
     * Checks if the budget list is completely empty.
     *
     * @return True if no budgets have been added, false otherwise.
     */
    public boolean isEmpty() {
        return budgets.isEmpty();
    }

    /**
     * Recalculates the totalTripExpense from actual spendings of activities.
     */
    public void recalculateTotalExpense() {
        totalTripExpense = 0;
        for (Budget budget : budgets.values()) {
            totalTripExpense += budget.getActualExpense();
        }
    }
}
