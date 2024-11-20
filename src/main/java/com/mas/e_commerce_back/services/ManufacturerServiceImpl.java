package com.mas.e_commerce_back.services;


import com.mas.e_commerce_back.entities.Manufacturer;
import com.mas.e_commerce_back.exceptions.NotFoundException;
import com.mas.e_commerce_back.inputs.ManufacturerInput;
import com.mas.e_commerce_back.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }



    @Override
    public Manufacturer createManufacturer(ManufacturerInput manufacturerInput) {
        // check if the manufacturer exists by name
        manufacturerRepository.findByName(manufacturerInput.getName()).ifPresent(
                manufacturer -> {
                    throw new DataIntegrityViolationException("Manufacturer with name " + manufacturerInput.getName() + " already exists");
                }
        );
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerInput.getName());
        // handle saving the image to the object database
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer getManufacturerById(Integer id) {
        return manufacturerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Manufacturer with id" + id + " not found")
        );
    }

    @Override
    public Manufacturer getManufacturerByName(String name) {
        return manufacturerRepository.findByName(name).orElseThrow(
                () -> new RuntimeException("Manufacturer with name" + name + " not found")
        );
    }

    @Override
    public boolean manufacturerExists(Integer id) {
        return manufacturerRepository.existsById(id);
    }


    @Override
    public Manufacturer updateManufacturerDetails(ManufacturerInput manufacturerInput) {
        // check if the manufacturer exists
        boolean updateManufacturer = false;
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerInput.getManufacturerId()).orElseThrow(
                () -> new NotFoundException("Manufacturer with id" + manufacturerInput.getManufacturerId() + " not found")
        );
        // if the name is not null and is not the same as the current
        if(manufacturerInput.getName() != null){
            // check if the name is unique
            manufacturerRepository.findByName(manufacturerInput.getName()).ifPresent(
                    manufacturer1 -> {
                        throw new DataIntegrityViolationException("Manufacturer with name " + manufacturerInput.getName() + " already exists");
                    }
            );
            updateManufacturer = true;
            manufacturer.setName(manufacturerInput.getName());
        }

        return updateManufacturer ? manufacturerRepository.save(manufacturer) : manufacturer;
    }

    @Override
    public Boolean deleteManufacturerById(Integer id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Manufacturer with id" + id + " not found")
        );
        boolean hasProducts = manufacturerRepository.productListExists(id);
        if(hasProducts){
            throw new DataIntegrityViolationException("Manufacturer with id " + id + " has products, you have to delete them first");
        }

        manufacturerRepository.delete(manufacturer);
        return true;

    }
}
