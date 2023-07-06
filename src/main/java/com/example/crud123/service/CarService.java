package com.example.crud123.service;

import com.example.crud123.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
  Car createCar(Car car);
  Car updateCar(Long id, Car car);
  void deleteCar(Long id);
  Optional<Car> getCarById(Long id);
  List<Car> getAllCars();
}

