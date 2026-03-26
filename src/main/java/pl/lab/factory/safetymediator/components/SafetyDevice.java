package pl.lab.factory.safetymediator.components;

import pl.lab.factory.safetymediator.SafetyMediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 2 (Bazowy komponent wzorca Mediator)
public abstract class SafetyDevice {
    protected SafetyMediator mediator;

    public SafetyDevice(SafetyMediator mediator){
        this.mediator = mediator;
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 2