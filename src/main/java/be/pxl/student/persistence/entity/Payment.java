package be.pxl.student.persistence.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private float amount;
    private String currency;
    private String detail;
    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;
    @ManyToOne
    @JoinColumn(name = "counterAccountId", nullable = false)
    private Account counterAccount;

    public Payment() {
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(Account counterAccount) {
        this.counterAccount = counterAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return getId() == payment.getId() &&
                Float.compare(payment.getAmount(), getAmount()) == 0 &&
                getDate().equals(payment.getDate()) &&
                getCurrency().equals(payment.getCurrency()) &&
                getDetail().equals(payment.getDetail()) &&
                getAccount().equals(payment.getAccount()) &&
                getCounterAccount().equals(payment.getCounterAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getAmount(), getCurrency(), getDetail(), getAccount(), getCounterAccount());
    }
}