package com.mas.e_commerce_back.services;


import com.mas.e_commerce_back.entities.Manufacturer;
import com.mas.e_commerce_back.inputs.ManufacturerInput;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManufacturerServiceImpl implements ManufacturerService {


    @Override
    public List<Manufacturer> getAllManufacturers() {
        return null;
    }

    @Override
    public Manufacturer getManufacturerById(Integer id) {
        return null;
    }

    @Override
    public Manufacturer getManufacturerByName(String name) {
        return null;
    }

    @Override
    public Manufacturer createManufacturer(ManufacturerInput manufacturerInput) {
        return null;
    }

    @Override
    public boolean manufacturerExists(Integer id) {
        return false;
    }

    @Override
    public Manufacturer updateManufacturerById(Integer id, ManufacturerInput manufacturerInput) {
        return null;
    }

    @Override
    public Manufacturer deleteManufacturerById(Integer id) {
        return null;
    }
}
