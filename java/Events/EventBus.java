package Events;

import jdk.jfr.Event;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class EventBus implements  Runnable
{
    private  final BlockingQueue<MarketEvent> events;

    private  final List<Consumer<MarketEvent>> subs;

    public EventBus(BlockingQueue<MarketEvent> events,List<Consumer<MarketEvent>> subscribers)
    {
        this.events = events;
        this.subs = subscribers;
    }
    @Override
    public void run() {
        try{
            while (!Thread.currentThread().isInterrupted())
            {
                MarketEvent event = events.take();
                for(var subscriber : subs )
                {
                    subscriber.accept(event);
                }
            }
        }catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }
}
