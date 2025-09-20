package br.com.orbitall.channels.controllers;

import br.com.orbitall.channels.canonicals.CustomerInput;
import br.com.orbitall.channels.canonicals.CustomerOutput;
import br.com.orbitall.channels.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers" )
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerOutput> create(@Valid @RequestBody CustomerInput input){
        CustomerOutput customer = customerService.create(input);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOutput> retrieve(@PathVariable UUID id){
        CustomerOutput customer = customerService.retrieve(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerOutput> update(@PathVariable UUID id, @Valid @RequestBody CustomerInput input){
        CustomerOutput customer = customerService.update(id, input);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerOutput> delete(@PathVariable UUID id){
        CustomerOutput customer = customerService.delete(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CustomerOutput>> findAll() {
        List<CustomerOutput> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
