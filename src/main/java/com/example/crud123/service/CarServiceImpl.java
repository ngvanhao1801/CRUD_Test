package com.example.crud123.service;

import com.example.crud123.dto.CarDto;
import com.example.crud123.exception.DuplicateException;
import com.example.crud123.exception.NotFoundException;
import com.example.crud123.mapper.CarMapper;
import com.example.crud123.model.Car;
import com.example.crud123.repository.CarRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

	private final CarRepository carRepository;

	private final CarMapper carMapper;

	public CarServiceImpl(CarRepository carRepository) {
		this.carRepository = carRepository;
		this.carMapper = Mappers.getMapper(CarMapper.class);
	}

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
		if (!car.isPresent()) {
			throw new NotFoundException("Không tìm thấy xe với id: " + id);
		}
		return car;
	}

	@Override
	public Car createCar(CarDto carDto) {
		if (carRepository.existsByCarName(carDto.getCarName())) {
			throw new DuplicateException("Tên xe đã tồn tại");
		}
		Car car = carMapper.toCar(carDto);
		return carRepository.save(car);
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
		Car car = carMapper.toCar(carDto);
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
