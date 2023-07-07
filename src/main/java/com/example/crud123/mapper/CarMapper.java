package com.example.crud123.mapper;

import com.example.crud123.dto.CarDto;
import com.example.crud123.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CarMapper {

  // ObjectTraVe tenMethod(ParamDto);

  @Mappings({
      @Mapping(source = "dto.carName", target = "carName"),
      @Mapping(source = "dto.year", target = "year"),
      @Mapping(source = "dto.price", target = "price"),
      @Mapping(source = "dto.address", target = "address")
  })
  Car toCar(CarDto dto);

}
