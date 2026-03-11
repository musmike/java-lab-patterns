package pl.lab.factory.manufacturing;

import pl.lab.factory.pcbboards.BaseBoard;

public abstract class BaseBoardFactory {

    // Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
    // Wzorzec Factory 1 (Factory Method)
    // Abstrakcyjna metoda fabryczna — podklasy decydują jaki konkretny typ
    // BaseBoard zostanie utworzony; BaseBoardFactory nie zna szczegółów implementacji
    public abstract BaseBoard createBoard();
    // Koniec, Tydzień 2
    // Wzorzec Factory 1
}