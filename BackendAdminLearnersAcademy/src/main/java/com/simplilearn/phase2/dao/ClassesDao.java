package com.simplilearn.phase2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.simplilearn.phase2.model.Classes;
import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.util.HibernateUtil;

/**
 * CRUD database operations
 * 
 * @author Anup Seth
 *
 */
public class ClassesDao {

	/**
	 * Save Classes
	 * 
	 * @param classes
	 */
	public void saveClasses(Classes classes) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(classes);
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
	 * Update Classes
	 * 
	 * @param classes
	 */
	public void updateClasses(Classes classes) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(classes);
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
	 * Delete Classes
	 * 
	 * @param id
	 */
	public void deleteClasses(int id) {

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// Delete a classes object
			Classes classes = session.get(Classes.class, id);
			if (classes != null) {
				session.delete(classes);
				System.out.println("classes is deleted");
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
	 * Get Classes By ID
	 * 
	 * @param id
	 * @return
	 */
	public Classes getClass(int id) {

		Transaction transaction = null;
		Classes classes = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an classes object
			classes = session.get(Classes.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return classes;
	}

	/**
	 * Get all Classes
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Classes> getAllClasses() {

		Transaction transaction = null;
		List<Classes> listOfClasses = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an classes object

			listOfClasses = session.createQuery("from Classes").getResultList();

			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfClasses;
	}

	public List<Subject> executeNamedQueries() {

		Transaction transaction = null;
		List<Subject> listOfStudent = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an classes object
			Query<Subject> namedQuery = session.createNamedQuery("GetAllSubjectsForAStudent", Subject.class);
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

	@SuppressWarnings("unchecked")
	public List<Object[]> executeNamedQueriesMultipleTableAsResult() {

		Transaction transaction = null;
		List<Object[]> listOfStudent = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an classes object
			Query namedQuery = session.createNamedQuery("GetAllSubjectsAndClassForAStudent");
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