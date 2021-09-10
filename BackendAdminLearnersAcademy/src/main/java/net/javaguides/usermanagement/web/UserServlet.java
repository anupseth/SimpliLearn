package net.javaguides.usermanagement.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;

import net.javaguides.usermanagement.dao.StudentDao;
import net.javaguides.usermanagement.dao.UserDao;
import net.javaguides.usermanagement.model.Student;
import net.javaguides.usermanagement.model.User;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the user.
 * 
 * @email Anup Seth
 */

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public void init() {
		userDao = new UserDao();
		

		/*
		 * StudentDao studDao = new StudentDao();
		 * 
		 * Student stud1 = new Student("Stud 1"); Student stud2 = new Student("Stud 2");
		 * Student stud3 = new Student("Stud 3");
		 * 
		 * studDao.saveUser(stud1); studDao.saveUser(stud2); studDao.saveUser(stud3);
		 * 
		 * List<Student> allUser = studDao.getAllStudent();
		 * 
		 * System.out.println(" "); allUser.forEach(System.out::println);
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(" ------> Servlet path " + action);
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			case "/list":
				listUser(request, response);
				break;
			case "/login":
				loginAdmin(request, response);
				break;
			default:
				System.out.println(" Inside default case .......");
				showLoginPage(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void loginAdmin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();

		String n = request.getParameter("userName");
		String p = request.getParameter("userPass");

		if (p.equals("servlet")) {
			RequestDispatcher rd = request.getRequestDispatcher("list");
			HttpSession session = request.getSession();
			session.setAttribute("userName", n);
			rd.forward(request, response);
		} else {
			out.print("Sorry UserName or Password Error!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.include(request, response);

		}

	}

	private void showLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
		requestDispatcher.forward(request, response);
	}

	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userName") == null) {
			out.print("Please login first");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			System.out.println(" listUser called .......");
			List<User> listUser = userDao.getAllUser();
			request.setAttribute("listUser", listUser);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userName") == null) {
			out.print("Please login first");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userName") == null) {
			out.print("Please login first");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			int id = Integer.parseInt(request.getParameter("id"));
			User existingUser = userDao.getUser(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			request.setAttribute("user", existingUser);
			dispatcher.forward(request, response);
		}
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userName") == null) {
			out.print("Please login first");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");
			User newUser = new User(name, email, country);
			userDao.saveUser(newUser);
			response.sendRedirect("list");
		}
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userName") == null) {
			out.print("Please login first");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {

			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String country = request.getParameter("country");

			User user = new User(id, name, email, country);
			userDao.updateUser(user);
			response.sendRedirect("list");
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("userName") == null) {
			out.print("Please login first");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {

			int id = Integer.parseInt(request.getParameter("id"));
			userDao.deleteUser(id);
			response.sendRedirect("list");
		}
	}
}
