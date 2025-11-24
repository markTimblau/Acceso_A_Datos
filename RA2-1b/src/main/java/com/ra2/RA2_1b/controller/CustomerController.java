package com.ra2.RA2_1b.controller;

import com.ra2.RA2_1b.model.Customer;
import com.ra2.RA2_1b.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/hello")
    public String jdbctemp() {
        // endpoint que devuelve un texto
        return "hello";
    }

    @PostMapping("/init-db")
    public String initializeDatabase() {
        // Crea la tabla de customers y agrega datos de ejemplo
        customerRepository.createTableCustomers();
        customerRepository.insertSampleData();
        return "Base de dades inicialitzada correctament";
    }

    @PostMapping("/customer")
    public ResponseEntity<String> createCustomers(@RequestBody Customer customer) {
        // Crea 10 Customers
        for (int i = 1; i <= 10; i++) {
            Customer c = new Customer();
            c.setName(customer.getName() + i); //Añadimos un numero para poder diferenciarlos
            c.setDescription(customer.getDescription());
            c.setAge(customer.getAge());
            c.setCourse(customer.getCourse());
            c.setPassword(customer.getPassword());
            c.setDataCreated(LocalDateTime.now()); // Establece fecha de creación
            c.setDataUpdated(LocalDateTime.now()); // Establece fecha de última actualización

            // Guarda cada customer en la base de datos
            customerRepository.save(c);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("S'han inserit correctament 10 alumnes!");
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customers = customerRepository.findAll();

            if (customers == null || customers.isEmpty()) {
                // Si no hay registros, devuelve null
                return ResponseEntity.ok(null);
            }

            // Devuelve la lista de customers OK
            return ResponseEntity.ok(customers);

        } catch (Exception e) {
            // Captura cualquier error y lo devuelve
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al recuperar els usuaris: " + e.getMessage());
        }
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customer_id") long id) {
        try {
            Customer customer = customerRepository.findById(id);

            if (customer == null) {
                // Si no existe, devuelve error
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer no trobat");
            }

            // Devuelve el customer encontrado
            return ResponseEntity.ok(customer);

        } catch (Exception e) {
            // Captura errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al recuperar el customer amb id " + id + ": " + e.getMessage());
        }
    }

    @PutMapping("/customer/{customer_id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable("customer_id") long id,
            @RequestBody Customer updatedCustomer) {
        try {
            // Busca el customer existente
            Customer existing = customerRepository.findById(id);
            if (existing == null) {
                return ResponseEntity.ok(null);
            }

            // Actualiza todos los campos
            existing.setName(updatedCustomer.getName());
            existing.setDescription(updatedCustomer.getDescription());
            existing.setAge(updatedCustomer.getAge());
            existing.setCourse(updatedCustomer.getCourse());
            existing.setPassword(updatedCustomer.getPassword());
            existing.setDataUpdated(LocalDateTime.now()); // Actualiza la fecha de modificación

            // Guarda los cambios
            customerRepository.update(existing);

            return ResponseEntity.ok(existing);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualitzar el customer amb id " + id + ": " + e.getMessage());
        }
    }

    @PatchMapping("/customer/{customer_id}/age")
    public ResponseEntity<Customer> updateCustomerAge(
            @PathVariable("customer_id") long customerId,
            @RequestParam("age") int age) {

        // Busca el customer por su id
        Customer existing = customerRepository.findById(customerId);

        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Actualiza solo la edad y la fecha de actualización
        existing.setAge(age);
        existing.setDataUpdated(LocalDateTime.now());
        customerRepository.update(existing);

        return ResponseEntity.ok(existing);
    }

    @DeleteMapping("/customer/{customer_id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customer_id") long customerId) {
        // Elimina el customer por su id
        boolean deleted = customerRepository.deleteById(customerId);

        if (deleted) {
            return ResponseEntity.ok("Customer amb id " + customerId + " eliminat correctament.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No s'ha trobat cap customer amb id " + customerId);
        }
    }
}
