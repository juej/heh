<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.OrderItem" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page import="model.Order" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>


<style>

    input[type="submit"] {
        display: block;
        font-size: 14px;
        width:240px;
        margin: 10px auto;
        padding: 10px 8px 10px 8px;
        border-radius: 5px;
        box-shadow: inset 0 1px 2px rgba(0,0,0, .55), 0px 1px 1px rgba(255,255,255,.5);
        border: 1px solid #666;
    }

    input[type="submit"] {
        opacity: 0.5;
    }

   input[type="text"],input[type="number"]{
        display:block;
        width: 300px;
        padding: 15px 0 15px 12px;

        font-weight: 400;
        background: rgba(0,0,0,0.3);
        outline: none;
        text-shadow: 1px 1px 1px rgba(0,0,0,0.3);
        border-radius: 4px;
        box-shadow: inset 0 -5px 45px rgba(100,100,100,0.2), 0 1px  1px rgba(255,255,255,0.2);
        text-indent: 60px;
        transition: all .3s ease-in-out;
        position: relative;
        font-size: 21px;
    }
    input[type="text"]:focus,input[type="number"]:focus{
        text-indent: 12px;
        box-shadow: inset 0 -5px 45px rgba(100,100,100,0.4), 0 1px 1px rgba(255,255,255,0.2);
    }


    input[type="submit"] {
        appearance: none;
        opacity: .99;
        width:120px;
        background: #08c;
        box-shadow: inset 0 1px 2px rgba(255,255,255, .35), 0px 1px 6px rgba(0,246,255,.5);
        border: 1px solid #0a5378;
        border-radius: 4px;
        color: #eee;
        cursor: pointer;
        text-shadow:0px -1px 0px rgba(0,0,0,.5);
    }

    input[type="submit"]:hover {
        background: #08c;
        width:120px;
        border: 1px solid #0a5378;
        border-radius: 3px;
        box-shadow: inset 0px 3px 16px rgba(0,0,0,.25),0px 1px 10px rgba(255,255,255,.5),inset 0px -1px 2px rgba(255,255,255,.35);
        text-shadow:0px 1px 1px rgba(0,0,0,.65);
        -webkit-transition: all 0.40s ease-out;
        transition: all 0.40s ease-out;
    }

    .table-title h3 {
        color: #fafafa;
        font-size: 30px;
        font-weight: 400;
        font-style:normal;
        font-family: "Roboto", helvetica, arial, sans-serif;
        text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);
        text-transform:uppercase;
    }

    table {
        background: white;
        border-radius:3px;
        border-collapse: collapse;
        height: 320px;
        margin: auto;
        max-width: 600px;
        padding:5px;
        width: 100%;
        box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
        animation: float 5s infinite;
    }

    th {
        color:#D5DDE5;;
        background:#1b1e24;
        border-bottom:4px solid #9ea7af;
        border-right: 1px solid #343a45;
        font-size:23px;
        font-weight: 100;
        padding:24px;
        text-align:left;
        text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
        vertical-align:middle;
    }

    th:first-child {
        border-top-left-radius:3px;
    }

    th:last-child {
        border-top-right-radius:3px;
        border-right:none;
    }

    tr {
        border-top: 1px solid #C1C3D1;
        border-bottom-: 1px solid #C1C3D1;
        color:#666B85;
        font-size:16px;
        font-weight:normal;
        text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);
    }

    tr:first-child {
        border-top:none;
    }

    tr:last-child {
        border-bottom:none;
    }

    tr:nth-child(odd) td {
        background:#EBEBEB;
    }

    tr:last-child td:first-child {
        border-bottom-left-radius:3px;
    }

    tr:last-child td:last-child {
        border-bottom-right-radius:3px;
    }

    td {
        background:#FFFFFF;
        padding:20px;
        text-align:left;
        vertical-align:middle;
        font-weight:300;
        font-size:18px;
        text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);
        border-right: 1px solid #C1C3D1;
    }

    td:last-child {
        border-right: 0px;
    }

    h4 {
        margin-bottom: 0em;
        padding-bottom: 0.5em;
        font-family: "Open Sans", sans-serif;
    }


    body{
        font-family:'Open Sans', sans-serif;
        background:#151837;
        margin:0 auto;
        padding:2em 0 6em;
        zoom: 0.7;
    }


    a,a:visited,a:hover,a:active{
        -webkit-backface-visibility:hidden;
        backface-visibility:hidden;
        position:relative;
        transition:0.5s color ease;
        text-decoration:none;
        color:#81b3d2;
        font-size:2.5em;
    }
    a:hover{
        color:#d73444;
    }


</style>


