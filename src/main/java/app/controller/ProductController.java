package app.controller;

import app.domain.Product;
import app.service.ProductService;

import java.util.List;

public class ProductController {

    private final ProductService service = ProductService.getInstance();

    public Product save(String title, double price) {
        Product product = new Product(title, price);
        return service.save(product);

    }

    public List<Product> getAll() {
        return service.getAllActiveProducts();
    }

    public Product getById(Long id) {
        return service.getActiveProductById(id);
    }

    public void update(Long id, double newPrice) {
        service.update(id, newPrice);

    }

    public void deleteById(Long id) {
        service.deleteById(id);
    }

    public void deleteByTitle(String title) {
        service.deleteByTitle(title);
    }

    public void restoreById(Long id) {
        service.restoreById(id);

    }

    public int getProductsNumber() {
        return service.getActiveProductsNumber();
    }

    public double getProductsTotalCost() {
        return service.getActiveProductsTotalCost();
    }

    public double getActiveProductsAveragePrice() {
        return service.getActiveProductsAveragePrice();
    }
}
