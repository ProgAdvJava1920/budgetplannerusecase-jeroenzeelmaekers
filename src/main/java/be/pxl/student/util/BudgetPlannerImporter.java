package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

    private Path filePath;

    public BudgetPlannerImporter(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Account> readFile() {
        List<Account> accounts = new ArrayList<>();
        List<Payment> payments = new ArrayList<>();
        Account currentAccount = null;
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split(",");
                if(lineSplit.length <= 6) {
                    line = reader.readLine();
                    if(line != null) {
                        lineSplit = line.split(",");
                    } else {
                        break;
                    }
                }
                if(IsNewAccount(accounts, lineSplit)) {
                    currentAccount = new Account(lineSplit[1], lineSplit[0]);
                    accounts.add(currentAccount);
                    payments = new ArrayList<>();
                }
                Payment currentPayment = mapPayment(lineSplit);
                payments.add(currentPayment);
                accounts.get(accounts.size() - 1).setPayments(payments);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return accounts;
    }

    private boolean IsNewAccount(List<Account> accounts, String[] lineSplit) {
        boolean isNewAccount = true;
        for (Account account: accounts) {
            if(account.getIBAN().equals(lineSplit[1])) {
                isNewAccount = false;
                break;
            }
        }
        return isNewAccount;
    }

    private Account mapAccount(String[] lineSplit) {
        return new Account(lineSplit[1], lineSplit[0]);
    }

    private Payment mapPayment(String[] lineSplit) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        return new Payment(LocalDateTime.parse(lineSplit[3], dateTimeFormatter),
                Float.parseFloat(lineSplit[4]),
                lineSplit[5],
                lineSplit[6]);
    }
}