<h4 align="center"><a href='/json?link=customer'> Customer Table in JSON format </a></h4>
<br>
<table align="center">
    <tr>
        <th> CUSTOMER ID</th>
        <th> LAST NAME</th>
        <th> FIRST NAME</th>
        <th> COUNTRY</th>
        <th> CITY</th>
        <th> PHONE NUMBER</th>
        <th> DATE OF CHANGE</th>
        <th> ACTION</th>
    </tr>

    <%
        for (Customer customer : (List<Customer>) request.getAttribute("customers")) {
    %>

    <tr>
        <%
            out.println(String.format("<form method=\"post\" action=\"/table?action=updateCustomer&customerId=%d\">", customer.getId()));
        %>
        <td>
            <%=customer.getId()%>
        </td>
        <td>
            <%
                out.println(String.format("<input type=\"text\" name=\"lastName\" value=\"%s\"/>", customer.getLastName()));
            %>
        </td>
        <td>
            <%
                out.println(String.format("<input type=\"text\" name=\"firstName\" value=\"%s\"/>", customer.getFirstName()));
            %>
        </td>
        <td>
            <%
                out.println(String.format("<input type=\"text\" name=\"country\" value=\"%s\"/>", customer.getCountry()));
            %>
        </td>
        <td>
            <%
                out.println(String.format("<input type=\"text\" name=\"city\" value=\"%s\"/>", customer.getCity()));
            %>
        </td>
        <td>
            <%
                out.println(String.format("<input type=\"text\" name=\"phone\" value=\"%s\"/>", customer.getPhone()));
            %>
        </td>
        <td>
            <%
                out.println(String.format("<input type=\"text\" name=\"dateOfChange\" value=\"%s\"/>", customer.getDateOfChange()));
            %>
        </td>


        <td>

            <input type="submit" value="UPDATE">
            </form>

            <%
                out.println(String.format("<form method=\"post\" action=\"/table?action=deleteCustomer&customerId=%d\">", customer.getId()));
            %>
            <input type="submit" value="DELETE">
            </form>


        </td>

    </tr>
    <%
        }
    %>

    <tr>
        <form method="post" action="/table?action=insertCustomer">
            <td><input type="number" name="customerId" min="1"/></td>
            <td><input type="text" name="lastName" /></td>
            <td><input type="text" name="firstName" /></td>
            <td><input type="text" name="country" /></td>
            <td><input type="text" name="city" /></td>
            <td><input type="text" name="phone" /></td>
            <td><input type="text" name="dateOfChange"/></td>
            <td><input type="submit" value="SAVE"/>
        </form>
        <form method="post" action="/table?action=restore">
            <input type="submit" value="RESTORE LAST"/>
        </form>
        </td>
    </tr>
</table>

<h4 align="center"><a href='/json?link=order'> Order Table in JSON format </a></h4>
<br>
<table align="center">
    <tr>
        <th> ORDER ID</th>
        <th> CUSTOMER ID</th>
        <th> ORDER DATE</th>
        <th> ORDER NUMBER</th>
        <th> TOTAL AMOUNT</th>
        <th> ACTION</th>
    </tr>

    <%
        for (Order order : (List<Order>) request.getAttribute("orders")) {
    %>

    <tr>

        <%
            out.println(String.format("<form method=\"post\" action=\"/table?action=deleteOrder&orderId=%d\">", order.getId()));
        %>
        <td>
            <%=order.getId()%>
        </td>
        <td>
            <%=order.getCustomerId()%>
        </td>
        <td>
            <%=order.getOrderDate()%>
        </td>
        <td>
            <%=order.getOrderNumber()%>
        </td>
        <td>
            <%=order.getTotalAmount()%>
        </td>

        <td>
            <input type="submit" value="DELETE">
        </td>

        </form>
    </tr>

    <%
        }
    %>

    <tr>
        <form method="post" action="/table?action=insertOrder">
            <td><input type="number" name="orderId" min="1"/></td>
            <td><input type="number" name="customerId" min="1"/></td>
            <td><input type="text" name="orderDate" min = "1"/></td>
            <td><input type="number" name="orderNumber" min="542000"/></td>
            <td><input type="number" name="totalAmount" min="1"></td>
            <td><input type="submit" value="SAVE">
        </form>
        <form method="post" action="/table?action=restore">
            <input type="submit" value="RESTORE LAST"/>
        </form>
        </td>
    </tr>
</table>

<h4 align="center"><a href='/json?link=orderitem'> Order's Item Table in JSON format </a></h4>
<br>
<table align="center">
    <tr>
        <th> ORDER ITEM ID</th>
        <th> ORDER ID</th>
        <th> PRODUCT ID</th>
        <th> QUANTITY</th>
        <th> UNIT PRICE</th>
        <th> ACTION</th>
    </tr>

    <%
        for (OrderItem orderItem : (List<OrderItem>) request.getAttribute("orderItems")) {
    %>

    <tr>

        <%
            out.println(String.format("<form method=\"post\" action=\"/table?action=deleteOrderItem&orderItemId=%d\">", orderItem.getId()));
        %>

        <td>
            <%=String.format("<a href='/info?id=%d&orderId=%d&productId=%d&quantity=%d&unitPrice=%d'</a>",
                    orderItem.getId(),
                    orderItem.getOrderId(),
                    orderItem.getProductId(),
                    orderItem.getQuantity(),
                    orderItem.getUnitPrice())%>
            <%=orderItem.getId()%>
            </a>
        </td>
        <td>
            <%=orderItem.getOrderId()%>
        </td>
        <td>
            <%=orderItem.getProductId()%>
        </td>
        <td>
            <%=orderItem.getQuantity()%>
        </td>
        <td>
            <%=orderItem.getUnitPrice()%>
        </td>
        <td>
            <input type="submit" value="DELETE">
        </td>

        </form>
    </tr>

    <%
        }
    %>
    <tr>
        <form method="post" action="/table?action=insertOrderItem">
            <td><input type="number" name="orderItemId" min="1"/></td>
            <td><input type="number" name="orderId" min="1"/></td>
            <td><input type="number" name="productId" min="1"/></td>
            <td><input type="number" name="quantity" min="1"/></td>
            <td><input type="number" name="unitPrice" min="1"></td>
            <td><input type="submit" value="SAVE">
        </form>
        <form method="post" action="/table?action=restore">
            <input type="submit" value="RESTORE LAST"/>
        </form>
        </td>
    </tr>
</table>


</body>
</html>
