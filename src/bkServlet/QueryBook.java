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
 * Servlet implementation class QueryBook
 */
@WebServlet("/queryBook")
public class QueryBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryBook() {
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

			String title = request.getParameter("title");
			System.out.printf("name:%s\n",title);
			String res = queryBook(title);

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
	private String queryBook(String title) {
		String res = "haha";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		int result = Integer.parseInt( 
				session
				.createQuery("select sum(amount) from Order where title = :title group by title")
				.setParameter("title", title)
				.uniqueResult().toString()
				);
		
		res = "The book "+ title + " has been sold " + result + " copies.";
		
		tx.commit();
		session.close();
		return res;
	}
	
}