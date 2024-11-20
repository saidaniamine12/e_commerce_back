package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Section;
import com.mas.e_commerce_back.inputs.SectionDetailsInput;
import com.mas.e_commerce_back.inputs.SectionPositionInput;

import java.util.List;

public interface SectionService {

    // create section
    Section createSection(SectionDetailsInput sectionDetailsInput);

    // get all sections
     List<Section> getAllSections();

     // get section by id
     Section getSectionById(Integer id);

     // update section
     Section updateSectionDetails(SectionDetailsInput sectionDetailsInput);

     // update sections position
     List<Section> swapSectionsPositions(List<SectionPositionInput> sectionPositionInputList);

     // set the default positions of a sections list by their alphabetical order
     List<Section> sortSectionsByAlphabeticalOrder();

    // delete section
     Boolean deleteSection(Integer id);

    // get section by name
     Section getSectionByName(String name);

     boolean existsByName(String name);

     boolean existsById(Integer id);




}
