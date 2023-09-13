package com.example.crud123.repository;

import com.example.crud123.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	boolean existsByCarName(String carName);

	boolean existsByCarNameAndIdNot(String carName, Long id);

}
