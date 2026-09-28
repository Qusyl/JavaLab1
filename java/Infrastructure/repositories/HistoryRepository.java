package Infrastructure.repositories;

import Infrastructure.connection.DatabaseConnectionBuilder;
import Infrastructure.dbconfig.DbConfig;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;

public class HistoryRepository implements Interface.repository.HistoryRepository {
    @Override
    public void makeSnapshot(Product product)
    {
        String sql = "INSERT INTO products_history (product_id, product_name ,product_price, uploaded_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = new DatabaseConnectionBuilder()
                .withUrl(DbConfig.url())
                .withUser(DbConfig.user())
                .withPassword(DbConfig.password())
                .build();
             PreparedStatement stmnt = conn.prepareStatement(sql))
        {

            stmnt.setInt(1, product.Id());
            stmnt.setString(2, product.productName());
            stmnt.setDouble(3, product.price());
            stmnt.setTimestamp(4, Timestamp.from(Instant.now()));
            int res = stmnt.executeUpdate();
        } catch (java.sql.SQLException ex){
            System.out.println("Ошибка во время загрузки снимка: " + product.Id() + ex.getMessage());

        }
    }
}
