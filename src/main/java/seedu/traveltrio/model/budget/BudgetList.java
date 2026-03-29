package seedu.traveltrio.model.budget;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

public class BudgetList {
    private final Map<Activity, Budget> budgets;
    private double totalTripBudget;
    private double totalTripExpense;
    private Double dailySpendingLimit;

    public BudgetList() {
        this.budgets = new HashMap<>();
        this.totalTripBudget = 0;
        this.dailySpendingLimit = null; /// no limit set
    }

    public double getTotalRemainingTripBudget() {
        double totalRemaining = 0;
        for (Budget b : budgets.values()) {
            totalRemaining += b.getRemainingBudget();
        }
        return totalRemaining;
    }

    public void addBudget(Activity activity, Budget budget) {
        if (budgets.containsKey(activity)) {
            Budget existingBudget = budgets.get(activity);
            totalTripBudget -= existingBudget.getActivityBudget();
        } 
        budgets.put(activity, budget);
        totalTripBudget += budget.getActivityBudget();
    }

    public void removeBudget(Activity activity) {
        Budget activityBudget = budgets.get(activity);
        if (activityBudget == null){
            return;
        }
        totalTripBudget -= activityBudget.getActivityBudget();
        totalTripExpense -= activityBudget.getActualExpense();

        budgets.remove(activity);
    }

    public Budget getBudget(Activity activity) {
        return budgets.get(activity);
    }

    public double getTotalTripBudget() {
        return totalTripBudget;
    }

    public Map<Activity, Budget> getBudgets() {
        return budgets;
    }

    public void setExpense(Activity activity, double newExpense) throws TravelTrioException {
        assert activity != null : "Activity should not be null";
        Budget budget = budgets.get(activity);
        if (budget == null) {
            throw new TravelTrioException("You must add a budget for this activity first.");
        }
        if (willExceedDailyLimit(activity, newExpense)){
            throw new TravelTrioException("This will result in daily spending limit exceeded.\n" +
                    "Please either set a lower expense amount or set a higher daily limit.");
        }

        double oldExpense = budget.getActualExpense();
        budget.setActualExpense(newExpense);

        //update trip's total expense
        double expenseIncrease = newExpense - oldExpense;
        totalTripExpense = totalTripExpense + expenseIncrease;
    }

    public double getTotalTripExpense() {
        return totalTripExpense;
    }

    public boolean hasDailyLimitSet(){
        return !(dailySpendingLimit == null);
    }

    public void setDailySpendingLimit(double limit) throws TravelTrioException {
        if (limit < 0) {
            throw new TravelTrioException("Daily spending limit cannot be negative.");
        }
        this.dailySpendingLimit = limit;
    }

    public Double getDailySpendingLimit() {
        return dailySpendingLimit;
    }

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

    public boolean isEmpty() {
        return budgets.isEmpty();
    }
}
