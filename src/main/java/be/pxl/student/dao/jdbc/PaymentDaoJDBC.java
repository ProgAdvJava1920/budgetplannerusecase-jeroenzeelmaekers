package be.pxl.student.dao.jdbc;

import be.pxl.student.dao.interfaces.IPaymentDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoJDBC implements IPaymentDao {

    private static final String CREATE = "INSERT INTO Payment (date, amount, currency, detail, accountId, counterAccountId) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM Payment";
    private static final String SELECT_BY_ID = "SELECT * FROM Payment WHERE id = ?";
    private static final String UPDATE = "UPDATE Payment SET date = ?, amount = ?, currency = ?, detail = ?, accountId = ?, counterAccountId = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Payment WHERE id = ?";

    private String url;
    private String user;
    private String password;

    public PaymentDaoJDBC(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Payment> read() {
        List<Payment> paymentList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                paymentList.add(mapPayment(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return paymentList;
    }

    @Override
    public boolean createPayment(Payment payment, Account account, Account counterAccount) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new Date(payment.getDate().getYear(), payment.getDate().getMonthValue(), payment.getDate().getDayOfMonth()));
            statement.setFloat(2, payment.getAmount());
            statement.setString(3, payment.getCurrency());
            statement.setString(4, payment.getDetail());
            statement.setLong(5, account.getId());
            statement.setLong(6, counterAccount.getId());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if(resultSet.next()) {
                        payment.setId(resultSet.getInt(1));
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
    public boolean updatePayment(Payment payment, Account account) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setDate(1, new Date(payment.getDate().getYear(), payment.getDate().getMonthValue(), payment.getDate().getDayOfMonth()));
            statement.setFloat(2, payment.getAmount());
            statement.setString(3, payment.getCurrency());
            statement.setString(4, payment.getDetail());
            statement.setLong(5, account.getId());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePayment(int id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Payment readPayment(int id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return mapPayment(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Payment mapPayment(ResultSet resultSet) throws SQLException{
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