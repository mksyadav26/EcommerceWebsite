package com.manoj.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.manoj.major.model.Product;
import com.manoj.major.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	ProductRepository  productRepository;
	
	public List<Product> getAllProduct() 
	{
	return	productRepository.findAll();
	}
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}

	public Optional<Product> getProductbyId(long id){
		return productRepository.findById(id); 
	}
	
	public List<Product> getAllProductsByCategoryId(int id){
		return productRepository.findAllByCategoryId(id);
	}
	
}
