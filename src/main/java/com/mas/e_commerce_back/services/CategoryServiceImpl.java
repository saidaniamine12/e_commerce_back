package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.Section;
import com.mas.e_commerce_back.exceptions.BadRequestException;
import com.mas.e_commerce_back.exceptions.NotFoundException;
import com.mas.e_commerce_back.inputs.CategoryDetailsInput;
import com.mas.e_commerce_back.inputs.CategoryPositionInput;
import com.mas.e_commerce_back.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final SectionService sectionService;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, SectionService sectionService) {
        this.categoryRepository = categoryRepository;
        this.sectionService = sectionService;
    }


    @Override
    public List<Category> getCategoriesBySectionId(Integer sectionId) {
        Section section = sectionService.getSectionById(sectionId);
        return categoryRepository.findAllBySectionIdOrderByPosition(sectionId);

    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("no category found with id:" + id)
        );
    }

    @Override
    public Category createCategory(CategoryDetailsInput categoryDetailsInput) {
        // check if the section exists
        Section section = sectionService.getSectionById(categoryDetailsInput.getSectionId());
        // check if another category by the same name exists
        categoryRepository.findByName(categoryDetailsInput.getName()).ifPresent(
                category -> {
                    throw new BadRequestException("Section with name " + category.getName() + " already exists");
                }
        );
        // create the new category
        AtomicReference<Integer> position = new AtomicReference<>(0);
        categoryRepository.findCategoryByLastPositionAndCategoryId(categoryDetailsInput.getSectionId()).ifPresent(
                category -> position.set(category.getPosition() + 1)
        );
        Category category = Category.builder()
                .name(categoryDetailsInput.getName())
                .description(categoryDetailsInput.getDescription())
                .position(position.get())
                .section(section)
                .build();

        return categoryRepository.save(category);

    }

    @Override
    public List<Category> swapCategoriesPositions(List<CategoryPositionInput> categoryPositionInputList) {
        Set<Integer> positions = categoryPositionInputList.stream().map(input -> input.getNewPosition()).collect(Collectors.toSet());
        Set<Integer> ids = categoryPositionInputList.stream().map(input -> input.getCategoryId()).collect(Collectors.toSet());

        if (ids.size() != categoryPositionInputList.size()){
            throw new BadRequestException("Cannot use the same id twice within the swapping list");
        }
        if (positions.size() != categoryPositionInputList.size()){
            throw new BadRequestException("Cannot use the same position twice within the swapping list");
        }
        List<Category> categoryList = categoryRepository.findByIdsOrderByPosition(ids);
        Integer sectionId = categoryList.get(0).getSection().getSectionId();
        if (sectionId == null){
            throw new NotFoundException("The section with id " + sectionId + " does not exist in the database");
        }
        // check if the category list is within the same section id
        categoryList.forEach(
                category -> {
                    if (category.getSection().getSectionId() != sectionId){
                        throw new BadRequestException("The category with id " + category.getCategoryId()
                                + " is not in the same section as the other categories,"
                                + " all categories must be in the same section");
                    }
                }
        );
        Set<Integer> sectionsPositions = categoryList.stream().map(category -> category.getSection().getSectionId()).collect(Collectors.toSet());
        Set<Integer> sectionsIds = categoryList.stream().map(category -> category.getSection().getSectionId()).collect(Collectors.toSet());

        sectionsPositions.forEach(
                position -> {
                    if (!positions.contains(position)){
                        throw new BadRequestException("The original position " + position + " of the category "
                                + categoryList.stream().filter( section -> section.getPosition() == position).findFirst().get().getName()
                                + " is not in the swapping list");

                    }
                }
        );

        // swap the positions
        for (CategoryPositionInput categoryPositionInput : categoryPositionInputList) {
            if (!sectionsIds.contains(categoryPositionInput.getSectionId())){
                throw new NotFoundException("The category with id " + categoryPositionInput.getCategoryId() + " does not exist in the database");
            }
            Category category = categoryList.stream().filter( category1 -> category1.getCategoryId() == categoryPositionInput.getCategoryId()).findFirst().get();
            category.setPosition(categoryPositionInput.getNewPosition());
        }

        categoryRepository.saveAll(categoryList);


        return categoryRepository.findAllBySectionIdOrderByPosition(sectionId);
    }

    @Override
    public List<Category> sortCategoriesByAlphabeticalOrder(Integer sectionId) {
        // check if the section exists
        if (!sectionService.existsById(sectionId)) {
            throw new NotFoundException("no section found with id:" + sectionId);
        }
        List<Category> category = categoryRepository.findAllBySectionIdOrderByName(sectionId);

        for (int i = 0; i < category.size(); i++) {
            if (category.get(i).getPosition() != i) {
                category.get(i).setPosition(i);
            }

        }
        categoryRepository.saveAll(category);
        return categoryRepository.findAllBySectionIdOrderByPosition(sectionId);
    }


    @Override
    public Category updateCategoryDetails(CategoryDetailsInput categoryDetailsInput) {

        boolean performUpdate = false;
        Category category = categoryRepository.findById(categoryDetailsInput.getCategoryId()).orElseThrow(
                () -> new NotFoundException("no category found with id:" + categoryDetailsInput.getCategoryId())
        );
        if (categoryDetailsInput.getName() != null) {
            if (categoryDetailsInput.getName().strip().length() == 0) {
                throw new BadRequestException("category name cannot be empty");
            }
            if (categoryRepository.existsByName(categoryDetailsInput.getName())){
                throw new DataIntegrityViolationException("Category with name" + categoryDetailsInput.getName() + " already exists");
            }
            category.setName(categoryDetailsInput.getName());
            performUpdate = true;
        }
        if (categoryDetailsInput.getDescription() != null) {
            category.setDescription(categoryDetailsInput.getDescription());
            performUpdate = true;
        }
        return performUpdate ? categoryRepository.save(category) : category;
    }

    @Override
    public Boolean deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("no category found with id:" + id)
        );

        // check if the category has product types
        if (categoryRepository.productTypeListExists(id)) {
            throw new BadRequestException("category with id:" + id + " has product types, you have to delete them first");
        }

        categoryRepository.delete(category);
        // get the section of the category

        // update the position of the sections
        List<Category> categoryList = categoryRepository.findAllBySectionIdOrderByPosition(category.getSection().getSectionId());
        for (int i = 0; i < categoryList.size(); i++) {
            categoryList.get(i).setPosition(i);
        }
        categoryRepository.saveAll(categoryList);
        return true;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(
                () -> new NotFoundException("no category found with name:" + name)
        );
    }

    @Override
    public boolean existsById(Integer id) {
        if (id == null) throw new BadRequestException("invalid id provided for category:" + null);
        return categoryRepository.existsById(id);
    }

}
