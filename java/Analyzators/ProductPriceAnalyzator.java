package Analyzators;

import Events.MarketEvent;
import Interface.analyzer.Analyzer;

import java.time.Instant;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductPriceAnalyzator implements Analyzer {
    private final Map<Instant, Double> story = new ConcurrentHashMap<>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Enumeration<Instant> keys() {
            return null;
        }

        @Override
        public Enumeration<Double> elements() {
            return null;
        }

        @Override
        public Double get(Object key) {
            return 0.0;
        }

        @Override
        public Double put(Instant key, Double value) {
            return 0.0;
        }

        @Override
        public Double remove(Object key) {
            return 0.0;
        }
    };


    public ProductPriceAnalyzator() {

    }

    @Override
    public void accept(MarketEvent marketEvent) {
        var time = Instant.now();
        story.put(time, marketEvent.price());
        System.out.println("[PriceAnalyzator][" + time.toString() + "]" + "Обновление рынка! Товар " + marketEvent.product() + " по цене " + marketEvent.price());
    }

    @Override
    public String name() {
        return "price";
    }
}
