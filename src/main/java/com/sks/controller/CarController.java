package com.sks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sks.model.Car;
import com.sks.service.CarService;

@RestController
@RequestMapping("api")
public class CarController {
	
    @Autowired
    private CarService carService;

    @PostMapping(value ="addcar",produces = "application/json")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car addedCar = carService.addCar(car);
        return new ResponseEntity<>(addedCar, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Void> removeCar(@PathVariable Long id) {
        carService.removeCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value ="Availablecars" ,produces = "application/json")
    public ResponseEntity<List<Car>> listAvailableCars() {
        List<Car> availableCars = carService.listAvailableCars();
        return new ResponseEntity<>(availableCars, HttpStatus.OK);
    }

    @GetMapping(value ="/{carId}",produces = "application/json" )
    public ResponseEntity<Car> findCarById(@PathVariable Long carId) {
        Car car = carService.findCarById(carId);
        return car != null ? 
               new ResponseEntity<>(car, HttpStatus.OK) : 
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "update/{carId}", produces = "application/json")
    public ResponseEntity<Car> updateCar(@PathVariable Long carId, @RequestBody Car car) {
        // Check if the car exists
        Car existingCar = carService.findCarById(carId);
        if (existingCar == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        // Update the car details
        car.setCar_id(carId); // Ensure the ID is correctly set
        Car updatedCar = carService.updateCar(car);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }
}
