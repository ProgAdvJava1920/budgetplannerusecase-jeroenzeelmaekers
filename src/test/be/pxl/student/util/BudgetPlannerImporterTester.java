package be.pxl.student.util;

import be.pxl.student.entity.Account;
import org.junit.jupiter.api.*;

public class BudgetPlannerImporterTester {

    public BudgetPlannerImporter budgetPlannerImporter;
    public Account testAccount;
    public String[] input;

    @BeforeEach
    public void SetUp() {
        budgetPlannerImporter = new BudgetPlannerImporter();
        testAccount = new Account("BE69771770897312", "Jos");
        input = new String[]{"Jos", "BE69771770897312", "BE75462985668329", "Wed Feb 05 16:33:04 CET 2020", "2779.33", "EUR", "Quo possimus id corrupti quod doloribus."};
    }

    @Test
    public void TestIfAccountMapperCorrectlyMapsAccountFromArrayInput() {
        Account mappedAccount = budgetPlannerImporter.mapAccount(input);
        Assertions.assertEquals(testAccount.getName(), mappedAccount.getName());
        Assertions.assertEquals(testAccount.getIBAN(), mappedAccount.getIBAN());

    }

}
