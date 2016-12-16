# Servlets, JSPs and Filters

## 1. INTRODUCTION

The goal of this assignment is to create a website that replaces the desktop interfaces of the administrator
and the user, developed for the [4th assignment](https://github.com/ddellagiacoma/webarch-2016-assignment-4). The website allows users to access a DB through an Enterprise JavaBean (EJB) Layer, performing some typical operation of online stores.

The users require to register or login to the website at first. After that the website show them in homepage
all the available books. Now the user is free to add books to the cart, see the content of the cart, buy all the
books present in the cart and empty the cart. Moreover, the user can logout though the session timeouts
automatically after 60 minutes.

On the other hand, the website allows administrator to do the same thing as regular users. In addition, the
administrator can add a new book to the DB, specifying title and price, and list all of buying operation
performed by users.

The implementation of the website was performed through servlets, JSPs and filters.

## 2. IMPLEMENTATION

The assignment has been developed in Eclipse using a Dynamic Web Project.

The **BookStoreWeb** project is composed by the following **servlets**:

* Login
* Logout
* Home
* Add
* Buy
* Empty
* InsertBook
* Operations
* Registration

These **JSPs** was used to generate web pages based on HTML:

* Login
* Registration
* Home
* Cart
* InsertBook
* Operations

In addition, two **filters** has been implemented:

* FilterLogin
* FilterAdministrator

The following remote interfaces are also present in order to access the EJB already existing:

* AccountRemote
* OperationRemote
* ShoppingCartRemote

Finally, all the servlets and filters are declared and mapped into the **web.xml**.

The servlets are mainly responsible for looking up the session beans and calling the related remote
methods. For example the **Login** servlet acts in this way: first of all it looks up the session beans.

```java
Properties jndiProperties = new Properties();
jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
jndiProperties.put("jboss.naming.client.ejb.context", true);

final Context ctx = new InitialContext(jndiProperties);

// look up beans
AccountRemote am = (AccountRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/AccountManager!bean.AccountRemote");
ShoppingCartRemote sc = (ShoppingCartRemote) ctx.lookup("BookStoreEE/BookStoreEE-ejb/ShoppingCartManager!bean.ShoppingCartRemote");
```

After that it creates a new session whether it does not exist, calls the remote method login to ensure that
the user is in the DB and assigns a new cart to him/her.

Finally, it sets as session attribute the id, the cart and the username of the user and redirects the user to
the **Home** servlet.

```java
HttpSession session = request.getSession(true);

int id = am.login(username, pw);
sc.getCart(id);
	    	
session.setAttribute("iduser", id);
session.setAttribute("sc", sc);
session.setAttribute("username", username);
	        
response.sendRedirect("Home");
```

On the other hand, the JSPs are mainly responsible for displaying dynamic web pages. For example this
represents part of the **Home** JSP.

```html
<h1>Hello <c:out value="${sessionScope.username}" /></h1>
<a href='Logout'>Logout</a>	
<h2>These are the books available:</h2>
<table>
  <c:forEach items="${list}" var="item">
    <c:set var="bookParts" value="${fn:split(item, ' ')}" />
    <tr>
      <td><c:out value="${item}" /></td>
      <td><a href='Add?idbook=${bookParts[1]}'>Add to cart</a></td>
    </tr>
  </c:forEach>
</table>
```

The JSP displays the name of the user logged in and put in a table all the available books. In fact, the
variable named **list** was passed by the **Home** servlet and contains a list of available books, their prices and
IDs.

Moreover, JavaServer Pages Standard Tag Library (**JSTL**) was used to implement complex functions. In
particular, core tags were used to implement conditions and iterations, and function tags were used to split
strings to get book IDs.

The two filters avoid that a user can call other servlets or JSPs without logging in. If a user try to do it the
**LoginFilter** redirect him/her to the “welcome file” which is **Login** JSP (as defined in **web.xml**).

```java
HttpServletRequest req = (HttpServletRequest) request;
HttpSession session = req.getSession(false);

if (session == null) {
  RequestDispatcher rd = request.getRequestDispatcher("/");
  rd.forward(request, response);
} else {

  if (session.getAttribute("iduser")==null) {
    RequestDispatcher rd = request.getRequestDispatcher("/");
    rd.forward(request, response);
  } else {
    chain.doFilter(request, response);
  }
}
```
As can be seen, the **LoginFilter** controls if there is a session and if the session have a iduser.

The **AdministratorFilter** acts after the **LoginFilter**. Its task is to prevent that users navigate to administrator
servlets and JSPs.
```xml
<filter-mapping>
  <filter-name>FilterAdministrator</filter-name>
  <servlet-name>Operations</servlet-name>
  <servlet-name>InsertBook</servlet-name>
  <url-pattern>/Operations.jsp</url-pattern>
  <url-pattern>/InsertBook.jsp</url-pattern>		
</filter-mapping>
```

For simplicity, it was assumed that the administrator is the one who have admin as username.

However, it is important to remember that the **BookStoreWeb** requires the **jboss-client.jar** and **jstl-1.2.jar**
libraries to work correctly.

## 3. DEPLOYMENT

To run the application is important configure correctly the environment.

First of all, it is necessary to run Apache Derby typing the following command:

**java –jar DERBY_HOME/lib/derbyrun.jar server start &**

![image](https://cloud.githubusercontent.com/assets/24565161/21272054/e6bca052-c3bd-11e6-9c4f-8c6ce2e0774c.png)

Now WildFly can be started launching the **standalone.bat** (or **standalone.sh**) file. **The BookStoreEE.ear** file
should be already present in the deployments folder of WildFly.

Finally, the war file BookStoreWeb.war can be copied into the deployment folder of Tomcat:

**CATALINA_HOME/apache-tomcat-8.5.5/webapps**

Launching the file **startup.bat** (or **startup.sh**) Tomcat starts and deploys the file.

The Tomcat port should be changed from 8080 to something else (8081 in my case) to avoid conflicts.

Now, connecting to the address **http://localhost:8081/BookStoreWeb/** it is possible to navigate into the website.

The following table shows the websites:

![image](https://cloud.githubusercontent.com/assets/24565161/21272075/fcd59a56-c3bd-11e6-9438-067a0ebaed32.png)
