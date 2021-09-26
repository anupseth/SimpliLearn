package com.simplilearn.phase2.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import com.simplilearn.phase2.model.Classes;
import com.simplilearn.phase2.model.Student;
import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.model.Teacher;

/**
 * Java based configuration
 * @author Anup Seth
 *
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				
				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/demo?useSSL=false&allowPublicKeyRetrieval=true");
				settings.put(Environment.USER, "root");
				settings.put(Environment.PASS, "Temp4now!");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
				
//				settings.put(Environment.HBM2DDL_CREATE_SOURCE, "script");
//				settings.put(Environment.HBM2DDL_LOAD_SCRIPT_SOURCE, "/InitialData.sql");

				settings.put(Environment.SHOW_SQL, "false");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, "create-drop");//create-drop

				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Student.class);
				configuration.addAnnotatedClass(Classes.class);
				configuration.addAnnotatedClass(Teacher.class);
				configuration.addAnnotatedClass(Subject.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
