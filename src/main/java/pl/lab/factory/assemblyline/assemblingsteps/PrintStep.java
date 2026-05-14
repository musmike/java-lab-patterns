package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.PrintingStrategy;

//Tydzien 8 - Podstawienie Liskov
//klasa którą można podstawić pod klasę dziedziczoną w dowolnym miejscu
public class PrintStep extends AssemblyStep{
    public PrintStep(PrintingStrategy strategy) {
        super(strategy);
    }

    @Override
    protected String getStepDescription() {
        return "Printing circuit...";
    }
}
//LSP 3
//koniec tydzien 8