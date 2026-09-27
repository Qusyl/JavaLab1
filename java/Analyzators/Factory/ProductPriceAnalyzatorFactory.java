package Analyzators.Factory;

import Analyzators.ProductPriceAnalyzator;
import Interface.analyzer.Analyzer;
import Interface.analyzer.AnalyzerFactory;

public class ProductPriceAnalyzatorFactory implements AnalyzerFactory
{
    @Override
    public Analyzer create() {
        return  new ProductPriceAnalyzator();
    }
}
