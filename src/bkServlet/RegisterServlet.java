package bkServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();

			String username = request.getParameter("username");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String password = request.getParameter("pwd");
			System.out.printf("name:%s pwd:%s phone:%s email:%s\n",username,password, phone, email);
			Boolean isValid = register(username, password, phone, email);

			out.println(isValid);
			out.flush();
			out.close();
		} catch (Exception ex) {
			if (ServletException.class.isInstance(ex)) {
				throw (ServletException) ex;
			} else {
				throw new ServletException(ex);
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	private Boolean register(String username, String password, String phone, String email) {
		Boolean isValid = false;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		User newuser = new User();
		newuser.setId((long)88); // tmp rand set 
		newuser.setUsername(username);
		newuser.setPassword(password);
		newuser.setPhone(phone);
		newuser.setEmail(email);
		newuser.setRole("USER");
		
		session.save(newuser);
		tx.commit();
		
		isValid = true;
		session.close();
		return isValid;
		/*
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<User> result = (List<User>) session
				.createQuery("select user from User user where firstname = :name and lastname = :pwd")
				.setParameter("name", username).setParameter("pwd", password).list();
		tx.commit();
		
		if (result.size() > 0) {
			isValid = false;
			System.out.println(result);
		}
		else {
			isValid = true;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx;
			List<User> res = (List<User>) session
					.createQuery("select user from User user").list();
			tx.commit();
			System.out.println(res);
			
			User newuser = new User();
			newuser.setId((long)88); // tmp rand set 
			newuser.setFirstname(username);
			newuser.setLastname(password);
			newuser.setPhone(phone);
			newuser.setEmail(email);
			newuser.setRole("USER");
			try {
				tx = session.beginTransaction();
				session.save(newuser);
				tx.commit();
			} catch (Exception e) {
				if(tx!=null){  
	                tx.rollback();  
	                e.printStackTrace();  
	            }  
			}
		
		session.close();
		return isValid;
		*/
	}

}
