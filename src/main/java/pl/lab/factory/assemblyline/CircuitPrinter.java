package pl.lab.factory.assemblyline;

import pl.lab.factory.pcbboards.BaseBoard;

// Tydzień 2 - Stosowanie konstrukcyjnych wzorców projektowych
// Wzorzec Singleton 2 (Double-checked locking)
// Singleton z double-checked locking zapewnia, że instancja jest tworzona tylko raz, nawet w środowisku wielowątkowym
// Pierwsze sprawdzenie instancji unika synchronizacji przy każdym wywołaniu getInstance()
public class CircuitPrinter implements Machine{
    private static volatile CircuitPrinter instance;

    private CircuitPrinter() {}

    public static CircuitPrinter getInstance() {
        if (instance == null) {
            synchronized (CircuitPrinter.class) {
                if (instance == null) {
                    instance = new CircuitPrinter();
                }
            }
        }
        return instance;
    }

    private void printCircuits(BaseBoard board) {
        board.printCircuits();
    }

    @Override
    public void perform(BaseBoard board) {

        System.out.println("[PRINTER] Printing board!");
        printCircuits(board);
    }
}
// Koniec, Tydzień 2
// Wzorzec Singleton 2
