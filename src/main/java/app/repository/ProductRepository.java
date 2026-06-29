package app.repository;

import app.domain.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public  class ProductRepository {

    private final List<Product> dataBase = new ArrayList<>();

    private long maxId;


    public Product save(Product product) {
        product.setId(++maxId);
        dataBase.add(product);
        return product;
    }

    public List<Product> findAll() {
        return dataBase;
    }

    public Product findById(Long id) {
        for (Product product : dataBase) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public void update(Long id, double newPrice) {
        for (Product product : dataBase) {
            if (product.getId().equals(id)) {
                product.setPrice(newPrice);
                break;
            }
        }
    }

    public void deleteById(Long id) {
        Iterator<Product> iterator = dataBase.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals(id)) {
                iterator.remove();
                break;
            }
        }
    }
}




