//package com.mas.e_commerce_back.controllers;
//
//import com.mas.e_commerce_back.dtos.ApiResponse;
//import com.mas.e_commerce_back.entities.Manufacturer;
//import com.mas.e_commerce_back.inputs.ManufacturerInput;
//import com.mas.e_commerce_back.services.ManufacturerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RequestMapping("api/v1/manufacturers")
//public class ManufacturerController {
//
//    private final ManufacturerService manufacturerService;
//
//    @Autowired
//    public ManufacturerController(ManufacturerService manufacturerService) {
//        this.manufacturerService = manufacturerService;
//    }
//
//    @GetMapping
//    public ResponseEntity<ApiResponse<List<Manufacturer>>> getManufacturers() {
//
//        final ApiResponse<List<Manufacturer>> response = new ApiResponse<>(
//                true,
//                "manufacturers retrieved successfully",
//                manufacturerService.getAllManufacturers()
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<Manufacturer>> getManufacturerById(@PathVariable("id") Integer id) {
//
//        final ApiResponse<Manufacturer> response = new ApiResponse<>(
//                true,
//                "manufacturer retrieved successfully",
//                manufacturerService.getManufacturerById(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/name/{name}")
//    public ResponseEntity<ApiResponse<Manufacturer>> getManufacturerByName(@PathVariable("name") String name) {
//
//        final ApiResponse<Manufacturer> response = new ApiResponse<>(
//                true,
//                "manufacturer retrieved successfully",
//                manufacturerService.getManufacturerByName(name)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("new")
//    public ResponseEntity<ApiResponse<Manufacturer>> createManufacturer(@RequestBody ManufacturerInput manufacturerInput) {
//
//        final ApiResponse<Manufacturer> response = new ApiResponse<>(
//                true,
//                "manufacturer created successfully",
//                manufacturerService.createManufacturer(manufacturerInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<Manufacturer>> updateManufacturerById(@PathVariable("id") Integer id, @RequestBody ManufacturerInput manufacturerInput) {
//
//        final ApiResponse<Manufacturer> response = new ApiResponse<>(
//                true,
//                "manufacturer updated successfully",
//                manufacturerService.updateManufacturerById(id, manufacturerInput)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<ApiResponse<Manufacturer>> deleteManufacturerById(@PathVariable("id") Integer id) {
//
//        final ApiResponse<Manufacturer> response = new ApiResponse<>(
//                true,
//                "manufacturer deleted successfully",
//                manufacturerService.deleteManufacturerById(id)
//        );
//        return ResponseEntity.ok(response);
//    }
//
//
//
//
//
//}
