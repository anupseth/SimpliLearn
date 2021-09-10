package net.javaguides.usermanagement.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.internal.build.AllowSysOut;

import net.javaguides.usermanagement.dao.ClassesDao;
import net.javaguides.usermanagement.dao.StudentDao;
import net.javaguides.usermanagement.dao.SubjectsDao;
import net.javaguides.usermanagement.dao.TeacherDao;
import net.javaguides.usermanagement.dao.UserDao;
import net.javaguides.usermanagement.model.Classes;
import net.javaguides.usermanagement.model.Student;
import net.javaguides.usermanagement.model.Subject;
import net.javaguides.usermanagement.model.Teacher;
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

		StudentDao studDao = new StudentDao();
		ClassesDao classedDao = new ClassesDao();
		SubjectsDao subdao = new SubjectsDao();
		TeacherDao teachDao = new TeacherDao();

		// Create Entities

		List<Student> studList = Arrays.asList(new Student("Anil"), new Student("Sunil"), new Student("Mathew"),
				new Student("Sunita"), new Student("Anya"), new Student("Shilpa"), new Student("Naresh"),
				new Student("Aachal"), new Student("Manish"), new Student("Swati"));

		List<Classes> classesList = Arrays.asList(new Classes("Class 5"), new Classes("Class 6"),
				new Classes("Class 7"));


		List<Subject> subjectList = Arrays.asList(new Subject("English"), new Subject("Hindi"), new Subject("Maths"),
				new Subject("Science"), new Subject("Geography"), new Subject("History"), new Subject("Computer"),
				new Subject("Social Science"));


		List<Teacher> teacherList = Arrays.asList(new Teacher("Ms Renuka"),new Teacher("Mr Vinod"),new Teacher("Ms Sarika"),
				new Teacher("Mr Anand"), new Teacher("Mr Jai"), new Teacher("Ms Aishwarya"));

		// Saving entities
		studList.forEach((stud) -> studDao.saveStudent(stud));
		classesList.forEach((classes) -> classedDao.saveClasses(classes));
		subjectList.forEach((subject) -> subdao.saveSubject(subject));
		teacherList.forEach((teacher) -> teachDao.saveTeacher(teacher));

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
