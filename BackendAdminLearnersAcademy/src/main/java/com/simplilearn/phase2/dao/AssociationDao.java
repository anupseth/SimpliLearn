package com.simplilearn.phase2.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

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
	}

	public static void saveSubjectClasses(String classId, String subjectId, String teacherId) {
		
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			Classes classes = session.get(Classes.class, Integer.parseInt(classId));
			Subject subject = session.get(Subject.class, Integer.parseInt(subjectId));
			Teacher teacher = session.get(Teacher.class, Integer.parseInt(teacherId));

			
			
			subject.setClasses(classes);
			subject.setTeacher(teacher);
			
			teacher.getSubjectList().add(subject);
			classes.getSubjects().add(subject);

			session.save(teacher);
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

	public static Classes getClassReport(String classId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
		//	transaction = session.beginTransaction();
			// save the student object
			Classes class1 = session.get(Classes.class, Integer.parseInt(classId));
			
			
			class1.getStudents().forEach((stud)->{
				stud.getName();
			});
			
			class1.getSubjects().forEach((sub) -> {
				sub.getTeacher();
			});
			// commit transaction
		//	transaction.commit();
			return class1;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return null;
		
	}
	
}