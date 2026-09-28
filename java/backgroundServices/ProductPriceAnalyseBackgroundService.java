package backgroundServices;

import Infrastructure.repositories.ProductPriceAnalyzeRepository;
import Infrastructure.repositories.ProductRepository;
import Interface.backgroundService.BackgroundService;
import Interface.repository.PriceAnalyzeRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ProductPriceAnalyseBackgroundService implements BackgroundService {
    private  final ExecutorService executor = Executors.newSingleThreadExecutor();
    private  final PriceAnalyzeRepository repository = new ProductPriceAnalyzeRepository();
    private Future<?> serviceTask;
    private ProductPriceAnalyseBackgroundService(){}

    private  final ProductRepository productRepository = new ProductRepository();
    private  static  class Holder{
        private  static final ProductPriceAnalyseBackgroundService INSTANCE = new ProductPriceAnalyseBackgroundService();
    }
    public static ProductPriceAnalyseBackgroundService getInstance()
    {
        return  Holder.INSTANCE;
    }

    @Override
    public synchronized void start()
    {
         if (serviceTask != null && !serviceTask.isDone())
         {
             System.out.println("Сервис анализа работает уже в фоне");
             return;
         }
            serviceTask = executor.submit(() -> {
                System.out.println("Сервис анализа цен запущен в фоновом режиме!");
             });
            while(!Thread.currentThread().isInterrupted()){
                try{
                    Thread.sleep(5000);
                    var products = repository.getAllAnalyzes();
                    products.forEach(product ->{
                        System.out.println("[" + product.ProductId()+"]\n"+" старая цена: " + product.ProductOldPrice() + "\nновая цена: " + product.ProductNewPrice() + "\n[" + product.timeUpdated() + "]");
                    });
                }catch (InterruptedException ex) {
                    System.out.println("Принудительное отключение фонового режима сервиса ...");
                    Thread.currentThread().interrupt();
                }catch (Exception exAll){
                    System.out.println("Непредвиденная ошибка произошла в фоновом сервисе");
                }
            }
         }

    @Override
    public synchronized void stop()
    {
        if (serviceTask != null)
        {
            serviceTask.cancel(true);
        }
    }
}
