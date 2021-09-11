package com.simplilearn.phase2.web;

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
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.build.AllowSysOut;

import com.simplilearn.phase2.dao.AssociationDao;
import com.simplilearn.phase2.dao.ClassesDao;
import com.simplilearn.phase2.dao.StudentDao;
import com.simplilearn.phase2.dao.SubjectsDao;
import com.simplilearn.phase2.dao.TeacherDao;
import com.simplilearn.phase2.dao.UserDao;
import com.simplilearn.phase2.model.Classes;
import com.simplilearn.phase2.model.Student;
import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.model.Teacher;
import com.simplilearn.phase2.model.User;
import com.simplilearn.phase2.util.HibernateUtil;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the user.
 * 
 * @email Anup Seth
 */

@WebServlet("/")
public class AcademyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	private ClassesDao classedDao;
	private StudentDao studDao;
	private SubjectsDao subdao;
	private TeacherDao teachDao;

	public void init() {
		userDao = new UserDao();
		studDao = new StudentDao();
		classedDao = new ClassesDao();
		subdao = new SubjectsDao();
		teachDao = new TeacherDao();

		// Create Entities

		List<Student> studList = Arrays.asList(new Student("Anil"), new Student("Sunil"), new Student("Mathew"),
				new Student("Sunita"), new Student("Anya"), new Student("Shilpa"), new Student("Naresh"),
				new Student("Aachal"), new Student("Manish"), new Student("Swati"));

		List<Classes> classesList = Arrays.asList(new Classes("Class A"), new Classes("Class B"),
				new Classes("Class C"));

		List<Subject> subjectList = Arrays.asList(new Subject("English"), new Subject("Hindi"), new Subject("Maths"),
				new Subject("Science"), new Subject("Geography"), new Subject("History"), new Subject("Computer"),
				new Subject("Social Science"));

		List<Teacher> teacherList = Arrays.asList(new Teacher("Ms Renuka"), new Teacher("Mr Vinod"),
				new Teacher("Ms Sarika"), new Teacher("Mr Anand"), new Teacher("Mr Jai"), new Teacher("Ms Aishwarya"));

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
			case "/addStudent":
				showAddStudentForm(request, response);
				break;
			case "/insertStudent":
				insertStudent(request, response);
				break;
			case "/addSubject":
				showAddSubjectsForm(request, response);
				break;
			case "/insertSubject":
				insertSubject(request, response);
				break;
			case "/addTeacher":
				showAddTeachersForm(request, response);
				break;
			case "/insertTeacher":
				insertTeacher(request, response);
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
	
	private void insertTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String teacherId = request.getParameter("teachersList");
		String subjectId = request.getParameter("subjectsList");
		AssociationDao.saveSubjectTeachers(teacherId, subjectId);
		response.sendRedirect("addTeacher");
	}

	private void showAddTeachersForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("subjects", subdao.getAllSubject());
		request.setAttribute("teachers", teachDao.getAllTeacher());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("add-teacher-form.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private void insertSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String classId = request.getParameter("classesList");
		String subjectId = request.getParameter("subjectsList");
		AssociationDao.saveSubjectClasses(classId, subjectId);
		response.sendRedirect("addSubject");
	}

	private void showAddSubjectsForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("classes", classedDao.getAllClasses());
		request.setAttribute("subjects", subdao.getAllSubject());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("add-subject-form.jsp");
		requestDispatcher.forward(request, response);

	}

	private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String classId = request.getParameter("classesList");
		String studentId = request.getParameter("studentsList");
		AssociationDao.saveStudClasses(classId, studentId);
		response.sendRedirect("addStudent");
	}

	private void showAddStudentForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("classes", classedDao.getAllClasses());
		request.setAttribute("students", studDao.getAllStudent());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("add-student-form.jsp");
		requestDispatcher.forward(request, response);

	}

	private void loginAdmin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();

		String n = request.getParameter("userName");
		String p = request.getParameter("userPass");

		if (p.equals("servlet")) {
			RequestDispatcher rd = request.getRequestDispatcher("addStudent");
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