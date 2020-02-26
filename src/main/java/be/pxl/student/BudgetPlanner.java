package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;
import java.util.List;

public class BudgetPlanner {

    public static void main(String[] args) {
        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();
        List<Account> accounts = budgetPlannerImporter.readFile("src/main/resources/account_payments.csv");
    }
}
