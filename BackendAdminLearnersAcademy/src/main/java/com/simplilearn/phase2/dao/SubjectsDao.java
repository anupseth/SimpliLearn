package com.simplilearn.phase2.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.util.HibernateUtil;

/**
 * CRUD database operations Subjects
 * 
 * @author Anup Seth
 *
 */
public class SubjectsDao {

	/**
	 * Save Subject
	 * 
	 * @param subject
	 */
	public void saveSubject(Subject subject) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
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

	/**
	 * Update Subject
	 * 
	 * @param subject
	 */
	public void updateSubject(Subject subject) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(subject);
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
	 * Delete Subject
	 * 
	 * @param id
	 */
	public void deleteSubject(int id) {

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();

			// Delete a subject object
			Subject subject = session.get(Subject.class, id);
			if (subject != null) {
				session.delete(subject);
				//System.out.println("subject is deleted");
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
	 * Get Subject By ID
	 * 
	 * @param id
	 * @return
	 */
	public Subject getSubject(int id) {

		Transaction transaction = null;
		Subject subject = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an subject object
			subject = session.get(Subject.class, id);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return subject;
	}

	/**
	 * Get all Subjects
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Subject> getAllSubject() {

		Transaction transaction = null;
		List<Subject> listOfSubjects = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// get an subject object

			 Query<Subject> createQuery = session.createQuery("FROM Subject",Subject.class);
			 listOfSubjects = createQuery.getResultList();
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return listOfSubjects;
	}
}