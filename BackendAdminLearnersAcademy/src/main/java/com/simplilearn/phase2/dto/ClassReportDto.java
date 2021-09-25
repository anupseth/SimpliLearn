package com.simplilearn.phase2.dto;

import java.util.ArrayList;
import java.util.List;

import com.simplilearn.phase2.model.Student;
import com.simplilearn.phase2.model.Subject;

public class ClassReportDto {
	


	private List<Student> students = new ArrayList<Student>();

	private List<Subject> subjects = new ArrayList<Subject>();

	public ClassReportDto() {
	}


	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
