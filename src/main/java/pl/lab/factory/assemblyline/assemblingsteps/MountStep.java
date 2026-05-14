package pl.lab.factory.assemblyline.assemblingsteps;

import pl.lab.factory.assemblyline.assemblingstrategies.MountingStrategy;

//Tydzien 8 - Podstawienie Liskov
//klasa którą można podstawić pod klasę dziedziczoną w dowolnym miejscu
public class MountStep extends AssemblyStep{
    public MountStep(MountingStrategy strategy) {
        super(strategy);
    }

    @Override
    protected String getStepDescription() {
        return "Mounting components...";
    }
}
//LSP 3
//koniec tydzien 8