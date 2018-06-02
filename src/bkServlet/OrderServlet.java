package bkServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/submitOrder")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrderServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();

			String cart[][] = null;
			String currUser = request.getParameter("Cname");
			System.out.println("user:"+currUser);
			
			String jsonData = request.getParameter("cart");
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			
			JSONArray jsonLine = JSONArray.fromObject(jsonData);
			
			for (int i = 0; i<jsonLine.length(); i++) {
				JSONArray jsonbook = (JSONArray) jsonLine.get(i);
				/*
				for(int j = 0 ; j < jsonbook.length() ; j++){ // j=5 is amount
	                //String tmp = (String)jsonbook.get(j); 
	                System.out.println(jsonbook.get(j)); 
	            }*/
				int amount;
				if ((amount = (Integer)jsonbook.get(5)) == 0 )
					continue;
				
				Order order = new Order();
				order.setId((long)88);
				order.setTitle((String)jsonbook.get(0));
				order.setUsername(currUser);
				order.setPrice( (Integer)jsonbook.get(4) );
				order.setAmount( amount );
				
				session.save(order);
			}
			
			tx.commit();
			session.close();

			out.println("haha");
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
}