package net.javaguides.usermanagement.dao;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.Transaction;

import net.javaguides.usermanagement.model.Teacher;
import net.javaguides.usermanagement.util.HibernateUtil;

/**
 * CRUD database operations
 * 
 * @author Anup Seth
 *
 */
public class TeacherDao {

	/**
	 * Save Teacher
	 * 
	 * @param teacher
	 */
	public void saveTeacher(Teacher teacher) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(teacher);
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
	 * Update Teacher
	 * 
	 * @param teacher
	 */
	public void updateTeacher(Teacher teacher) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(teacher);
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
	 * Delete Teacher
	 * 
	 * @param id
	 */
	public void deleteTeacher(int id) {

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// Delete a teacher object
			Teacher teacher = session.get(Teacher.class, id);
			if (teacher != null) {
				session.delete(teacher);
				System.out.println("teacher is deleted");
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
	 * Get Teacher By ID
	 * 
	 * @param id
	 * @return
	 */
	public Teacher getTeacher(int id) {

		Transaction transaction = null;
		Teacher teacher = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an teacher object
			teacher = session.get(Teacher.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return teacher;
	}

	/**
	 * Get all Teachers
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Teacher> getAllTeacher() {

		Transaction transaction = null;
		List<Teacher> listOfTeacher = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an teacher object

			listOfTeacher = session.createQuery("from Teacher").getResultList();

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfTeacher;
	}
}