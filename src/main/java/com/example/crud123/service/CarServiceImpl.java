package com.example.crud123.service;

import com.example.crud123.exception.DuplicateException;
import com.example.crud123.exception.NotFoundException;
import com.example.crud123.model.Car;
import com.example.crud123.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
  private final CarRepository carRepository;

  public CarServiceImpl(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @Override
  public Car createCar(Car car) {
    // Kiểm tra trùng lặp theo tên car
    if (carRepository.existsByCarName(car.getCarName())) {
      throw new DuplicateException("Car name already exists");
    }
    return carRepository.save(car);
  }

  @Override
  public Car updateCar(Long id, Car car) {
    Optional<Car> existingCar = carRepository.findById(id);
    if (!existingCar.isPresent()) {
      throw new NotFoundException("Car not found with id: " + id);
    }
    // Kiểm tra trùng lặp theo tên car (trừ khi đang update chính nó)
    if (carRepository.existsByCarNameAndIdNot(car.getCarName(), id)) {
      throw new DuplicateException("Car name already exists");
    }
    car.setId(id);
    return carRepository.save(car);
  }

  @Override
  public void deleteCar(Long id) {
    if (!carRepository.existsById(id)) {
      throw new NotFoundException("Car not found with id: " + id);
    }
    carRepository.deleteById(id);
  }

  @Override
  public Optional<Car> getCarById(Long id) {
    return carRepository.findById(id);
  }

  @Override
  public List<Car> getAllCars() {
    List<Car> cars = carRepository.findAll();
    if (cars.isEmpty()) {
      throw new NotFoundException("No cars found");
    }
    return cars;
  }
}
