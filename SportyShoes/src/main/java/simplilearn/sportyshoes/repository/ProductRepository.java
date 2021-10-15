package simplilearn.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import simplilearn.sportyshoes.entities.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	
}
