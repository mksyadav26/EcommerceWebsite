package com.manoj.major.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.major.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
