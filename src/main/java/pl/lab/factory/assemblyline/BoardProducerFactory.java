package pl.lab.factory.assemblyline;

public class BoardProducerFactory {
    public static BoardProducer createStandardProducer() {
        return new QualityControlDecorator(new TimedProducerDecorator(new SimpleBoardProducer()));
    }
}