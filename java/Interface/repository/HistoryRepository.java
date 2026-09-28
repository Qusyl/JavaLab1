package Interface.repository;

import Model.Product;

public interface HistoryRepository
{
    void makeSnapshot(Product product);
}
