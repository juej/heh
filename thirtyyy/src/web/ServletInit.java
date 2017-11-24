package web;

import dao.CustomerDAO;
import model.Customer;
import dao.OrderDAO;
import model.Order;
import dao.OrderItemDAO;
import model.OrderItem;
import org.postgresql.ds.PGPoolingDataSource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

public class ServletInit extends HttpServlet {
    private DataSource dataSource;

    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dataSource = createDataSource();
        customerDAO = new CustomerDAO(dataSource);
        orderDAO = new OrderDAO(dataSource);

        orderItemDAO = new OrderItemDAO(dataSource);

        this.getServletContext().setAttribute("customerDAO", customerDAO);
        this.getServletContext().setAttribute("orderItemDAO", orderItemDAO);
        this.getServletContext().setAttribute("orderDAO", orderDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String tableFormat = "<table border=\"10\" bordercolor=\"#d3d3d3\" cellspacing=\"10\" cellpadding=\"10\" align=\"center\">";

        out.println("<html>");
        out.println("<head><title> Table </title></head>");
        out.println("<body>");

//          out.println("<h1> Order's Item </h1>");
        out.println(tableFormat);
        out.println("<caption> <big> <a href=\"/json?link=orderitem\"> Order's Item </big></caption>");
        out.println("<colgroup style=\"background: #fffdce;\">");

        List<OrderItem> orderItems = orderItemDAO.list();
        out.println("<tr bgcolor=#fffdce><th> ORDER ITEM ID </th><th> ORDER ID </th><th> PRODUCT ID </th><th> QUANTITY </th><th> UNIT PRICE </th> </tr>");
        for (OrderItem orderItem : orderItems) {
            out.println(String.format("<tr>  <td> <a href=\"/info?id=%d&orderId=%d&productId=%d&quantity=%d&unitPrice=%d\"> %03d</td> <td>%03d</td> <td>%03d</td>" +
                            " <td name=quantity>%d</td> <td name=unitPrice>%d</td> </tr>",
                    orderItem.getId(), orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getUnitPrice(),
                    orderItem.getId(), orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getUnitPrice()));
        }
        out.println("</table>");
        out.println("<br>");

//        out.println("<h1> Orders </h1>");
        out.println(tableFormat);
        out.println("<caption> <big>  <a href=\"/json?link=order\"> Orders</big> </caption>");
        out.println("<colgroup style=\"background: #fffdce;\">");

        List<Order> orders = orderDAO.list();
        out.println("<tr bgcolor=#fffdce>" +
                "<th> ORDER ID </th>" +
                "<th> CUSTOMER ID </th>" +
                "<th> ORDER DATE </th>" +
                "<th> ORDER NUMBER </th>" +
                "<th> TOTAL AMOUNT </th> </tr>");
        for (Order order : orders) {
            out.println(String.format("<tr> <td>%03d</td> <td>%03d</td> <td>%s</td> <td>%s</td> <td>%d</td> </tr>",
                    order.getId(), order.getCustomerId(), order.getOrderDate(), order.getOrderNumber(), order.getTotalAmount()));
        }
        out.println("</table>");
        out.println("<br>");

//          out.println("<h1> Customers </h1>");


        out.println(tableFormat);
        out.println("<caption> <big>  <a href=\"/json?link=customer\"> Customers </big> </caption>");
        out.println("<colgroup style=\"background: #fffdce;\">");
        out.println("<tr bgcolor=#fffdce>" +
                " <th> CUSTOMER ID </th>" +
                " <th> LAST NAME </th>" +
                " <th> FIRST NAME </th>" +
                " <th> COUNTRY </th> " +
                "<th> CITY </th>" +
                " <th> PHONE NUMBER </th>" +
                " <th> DATE OF CHANGE </th> </tr>");

        List<Customer> customers = customerDAO.list();

        for (Customer customer : customers) {
//            out.println(String.format("<tr><td>%03d</td><td>%s %s</td><td>%s<td></tr>",
//                    customer.getId(), customer.getLastName(), customer.getFirstName(),
//                    customer.getDateOfChange() != null ?
//                            " [" + SimpleDateFormat.getDateInstance().format(customer.getDateOfChange()) + "]"
//                            : ""));

            out.println(String.format("<tr><td>%03d</td><td>%s</td><td> %s</td><td>%s </td><td>%s</td><td>%s </td><td>%s</td> </tr>",
                    customer.getId(), customer.getLastName(), customer.getFirstName(), customer.getCountry(), customer.getCity(), customer.getPhone(), customer.getDateOfChange() != null ?
                            " [" + customer.getDateOfChange() + "]"
                            : ""));
        }


        out.println("</table>");
        out.println("</body>");
        out.println("</html>");


    }


    public static DataSource createDataSource() {
        PGPoolingDataSource poolingDataSource = new PGPoolingDataSource();
        poolingDataSource.setDataSourceName("Lab 3 data source");

        poolingDataSource.setServerName("localhost");
        poolingDataSource.setDatabaseName("Ex");
        poolingDataSource.setUser("postgres");
        poolingDataSource.setPassword("1");
        poolingDataSource.setAllowEncodingChanges(true);
        poolingDataSource.setMaxConnections(8);
        poolingDataSource.setInitialConnections(2);
        return poolingDataSource;
    }
}


