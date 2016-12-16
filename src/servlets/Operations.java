package servlets;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import bean.OperationRemote;


public class Operations extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
    	
    	// set up for widfly access
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);

        final Context ctx = new InitialContext(jndiProperties);
  
        //look up the administrator remote interface
        OperationRemote or = (OperationRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/OperationManager!bean.OperationRemote");

        List<String> listOperations = or.listOperation();
        
        request.setAttribute("list",  listOperations);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Operations.jsp");
        dispatcher.forward(request, response);

    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			processRequest(request, response);
		} catch (NamingException e) {
			e.printStackTrace();
		}
    }
}