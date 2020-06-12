package be.pxl.student.persistence;

import be.pxl.student.persistence.dao.jpa.AccountDaoJPA;
import be.pxl.student.persistence.dao.jpa.PaymentDaoJPA;
import be.pxl.student.persistence.entity.Account;
import be.pxl.student.persistence.entity.Payment;
import be.pxl.student.persistence.util.BudgetPlannerImporter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BudgetPlannerJPA {

    public static void main(String[] args) {
        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner");

        List<Account> importAccountList = budgetPlannerImporter.readFile("src/main/resources/account_payments.csv");

        AccountDaoJPA accountDaoJPA = new AccountDaoJPA(entityManagerFactory);
        PaymentDaoJPA paymentDaoJPA = new PaymentDaoJPA(entityManagerFactory);

        for (Account account: importAccountList) {
            accountDaoJPA.createAccount(account);
            for (Payment payment: account.getPayments()) {
                paymentDaoJPA.createPayment(payment, account, account);
            }
        }
    }
}
