package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.ProductType;
import com.mas.e_commerce_back.exceptions.BadRequestException;
import com.mas.e_commerce_back.exceptions.NotFoundException;
import com.mas.e_commerce_back.inputs.ProductTypeInput;
import com.mas.e_commerce_back.inputs.ProductTypePositionInput;
import com.mas.e_commerce_back.repositories.ProductTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private final CategoryService categoryService;
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeServiceImpl(CategoryService categoryService, ProductTypeRepository productTypeRepository) {
        this.categoryService = categoryService;
        this.productTypeRepository = productTypeRepository;
    }


    @Override
    public List<ProductType> getAllProductTypesByCategoryId(Integer categoryId) {
        return productTypeRepository.findAllByCategoryIdOrderByPosition(categoryId);
    }

    @Override
    public ProductType getProductTypeById(Integer id) {
        return productTypeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Section with id" + id + " not found")
        );

    }

    @Override
    public ProductType getProductTypeByName(String name) {
        return productTypeRepository.findByName(name).orElseThrow(
                () -> new NotFoundException("product type with name" + name + " not found")
        );
    }

    @Override
    public ProductType createProductType(ProductTypeInput productTypeInput) {
        Category category = categoryService.getCategoryById(productTypeInput.getCategoryId());
        // check if the category exists
        if(category == null){
            throw new BadRequestException("Category with id: " + productTypeInput.getCategoryId()+" does not exist");
        }
        // check for the name
        productTypeRepository.findByName(productTypeInput.getName()).ifPresent(
                productType -> {
                    throw new DataIntegrityViolationException("Section with name " + productTypeInput.getName() + " already exists");
                }
        );
        AtomicReference<Integer> position = new AtomicReference<>(0);
        productTypeRepository.findProductTypeByLastPositionAndCategoryId(productTypeInput.getCategoryId()).ifPresent(
                section -> position.set(section.getPosition() + 1)
        );
        ProductType productType = ProductType.builder()
                .name(productTypeInput.getName())
                .category(category)
                .position(position.get())
                .build();
        return productTypeRepository.save(productType);
    }

    @Override
    public ProductType updateProductTypeDetails(ProductTypeInput productTypeInput) {
        // find by id

        ProductType productType = productTypeRepository.findById(productTypeInput.getProductTypeId())
                .orElseThrow(() -> new NotFoundException("Product type with id " + productTypeInput.getProductTypeId() + " not found"));

        // check for the name
        if (productTypeInput.getName() != null) {
            if (productTypeInput.getName().strip().length() == 0) {
                throw new BadRequestException("Product type name cannot be empty");
            }
            if (productTypeRepository.existsByName(productTypeInput.getName())) {
                throw new DataIntegrityViolationException("Product type with name " + productTypeInput.getName() + " already exists");
            }
            productType.setName(productTypeInput.getName());
            return productTypeRepository.save(productType);
        }

        return productType;
    }

    @Override
    public List<ProductType> swapProductTypesPositions(List<ProductTypePositionInput> productTypePositionInputs) {

        Set<Integer> positions = productTypePositionInputs.stream().map(input -> input.getNewPosition()).collect(Collectors.toSet());
        Set<Integer> ids = productTypePositionInputs.stream().map(input -> input.getCategoryId()).collect(Collectors.toSet());

        if (ids.size() != productTypePositionInputs.size()){
            throw new BadRequestException("Cannot use the same id twice within the swapping list");
        }
        if (positions.size() != productTypePositionInputs.size()){
            throw new BadRequestException("Cannot use the same position twice within the swapping list");
        }
        List<ProductType> productTypeList = productTypeRepository.findByIdsOrderByPosition(ids);
        Integer categoryId = productTypeList.get(0).getCategory().getCategoryId();
        if (categoryId == null){
            throw new NotFoundException("The category with id " + categoryId + " does not exist in the database");
        }
        // check if the category list is within the same section id
        for (int i = 0; i < productTypeList.size(); i++) {
            if (productTypeList.get(i).getCategory().getCategoryId() != categoryId){
                throw new BadRequestException("The category with id " + productTypeList.get(i).getCategory().getCategoryId() + " is not in the same section as the other categories");
            }
        }

        Set<Integer> categoriesPositions = productTypeList.stream().map(productType -> productType.getCategory().getCategoryId()).collect(Collectors.toSet());
        Set<Integer> categoryIds = productTypeList.stream().map(productType -> productType.getCategory().getCategoryId()).collect(Collectors.toSet());

        categoriesPositions.forEach(
                position -> {
                    if (!positions.contains(position)){
                        throw new BadRequestException("The original position " + position + " of the product type "
                                + productTypeList.stream().filter( section -> section.getPosition() == position).findFirst().get().getName()
                                + " is not in the swapping list");

                    }
                }
        );

        // swap the positions
        for (ProductTypePositionInput productTypePositionInput : productTypePositionInputs) {
            if (!categoryIds.contains(productTypePositionInput.getCategoryId())){
                throw new NotFoundException("The productType with id " + productTypePositionInput.getCategoryId() + " does not exist in the database");
            }
            ProductType productType = productTypeList.stream().filter( productType1 -> productType1.getProductTypeId() == productTypePositionInput.getProductTypeId()).findFirst().orElseThrow(
                    () -> new NotFoundException("Product type with id " + productTypePositionInput.getProductTypeId() + " not found")
            );
            productType.setPosition(productTypePositionInput.getNewPosition());
        }

        productTypeRepository.saveAll(productTypeList);


        return productTypeRepository.findAllByCategoryIdOrderByPosition(categoryId);
    }

    @Override
    public List<ProductType> sortProductTypesByAlphabeticalOrder(Integer categoryId) {
        return null;
    }


    // we can implement and add a wild card so if it is true then the user agrees to brute force the deletion
    @Override
    public Boolean deleteProductType(Integer id) {
        // get the product by id
        ProductType productType = productTypeRepository.findById(id).orElseThrow (
                () -> new NotFoundException("product type with id " + id + "not found")
        );
        // check if it is empty of all the products XD
        if (productTypeRepository.productListExists(id)) {
            throw new BadRequestException("Section with id " + id + " has categories, you have to delete them first");
        }
        productTypeRepository.delete(productType);

        return true;
    }
}
