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
 * Servlet implementation class QueryUser
 */
@WebServlet("/queryUser")
public class QueryUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryUser() {
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
			System.out.printf("name:%s\n",username);
			String res = queryUser(username);

			out.println(res);
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
	private String queryUser(String username) {
		String res = "haha";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		int result = Integer.parseInt( 
				session
				.createQuery("select sum(amount) from Order where username = :name group by username")
				.setParameter("name", username)
				.uniqueResult().toString()
				);
		
		res = "The user "+ username + " has bought " + result + " books.";
		
		tx.commit();
		session.close();
		return res;
	}
	
}