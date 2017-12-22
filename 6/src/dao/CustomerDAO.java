package dao;

import model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO extends AbstractCachingDAO<Customer> {

    public static final String SELECT_BY_ID = "SELECT * FROM CUSTOMER WHERE customer_id = ?";
    public static final String SELECT_ALL = "SELECT * FROM CUSTOMER ORDER BY last_name, first_name";
    public static final String SELECT_FOR_ORDER = "SELECT A.* FROM \"order\" a JOIN customer fa USING (customer_id) WHERE order_id = ?";

    public static final String INSERT = "INSERT INTO CUSTOMER (last_name, first_name) VALUES (?,?)";
    public static final String UPDATE = "UPDATE CUSTOMER SET last_name = ?, first_name = ? where customer_id = ?";
    public static final String DELETE = "DELETE FROM CUSTOMER WHERE customer_id = ?";

    public CustomerDAO(DataSource dataSource) {
        super(dataSource, SELECT_ALL, SELECT_BY_ID, INSERT, UPDATE, DELETE);
    }

    public Customer readObject(ResultSet rs) throws SQLException {
        Customer s = new Customer();
        s.setId(rs.getLong("customer_id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setCity(rs.getString("city"));
        s.setCountry(rs.getString("country"));
        s.setPhone(rs.getString("phone"));
        s.setDateOfChange(rs.getString("date_of_change"));
        return s;
    }

    @Override
    protected int writeObject(Customer obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setString(idx++, obj.getLastName());
        ps.setString(idx++, obj.getFirstName());
        ps.setString(idx++, obj.getCity());
        ps.setString(idx++, obj.getCountry());
        ps.setString(idx++, obj.getPhone());

        return idx;
    }


    public void insert(long customerId, String firstName, String lastName, String city, String country, String phone) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO customer (customer_id, first_name, last_name, city, country, phone) VALUES (?,?,?,?,?,?)");
            ps.setLong(1, customerId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, city);
            ps.setString(5, country);
            ps.setString(6, phone);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(long orderId, long customerId) {
        try (Connection connection = dataSource.getConnection()) {


            PreparedStatement ps = connection.prepareStatement("DELETE FROM orderitem WHERE order_id = ?");
            ps.setLong(1, orderId);
            ps.executeUpdate();
            ps.close();

            ps = connection.prepareStatement("DELETE FROM \"order\" WHERE customer_id = ?");
            ps.setLong(1, customerId);
            ps.executeUpdate();
            ps.close();

            ps = connection.prepareStatement("DELETE FROM customer WHERE customer_id = ?");
            ps.setLong(1, customerId);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(long customerId, String firstName, String lastName, String city, String country, String phone) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement ps = connection.prepareStatement("UPDATE customer SET first_name = ?, last_name = ?, city = ?, country = ?, phone = ? WHERE customer_id = ?");
            ps.setLong(6, customerId);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, city);
            ps.setString(4, country);
            ps.setString(5, phone);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}