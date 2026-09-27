package Infrastructure.repositories;
import Infrastructure.connection.DatabaseConnectionBuilder;
import Infrastructure.dbconfig.DbConfig;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Interface.repository.ProductRepository
{

    @Override
    public List<Product> getAllProducts()
    {
        var list = new ArrayList<Product>();
        String sql = "SELECT * FROM products";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             Statement stmnt = conn.createStatement();
             ResultSet results = stmnt.executeQuery(sql);
        )
        {
            while(results.next())
            {
                int id = results.getInt("id");
                String name = results.getString("product_name");
                double price = results.getDouble("product_price");
                var product = new Product(id, name, price);
                list.add(product);
            }
            return  list;
        }
        catch (java.sql.SQLException ex)
        {
            System.out.println("Ошибка во время загрузки всех продуктов: " + ex.getMessage());
            return list;
        }
    }

    @Override
    public Product getProduct(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, id);
            var rs = stmnt.executeQuery();
            int productId = rs.getInt("id");
            String name = rs.getString("product_name");
            double price = rs.getDouble("product_price");
            var product = new Product(productId, name, price);
            return  product;
        } catch (java.sql.SQLException ex) {
            System.out.println("Ошибка во время загрузки продукта: " + id + ex.getMessage());
            return null;
        }
    }

    @Override
    public int insertProduct(Product product)
    {
        String sql = "INSERT INTO products (product_name, product_price) VALUES (?, ?)";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {
            stmnt.setString(1, product.productName());
            stmnt.setDouble(2, product.price());
            int res = stmnt.executeUpdate();
            return  res;
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время добавления продукта: " + product.Id() + ex.getMessage());
            return  0;
        }
    }

    @Override
    public int updateProduct(int id, String name, double price)
    {
        String sql = "UPDATE products SET (product_name, product_price) = (?, ?) WHERE id = ?";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {

            stmnt.setString(1, name);
            stmnt.setDouble(2, price);
            stmnt.setInt(3,id);
            int res = stmnt.executeUpdate();
            return  res;
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время обновления продукта: " + id + ex.getMessage());
            return  0;
        }
    }

    @Override
    public int deleteProduct(int id) {
        String sql = "DELETE  FROM  products WHERE id = ?";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {
            stmnt.setInt(1,id);
            int res = stmnt.executeUpdate();
            return  res;
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время удаления продукта: " + id + ex.getMessage());
            return  0;
        }
    }
}
