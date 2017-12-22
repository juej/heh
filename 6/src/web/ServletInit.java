package web;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.Customer;
import model.Order;
import model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServletInit extends HttpServlet {
    Customer lastCustomer = null;
    OrderItem lastOrderItem = null;
    Order lastOrder = null;
    boolean deleteFlag = false;
    boolean restoreFlag = true;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderItemDAO orderItemDAO = (OrderItemDAO) this.getServletContext().getAttribute("orderItemDAO");
        CustomerDAO customerDAO = (CustomerDAO) this.getServletContext().getAttribute("customerDAO");
        OrderDAO orderDAO = (OrderDAO) this.getServletContext().getAttribute("orderDAO");

        List<OrderItem> orderItems = orderItemDAO.list();
        request.setAttribute("orderItems", orderItems);

        List<Order> orders = orderDAO.list();
        request.setAttribute("orders", orders);

        List<Customer> customers = customerDAO.list();
        request.setAttribute("customers", customers);

        this.getServletContext().getRequestDispatcher("/table.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderItemDAO orderItemDAO = (OrderItemDAO) this.getServletContext().getAttribute("orderItemDAO");
        OrderDAO orderDAO = (OrderDAO) this.getServletContext().getAttribute("orderDAO");
        CustomerDAO customerDAO = (CustomerDAO) this.getServletContext().getAttribute("customerDAO");

        String action = request.getParameter("action");
        if (action.equals("insertCustomer")) {
            Long customerId = Long.parseLong(request.getParameter("customerId"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String phone = request.getParameter("phone");
            String dateOfChange = request.getParameter("dateOfChange");
            try {
                customerDAO.insert(customerId, firstName, lastName, city, country, phone);
            } catch (Exception e) {
                log("Failed to insert customer", e);
                response.sendError(500, "Failed to insert customer: " + e.getMessage());
            }
        }

        if (action.equals("insertOrder")) {
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            String orderDate = request.getParameter("orderDate");
            Long customerId = Long.parseLong(request.getParameter("customerId"));
            Long totalAmount = Long.parseLong(request.getParameter("totalAmount"));
            String orderNumber = request.getParameter("orderNumber");
            try {
                orderDAO.insert(orderId, orderDate, customerId, totalAmount, orderNumber);
            } catch (Exception e) {
                log("Failed to insert order", e);
                response.sendError(500, "Failed to insert order: " + e.getMessage());
            }
        }

        if (action.equals("insertOrderItem")) {
            Long orderItemId = Long.parseLong(request.getParameter("orderItemId"));
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            Long productId = Long.parseLong(request.getParameter("productId"));
            Long unitPrice = Long.parseLong(request.getParameter("unitPrice"));
            Long quantity = Long.parseLong(request.getParameter("quantity"));
            try {
                orderItemDAO.insert(orderItemId, orderId, productId, unitPrice, quantity);
            } catch (Exception e) {
                log("Failed to insert orderItem", e);
                response.sendError(500, "Failed to insert orderItem: " + e.getMessage());
            }
        }

        if (action.equals("updateCustomer")) {
            Long customerId = Long.parseLong(request.getParameter("customerId"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String phone = request.getParameter("phone");
            String dateOfChange = request.getParameter("dateOfChange");
            try {
                customerDAO.update(customerId, firstName, lastName, city, country, phone);
            } catch (Exception e) {
                log("Failed to insert customer", e);
                response.sendError(500, "Failed to insert customer: " + e.getMessage());
            }
        }

        if (action.equals("deleteCustomer")) {
            Long customerId = Long.parseLong(request.getParameter("customerId"));

            List<Customer> customers = customerDAO.list();
            for (Customer customer : customers) {
                if (customer.getId() == customerId) {
                    lastCustomer = customer;
                }
            }

            List<Order> orders = orderDAO.list();
            Long orderId = 0L;

            for (Order order : orders) {
                if (order.getCustomerId() == customerId) {
                    orderId = order.getId();
                    lastOrder = order;
                }
            }

            List<OrderItem> orderItems = orderItemDAO.list();

            for (OrderItem orderItem : orderItems) {
                if (orderItem.getOrderId() == orderId) {
                    lastOrderItem = orderItem;
                }
            }

            customerDAO.delete(orderId, customerId);
            deleteFlag = true;
            restoreFlag = true;
        }

        if (action.equals("deleteOrder")) {
            Long orderId = Long.parseLong(request.getParameter("orderId"));
            List<Order> orders = orderDAO.list();

            for (Order order : orders) {
                if (order.getId() == orderId) {
                    lastOrder = order;
                }
            }

            List<OrderItem> orderItems = orderItemDAO.list();

            for (OrderItem orderItem : orderItems) {
                if (orderItem.getOrderId() == orderId) {
                    lastOrderItem = orderItem;
                }
            }

            orderDAO.delete(orderId);
            deleteFlag = true;
            restoreFlag = true;
        }

        if (action.equals("deleteOrderItem")) {
            Long orderItemId = Long.parseLong(request.getParameter("orderItemId"));

            List<OrderItem> orderItems = orderItemDAO.list();

            for (OrderItem orderItem : orderItems) {
                if (orderItem.getId() == orderItemId) {
                    lastOrderItem = orderItem;
                }
            }

            orderItemDAO.delete(orderItemId);
            deleteFlag = true;
            restoreFlag = true;
        }
        if (action.equals("restore") && deleteFlag && restoreFlag) {
            if (lastCustomer != null)
                customerDAO.insert(lastCustomer.getId(), lastCustomer.getFirstName(), lastCustomer.getLastName(), lastCustomer.getCity(), lastCustomer.getCountry(), lastCustomer.getPhone());
            if (lastOrder != null)
                orderDAO.insert(lastOrder.getId(), lastOrder.getOrderDate(), lastOrder.getCustomerId(), lastOrder.getTotalAmount(), lastOrder.getOrderNumber());
            if (lastOrderItem != null)
                orderItemDAO.insert(lastOrderItem.getId(), lastOrderItem.getOrderId(), lastOrderItem.getProductId(), lastOrderItem.getUnitPrice(), lastOrderItem.getQuantity());
            lastOrderItem = null;
            lastOrder = null;
            lastCustomer = null;
            restoreFlag = false;
        }


        response.sendRedirect("/table");
    }
}


