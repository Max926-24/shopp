package app.repository;

import app.domain.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepository {

    private final Map<Long, Customer> dataBaseMap = new HashMap<>();
    private long maxId;

    public Customer save(Customer customer) {
        customer.setId(++maxId);
        dataBaseMap.put(maxId, customer);
        return customer;

    }

    public List<Customer> findAll() {
        return new ArrayList<>(dataBaseMap.values());
    }

    public Customer findById(long id) {
        return dataBaseMap.get(id);
    }

    public void update(Long id, String newName) {
        Customer customer = findById(id);
        if (customer != null) {
            customer.setName(newName);
        }
    }

    public void deleteById(Long id) {
        dataBaseMap.remove(id);
    }
}
