package be.pxl.student.persistence.dao.interfaces;

import be.pxl.student.persistence.entity.Account;
import be.pxl.student.persistence.entity.Payment;

import java.util.List;

public interface IPaymentDao {

    List<Payment> read();

    boolean createPayment(Payment payment, Account account, Account counterAccount);

    boolean updatePayment(Payment payment, Account account, Account counterAccount);

    boolean deletePayment(int id);

    Payment readPayment(int id);
}