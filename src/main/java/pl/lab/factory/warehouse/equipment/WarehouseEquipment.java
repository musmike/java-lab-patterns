package pl.lab.factory.warehouse.equipment;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.warehouse.equipment.observer.MaintenanceObservable;
import pl.lab.factory.warehouse.equipment.observer.MaintenanceObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Iterator 2 (Element kolekcji)
public class WarehouseEquipment implements MaintenanceObservable {
    private static final int MAINTENANCE_HOURS_LIMIT = 500;
    private static final int MAINTENANCE_YEARS_LIMIT = 1;
    
    private final String  id;
    private final String name;
    private LocalDate lastServiceDate;
    private int operatingHours;
    private boolean isOperational = true;

    // Tydzień 6 - Stosowanie czynnościowych wzorców projektowych cz. 2
    // Wzorzec Observer 2 (Concrete Observable)

    private final List<MaintenanceObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(MaintenanceObserver obs){
        observers.add(obs);
    }

    @Override
    public void removeObserver(MaintenanceObserver obs){
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(String reason){
        for(MaintenanceObserver obs : observers){
            obs.onMaintenanceRequired(this, reason);
        }
    }

    public void use(int hours){
        if(!isOperational){
            SystemLogger.INSTANCE.logInfo(this.name.toUpperCase(), "ALERT: Cannot use " + this.id + ". It is under maintenance!");
            return;
        }
        this.operatingHours += hours;
        if(this.operatingHours > MAINTENANCE_HOURS_LIMIT){
            notifyObservers("Maintenance limit reached: " + operatingHours + "h");
        }
    }
    // Koniec, Tydzień 6
    // Wzorzec Observer 2

    public WarehouseEquipment(String id, String name, LocalDate lastServiceDate, int operatingHours){
        this.id = id;
        this.name = name;
        this.lastServiceDate = lastServiceDate;
        this.operatingHours = operatingHours;
    }

    public WarehouseEquipment(String id, String name){
        this(id, name, LocalDate.now(), 0);
    }

    public boolean needsMaintenance(){
        return operatingHours >= MAINTENANCE_HOURS_LIMIT || lastServiceDate.isBefore(LocalDate.now().minusYears(MAINTENANCE_YEARS_LIMIT));
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setOperational(boolean status){this.isOperational = status;}

    public boolean isOperational(){return isOperational;}

    public void resetMaintenance(){
        this.operatingHours = 0;
        this.lastServiceDate = LocalDate.now();
    }

    @Override
    public String toString(){
        return name+" "+id+" | Last Service: "+lastServiceDate+" | Hours: "+operatingHours;
    }

}
// Koniec, Tydzień 5
// Wzorzec Iterator 2