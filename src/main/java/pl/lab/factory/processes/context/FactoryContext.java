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
    private BaseBoard boardA;
    private BaseElectronicBoard electronicBoardA;
    private BaseElectronicBoard electronicBoardB;
    private OrderSpecification specPcb;
    private OrderSpecification specElectronic;
    private ProductionOrder orderA;
    private ProductionOrder orderB;
    private ProductionOrder orderC;
    private ProductionOrder orderBaseClone;
    private ProductionOrder orderElectronicClone;
    private List<ProductionOrder> allOrders = new ArrayList<>();
    private OrderInvoice invoiceA;
    private OrderInvoice invoiceB;
    private OrderProcessor pcbProcessor;
    private OrderProcessor electronicBoardProcessor;
    private WarehouseAdapter warehouseAdapter;
    private WarehouseService warehouse;
    private ProductionDepartment productionDepartment;

    public BaseBoard getBoardA() { return boardA; }
    public void setBoardA(BaseBoard boardA) { this.boardA = boardA; }

    public BaseElectronicBoard getElectronicBoardA() { return electronicBoardA; }
    public void setElectronicBoardA(BaseElectronicBoard electronicBoardA) { this.electronicBoardA = electronicBoardA; }

    public BaseElectronicBoard getElectronicBoardB() { return electronicBoardB; }
    public void setElectronicBoardB(BaseElectronicBoard electronicBoardB) { this.electronicBoardB = electronicBoardB; }

    public OrderSpecification getSpecPcb() { return specPcb; }
    public void setSpecPcb(OrderSpecification specPcb) { this.specPcb = specPcb; }

    public OrderSpecification getSpecElectronic() { return specElectronic; }
    public void setSpecElectronic(OrderSpecification specElectronic) { this.specElectronic = specElectronic; }

    public ProductionOrder getOrderA() { return orderA; }
    public void setOrderA(ProductionOrder orderA) { this.orderA = orderA; }

    public ProductionOrder getOrderB() { return orderB; }
    public void setOrderB(ProductionOrder orderB) { this.orderB = orderB; }

    public ProductionOrder getOrderC() { return orderC; }
    public void setOrderC(ProductionOrder orderC) { this.orderC = orderC; }

    public ProductionOrder getOrderBaseClone() { return orderBaseClone; }
    public void setOrderBaseClone(ProductionOrder orderBaseClone) { this.orderBaseClone = orderBaseClone; }

    public ProductionOrder getOrderElectronicClone() { return orderElectronicClone; }
    public void setOrderElectronicClone(ProductionOrder orderElectronicClone) { this.orderElectronicClone = orderElectronicClone; }

    public List<ProductionOrder> getAllOrders() { return allOrders; }
    public void setAllOrders(List<ProductionOrder> allOrders) { this.allOrders = allOrders; }

    public OrderInvoice getInvoiceA() { return invoiceA; }
    public void setInvoiceA(OrderInvoice invoiceA) { this.invoiceA = invoiceA; }

    public OrderInvoice getInvoiceB() { return invoiceB; }
    public void setInvoiceB(OrderInvoice invoiceB) { this.invoiceB = invoiceB; }

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