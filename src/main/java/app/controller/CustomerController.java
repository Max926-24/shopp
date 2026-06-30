package app.controller;

import app.domain.Customer;
import app.service.CustomerService;

import java.util.List;

public class CustomerController {

    private final CustomerService service = new CustomerService();

    public Customer save(String name) {
        Customer customer = new Customer(name);
        return service.save(customer);
    }

    public List<Customer> getAll() {
        return service.getAllCustomers();
    }
    public int getCustomersNumber(){
        return service.getAllCustomers().size();

    }

    public Customer getById(Long id) {
        return service.getActiveCustomerById(id);
    }

    public void update(Long id, String name) {
        service.update(id, name);
    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }

    public void deleteByName(String name) {
        service.deleteByName(name);
    }

    public void restoreById(Long id) {
        service.restoreById(id);
    }

    public double getCustomersCartsTotalCost(Long id) {
        return service.getCustomersCartTotalCost(id);

    }

    public double getCustomersTotalAveragePrice(Long id) {
        return service.getCustomersCartAveragePrice(id);
    }

    public void addProductToCustomersCart(Long customerId, Long productId) {
        service.addProductToCart(customerId, productId);
    }

    public void removeProductFromCart(Long customerId, Long productId) {
        service.removeProductFromCart(customerId, productId);
    }

    public void clearAllProductsFromCart(Long customerId) {
        service.clearCustomerCards(customerId);
    }
}
