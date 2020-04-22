package be.pxl.student.dao.jpa;

import be.pxl.student.dao.interfaces.IAccountDao;
import be.pxl.student.entity.Account;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class AccountDaoJPA implements IAccountDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("budgetplanner");

    @Override
    public List<Account> read() {
        return null;
    }

    @Override
    public boolean createAccount(Account account) {
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        return false;
    }

    @Override
    public boolean deleteAccount(int id) {
        return false;
    }

    @Override
    public Account readAccount(int id) {
        return null;
    }
}
