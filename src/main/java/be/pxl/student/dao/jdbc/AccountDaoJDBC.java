package be.pxl.student.dao.jdbc;

import be.pxl.student.dao.interfaces.IAccountDao;
import be.pxl.student.entity.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoJDBC implements IAccountDao {

    private static final String CREATE = "INSERT INTO Account (name, IBAN) VALUES (?,?)";
    private static final String SELECT_ALL = "SELECT * FROM Account";
    private static final String SELECT_BY_ID = "SELECT * FROM Account WHERE id = ?";
    private static final String UPDATE = "UPDATE Account SET name = ?, IBAN = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Account WHERE id = ?";

    private String url;
    private String user;
    private String password;

    public AccountDaoJDBC(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Account> read() {
        List<Account> accountList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                accountList.add(mapAccount(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return accountList;
    }

    @Override
    public boolean createAccount(Account account) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getIBAN());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if(resultSet.next()) {
                        account.setId(resultSet.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getIBAN());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAccount(int id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account readAccount(int id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Account mapAccount(ResultSet resultSet) throws SQLException{
        Account account = new Account(resultSet.getString("IBAN"),resultSet.getString("name"));
        account.setId(resultSet.getInt("id"));
        return account;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
