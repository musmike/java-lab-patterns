package pl.lab.factory.mediator;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.safetymediator.FactoryEvent;


// Tydzień 5 - Stosowanie czynnościowych wzorców projektowych cz. 1
// Wzorzec Mediator 1 (Konkretny Mediator)
// Centralizuje logikę przepływu po produkcji pojedynczego zamówienia.
// Departments nie wiedzą o sobie nawzajem — jedynie zgłaszają event do mediatora.
public class CentralControlMediator implements FactoryMediator {
    private ProductionDepartment productionDepartment;
    private WarehouseDepartment warehouseDepartment;
    private ReportingDepartment reportingDepartment;

    public void setProductionDepartment(ProductionDepartment productionDepartment) {
        this.productionDepartment = productionDepartment;
    }
    public void setWarehouseDepartment(WarehouseDepartment warehouseDepartment) {
        this.warehouseDepartment = warehouseDepartment;
    }
    public void setReportingDepartment(ReportingDepartment reportingDepartment) {
        this.reportingDepartment = reportingDepartment;
    }

    @Override
    public void notify(Department sender, FactoryEvent event, ProductionOrder order) {
        if (sender == this.productionDepartment && event == FactoryEvent.ORDER_COMPLETED) {
            SystemLogger.INSTANCE.logInfo("MEDIATOR", "Order " + order.getId() + " produced. Triggering reporting and storage.");
            if (reportingDepartment != null) reportingDepartment.report(order);
            if (warehouseDepartment != null) warehouseDepartment.store(order);
        } else if (sender == this.warehouseDepartment && event == FactoryEvent.ORDER_STORED) {
            SystemLogger.INSTANCE.logInfo("MEDIATOR", "Order " + order.getId() + " is safely stored in warehouse.");
        }
    }
}
// Koniec, Tydzień 5
// Wzorzec Mediator 1