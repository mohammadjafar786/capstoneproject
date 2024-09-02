package com.sks;
import com.sks.model.Car;

import com.sks.repository.CarRepository;
import com.sks.service.CarService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest(classes=CarRentalSystemApplicationTests.class)
public class CarServiceTest {

@InjectMocks
private CarService carService;
@Mock
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
    public void testAddCar() {
      Car car = new Car();
     car.setCar_id(1L);
      when(carRepository.save(car)).thenReturn(car);
       Car result = carService.addCar(car);
       assertNotNull(result);
        assertEquals(car.getCar_id(), result.getCar_id());
      verify(carRepository, times(1)).save(car);
   }

   @Test
    public void testRemoveCar_Success() {
        Long carId = 1L;
       doNothing().when(carRepository).deleteById(carId);

        carService.removeCar(carId);
        verify(carRepository, times(1)).deleteById(carId);
   }

  @Test
    public void testRemoveCar_NotFound() {
       Long carId = 1L;
       doThrow(new EmptyResultDataAccessException("Car not found", 1)).when(carRepository).deleteById(carId);

        carService.removeCar(carId);
       verify(carRepository, times(1)).deleteById(carId);
   }
  @Test
  public void testListAvailableCars() {
      Car car1 = new Car();
       car1.setCar_id(1L);
      Car car2 = new Car();
       car2.setCar_id(2L);
        List<Car> cars = Arrays.asList(car1, car2);

       when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.listAvailableCars();
        assertNotNull(result);
       assertEquals(2, result.size());
       verify(carRepository, times(1)).findAll();
 }

    @Test
   public void testFindCarById_Found() {
       Long carId = 1L;
       Car car = new Car();
        car.setCar_id(carId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car result = carService.findCarById(carId);
        assertNotNull(result);
        assertEquals(carId, result.getCar_id());
       verify(carRepository, times(1)).findById(carId);
    }
   @Test
    public void testFindCarById_NotFound() {
      Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

       Car result = carService.findCarById(carId);
       assertNull(result);
        verify(carRepository, times(1)).findById(carId);
    }
   }





