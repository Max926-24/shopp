package app.service;

import app.domain.Customer;
import app.domain.Product;
import app.exceptions.CustomerNotFoundException;
import app.exceptions.CustomerSaveException;
import app.exceptions.CustomerUpdateException;
import app.repository.CustomerRepository;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.List;

public class CustomerService {

    private final CustomerRepository repository = new CustomerRepository();
    private final ProductService productService;

    public CustomerService(ProductService productService) {
        this.productService = productService;
    }

    public Customer save(Customer customer) {
        if (customer == null) {
            throw new CustomerSaveException("Customer cannot be null");
        }
        String name = customer.getName();
        if (name == null || name.isBlank()) {
            throw new CustomerSaveException("Customer name cannot be blank");
        }
        customer.setActive(true);
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .toList();

    }

    public Customer getActiveCustomerById(Long id) {
        Customer customer = repository.findById(id);
        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }
        return customer;

    }

    public void update(Long id, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new CustomerUpdateException("New name cannot be blank");
        }
        repository.update(id, newName);
    }

    public void deleteById(Long id) {
        Customer customer = getActiveCustomerById(id);
        customer.setActive(false);

    }

    public void deleteByName(String name) {
        getAllCustomers()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));

    }

    public void restoreById(Long id) {
        Customer customer = repository.findById(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        customer.setActive(true);
    }

    public int customersNumber(Customer customer) {
        return getAllCustomers().size();
    }

    public double getCustomersCartTotalCost(Long id) {
        return getActiveCustomerById(id)
                .getCart()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(Product::getPrice)
                .sum();

    }

    public double getCustomersCartAveragePrice(Long id) {
        return getActiveCustomerById(id)
                .getCart()
                .stream()
                .filter(Product::isActive)
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }

    public void addProductToCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomerById(customerId);
        Product product = productService.getActiveProductById(productId);
        customer.getCart().add(product);

    }

    public void removeProductFromCart(Long customerId, Long productId) {
        Customer customer = getActiveCustomerById(customerId);
        List<Product> cart = customer.getCart();
        Iterator<Product> iterator = cart.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(productId)) {
                iterator.remove();
                break;
            }
        }

    }

    public void clearCustomerCards(Long id) {
        Customer customer = getActiveCustomerById(id);
        customer.getCart().clear();
    }

}

