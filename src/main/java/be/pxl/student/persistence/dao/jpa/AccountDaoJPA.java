package be.pxl.student.persistence.dao.jpa;

import be.pxl.student.persistence.dao.interfaces.IAccountDao;
import be.pxl.student.persistence.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class AccountDaoJPA implements IAccountDao {

    private EntityManager entityManager;

    public AccountDaoJPA(EntityManagerFactory entityManagerFactory) {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Account> read() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        List<Account> accountList = entityManager.createQuery("SELECT a from Account a").getResultList();
        transaction.commit();
        return accountList;
    }

    @Override
    public boolean createAccount(Account newAccount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Account account = new Account();
        account.setName(newAccount.getName());
        account.setIBAN(newAccount.getIBAN());
        entityManager.persist(account);
        entityManager.flush();
        newAccount.setId(account.getId());
        transaction.commit();
        return true;
    }

    @Override
    public boolean updateAccount(Account updateAccount) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Account account = entityManager.find(Account.class, updateAccount.getId());
        account.setName(updateAccount.getName());
        account.setIBAN(updateAccount.getIBAN());
        entityManager.persist(account);
        transaction.commit();
        return true;
    }

    @Override
    public boolean deleteAccount(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Account account = entityManager.find(Account.class, id);
        if (account != null) {
            entityManager.remove(account);
        }
        transaction.commit();
        return true;
    }

    @Override
    public Account readAccount(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Account account = entityManager.find(Account.class, id);
        transaction.commit();
        return account;
    }
}
