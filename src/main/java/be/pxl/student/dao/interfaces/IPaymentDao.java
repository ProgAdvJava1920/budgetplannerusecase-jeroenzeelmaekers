package be.pxl.student.dao.interfaces;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

import java.util.List;

public interface IPaymentDao {

    List<Payment> read();

    boolean createPayment(Payment payment, Account account, Account counterAccount);

    boolean updatePayment(Payment payment, Account account);

    boolean deletePayment(int id);

    Payment readPayment(int id);
}