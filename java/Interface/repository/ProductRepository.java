package Interface.repository;

import Model.Product;

import java.util.List;

public interface ProductRepository
{
    List<Product> getAllProducts();
    Product getProduct(int id);
    int insertProduct(Product product);
    int updateProduct(int id, String name, double price);
    int deleteProduct(int id);
}
