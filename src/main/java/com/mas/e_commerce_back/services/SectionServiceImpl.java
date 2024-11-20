package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Category;
import com.mas.e_commerce_back.entities.Section;
import com.mas.e_commerce_back.exceptions.BadRequestException;
import com.mas.e_commerce_back.exceptions.NotFoundException;
import com.mas.e_commerce_back.inputs.SectionDetailsInput;
import com.mas.e_commerce_back.inputs.SectionPositionInput;
import com.mas.e_commerce_back.repositories.CategoryRepository;
import com.mas.e_commerce_back.repositories.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;


    @Autowired
    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }


    @Override
    public Section createSection(SectionDetailsInput sectionDetailsInput) {
        // managing the section position but the sections can be modified
        // so the position should be managed by the service
        sectionRepository.findByName(sectionDetailsInput.getName()).ifPresent(
                section -> {
                    throw new DataIntegrityViolationException("Section with name " + sectionDetailsInput.getName() + " already exists");
                }
        );
        AtomicReference<Integer> position = new AtomicReference<>(0);
        sectionRepository.findLastSectionByPosition().ifPresent(
                section -> position.set(section.getPosition() + 1)
        );
        Section section = Section.builder()
                .name(sectionDetailsInput.getName())
                .description(sectionDetailsInput.getDescription())
                .position(position.get())
                        .build();
        System.out.println(" section to string" + section.toString());
        return sectionRepository.save(section);
    }

    @Override
    public List<Section> getAllSections() {
        return sectionRepository.findAllOrderByPosition();
    }

    @Override
    public Section getSectionById(Integer id) {
        return sectionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Section with id " + id + " not found")
        );

    }


    @Override
    public Section updateSectionDetails(SectionDetailsInput sectionDetailsInput) {
        boolean performUpdate = false;
        Section section = sectionRepository.findById(sectionDetailsInput.getSectionId()).orElseThrow(
                () -> new NotFoundException("Section with id " + sectionDetailsInput.getSectionId() + " not found")
        );

        if (sectionDetailsInput.getName() != null) {
            if (sectionDetailsInput.getName().strip().length() == 0) {
                throw new BadRequestException("Section name cannot be empty");
            }
            if (sectionRepository.existsByName(sectionDetailsInput.getName())){
                throw new DataIntegrityViolationException("Section with name " + sectionDetailsInput.getName() + " already exists" );
            }

            section.setName(sectionDetailsInput.getName());
            performUpdate = true;
        }
        if (sectionDetailsInput.getDescription() != null) {
            section.setDescription(sectionDetailsInput.getDescription());
            performUpdate = true;
        }
        return performUpdate ? sectionRepository.save(section) : section;
    }

    //accepting only the list of the sections to swap
    @Override
    public List<Section> swapSectionsPositions(List<SectionPositionInput> sectionPositionInputList) {
        // get the sections
        Set<Integer> ids = sectionPositionInputList.stream().map( input -> input.getSectionId()).collect(Collectors.toSet());
        Set<Integer> positions = sectionPositionInputList.stream().map( input -> input.getPosition()).collect(Collectors.toSet());
        if (ids.size() != sectionPositionInputList.size()){
            throw new BadRequestException("Cannot use the same id twice within the swapping list");
        }
        if (positions.size() != sectionPositionInputList.size()){
            throw new BadRequestException("Cannot use the same position twice within the swapping list");
        }


        List<Section> sections = sectionRepository.findByIdsOrderByPosition(ids);
        Set<Integer> sectionsPositions = sections.stream().map( section -> section.getPosition()).collect(Collectors.toSet());
        Set<Integer> sectionsIds = sections.stream().map( section -> section.getSectionId()).collect(Collectors.toSet());

        sectionsPositions.forEach(
                position -> {
                    if (!positions.contains(position)){
                        throw new BadRequestException("The original position " + position + " of the section "
                        + sections.stream().filter( section -> section.getPosition() == position).findFirst().get().getName()
                                + " is not in the swapping list");

                    }
                }
        );

        // swap the positions
        for (SectionPositionInput sectionPositionInput : sectionPositionInputList) {
            if (!sectionsIds.contains(sectionPositionInput.getSectionId())){
                throw new NotFoundException("The section with id " + sectionPositionInput.getSectionId() + " does not exist in the database");
            }
            Section section = sections.stream().filter( section1 -> section1.getSectionId() == sectionPositionInput.getSectionId()).findFirst().get();
            section.setPosition(sectionPositionInput.getPosition());
        }

        sectionRepository.saveAll(sections);

        return sectionRepository.findAllOrderByPosition();
    }

    @Override
    public List<Section> sortSectionsByAlphabeticalOrder() {
        List<Section> updatedList = new ArrayList<>();
        List<Section> sections = sectionRepository.findAllByOrderByName();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).getPosition() != i) {
                sections.get(i).setPosition(i);
                updatedList.add(sections.get(i));
            }
        }
        sectionRepository.saveAll(updatedList);
        return sectionRepository.findAllOrderByPosition();
    }


    @Override
    public Boolean deleteSection(Integer id) {

        Section section = sectionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Section with id " + id + " not found")
        );

        if (section.getCategories().size() > 0) {
            throw new BadRequestException("Section with id " + id + " has categories, you have to delete them first");
        }
        sectionRepository.delete(section);

        // update the position of the sections
        List<Section> sections = sectionRepository.findAllOrderByPosition();
        for (int i = 0; i < sections.size(); i++) {
            if (sections.get(i).getPosition() != i) {
                sections.get(i).setPosition(i);
            }
        }
        sectionRepository.saveAll(sections);
        return true;
    }

    @Override
    public Section getSectionByName(String name) {
        return sectionRepository.findByName(name).orElseThrow(
                () -> new NotFoundException("Section with name " + name + " not found")
        );
    }

    @Override
    public boolean existsByName(String name) {

        return sectionRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Integer id) {
        return sectionRepository.existsById(id);
    }


}
