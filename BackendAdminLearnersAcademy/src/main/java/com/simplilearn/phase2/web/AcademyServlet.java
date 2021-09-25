package com.simplilearn.phase2.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplilearn.phase2.dao.AssociationDao;
import com.simplilearn.phase2.dao.ClassesDao;
import com.simplilearn.phase2.dao.StudentDao;
import com.simplilearn.phase2.dao.SubjectsDao;
import com.simplilearn.phase2.dao.TeacherDao;
import com.simplilearn.phase2.model.Classes;
import com.simplilearn.phase2.model.Student;
import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.model.Teacher;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the user.
 * 
 * @email Anup Seth
 */

@WebServlet("/")
public class AcademyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassesDao classedDao;
	private StudentDao studDao;
	private SubjectsDao subdao;
	private TeacherDao teachDao;

	public void init() {
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
		switch (action) {
		case "/showReportForm":
			showReportForm(request, response);
			break;
		case "/showReport":
			showReport(request, response);
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
		case "/login":
			loginAdmin(request, response);
			break;
		default:
			System.out.println(" Inside default case .......");
			showLoginPage(request, response);
			break;
		}
	}

	private void showReport(HttpServletRequest request, HttpServletResponse response) {

		try {

			PrintWriter out = response.getWriter();
			boolean loggedin = checkIfUserLoggedIn(request);

			if (!loggedin) {

				out.print("Please login First!");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.include(request, response);

			} else {

				String classId = request.getParameter("classesList");

				Classes class1 = AssociationDao.getClassReport(classId);
//		
//		System.out.println("Students for class");
//		class1.getStudents().forEach(System.out::println);
//		
//		System.out.println("Subjects for class and their tacher");
//		class1.getSubjects().forEach((sub) -> {
//			System.out.println("Subject  = "+sub.getName() + "    Teacher ="+ sub.getTeacher().getName());
//		});

				request.setAttribute("students", class1.getStudents());
				request.setAttribute("subjects", class1.getSubjects());
				request.setAttribute("selClass", class1);
				request.setAttribute("classes", classedDao.getAllClasses());
				request.setAttribute("showReport", true);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("show-report-form.jsp");
				requestDispatcher.forward(request, response);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void showReportForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean loggedin = checkIfUserLoggedIn(request);

		if (!loggedin) {

			out.print("Please login First!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.include(request, response);

		} else {
			request.setAttribute("classes", classedDao.getAllClasses());
			request.setAttribute("showReport", false);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("show-report-form.jsp");
			requestDispatcher.forward(request, response);
		}
	}

	private void insertSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		boolean loggedin = checkIfUserLoggedIn(request);

		if (!loggedin) {

			out.print("Please login First!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			try {
				rd.include(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			String classId = request.getParameter("classesList");
			String subjectId = request.getParameter("subjectsList");
			String teacherId = request.getParameter("teachersList");
			AssociationDao.saveSubjectClasses(classId, subjectId, teacherId);
			response.sendRedirect("addSubject");
		}
	}

	private void showAddSubjectsForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean loggedin = checkIfUserLoggedIn(request);

		if (!loggedin) {

			out.print("Please login First!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.include(request, response);

		} else {

			request.setAttribute("classes", classedDao.getAllClasses());
			request.setAttribute("subjects", subdao.getAllSubject());
			request.setAttribute("teachers", teachDao.getAllTeacher());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("add-subject-form.jsp");
			requestDispatcher.forward(request, response);
		}

	}

	private void insertStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		boolean loggedin = checkIfUserLoggedIn(request);

		if (!loggedin) {

			out.print("Please login First!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			try {
				rd.include(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			String classId = request.getParameter("classesList");
			String studentId = request.getParameter("studentsList");
			AssociationDao.saveStudClasses(classId, studentId);
			response.sendRedirect("addStudent");
		}
	}

	private void showAddStudentForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		boolean loggedin = checkIfUserLoggedIn(request);

		if (!loggedin) {

			out.print("Please login First!");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.include(request, response);

		} else {
			request.setAttribute("classes", classedDao.getAllClasses());
			request.setAttribute("students", studDao.getAllStudent());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("add-student-form.jsp");
			requestDispatcher.forward(request, response);
		}

	}

	private boolean checkIfUserLoggedIn(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute("userName") != null)
			return true;

		return false;
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
}
