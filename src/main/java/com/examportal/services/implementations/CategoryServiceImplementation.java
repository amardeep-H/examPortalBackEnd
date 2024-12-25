package com.examportal.services.implementations;

import com.examportal.models.Category;
import com.examportal.repository.CategoryRepository;
import com.examportal.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CategoryServiceImplementation implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }

    @Override
    public Category getCategory(Long cId) {
        return this.categoryRepository.findById(cId).get();
    }

    @Override
    public void deleteCategory(Long cId) {
        //deletes category by id
        this.categoryRepository.deleteById(cId);

//        //deletes category  by name
//        Category category = new Category();
//        category.setCid(categoryId);
//        this.categoryRepository.delete(category);
    }
}
