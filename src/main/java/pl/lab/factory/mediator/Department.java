package pl.lab.factory.mediator;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 1 (Colleague – klasa abstrakcyjna)
// Klasa bazowa dla wszystkich działów. Zna tylko interfejs mediatora,
// nie wie o istnieniu innych działów (gwarantuje luźne powiązanie).
public abstract class Department {
    protected FactoryMediator mediator;

    public Department(FactoryMediator mediator) {
        this.mediator = mediator;
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 1