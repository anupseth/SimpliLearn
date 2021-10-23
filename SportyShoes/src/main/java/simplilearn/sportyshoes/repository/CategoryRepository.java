package simplilearn.sportyshoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import simplilearn.sportyshoes.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	@Query("Select c.name from Category c")
	public List<String> getCatNames();
	
	
	public Category findByName(String name);
}
