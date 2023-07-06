package com.example.crud123.service;

import com.example.crud123.dto.CarDto;
import com.example.crud123.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
  Car createCar(CarDto carDto);
  Car updateCar(Long id, Car car);

  Car updateCar(Long id, CarDto carDto);


  void deleteCar(Long id);
  Optional<Car> getCarById(Long id);
  List<Car> getAllCars();
}

