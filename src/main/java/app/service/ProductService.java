package app.service;

import app.domain.Product;
import app.exceptions.ProductNotFoundException;

import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository repository = new ProductRepository();

    public Product save(Product product) {
        if (product == null) {
            throw new ProductSaveException("Product is can't be null");
        }
        String title = product.getTitle();
        if (title == null || title.isBlank()) {
            throw new ProductSaveException("Title is can't be empty");
        }
        if (product.getPrice() <= 0) {
            throw new ProductSaveException("Price is can't be negative");
        }
        product.setActive(true);
        return repository.save(product);

    }

    public List<Product> getAllActiveProducts() {
        return repository.findAll()
                .stream()
                .filter(Product::isActive)
                .toList();
    }

    public Product getActiveProductById(Long id) {
        Product product = repository.findById(id);
        if (product == null || !product.isActive()) {
            throw new ProductNotFoundException(id);
        }
        return product;
    }

    public void update(Long id, double newPrice) {
        if (newPrice <= 0) {
            throw new ProductUpdateException("Price is can't be negative or 0");
        }
        repository.update(id, newPrice);
    }

    public void deleteById(Long id) {
        Product product = getActiveProductById(id);
        product.setActive(false);
    }

    public void deleteByTitle(String title) {
        getAllActiveProducts()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));

    }

    public void restoreById(Long id) {
        Product product = repository.findById(id);
        if (product == null || !product.isActive()) {
            throw new ProductNotFoundException(id);

        }
        product.setActive(true);
    }

    public int getActiveProductsNumber() {
        return getAllActiveProducts().size();
    }

    public double getActiveProductsTotalCost() { // public double getActiveProductsTotalCost(){
        // Способ 2 :
     /*   double sum = 0;
        for (Product product : getAllActiveProducts()) {
            sum += product.getPrice();
        }
        return sum;
         */
        return getAllActiveProducts()
                .stream().
                mapToDouble(Product::getPrice).
                sum();
    }

    public double getActiveProductsAveragePrice() {
     /*   // способ 1 :
        int productsNumber = getActiveProductsNumber();
        if (productsNumber == 0){
            return 0.0;
        }
        return getActiveProductsTotalCost()/productsNumber;

   */

        //Способ 2 :
        return getAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);


    }


}










