package seedu.traveltrio.command.finance.expense;

import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.budget.BudgetList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a command to set a maximum daily spending limit for the trip. A SetDailyLimitCommand
 * object updates the budget list with a threshold to help warn users against overspending on a single day.
 */
public class SetDailyLimitCommand {

    private final BudgetList budgetList;
    private final double amount;
    private final String startDate;
    private final String endDate;

    /**
     * Initializes a SetDailyLimitCommand with the specified budget list, limit amount, and trip dates.
     *
     * @param budgetList The list containing the financial data and budgets for the current trip.
     * @param amount The maximum monetary amount the user intends to spend per day.
     * @param startDate The start date of the trip in YYYY-MM-DD format.
     * @param endDate The end date of the trip in YYYY-MM-DD format.
     */
    public SetDailyLimitCommand(BudgetList budgetList, double amount, String startDate, String endDate) {
        this.budgetList = budgetList;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Executes the command to update the trip's daily spending limit and checks all dates
     * in the trip to identify any days where the current spending exceeds the new limit.
     *
     * @return Formatted string confirming the newly set daily spending limit and any exceeded dates.
     * @throws TravelTrioException If an error occurs while setting the limit or parsing dates.
     */
    public String execute() throws TravelTrioException {
        budgetList.setDailySpendingLimit(amount);
        
        // Check all dates in the trip for exceeded limits
        StringBuilder result = new StringBuilder();
        result.append(String.format("Daily spending limit has been set to $%.2f.", amount));
        
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            LocalDate current = start;
            
            boolean hasExceeded = false;
            StringBuilder exceededDates = new StringBuilder();
            
            while (!current.isAfter(end)) {
                double dailyExpense = budgetList.getTotalExpenseForDate(current);
                if (dailyExpense > amount) {
                    if (!hasExceeded) {
                        exceededDates.append("\n\nWarning: The following dates already exceed the new daily limit:\n");
                        hasExceeded = true;
                    }
                    exceededDates.append(String.format("- %s: $%.2f spent\n", current, dailyExpense));
                }
                current = current.plusDays(1);
            }
            
            if (hasExceeded) {
                result.append(exceededDates.toString());
            }
            
        } catch (DateTimeParseException e) {
            throw new TravelTrioException("Invalid date format in trip dates.");
        }
        
        return result.toString();
    }
}
