package net.javaguides.usermanagement.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Classes.java This is a model class represents a Class entity
 * 
 * @author Anup Seth
 *
 */

@Entity
@Table(name = "class")
@NamedQueries({
	@NamedQuery(name="GetAllSubjectsForAStudent", 
			query = "Select sb from Classes c INNER JOIN c.students s INNER JOIN c.subjects sb where s.id = :id"
			),
	@NamedQuery(name="GetAllSubjectsAndClassForAStudent", 
	query = "Select sb.name, c.name from Classes c INNER JOIN c.students s INNER JOIN c.subjects sb where s.id = :id"
	)
	
})
public class Classes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;

	@Column(name = "name")
	protected String name;

	@OneToMany(mappedBy = "classes")
	private List<Student> students = new ArrayList<Student>();

	@OneToMany(mappedBy = "classes")
	private List<Subject> subjects = new ArrayList<Subject>();

	public Classes() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Classes(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + "]";
	}

	public Classes(String name) {
		super();
		this.name = name;
	}

	

	
}
