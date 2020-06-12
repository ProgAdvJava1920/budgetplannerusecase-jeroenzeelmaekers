package be.pxl.student.business;

import be.pxl.student.persistence.dao.jpa.AccountDaoJPA;
import be.pxl.student.persistence.entity.Account;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/account")
public class AccountRest {

    @GET
    @Path("/accounts")
    public List<Account> getAllAccounts() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner");
        AccountDaoJPA accountDaoJPA = new AccountDaoJPA(entityManagerFactory);
        List<Account> accountList = accountDaoJPA.read();
        return accountList;
    }
}
