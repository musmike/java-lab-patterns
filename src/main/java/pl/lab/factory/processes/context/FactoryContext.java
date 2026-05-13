package pl.lab.factory.processes.context;

import pl.lab.factory.electronicboards.BaseElectronicBoard;
import pl.lab.factory.manufacturing.OrderProcessor;
import pl.lab.factory.mediator.ProductionDepartment;
import pl.lab.factory.orders.OrderSpecification;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.invoice.OrderInvoice;
import pl.lab.factory.pcbboards.BaseBoard;
import pl.lab.factory.warehouse.WarehouseAdapter;
import pl.lab.factory.warehouse.WarehouseService;

import java.util.ArrayList;
import java.util.List;

public class FactoryContext {

    private BaseBoard standardPcbBoard;
    private BaseElectronicBoard highlyDependentElectronicBoard;
    private BaseElectronicBoard hotfixElectronicBoard;

    private OrderSpecification pcbSpecification;
    private OrderSpecification electronicBoardSpecification;

    private ProductionOrder pcbOrder;
    private ProductionOrder standardElectronicOrder;
    private ProductionOrder superElectronicOrder;
    private ProductionOrder clonedPcbOrder;
    private ProductionOrder clonedElectronicOrder;

    private List<ProductionOrder> allOrders = new ArrayList<>();

    private OrderInvoice pcbInvoice;
    private OrderInvoice electronicBoardInvoice;

    private OrderProcessor pcbProcessor;
    private OrderProcessor electronicBoardProcessor;

    private WarehouseAdapter warehouseAdapter;
    private WarehouseService warehouse;

    private ProductionDepartment productionDepartment;

    public BaseBoard getStandardPcbBoard() { return standardPcbBoard; }
    public void setStandardPcbBoard(BaseBoard standardPcbBoard) { this.standardPcbBoard = standardPcbBoard; }

    public BaseElectronicBoard getHighlyDependentElectronicBoard() { return highlyDependentElectronicBoard; }
    public void setHighlyDependentElectronicBoard(BaseElectronicBoard highlyDependentElectronicBoard) { this.highlyDependentElectronicBoard = highlyDependentElectronicBoard; }

    public BaseElectronicBoard getHotfixElectronicBoard() { return hotfixElectronicBoard; }
    public void setHotfixElectronicBoard(BaseElectronicBoard hotfixElectronicBoard) { this.hotfixElectronicBoard = hotfixElectronicBoard; }

    public OrderSpecification getPcbSpecification() { return pcbSpecification; }
    public void setPcbSpecification(OrderSpecification pcbSpecification) { this.pcbSpecification = pcbSpecification; }

    public OrderSpecification getElectronicBoardSpecification() { return electronicBoardSpecification; }
    public void setElectronicBoardSpecification(OrderSpecification electronicBoardSpecification) { this.electronicBoardSpecification = electronicBoardSpecification; }

    public ProductionOrder getPcbOrder() { return pcbOrder; }
    public void setPcbOrder(ProductionOrder pcbOrder) { this.pcbOrder = pcbOrder; }

    public ProductionOrder getStandardElectronicOrder() { return standardElectronicOrder; }
    public void setStandardElectronicOrder(ProductionOrder standardElectronicOrder) { this.standardElectronicOrder = standardElectronicOrder; }

    public ProductionOrder getSuperElectronicOrder() { return superElectronicOrder; }
    public void setSuperElectronicOrder(ProductionOrder superElectronicOrder) { this.superElectronicOrder = superElectronicOrder; }

    public ProductionOrder getClonedPcbOrder() { return clonedPcbOrder; }
    public void setClonedPcbOrder(ProductionOrder clonedPcbOrder) { this.clonedPcbOrder = clonedPcbOrder; }

    public ProductionOrder getClonedElectronicOrder() { return clonedElectronicOrder; }
    public void setClonedElectronicOrder(ProductionOrder clonedElectronicOrder) { this.clonedElectronicOrder = clonedElectronicOrder; }

    public List<ProductionOrder> getAllOrders() { return allOrders; }
    public void setAllOrders(List<ProductionOrder> allOrders) { this.allOrders = allOrders; }

    public OrderInvoice getPcbInvoice() { return pcbInvoice; }
    public void setPcbInvoice(OrderInvoice pcbInvoice) { this.pcbInvoice = pcbInvoice; }

    public OrderInvoice getElectronicBoardInvoice() { return electronicBoardInvoice; }
    public void setElectronicBoardInvoice(OrderInvoice electronicBoardInvoice) { this.electronicBoardInvoice = electronicBoardInvoice; }

    public OrderProcessor getPcbProcessor() { return pcbProcessor; }
    public void setPcbProcessor(OrderProcessor pcbProcessor) { this.pcbProcessor = pcbProcessor; }

    public OrderProcessor getElectronicBoardProcessor() { return electronicBoardProcessor; }
    public void setElectronicBoardProcessor(OrderProcessor electronicBoardProcessor) { this.electronicBoardProcessor = electronicBoardProcessor; }

    public WarehouseAdapter getWarehouseAdapter() { return warehouseAdapter; }
    public void setWarehouseAdapter(WarehouseAdapter warehouseAdapter) { this.warehouseAdapter = warehouseAdapter; }

    public WarehouseService getWarehouse() { return warehouse; }
    public void setWarehouse(WarehouseService warehouse) { this.warehouse = warehouse; }

    public ProductionDepartment getProductionDepartment() { return productionDepartment; }
    public void setProductionDepartment(ProductionDepartment productionDepartment) { this.productionDepartment = productionDepartment; }
}