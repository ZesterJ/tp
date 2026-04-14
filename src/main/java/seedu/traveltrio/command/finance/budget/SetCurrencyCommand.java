package seedu.traveltrio.command.finance.budget;

import seedu.traveltrio.model.activity.Activity;
import seedu.traveltrio.model.activity.ActivityList;
import seedu.traveltrio.model.budget.BudgetList;
import seedu.traveltrio.TravelTrioException;

/**
 * Represents a command to set the currency exchange rate for the trip. A SetCurrencyCommand
 * object updates the multiplier used to convert foreign currency expenses into the user's home currency.
 */
public class SetCurrencyCommand extends BudgetCommand{

    private final double exchangeRate;

    /**
     * Initializes a SetCurrencyCommand with the specified budget list, activity list, target activity,
     * and exchange rate. Passes a null or dummy activity to the superclass since the exchange rate applies
     * to the entire trip.
     *
     * @param budgetList The list containing all budgets for the current trip.
     * @param activityList The list containing all scheduled activities for the current trip.
     * @param activity The specific activity associated with the command.
     * @param exchangeRate The conversion rate where 1 unit of foreign currency equals this amount of home currency.
     */
    public SetCurrencyCommand(BudgetList budgetList, ActivityList activityList,
            Activity activity, double exchangeRate) {
        super(budgetList, activityList, activity);
        if (Math.abs(exchangeRate) < 0.0001) {
            this.exchangeRate = 0;
        } else {
            this.exchangeRate = exchangeRate;
        }
    }

    /**
     * Executes the command to update the trip's currency exchange rate.
     *
     * @return Formatted string confirming the new exchange rate.
     * @throws TravelTrioException If the provided exchange rate is less than or equal to zero.
     */
    @Override
    public String execute() throws TravelTrioException {
        
        if (exchangeRate <= 0) {
            throw new TravelTrioException("Exchange rate must be a positive number.");
        }
        budgetList.setExchangeRate(exchangeRate);
        return "Currency exchange rate set to: 1 Foreign Currency = " 
                + String.format("%.4f", exchangeRate) + " Home Currency";
    }
}
