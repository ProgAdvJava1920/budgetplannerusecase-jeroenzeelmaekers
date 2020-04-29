package be.pxl.student.dao.jdbc;

import be.pxl.student.dao.interfaces.ILabelDao;
import be.pxl.student.entity.Label;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelDaoJDBC implements ILabelDao {

    private static final String CREATE = "INSERT INTO Label ('name', 'description') VALUES (?,?)";
    private static final String SELECT_ALL = "SELECT * FROM Label";
    private static final String SELECT_BY_ID = "SELECT * FROM Label WHERE id = ?";
    private static final String UPDATE = "UPDATE Label SET 'description' = ?, 'name' = ? WHERE 'id' = ?";
    private static final String DELETE = "DELETE FROM Label WHERE 'id' = ?";

    private String url;
    private String user;
    private String password;

    public LabelDaoJDBC(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Label> read() {
        List<Label> labelList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                labelList.add(mapLabel(resultSet));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return labelList;
    }

    @Override
    public boolean createLabel(Label label) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, label.getName());
            statement.setString(2, label.getDescription());
            if (statement.executeUpdate() == 1) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if(resultSet.next()) {
                        label.setId(resultSet.getInt(1));
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
    public boolean updateLabel(Label label) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, label.getName());
            statement.setString(2, label.getDescription());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteLabel(int id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Label readLabel(int id) {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return mapLabel(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Label mapLabel(ResultSet resultSet) throws SQLException {
        Label label = new Label();
        label.setName(resultSet.getString("name"));
        label.setDescription(resultSet.getString("description"));
        label.setId(resultSet.getInt("id"));
        return label;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
