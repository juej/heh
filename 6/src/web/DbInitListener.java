package web;

import org.postgresql.ds.PGPoolingDataSource;
import dao.CustomerDAO;
import dao.OrderItemDAO;
import dao.OrderDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class DbInitListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public DbInitListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Initializing data source connection");

        PGPoolingDataSource poolingDataSource = new PGPoolingDataSource();
        poolingDataSource.setDataSourceName("Lab 2 data source");

        poolingDataSource.setServerName("localhost");
        poolingDataSource.setDatabaseName("Ex");
        poolingDataSource.setUser("postgres");
        poolingDataSource.setPassword("1");
        poolingDataSource.setMaxConnections(8);
        poolingDataSource.setInitialConnections(1);

        OrderItemDAO orderItemDAO = new OrderItemDAO(poolingDataSource);
        CustomerDAO customerDAO = new CustomerDAO(poolingDataSource);
        OrderDAO orderDAO = new OrderDAO(poolingDataSource);

        sce.getServletContext().setAttribute("customerDAO", customerDAO);
        sce.getServletContext().setAttribute("orderDAO", orderDAO);
        sce.getServletContext().setAttribute("orderItemDAO", orderItemDAO);

        sce.getServletContext().setAttribute("datasource", poolingDataSource);

        sce.getServletContext().log("Initialized all DAOs");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("Closing connections pool");

        PGPoolingDataSource poolingDataSource = (PGPoolingDataSource) sce.getServletContext().getAttribute("datasource");
        poolingDataSource.close();

    }
}