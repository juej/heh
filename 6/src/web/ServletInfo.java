package web;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.Customer;
import model.Order;
import model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet(name = "ServletInfo")
public class ServletInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CustomerDAO customerDAO = (CustomerDAO) this.getServletContext().getAttribute("customerDAO");
        OrderItemDAO orderItemDAO = (OrderItemDAO) this.getServletContext().getAttribute("orderItemDAO");
        OrderDAO orderDAO = (OrderDAO) this.getServletContext().getAttribute("orderDAO");

        List<OrderItem> orderItems = orderItemDAO.list();
        List<Customer> customers = customerDAO.list();
        List<Order> orders = orderDAO.list();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<html><head><title>JSON</title></head>");
        out.print("<body><style> body{\n" +
                "        font-family:'Open Sans', sans-serif;\n" +
                "        background:#151837;\n" +
                "        width:90%;\n" +
                "        margin:0 auto;\n" +
                "        padding:2em 0 6em;\n" +
                "         transform: scale(0.67);\n" +
                "        transform-origin: 0 0;" +
                "    }" +
                " h1{\n" +
                "  font-size:3em;\n" +
                "  color:#fefefe;\n" +
                "  text-transform:lowercase;\n" +
                "}</style>");

//        String s = request.getParameter("productId");


        long orderItemId = Long.parseLong(request.getParameter("id"));
        long orderId = Long.parseLong(request.getParameter("orderId"));

//        long productId = Long.parseLong(request.getParameter("productId"));

//        long quantity = Long.parseLong(request.getParameter("quantity"));
//        long unitPrice = Long.parseLong(request.getParameter("unitPrice"));

//        OrderItem orderItem = new OrderItem();
//        orderItem.setId(orderItemId);
//         orderItem.setOrderId(orderId);
//        orderItem.setProductId(productId);
//        orderItem.setQuantity(quantity);
//        orderItem.setUnitPrice(unitPrice);
//        boolean o = orderItems.equals(orderItem);

//        int orderItemIndex = orderItems.indexOf(orderItem);
//        int orderId = (int) orderItems.get(orderItemIndex).getOrderId();

        int i;
        for (i = 0; i < orders.size(); i++) {
//            out.println(orderItems.get(i).getId()+" ");
//            out.println(orderItems.get(i).getQuantity()+" ");
//            out.println(orderItems.get(i).getUnitPrice()+" ");
//            out.println(orderItems.get(i).getOrderId()+" ");
//            out.println(orderItems.get(i).getProductId()+" ");
            if (orderId == orders.get(i).getId()) {
                break;
            }
        }

        int customerId = (int) orders.get(i).getCustomerId();

        int j;
        for (j = 0; j < customers.size(); j++) {
            if (customerId == customers.get(j).getId()) {
                break;
            }
        }

        String customerInfo = " ID: " + customerId + "<br>" + customers.get(j).getFirstName() + " " + customers.get(j).getLastName();

        int k;
        for (k = 0; k < orders.size(); k++) {
            if (orderId == orders.get(k).getId()) {
                break;
            }
        }

        String orderInfo = " ID: " + orderId + "<br> ORDER DATE: " + orders.get(k).getOrderDate() + "<br> ORDER NUMBER: " + orders.get(k).getOrderNumber();
        String orderItemInfo = "ORDER'S ITEM ID: " + orderItemId;

//        out.println("<p><h2>index: " + j + "<h1></p>");
//        out.println("<p><h2>id: " + orderItemId + " <h1></p>");
//        out.println("<p><h2>productid: " + productId + " <h1></p>");
//        out.println("<p><h2>orderid: " + orderId + " <h1></p>");
//        out.println("<p><h2>quantity: " + quantity + " <h1></p>");
//        out.println("<p><h2>unitprice: " + unitPrice + " <h1></p>");
        out.println("<h1>" + orderItemInfo + "<br>CUSTOMER " + customerInfo + "<br>ORDER " + orderInfo +
                "</h1>");

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }
}
