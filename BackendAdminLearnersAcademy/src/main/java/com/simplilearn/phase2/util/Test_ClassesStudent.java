package com.simplilearn.phase2.util;

import java.util.List;


import com.simplilearn.phase2.dao.ClassesDao;
import com.simplilearn.phase2.dao.StudentDao;
import com.simplilearn.phase2.dao.SubjectsDao;
import com.simplilearn.phase2.dao.TeacherDao;
import com.simplilearn.phase2.model.Classes;
import com.simplilearn.phase2.model.Student;
import com.simplilearn.phase2.model.Subject;
import com.simplilearn.phase2.model.Teacher;

public class Test_ClassesStudent {

	public static void main(String[] args) {
		
		StudentDao studDao = new StudentDao();
		ClassesDao classedDao = new ClassesDao();
		SubjectsDao subdao = new SubjectsDao();
		TeacherDao teachDao = new TeacherDao();
		
		//Create objects
		Student stud1 = new Student("Stud 1");
		Student stud2 = new Student("Stud 2");
		Student stud3 = new Student("Stud 3");

		Classes c1 = new Classes("C1");
		Classes c2 = new Classes("C2");
		Classes c3 = new Classes("C3");
		
		Subject sub1 = new Subject("Sub1_class1");
		Subject sub2 = new Subject("Sub2_class1");
		Subject sub3 = new Subject("Sub3_class2");
		
		
		Teacher teah1 = new Teacher("Teach1");
		Teacher teah2 = new Teacher("Teach2");
		Teacher teah3 = new Teacher("Teach3");
		
		
		//Do associations
		stud1.setClasses(c1);
		stud2.setClasses(c1);
		stud3.setClasses(c2);
		
		sub1.setClasses(c1);
		sub2.setClasses(c1);
		sub3.setClasses(c2);
		
		sub3.setTeacher(teah3);
		sub2.setTeacher(teah2);
		sub1.setTeacher(teah3);
		
		
		teah2.getSubjectList().add(sub2);
		teah3.getSubjectList().add(sub3);
		teah3.getSubjectList().add(sub1);
		
		
		
//		c1.getStudents().add(stud1);
//		c1.getStudents().add(stud2);
//		c2.getStudents().add(stud3);
		
		c1.getSubjects().add(sub1);
		c1.getSubjects().add(sub2);
		c2.getSubjects().add(sub3);
		
		
		//save entities
		classedDao.saveClasses(c1);
		classedDao.saveClasses(c2);
		classedDao.saveClasses(c3);
		
		studDao.saveStudent(stud1);
		studDao.saveStudent(stud2);
		studDao.saveStudent(stud3);
		
		teachDao.saveTeacher(teah3);
		teachDao.saveTeacher(teah2);
		teachDao.saveTeacher(teah1);
		
		subdao.saveSubject(sub1);
		subdao.saveSubject(sub2);
		subdao.saveSubject(sub3);
		
		
		
		
		// Get entities
		List<Student> allUser = studDao.getAllStudent();
		List<Object[]> allStudentsForParticularClass = classedDao.executeNamedQueriesMultipleTableAsResult();
		
		
		allStudentsForParticularClass.forEach((obj) -> System.out.println(obj[0] + "   " + obj[1]));
		
		System.out.println("All Teachers");
		List<Teacher> allTeacher = teachDao.getAllTeacher();
		
		allTeacher.forEach(System.out::println);

	}

}
