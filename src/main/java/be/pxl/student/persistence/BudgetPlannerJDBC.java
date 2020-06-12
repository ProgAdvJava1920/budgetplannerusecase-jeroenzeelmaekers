package be.pxl.student.persistence;

import be.pxl.student.persistence.dao.jdbc.AccountDaoJDBC;
import be.pxl.student.persistence.dao.jdbc.LabelDaoJDBC;
import be.pxl.student.persistence.dao.jdbc.PaymentDaoJDBC;
import be.pxl.student.persistence.entity.Account;
import be.pxl.student.persistence.entity.Label;
import be.pxl.student.persistence.entity.Payment;
import be.pxl.student.persistence.util.BudgetPlannerImporter;

import java.util.List;

public class BudgetPlannerJDBC {

    public static void main(String[] args) {
        BudgetPlannerImporter budgetPlannerImporter = new BudgetPlannerImporter();

        String user = "root";
        String password = "admin";
        String url = "jdbc:mysql://localhost:3306/budgetplanner?useSSL=false";

        List<Account> importAccountList = budgetPlannerImporter.readFile("src/main/resources/account_payments.csv");

        AccountDaoJDBC accountDaoJDBC = new AccountDaoJDBC(url, user, password);
        PaymentDaoJDBC paymentDaoJDBC = new PaymentDaoJDBC(url, user, password);
        LabelDaoJDBC labelDaoJDBC = new LabelDaoJDBC(url, user, password);

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
            System.out.println(account.toString());
        }
            System.out.println("Accounts done!");

        for (Payment payment : paymentList) {
            System.out.println(payment.toString());
        }
            System.out.println("Payments done!");

        for (Label label : labelList
        ) {
            System.out.println(label.toString());
        }
            System.out.println("Labels done!");

    }

}

