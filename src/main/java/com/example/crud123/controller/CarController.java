package com.example.crud123.controller;

import com.example.crud123.exception.NotFoundException;
import com.example.crud123.model.Car;
import com.example.crud123.service.CarService;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/control")
public class CarController {
  public static Logger logger = (Logger) LoggerFactory.getLogger(CarController.class);

  private final CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  @PostMapping("/cars/post")
  public ResponseEntity<Car> createCar(@RequestBody Car car) {
    Car createdCar = carService.createCar(car);
    return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
    Car updatedCar = carService.updateCar(id, car);
    return new ResponseEntity<>(updatedCar, HttpStatus.OK);
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<String> deleteCar(@PathVariable Long id) {
    carService.deleteCar(id);
    return new ResponseEntity<>("Đã xoá thành công", HttpStatus.OK);
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Car> getCarById(@PathVariable Long id) {
    Optional<Car> car = carService.getCarById(id);
    if (car.isPresent()) {
      return new ResponseEntity<>(car.get(), HttpStatus.OK);
    } else {
      throw new NotFoundException("Không tìm thấy xe với id: " + id);
    }
  }

  @GetMapping("/cars/all")
  public ResponseEntity<List<Car>> getAllCars() {
    List<Car> cars = carService.getAllCars();
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }
}