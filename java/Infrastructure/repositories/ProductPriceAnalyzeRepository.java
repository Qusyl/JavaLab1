package Infrastructure.repositories;

import Infrastructure.connection.DatabaseConnectionBuilder;
import Infrastructure.dbconfig.DbConfig;
import Interface.repository.PriceAnalyzeRepository;
import Model.PriceAnalyze;


import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductPriceAnalyzeRepository implements PriceAnalyzeRepository {
    @Override
    public List<PriceAnalyze> getAllAnalyzes() {
        var list = new ArrayList<PriceAnalyze>();
        String sql = "SELECT * FROM price_analyses";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             Statement stmnt = conn.createStatement();
             ResultSet results = stmnt.executeQuery(sql);) {
            while(results.next())
            {

                int id = results.getInt("id");
                int product_id = results.getInt("product_id");
                double oldPrice = results.getDouble("product_old_price");
                double newPrice = results.getDouble("product_new_price");
                Timestamp ts = results.getTimestamp("updated_at");
                Instant updatedAt = (ts != null) ? ts.toInstant() : null;
                var product = new PriceAnalyze(id, product_id, oldPrice, newPrice, updatedAt);
                list.add(product);
            }
            return  list;
        } catch (java.sql.SQLException ex) {
            System.out.println("Ошибка во время загрузки анализов: " +  ex.getMessage());
            return list;
        }
    }

    @Override
    public int updateByProductId(int id, double oldPrice, double newPrice, Instant updatedAt) {
        String sql = "UPDATE price_analyses SET (product_old_price, product_new_price, updated_at) = (?, ?, ?) WHERE product_id = ?";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {

            stmnt.setDouble(1, oldPrice);
            stmnt.setDouble(2, newPrice);
            stmnt.setTimestamp(3, Timestamp.from(updatedAt));
            stmnt.setInt(4, id);
            int res = stmnt.executeUpdate();
            return  res;
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время обновления анализа: " + id + ex.getMessage());
            return  0;
        }
    }

    @Override
    public int insertNewAnalyze(PriceAnalyze analyze) {
        String sql = "INSERT INTO price_analyses (product_id, product_old_price, product_new_price, updated_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {

            stmnt.setInt(1, analyze.ProductId());
            stmnt.setDouble(2, analyze.ProductOldPrice());
            stmnt.setDouble(3, analyze.ProductNewPrice());
            stmnt.setTimestamp(4, Timestamp.from(analyze.timeUpdated()));
            int res = stmnt.executeUpdate();
            return  res;
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время обновления анализа: " + analyze.Id() + ex.getMessage());
            return  0;
        }
    }

    @Override
    public int deleteAnalyzeByProductId(int Id) {
        String sql = "DELETE  FROM  price_analyses WHERE product_id = ?";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {
            stmnt.setInt(1, Id);
            int res = stmnt.executeUpdate();
            return  res;
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время удаления анализа: " + Id + ex.getMessage());
            return  0;
        }
    }
}
