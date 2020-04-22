package be.pxl.student;

import be.pxl.student.dao.jdbc.AccountDaoJDBC;
import be.pxl.student.dao.jdbc.LabelDaoJDBC;
import be.pxl.student.dao.jdbc.PaymentDaoJDBC;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;

import java.util.List;

public class BudgetPlannerJDBC {

    public static void main(String[] args) {

        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

        List<Account> importAccountList = budgetPlannerImporter.readFile("src/main/resources/account_payments.csv");

        AccountDaoJDBC accountDaoJDBC = new AccountDaoJDBC("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
        PaymentDaoJDBC paymentDaoJDBC = new PaymentDaoJDBC("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");
        LabelDaoJDBC labelDaoJDBC = new LabelDaoJDBC("jdbc:mysql://localhost:3306/budgetplanner?useSSL=false", "root", "admin");

        List<Account> accountList = accountDaoJDBC.read();
        List<Payment> paymentList = paymentDaoJDBC.read();
        List<Label> labelList = labelDaoJDBC.read();

        for (Account account : importAccountList) {
            accountDaoJDBC.createAccount(account);
            for (Payment payment: account.getPayments()) {
                paymentDaoJDBC.createPayment(payment, account, account);
            }
        }

        for (Account account : accountList) {
            System.out.println(account.getIBAN());
        }
            System.out.println("Accounts done!");

        for (Payment payment : paymentList) {
            System.out.println(payment);
        }
            System.out.println("Payments done!");

        for (Label label : labelList
        ) {
            System.out.println(label);
        }
            System.out.println("Labels done!");

    }

}

