package bkServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bkServlet.HibernateUtil;
import bkServlet.User;

/**
 * Servlet implementation class UserManagerServlet
 */
@WebServlet("/checkuser")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();

			String username = request.getParameter("name");
			String password = request.getParameter("pwd");
			Boolean isValid = login(username, password);

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
	private Boolean login(String username, String password) {
		Boolean isValid = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<User> result = (List<User>) session
				.createQuery("select user from User user where username = :name and password = :pwd")
				.setParameter("name", username).setParameter("pwd", password).list();
		tx.commit();
		
		if (result.size() > 0) {
			isValid = true;
			System.out.println(result);
		}
		else
			isValid = false;
		session.close();
		return isValid;
	}

}