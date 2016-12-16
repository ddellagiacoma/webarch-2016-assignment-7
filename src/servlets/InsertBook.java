package servlets;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.OperationRemote;

public class InsertBook extends HttpServlet {

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
        
        String title = request.getParameter("title");
		String price = request.getParameter("price");
        
        or.addBook(title, price);

        response.sendRedirect("InsertBook.jsp");
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