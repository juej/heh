package web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.Customer;
import model.Order;
import model.OrderItem;
import org.json.simple.JSONObject;

public class ServletJSON extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CustomerDAO customerDAO = (CustomerDAO) this.getServletContext().getAttribute("customerDAO");
        OrderItemDAO orderItemDAO = (OrderItemDAO) this.getServletContext().getAttribute("orderItemDAO");
        OrderDAO orderDAO = (OrderDAO) this.getServletContext().getAttribute("orderDAO");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<html><head><title>JSON</title></head>");
        out.print("<body><style> body{\n" +
                "        font-family:'Open Sans', sans-serif;\n" +
                "        background:#151837;\n" +
                "        width:90%;\n" +
                "        margin:0 auto;\n" +
                "        padding:2em 0 6em;\n" +
                "        transform: scale(0.67);\n" +
                "        transform-origin: 0 0;}" +
                " h1{\n" +
                "  font-size:3em;\n" +
                "  color:#fefefe;\n" +
                "  text-transform:lowercase;\n" +
                "}</style>");

        List<OrderItem> orderItems = orderItemDAO.list();
        List<Customer> customers = customerDAO.list();
        List<Order> orders = orderDAO.list();

        JSONObject root = new JSONObject();
        JSONObject temp;

        String s = request.getParameter("link");

        if (s.equals("orderitem")) {
            out.println("<h1>orderItem.json<br><br>");
            for (int i = 0; i < orderItems.size(); i++) {
                temp = new JSONObject();
                temp.put("orderitem_id", orderItems.get(i).getId());
                temp.put("order_id", orderItems.get(i).getOrderId());
                temp.put("product_id", orderItems.get(i).getProductId());
                temp.put("unit_price", orderItems.get(i).getUnitPrice());
                temp.put("quantity", orderItems.get(i).getQuantity());
                root.put(i + 1, temp);
            }
        }
        if (s.equals("order")) {
            out.println("<h1>order.json<br><br>");
            for (int i = 0; i < orders.size(); i++) {
                temp = new JSONObject();
                temp.put("order_id", orders.get(i).getId());
                temp.put("order_date", orders.get(i).getOrderDate());
                temp.put("customer_id", orders.get(i).getCustomerId());
                temp.put("total_amount", orders.get(i).getTotalAmount());
                temp.put("order_number", orders.get(i).getOrderNumber());
                root.put(i + 1, temp);
            }
        }
        if (s.equals("customer")) {
            out.println("<h1>customer.json<br><br>");
            for (int i = 0; i < customers.size(); i++) {
                temp = new JSONObject();
                temp.put("customer_id", customers.get(i).getId());
                temp.put("first_name", customers.get(i).getFirstName());
                temp.put("last_name", customers.get(i).getLastName());
                temp.put("city", customers.get(i).getCity());
                temp.put("country", customers.get(i).getCountry());
                temp.put("phone", customers.get(i).getPhone());
                temp.put("date_of_change", customers.get(i).getDateOfChange());
                root.put(i + 1, temp);
            }
        }


        if (s.equals("orderitem")) {
            try (FileWriter file = new FileWriter("C:/Users/Евгений/IdeaProjects/fiftyfive/src/orderItem.json")) {
                file.write(root.toJSONString());
                out.println(root);
            }
        }
        if (s.equals("order")) {
            try (FileWriter file = new FileWriter("C:/Users/Евгений/IdeaProjects/fiftyfive/src/order.json")) {
                file.write(root.toJSONString());
                out.println(root);
            }
        }
        if (s.equals("customer")) {
            try (FileWriter file = new FileWriter("C:/Users/Евгений/IdeaProjects/fiftyfive/src/customer.json")) {
                file.write(root.toJSONString());
                out.println(root);
            }
        }

        out.println("</body>");
        out.println("</html>");
    }

}

