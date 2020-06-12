package be.pxl.student.persistence.dao.interfaces;

import be.pxl.student.persistence.entity.Account;

import java.util.List;

public interface IAccountDao {

    List<Account> read();

    boolean createAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(int id);

    Account readAccount(int id);
}