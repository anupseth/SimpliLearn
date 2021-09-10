package net.javaguides.usermanagement.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import net.javaguides.usermanagement.model.Student;
import net.javaguides.usermanagement.util.HibernateUtil;

/**
 * CRUD database operations
 * 
 * @author Anup Seth
 *
 */
public class StudentDao {

	/**
	 * Save Student
	 * 
	 * @param Student
	 */
	public void saveStudent(Student student) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
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

	/**
	 * Update Student
	 * 
	 * @param student
	 */
	public void updateStudent(Student student) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(student);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	/**
	 * Delete Student
	 * 
	 * @param id
	 */
	public void deleteStudent(int id) {

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// Delete a Student object
			Student student = session.get(Student.class, id);
			if (student != null) {
				session.delete(student);
				System.out.println("Student is deleted");
			}

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	/**
	 * Get Student By ID
	 * 
	 * @param id
	 * @return
	 */
	public Student getStudent(int id) {

		Transaction transaction = null;
		Student student = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an Student object
			student = session.get(Student.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return student;
	}

	/**
	 * Get all Students
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Student> getAllStudent() {

		Transaction transaction = null;
		List<Student> listOfStudent = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an Student object

			 Query<Student> createQuery = session.createQuery("FROM Student",Student.class);
			 listOfStudent = createQuery.getResultList();
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfStudent;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getAllStudentsForParticularClass(){
		
		Transaction transaction = null;
		List<Object[]> listOfStudent = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an Student object

			
			 @SuppressWarnings("rawtypes")
			 Query namedQuery = session.createNamedQuery("findAllStudentInClass");
			 namedQuery.setParameter("id", 1);
			 listOfStudent = namedQuery.getResultList();
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfStudent;
		
	}
}