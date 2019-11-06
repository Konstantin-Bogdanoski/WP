package ukim.mk.finki.konstantin.bogdanoski.wp.util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */

@WebListener()
public class PizzaListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    private final Logger logger;

    // Public constructor is required by servlet spec
    public PizzaListener(Logger logger) {
        this.logger = logger;
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        logger.info("\u001B[1m\u001B[34mWeb context has been initialized\u001B[0m");
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        logger.info("\u001B[1m\u001B[31mWeb context has been destroyed\u001B[0m");
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        logger.warning("\u001B[1mSession\u001B[0m has been\u001B[32m CREATED\u001B[0m, ID => " + se.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        logger.warning("\u001B[1mSession\u001B[0m has been\u001B[31m DESTROYED\u001B[0m, ID => " + se.getSession().getId());
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
        logger.warning("\u001B[1mAttribute\u001B[0m has been\u001B[32m ADDED\u001B[0m, {ATTR:" + sbe.getName() + " | VAL:" + sbe.getValue() + "}");
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        logger.warning("\u001B[1mAttribute\u001B[0m has been\u001B[31m REMOVED\u001B[0m, {ATTR:" + sbe.getName() + " | VAL:" + sbe.getValue() + "}");
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
        logger.warning("\u001B[1mAttribute\u001B[0m has been\u001B[34m REPLACED\u001B[0m, {ATTR:" + sbe.getName() + " | VAL:" + sbe.getValue() + "}");
    }
}
