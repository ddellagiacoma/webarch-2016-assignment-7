package servlets;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AccountRemote;
import bean.ShoppingCartRemote;

public class Login extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

		try {
			Properties jndiProperties = new Properties();
			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			jndiProperties.put("jboss.naming.client.ejb.context", true);

			final Context ctx = new InitialContext(jndiProperties);

			// look up beans
			AccountRemote am = (AccountRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/AccountManager!bean.AccountRemote");
	        ShoppingCartRemote sc = (ShoppingCartRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/ShoppingCartManager!bean.ShoppingCartRemote");

	        String username = request.getParameter("username");
	        String pw = request.getParameter("password");

	        HttpSession session = request.getSession(true);

			int id = am.login(username, pw);
	    	sc.getCart(id);
	    	
			session.setAttribute("iduser", id);
	        session.setAttribute("sc", sc);
	        session.setAttribute("username", username);
	        
			response.sendRedirect("Home");
		} catch (Exception e) {
			try {
				response.sendRedirect("Login.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		processRequest(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		processRequest(request, response);
		System.out.println("Sono nel catch del doPost");

	}
}