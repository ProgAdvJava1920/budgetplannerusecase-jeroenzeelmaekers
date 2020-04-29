package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

    private static Logger logger;

    public BudgetPlannerImporter() {
        logger = LogManager.getLogger();
    }

    public List<Account> readFile(String path) {
        Path filePath = Paths.get(path);
        HashMap<String, Account> accountHashMap = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineSplit = line.split(",");
                Account account = mapAccount(lineSplit);
                Payment payment = mapPayment(lineSplit);
                logger.debug("New payment created" + payment);
                if (!accountHashMap.containsKey(account.getIBAN())) {
                    List<Payment> payments = new ArrayList<>();
                    account.setPayments(payments);
                    accountHashMap.put(account.getIBAN(), account);
                }
                accountHashMap.get(account.getIBAN()).getPayments().add(payment);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        logger.debug("csv succesfully imported!");
        return new ArrayList<>(accountHashMap.values());
    }

    public Account mapAccount(String[] lineSplit) {
        Account account = new Account();
        account.setIBAN(lineSplit[1]);
        account.setName(lineSplit[0]);
        return account;
    }

    public Payment mapPayment(String[] lineSplit) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Payment payment = new Payment();
        payment.setDate(LocalDateTime.parse(lineSplit[3], dateTimeFormatter));
        payment.setAmount(Float.parseFloat(lineSplit[4]));
        payment.setCurrency(lineSplit[5]);
        payment.setDetail(lineSplit[6]);
        return payment;
    }
}