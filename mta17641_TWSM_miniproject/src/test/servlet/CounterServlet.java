package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tests.Counter;

/**
 * Servlet implementation class CounterServlet
 */
@WebServlet("/CounterServlet")
public class CounterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	int count = 0;
	private Counter counter;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CounterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set a cookie for the user, so that the counter does not increase
        // every time the user press refresh
        HttpSession session = request.getSession(true);
        // Set the session valid for 5 secs
        session.setMaxInactiveInterval(5);
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        if (session.isNew()) {
                count++;
        }
        out.println("This site has been accessed " + count + " times.");
	}

	@Override
    public void init() throws ServletException {
            counter = new Counter();
            try {
                    count = counter.getCount();
            } catch (Exception e) {
                    getServletContext().log("An exception occurred in FileCounter", e);
                    throw new ServletException("An exception occurred in FileCounter"
                                    + e.getMessage());
            }
    }

    public void destroy() {
            super.destroy();
            try {
                    counter.save(count);
            } catch (Exception e) {
                    e.printStackTrace();
            }
    }

}
