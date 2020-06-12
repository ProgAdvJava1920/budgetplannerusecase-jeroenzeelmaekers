package be.pxl.student.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String IBAN;
    private String name;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "account")
    private List<Payment> payments;

    public Account() {
        payments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
                ", IBAN='" + IBAN + '\'' +
                ", name='" + name + '\'' +
                ", payments=" + payments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return getId() == account.getId() &&
                getIBAN().equals(account.getIBAN()) &&
                getName().equals(account.getName()) &&
                getPayments().equals(account.getPayments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIBAN(), getName(), getPayments());
    }
}