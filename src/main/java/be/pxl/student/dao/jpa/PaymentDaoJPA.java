package be.pxl.student.dao.jpa;

import be.pxl.student.dao.interfaces.IPaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PaymentDaoJPA implements IPaymentDao {

    private EntityManager entityManager;

    public PaymentDaoJPA(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Payment> read() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        List<Payment> paymentList = entityManager.createQuery("SELECT p FROM Payment p").getResultList();
        transaction.commit();
        return paymentList;
    }

    @Override
    public boolean createPayment(Payment newPayment, Account account, Account counterAccount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Payment payment = new Payment();
        payment.setDate(newPayment.getDate());
        payment.setAmount(newPayment.getAmount());
        payment.setCurrency(newPayment.getCurrency());
        payment.setDetail(newPayment.getDetail());
        payment.setAccount(account);
        payment.setCounterAccount(counterAccount);
        entityManager.persist(payment);
        transaction.commit();
        return true;
    }

    @Override
    public boolean updatePayment(Payment updatePayment, Account account, Account counterAccount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Payment payment = entityManager.find(Payment.class, updatePayment.getId());
        payment.setDate(updatePayment.getDate());
        payment.setAmount(updatePayment.getAmount());
        payment.setCurrency(updatePayment.getCurrency());
        payment.setDetail(updatePayment.getDetail());
        payment.setAccount(account);
        payment.setCounterAccount(counterAccount);
        entityManager.persist(payment);
        transaction.commit();
        return true;
    }

    @Override
    public boolean deletePayment(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Payment payment = entityManager.find(Payment.class, id);
        if (payment != null) {
            entityManager.remove(payment);
        }
        transaction.commit();
        return true;
    }

    @Override
    public Payment readPayment(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Payment payment = entityManager.find(Payment.class, id);
        transaction.commit();
        return payment;
    }
}