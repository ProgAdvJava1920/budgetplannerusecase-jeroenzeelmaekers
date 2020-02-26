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
import java.util.*;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

    public List<Account> readFile(String path) {
        Path filePath = Paths.get(path);
        HashMap<String, Account> accountHashMap = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split(",");
                Account account = mapAccount(lineSplit);
                Payment payment = mapPayment(lineSplit);
                if (!accountHashMap.containsKey(account.getIBAN())) {
                    List<Payment> payments = new ArrayList<>();
                    account.setPayments(payments);
                    accountHashMap.put(account.getIBAN(), account);
                }
                accountHashMap.get(account.getIBAN()).getPayments().add(payment);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>(accountHashMap.values());
    }

    private Account mapAccount(String[] lineSplit) {
        return new Account(lineSplit[1], lineSplit[0]);
    }

    private Payment mapPayment(String[] lineSplit) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        return new Payment(LocalDateTime.parse(lineSplit[3], dateTimeFormatter), Float.parseFloat(lineSplit[4]), lineSplit[5], lineSplit[6]);
    }
}