package com.mas.e_commerce_back.services;

import com.mas.e_commerce_back.entities.Manufacturer;
import com.mas.e_commerce_back.inputs.ManufacturerInput;

import java.util.List;

public interface ManufacturerService {

    List<Manufacturer> getAllManufacturers();

    Manufacturer getManufacturerById(Integer id);

    Manufacturer getManufacturerByName(String name);

    Manufacturer createManufacturer(ManufacturerInput manufacturerInput);

    boolean manufacturerExists(Integer id);


    Manufacturer updateManufacturerDetails(ManufacturerInput manufacturerInput);

    Boolean deleteManufacturerById(Integer id);

}
