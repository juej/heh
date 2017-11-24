package dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Order;

public class OrderDAO extends AbstractDAO<Order> {
    private CustomerDAO customerDAO;

    public OrderDAO(DataSource dataSource) {
        super(dataSource, "SELECT * FROM \"order\" ORDER BY total_amount", "SELECT * FROM \"order\" WHERE order_id=?",
                "INSERT INTO \"order\" (order_date, customer_id, total_amount, order_number) VALUES(?,?,?,?)",
                "UPDATE \"order\" set order_date=?, customer_id=?, total_amount=?, order_number=? WHERE order_id = ?",
                "DELETE FROM \"order\" WHERE order_id = ?");
    }

    @Override
    public Order readObject(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("order_id"));
        order.setCustomerId(rs.getLong("customer_id"));
        order.setOrderDate(rs.getString("order_date"));
        order.setTotalAmount(rs.getLong("total_amount"));
        order.setOrderNumber(rs.getString("order_number"));

//            order.setCustomers(customerDAO.listForOrder(order.getId()));

        return order;
    }

    @Override
    public long save(Order obj) throws SQLException {
        long id = super.save(obj);
//            customerDAO.saveCustomersOrder(id, obj.getCustomers());
        return id;

    }

    @Override
    protected int writeObject(Order obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setString(idx++, obj.getOrderDate());
        ps.setString(idx++, obj.getOrderNumber());
        ps.setLong(idx++, obj.getTotalAmount());
        return idx;
    }

//        @Override
//        public void delete(long id) {
//            super.delete(id);
//            customerDAO.deleteForCustomer(id);
//
//
//        }


    public void insert(long orderId, String orderDate, long customerId, int totalAmount, String orderNumber){
        try (Connection connection = dataSource.getConnection()) {

//            PreparedStatement ps = connection.prepareStatement("DELETE FROM \"order\" WHERE order_id = ?");
//            ps.setLong(1, orderId);
//            ps.executeUpdate();
//            ps.close();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO \"order\" (order_id, order_date, customer_id, total_amount, order_number) VALUES (?,?,?,?,?)");
            ps.setLong(1, orderId);
            ps.setString(2, orderDate);
            ps.setLong(3, customerId);
            ps.setDouble(4, totalAmount);
            ps.setString(5, orderNumber);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public CustomerDAO getCustomerDAO() {
//        return customerDAO;
//    }
//
//    public void setCustomerDAO(CustomerDAO customerDAO) {
//        this.customerDAO = customerDAO;
//    }

}
