package Interface.repository;

import Model.PriceAnalyze;

import java.time.Instant;
import java.util.List;

public interface PriceAnalyzeRepository
{
    List<PriceAnalyze> getAllAnalyzes();
    int updateByProductId(int id, double oldPrice, double newPrice, Instant updatedAt);
    int insertNewAnalyze(PriceAnalyze analyze);
    int deleteAnalyzeByProductId(int Id);
}
