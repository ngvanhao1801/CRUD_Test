package com.example.crud123.service;

import com.example.crud123.dto.CarDto;
import com.example.crud123.exception.DuplicateException;
import com.example.crud123.exception.NotFoundException;
import com.example.crud123.model.Car;
import com.example.crud123.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
  private final CarRepository carRepository;

  public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
    this.carRepository = carRepository;
    this.modelMapper = modelMapper;
  }

  private final ModelMapper modelMapper;

  @Override
  public List<Car> getAllCars() {
    List<Car> cars = carRepository.findAll();
    if (cars.isEmpty()) {
      throw new NotFoundException("Không tìm thấy xe nào");
    }
    return cars;
  }

  @Override
  public Optional<Car> getCarById(Long id) {
    Optional<Car> car = carRepository.findById(id);
    if (car.isEmpty()) {
      throw new NotFoundException("Không tìm thấy xe với id: " + id);
    }
    return car;
  }

  @Override
  public Car createCar(CarDto carDto) {
    if (carRepository.existsByCarName(carDto.getCarName())) {
      throw new DuplicateException("Tên xe đã tồn tại");
    }
    Car car = modelMapper.map(carDto, Car.class);
    return carRepository.save(car);
  }

  @Override
  public Car updateCar(Long id, Car car) {
    return null;
  }

  @Override
  public Car updateCar(Long id, CarDto carDto) {
    Optional<Car> existingCar = carRepository.findById(id);
    if (!existingCar.isPresent()) {
      throw new NotFoundException("Không tìm thấy xe với id: " + id);
    }
    if (carRepository.existsByCarNameAndIdNot(carDto.getCarName(), id)) {
      throw new DuplicateException("Tên xe đã tồn tại");
    }
    Car car = modelMapper.map(carDto, Car.class);
    return carRepository.save(car);
  }

  @Override
  public void deleteCar(Long id) {
    if (!carRepository.existsById(id)) {
      throw new NotFoundException("Không tìm thấy xe với id: " + id);
    }
    carRepository.deleteById(id);
  }
}
