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
    if (carRepository.existsByCarName(car.getCarName())) {
      throw new DuplicateException("Tên xe đã tồn tại");
    }
    return carRepository.save(car);
  }

  @Override
  public Car updateCar(Long id, Car car) {
    Optional<Car> existingCar = carRepository.findById(id);
    if (!existingCar.isPresent()) {
      throw new NotFoundException("Không tìm thấy xe với id: " + id);
    }
    if (carRepository.existsByCarNameAndIdNot(car.getCarName(), id)) {
      throw new DuplicateException("Tên xe đã tồn tại");
    }
    car.setId(id);
    return carRepository.save(car);
  }

  @Override
  public void deleteCar(Long id) {
    if (!carRepository.existsById(id)) {
      throw new NotFoundException("Không tìm thấy xe với id: " + id);
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
      throw new NotFoundException("Không tìm thấy xe nào");
    }
    return cars;
  }
}
