package be.pxl.student.dao;

import be.pxl.student.entity.Account;

import java.sql.*;

public class AccountDAO {

    private static final String SELECT = "SELECT * FROM Account WHERE id = ?";
    private static final String UPDATE = "UPDATE Account SET name=?, IBAN=? WHERE id = ?";
    private static final String INSERT = "INSERT INTO Account (name, IBAN) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM Account WHERE id = ?";

    private String url;
    private String user;
    private String password;

    public AccountDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Account createAccount(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getIBAN());
            if (statement.executeUpdate() == 1) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        account.setId(rs.getInt(1));
                        return account;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getIBAN());
            statement.setLong(3, account.getId());
            return statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccountById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Account getAccountById(long id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Account mapAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account(resultSet.getString("IBAN"), resultSet.getString("name"));
        account.setId(resultSet.getInt("id"));
        return account;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}