package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.Section;
import com.mas.e_commerce_back.inputs.CategoryDetailsInput;
import com.mas.e_commerce_back.inputs.CategoryPositionInput;
import com.mas.e_commerce_back.inputs.SectionPositionInput;

import java.util.List;


public interface CategoryService {

    // get all categories by section id
     List<Category> getCategoriesBySectionId(Integer sectionId);

    // get category by id
     Category getCategoryById(Integer id);

    // create category
     Category createCategory(CategoryDetailsInput categoryDetailsInput);

    // update sections position
    List<Category> swapCategoriesPositions(List<CategoryPositionInput> categoryPositionInput);

    // set the default positions of a sections list by their alphabetical order
    List<Category> sortCategoriesByAlphabeticalOrder(Integer SectionId);

    // update category
     Category updateCategoryDetails(CategoryDetailsInput categoryDetailsInput);

    // delete category
     Boolean deleteCategory(Integer id);

    // get category by name
     Category getCategoryByName(String name);

     boolean existsById(Integer id);
}
