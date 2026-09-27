package Interface.analyzer;

import Events.MarketEvent;

import java.util.function.Consumer;

public interface Analyzer extends Consumer<MarketEvent>
{
    String name();
}
