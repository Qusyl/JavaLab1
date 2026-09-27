package Market;

import Events.MarketEvent;

import java.time.Instant;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Market implements Consumer<MarketEvent> {

    private Map<Instant, Double> story = new ConcurrentHashMap<Instant, Double>() {
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

    public Market() {
    }

    @Override
    public void accept(MarketEvent marketEvent) {
        story.put(Instant.now(), marketEvent.price());
        System.out.println("[Market] Обновление рынка! Товар " + marketEvent.product() + " по цене " + marketEvent.price());
    }
}
