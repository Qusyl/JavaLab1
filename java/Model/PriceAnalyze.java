package Model;

import java.time.Instant;

public record PriceAnalyze(
        int Id,
        int ProductId,
        double ProductOldPrice,
        double ProductNewPrice,
        Instant timeUpdated){}
