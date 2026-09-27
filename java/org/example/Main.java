package org.example;

import Infrastructure.connection.DatabaseConnectionBuilder;
import Infrastructure.repositories.ProductPriceAnalyzeRepository;
import Infrastructure.repositories.ProductRepository;
import Model.PriceAnalyze;
import Model.Product;

import java.sql.Connection;
import java.time.Instant;


public class Main {
    public static void main(String[] args) {

        var repository = new ProductRepository();

        var analyse = new ProductPriceAnalyzeRepository();

       var analyseList = analyse.getAllAnalyzes();

       analyseList.forEach(Panalyse -> {
           System.out.println(Panalyse + "\n");
       });

       analyse.updateByProductId(1, 100, 200, Instant.now());

    }
}