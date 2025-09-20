package br.com.orbitall.channels.services;

import br.com.orbitall.channels.canonicals.CustomerInput;
import br.com.orbitall.channels.canonicals.CustomerOutput;
import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import br.com.orbitall.channels.models.Customer;
import br.com.orbitall.channels.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerOutput MapToCustomerOutput(Customer customer){
        return new CustomerOutput(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.isActive()
        );
    }
    public CustomerOutput create(CustomerInput input){
        Customer customer = new Customer();
        customer.setFullName(input.fullName());
        customer.setEmail(input.email());
        customer.setPhone(input.phone());

        Customer savedCustomer = customerRepository.save(customer);
        return MapToCustomerOutput(savedCustomer);
    }

    public CustomerOutput retrieve(UUID id){
        Customer customer =customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID:" + id));
        return MapToCustomerOutput(customer);
    }

    public CustomerOutput update(UUID id, CustomerInput input){
        Customer existingCustomer = customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not found with ID:" + id));
        existingCustomer.setFullName(input.fullName());
        existingCustomer.setEmail(input.email());
        existingCustomer.setPhone(input.phone());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return MapToCustomerOutput(updatedCustomer);
    }
    public CustomerOutput delete(UUID id) {
        Customer existingCustomer = customerRepository.findById(id)
                .filter(Customer::isActive)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        existingCustomer.setActive(false);
        Customer deletedCustomer = customerRepository.save(existingCustomer);
        return MapToCustomerOutput(deletedCustomer);
}
    public List<CustomerOutput> findAll() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(Customer::isActive)
                .map(this::MapToCustomerOutput)
                .collect(Collectors.toList());
    }
}