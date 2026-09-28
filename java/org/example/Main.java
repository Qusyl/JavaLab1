package org.example;

import backgroundServices.ProductBackgroundService;
import backgroundServices.ProductPriceAnalyseBackgroundService;

public class Main {
    public static void main(String[] args) {
        ProductBackgroundService.getInstance().start();

        ProductPriceAnalyseBackgroundService.getInstance().start();
    }
}