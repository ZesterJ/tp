package seedu.traveltrio.model.budget;

import org.junit.jupiter.api.Test;
import seedu.traveltrio.TravelTrioException;
import seedu.traveltrio.model.activity.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BudgetTest {

    @Test
    void getRemainingBudget_budgetWithExpense_returnsCorrectRemainder() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        Budget b = new Budget(2000.00, a);
        b.setActualExpense(500.00);
        assertEquals(1500.00, b.getRemainingBudget());
    }

    @Test
    void setActualExpense_nonNegativeAmount_success() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        double budgetAmount = 2000.00;
        Budget b = new Budget(budgetAmount, a);
        double actualExpense = 1500.00;
        b.setActualExpense(actualExpense);

        assertEquals(1500.00, b.getActualExpense());
    }


    @Test
    void setActualExpense_negativeAmount_throwsException() throws TravelTrioException {
        Activity a = new Activity("Shopping", "Mall", "2026-03-11", "11:30", "15:00");
        double budgetAmount = 2000.00;
        Budget b = new Budget(budgetAmount, a);

        TravelTrioException e = assertThrows(TravelTrioException.class, () -> {
            b.setActualExpense(-500.0);
        });

        assertEquals("Expense amount cannot be negative.", e.getMessage());
    }

}

