package com.example.crud123.controller;

import com.example.crud123.dto.CarDto;
import com.example.crud123.model.Car;
import com.example.crud123.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/control")
public class CarController {
	private final CarService carService;

	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getAllCars() {
		List<Car> cars = carService.getAllCars();
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		Optional<Car> car = carService.getCarById(id);
		return new ResponseEntity<>(car.get(), HttpStatus.OK);
	}

	@PostMapping("/cars")
	public ResponseEntity<Car> createCar(@RequestBody CarDto carDto) {
		Car createdCar = carService.createCar(carDto);
		return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
	}

	@PutMapping("/cars/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody CarDto carDto) {
		Car updatedCar = carService.updateCar(id, carDto);
		return new ResponseEntity<>(updatedCar, HttpStatus.OK);
	}

	@DeleteMapping("/cars/{id}")
	public ResponseEntity<String> deleteCar(@PathVariable Long id) {
		carService.deleteCar(id);
		return new ResponseEntity<>("Đã xoá thành công", HttpStatus.OK);
	}
}
