package com.holi.api.drivers.infraestructure.controller;

import com.holi.api.drivers.application.dto.DriverRequest;
import com.holi.api.drivers.application.dto.DriverResponse;
import com.holi.api.drivers.application.service.DriverService;
import com.holi.api.users.application.dto.UserRequest;
import com.holi.api.users.application.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/drivers")
@CrossOrigin("*")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public  DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<DriverResponse> registerDriver(@RequestBody DriverRequest driverRequest){
        try{
            DriverResponse registerDriver = driverService.registerDriver(driverRequest);
            return  new ResponseEntity<>(registerDriver, HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<DriverResponse> getDriverById(@PathVariable Long driverId) {
        try {
            // Obtener el usuario usando el servicio
            DriverResponse driverResponse = driverService.getDriverById(driverId);
            return new ResponseEntity<>(driverResponse, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // El usuario no se encuentra
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Error interno
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{driverId}")
    public ResponseEntity<DriverResponse> updatedDriverData(@PathVariable Long driverId, @RequestBody DriverRequest driverRequest) {
        try {
            if (driverRequest == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Mala solicitud si los datos están vacíos
            }
            // Llamada al servicio para actualizar el usuario
            DriverResponse updatedDriverData = driverService.updatedDriverData(driverId, driverRequest);

            return new ResponseEntity<>(updatedDriverData, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            // Si el usuario no se encuentra
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            // En caso de que haya algún error con los datos (por ejemplo, email duplicado)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Error interno
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<String> driverDelete(@PathVariable Long driverId) {
        try {
            driverService.driverDelete(driverId);
            return ResponseEntity.ok("Usuario eliminado con éxito");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se produjo un error al procesar la solicitud");
        }
    }
}
