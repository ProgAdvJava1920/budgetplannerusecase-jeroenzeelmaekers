package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private static final String SELECT_BY_ACCOUNT_ID = "SELECT * FROM Payment WHERE id = ?";
    private static final String SELECT = "SELECT * FROM Payment";
    private static final String UPDATE = "UPDATE Payment SET date=?, amount=?, currency=?, detail=?, accountId=?, counterAccountId=?, labelId=? WHERE id = ?";
    private static final String INSERT = "INSERT INTO Payment (date, amount, currency, detail, accountId, counterAccountId, labelid ) VALUES (?,?,?,?,?,?,1)";
    private static final String DELETE = "DELETE FROM Payment WHERE id = ?";

    private String url;
    private String user;
    private String password;

    public PaymentDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Payment createPayment(Payment payment, Account account, Account counterAccount) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new Date(payment.getDate().getYear(), payment.getDate().getMonthValue(), payment.getDate().getDayOfMonth()));
            statement.setFloat(2, payment.getAmount());
            statement.setString(3, payment.getCurrency());
            statement.setString(4, payment.getDetail());
            statement.setLong(5, account.getId());
            statement.setLong(6, counterAccount.getId());
            if (statement.executeUpdate() == 1) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if(rs.next()) {
                        payment.setId(rs.getInt("id"));
                        return payment;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteAccountById(long id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Payment getPaymentById(long id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return mapPayment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                payments.add(mapPayment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean updatePayment(Payment payment, Account account) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setDate(1, new Date(payment.getDate().getYear(), payment.getDate().getMonthValue(), payment.getDate().getDayOfMonth()));
            stmt.setFloat(2, payment.getAmount());
            stmt.setString(3, payment.getCurrency());
            stmt.setString(4, payment.getDetail());
            stmt.setLong(5, account.getId());
            return stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Payment mapPayment(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment(resultSet.getTimestamp("date").toLocalDateTime(), resultSet.getFloat("amount"), resultSet.getString("currency"), resultSet.getString("detail"));
        payment.setId(resultSet.getInt("id"));
        payment.setAccountId(resultSet.getInt("accountId"));
        payment.setCounterAccountId(resultSet.getInt("counterAccountId"));
        return payment;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}