package com.simplilearn.phase2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.simplilearn.phase2.model.Classes;
import com.simplilearn.phase2.model.Student;
import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.model.Teacher;
import com.simplilearn.phase2.util.HibernateUtil;

/**
 * CRUD database operations
 * 
 * @author Anup Seth
 *
 */
public class AssociationDao {

	/**
	 * 
	 * @param classId
	 * @param studentId
	 */
	public static void saveStudClasses(String classId, String studentId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			Classes classes = session.get(Classes.class, Integer.parseInt(classId));
			Student student = session.get(Student.class, Integer.parseInt(studentId));

			student.setClasses(classes);
			classes.getStudents().add(student);

			session.save(classes);
			session.save(student);

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

//		Student student = studDao.getStudent(Integer.parseInt(studentId));
//		Classes classes = classedDao.getClass(Integer.parseInt(classId));
//		
//		student.setClasses(classes);
//		classes.getStudents().add(student);
//		classedDao.saveClasses(classes);
//		studDao.saveStudent(student);
	}

	public static void saveSubjectClasses(String classId, String subjectId) {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			Classes classes = session.get(Classes.class, Integer.parseInt(classId));
			Subject subject = session.get(Subject.class, Integer.parseInt(subjectId));

			subject.setClasses(classes);
			classes.getSubjects().add(subject);

			session.save(classes);
			session.save(subject);

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
	}

	public static void saveSubjectTeachers(String teacherId, String subjectId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			Teacher teacher = session.get(Teacher.class, Integer.parseInt(teacherId));
			Subject subject = session.get(Subject.class, Integer.parseInt(subjectId));

			subject.setTeacher(teacher);;
			teacher.getSubjectList().add(subject);

			session.save(teacher);
			session.save(subject);

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
	}

	
	
}