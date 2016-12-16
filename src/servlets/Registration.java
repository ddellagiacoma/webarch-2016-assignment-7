package servlets;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Properties;

import bean.AccountRemote;
import bean.ShoppingCartRemote;

public class Registration extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NamingException {

		Properties jndiProperties = new Properties();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		jndiProperties.put("jboss.naming.client.ejb.context", true);

		final Context ctx = new InitialContext(jndiProperties);

		// look up accountmanager bean
		AccountRemote am = (AccountRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/AccountManager!bean.AccountRemote");
        ShoppingCartRemote sc = (ShoppingCartRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/ShoppingCartManager!bean.ShoppingCartRemote");

		
		String username = request.getParameter("username");
		String pw = request.getParameter("password");
		
        HttpSession session = request.getSession(true);

		int id = am.register(username, pw);
		System.out.println("Succesfully registred as " + am.getAccountInfo(id).getUsername() + "!");
		sc.getCart(id);
    	
		session.setAttribute("iduser", id);
        session.setAttribute("sc", sc);
        session.setAttribute("username", username);

		response.sendRedirect("Home");

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				processRequest(request, response);
			} catch (NamingException e) {
				e.printStackTrace();
			}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				processRequest(request, response);
			} catch (NamingException e) {
				e.printStackTrace();
			}
	}
}