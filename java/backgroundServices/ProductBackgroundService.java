package backgroundServices;

import Infrastructure.repositories.ProductPriceAnalyzeRepository;
import Infrastructure.repositories.ProductRepository;
import Interface.backgroundService.BackgroundService;
import Interface.repository.HistoryRepository;
import Model.PriceAnalyze;
import Model.Product;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ProductBackgroundService implements BackgroundService {
    private  final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<?> serviceTask;
    private ProductBackgroundService(){}
    private  final ProductRepository productRepository = new ProductRepository();
    private  final HistoryRepository historyRepository = new Infrastructure.repositories.HistoryRepository();
    private  final ProductPriceAnalyzeRepository productPriceAnalyseRepository = new ProductPriceAnalyzeRepository();
    private  static  class Holder{
        private  static final ProductBackgroundService INSTANCE = new ProductBackgroundService();
    }
    public static ProductBackgroundService getInstance()
    {
        return  Holder.INSTANCE;
    }


    public synchronized void start(){
        if(serviceTask != null && !serviceTask.isDone()){
            System.out.println("Сервис уже работает в фоновом режиме!");
        }

        serviceTask = executor.submit(() -> {
            System.out.println("Сервис продуктов запущен в фоновом режиме!");
            while(!Thread.currentThread().isInterrupted())
            {
                var random = new Random();
                try
                {
                    Thread.sleep(10000);
                    var product =
                            new Product(random.nextInt(1000) + 1,
                            "product"+random.nextInt(1000) + 1,
                            random.nextDouble());
                    productRepository.insertProduct(product);
                    historyRepository.makeSnapshot(product);
                    productPriceAnalyseRepository.insertNewAnalyze(new PriceAnalyze(1, product.Id(), product.price(), product.price(), Instant.now()));
                    System.out.println("[Сервис продуктов] Добавлен новый продукт!");

                } catch (InterruptedException ex) {
                    System.out.println("Принудительное отключение фонового режима сервиса продуктов...");
                    Thread.currentThread().interrupt();
                }catch (Exception exAll){
                    System.out.println("Непредвиденная ошибка произошла в фоновом сервисе продуктов");
                }
            }
        });
    }
    public  synchronized void stop()
    {
        if (serviceTask != null)
        {
            serviceTask.cancel(true);
        }
    }

}
