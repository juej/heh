package dao;

import model.OrderItem;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class OrderItemDAO extends AbstractDAO<OrderItem> {

    public OrderItemDAO(DataSource dataSource) {
        super(dataSource, "SELECT * FROM orderitem ORDER by quantity", "SELECT * FROM orderitem WHERE orderitem_ID=?",
                "INSERT INTO orderitem (order_id, product_id, unit_price, quantity) VALUES(?,?,?,?,?)",
                "UPDATE FILM set order_id=?, product_id=?, unit_price=?, quantity=? WHERE orderitem_id = ?",
                "DELETE FROM orderitem WHERE orderitem_id = ?");
    }

    @Override
    public OrderItem readObject(ResultSet rs) throws SQLException {
        OrderItem order = new OrderItem();
        order.setId(rs.getLong("orderitem_id"));
        order.setOrderId(rs.getLong("order_id"));
        order.setProductId(rs.getLong("product_id"));
        order.setUnitPrice(rs.getLong("unit_price"));
        order.setQuantity(rs.getLong("quantity"));

        return order;
    }

//    @Override
//    public long save(OrderItem obj) throws SQLException {
//        long id = super.save(obj);
////        customerDAO.saveCustomersOrder(id, obj.getCustomers());
//        return id;
//
//    }

    @Override
    protected int writeObject(OrderItem obj, PreparedStatement ps, int idx) throws SQLException {
        ps.setLong(idx++,obj.getUnitPrice());
        ps.setLong(idx++, obj.getQuantity());
        return idx;
    }


    public void insert(long orderItemId, long orderId, long productId, double unitPrice, int quantity){
        try (Connection connection = dataSource.getConnection()) {

//            PreparedStatement ps = connection.prepareStatement("DELETE FROM orderitem WHERE order_id = ?");
//            ps.setLong(1, orderId);
//            ps.executeUpdate();
//            ps.close();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO orderitem (orderitem_id, order_id, product_id, unit_price, quantity) VALUES (?,?,?,?,?)");
            ps.setLong(1, orderItemId);
            ps.setLong(2, orderId);
            ps.setLong(3, productId);
            ps.setDouble(4, unitPrice);
            ps.setInt(5, quantity);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//        @Override
//        public void delete(long id) {
//            super.delete(id);
//            customerDAO.deleteForCustomer(id);
//        }


}

