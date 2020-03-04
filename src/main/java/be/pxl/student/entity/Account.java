package be.pxl.student.entity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Account {

    private long id;
    private String IBAN;
    private String name;
    private List<Payment> payments;

    public Account(String IBAN, String name) {
        this.IBAN = IBAN;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                "IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=[" + payments.stream()
                .map(Payment::toString)
                .collect(Collectors.joining(",")) + "]}";
    }
}
