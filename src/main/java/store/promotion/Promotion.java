package store.promotion;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name) {
        this.name = name;
        this.buyQuantity = 0;
        this.freeQuantity = 0;
        this.startDate = null;
        this.endDate = null;
    }

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buyQuantity = buy;
        this.freeQuantity = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
