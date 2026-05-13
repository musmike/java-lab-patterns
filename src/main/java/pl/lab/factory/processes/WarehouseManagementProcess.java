package pl.lab.factory.processes;

import pl.lab.factory.SystemLogger;
import pl.lab.factory.orders.OrderStatus;
import pl.lab.factory.orders.ProductionOrder;
import pl.lab.factory.orders.ProductionOrderFactory;
import pl.lab.factory.processes.context.FactoryContext;
import pl.lab.factory.orders.OrderContext;
import pl.lab.factory.safetymediator.SafetyHistory;
import pl.lab.factory.safetymediator.SafetySystemMemento;
import pl.lab.factory.safetymediator.components.AlarmSiren;
import pl.lab.factory.safetymediator.components.FireGate;
import pl.lab.factory.safetymediator.components.SmokeDetector;
import pl.lab.factory.safetymediator.components.SprinklerSystem;
import pl.lab.factory.warehouse.WarehouseSafetyCenter;
import pl.lab.factory.warehouse.equipment.EquipmentIterator;
import pl.lab.factory.warehouse.WarehouseAssetsManager;
import pl.lab.factory.warehouse.equipment.MaintenanceRecord;
import pl.lab.factory.warehouse.equipment.WarehouseEquipment;
import pl.lab.factory.warehouse.equipment.maintenance.ForkliftMaintenance;
import pl.lab.factory.warehouse.equipment.maintenance.MaintenanceProtocol;
import pl.lab.factory.warehouse.equipment.maintenance.ScannerMaintenance;
import pl.lab.factory.warehouse.equipment.observer.MaintenanceService;
import pl.lab.factory.warehouse.interpreter.OrderAndExpression;
import pl.lab.factory.warehouse.interpreter.OrderExpression;
import pl.lab.factory.warehouse.interpreter.OrderMinQuantityExpression;
import pl.lab.factory.warehouse.interpreter.OrderStatusExpression;
import pl.lab.factory.warehouse.packing.AntiStaticBagDecorator;
import pl.lab.factory.warehouse.packing.FoamProtectionDecorator;
import pl.lab.factory.warehouse.packing.PackageFactory;
import pl.lab.factory.warehouse.packing.PackageImplementor;
import pl.lab.factory.warehouse.shipping.PriorityShipping;
import pl.lab.factory.warehouse.shipping.ShippingFacade;
import pl.lab.factory.warehouse.shipping.ShippingStrategy;
import pl.lab.factory.warehouse.shipping.StandardShipping;
import pl.lab.factory.warehouse.shipping.command.ShipOrderCommand;
import pl.lab.factory.warehouse.shipping.command.ShippingManager;
import pl.lab.factory.warehouse.shipping.strategy.PriorityFirstDispatcher;
import pl.lab.factory.warehouse.state.LockedState;
import pl.lab.factory.warehouse.state.OpenState;

import java.time.LocalDate;
import java.util.NoSuchElementException;

public class WarehouseManagementProcess {

    private static final String LOG_CONTEXT = WarehouseManagementProcess.class.getSimpleName();

    private static final String UNAPPROVED_ORDER_ID = "ORD-999";
    private static final String PACKAGE_CARDBOARD = "cardboard";
    private static final String PACKAGE_PLASTIC = "plastic";
    private static final int LARGE_QUANTITY_THRESHOLD = 5;

    private static final String FORKLIFT_ID = "FORK-001";
    private static final String FORKLIFT_NAME = "Toyota Forklift";
    private static final int FORKLIFT_MONTHS = 14;
    private static final int FORKLIFT_HOURS = 498;
    private static final int FORKLIFT_USAGE = 5;

    private static final String SCANNER_ID = "SCAN-052";
    private static final String SCANNER_NAME = "Zebra Scanner";
    private static final int SCANNER_MONTHS = 2;
    private static final int SCANNER_HOURS = 540;
    private static final String SERVICE_SHORT = "SHORT";
    private static final int SERVICE_SHORT_MINS = 20;
    private static final String SERVICE_STANDARD = "STANDARD";

    private static final String SOLDERER_ID = "SOLDER-20";
    private static final String SOLDERER_NAME = "Weller Station";
    private static final int SOLDERER_MONTHS = 1;
    private static final int SOLDERER_HOURS = 120;

    private static final String TERMINAL_ID = "TERM-03";
    private static final String TERMINAL_NAME = "Logistics Terminal";

    private static final String FIRE_ORDER_ID = "FIRE-01";
    private static final int FIRE_ORDER_QUANTITY = 1;

    private final FactoryContext context;

    public WarehouseManagementProcess(FactoryContext context) {
        this.context = context;
    }

    public void execute() {
        validateContext();
        testWarehouseAndShipping();
        testWarehouseFiltering();
        testWarehouseEquipmentAndMaintenance();
        testFireAlarm();
    }

    private void validateContext() {
        if (context.getWarehouse() == null || context.getPcbOrder() == null) {
            throw new IllegalStateException("Warehouse & Orders must be initialized.");
        }
    }

    private void testWarehouseAndShipping() {
        storeUnapprovedOrder();
        printWarehouseStates();
        executeShippingCommands();
    }

    private void storeUnapprovedOrder() {
        ProductionOrder unapprovedOrder = context.getPcbOrder().clone(UNAPPROVED_ORDER_ID);
        try {
            context.getWarehouse().storeOrder(unapprovedOrder);
        } catch (SecurityException e) {
            SystemLogger.INSTANCE.logError("SECURITY", e.getMessage());
        }
    }

    private void printWarehouseStates() {
        context.getWarehouse().printLegacyWarehouse();
        context.getWarehouse().printWarehouse();
    }

    private void executeShippingCommands() {
        PackageImplementor cardboardBoxPackaging = createPackaging(PACKAGE_CARDBOARD);
        PackageImplementor plasticBoxPackaging   = createPackaging(PACKAGE_PLASTIC);

        ShippingStrategy standardShipping = new StandardShipping(cardboardBoxPackaging);
        ShippingStrategy priorityShipping = new PriorityShipping(plasticBoxPackaging);

        ShippingFacade shippingFacade = new ShippingFacade(context.getWarehouse());
        ShippingManager shippingManager = new ShippingManager();

        shippingManager.addCommand(new ShipOrderCommand(shippingFacade, priorityShipping, context.getStandardElectronicOrder()));
        shippingManager.addCommand(new ShipOrderCommand(shippingFacade, standardShipping, context.getPcbOrder()));
        shippingManager.addCommand(new ShipOrderCommand(shippingFacade, standardShipping, context.getSuperElectronicOrder()));

        //Liskov
        shippingManager.setDispatcher(new PriorityFirstDispatcher());
        shippingManager.shipEverything();

        shippingManager.undoLastAction();
        context.getWarehouse().printWarehouse();
    }

    private PackageImplementor createPackaging(String packageType) {
        return new FoamProtectionDecorator(
                new AntiStaticBagDecorator(PackageFactory.getPackage(packageType))
        );
    }

    private void testWarehouseFiltering() {
        OrderExpression isCompleted = new OrderStatusExpression(OrderStatus.COMPLETED);
        OrderExpression isLarge = new OrderMinQuantityExpression(LARGE_QUANTITY_THRESHOLD);

        OrderExpression complexRule = new OrderAndExpression(isCompleted, isLarge);

        SystemLogger.INSTANCE.logInfo("INTERPRETER", "Filtering completed orders with quantity >= " + LARGE_QUANTITY_THRESHOLD);
        context.getAllOrders().stream()
                .filter(complexRule::interpret)
                .forEach(order -> SystemLogger.INSTANCE.logInfo("[FILTERING]", "Match found: " + order.getId()));
    }

    private void testWarehouseEquipmentAndMaintenance() {
        WarehouseAssetsManager assetsManager = setupWarehouseEquipment();
        verifyMaintenanceNeeds(assetsManager);
        executeMaintenanceProtocols(assetsManager);
    }

    private WarehouseAssetsManager setupWarehouseEquipment() {
        WarehouseAssetsManager assetsManager = new WarehouseAssetsManager();

        assetsManager.addEquipment(new WarehouseEquipment(FORKLIFT_ID, FORKLIFT_NAME, new MaintenanceRecord(LocalDate.now().minusMonths(FORKLIFT_MONTHS), FORKLIFT_HOURS)));
        assetsManager.addEquipment(new WarehouseEquipment(SCANNER_ID, SCANNER_NAME, new MaintenanceRecord(LocalDate.now().minusMonths(SCANNER_MONTHS), SCANNER_HOURS)));
        assetsManager.addEquipment(new WarehouseEquipment(SOLDERER_ID, SOLDERER_NAME, new MaintenanceRecord(LocalDate.now().minusMonths(SOLDERER_MONTHS), SOLDERER_HOURS)));
        assetsManager.addEquipment(new WarehouseEquipment(TERMINAL_ID, TERMINAL_NAME));

        assetsManager.subscribeAll(new MaintenanceService());
        return assetsManager;
    }

    private void verifyMaintenanceNeeds(WarehouseAssetsManager manager) {
        EquipmentIterator maintenanceIterator = manager.getMaintenanceIterator();
        SystemLogger.INSTANCE.logInfo("MAINTENANCE", "Devices requiring immediate service:");
        while (maintenanceIterator.hasNext()) {
            SystemLogger.INSTANCE.logInfo("MAINTENANCE", "Check needed: " + maintenanceIterator.next());
        }
    }

    private void executeMaintenanceProtocols(WarehouseAssetsManager manager) {
        WarehouseEquipment forklift = getEquipmentById(manager, FORKLIFT_ID);
        WarehouseEquipment scanner = getEquipmentById(manager, SCANNER_ID);

        if (forklift != null) forklift.use(FORKLIFT_USAGE);

        MaintenanceProtocol forkliftService = new ForkliftMaintenance();
        MaintenanceProtocol scannerService = new ScannerMaintenance();

        scannerService.registerServiceDuration(SERVICE_SHORT, SERVICE_SHORT_MINS);

        if (forklift != null) forkliftService.executeService(forklift, SERVICE_STANDARD);
        if (scanner != null) scannerService.executeService(scanner, SERVICE_SHORT);
    }

    private WarehouseEquipment getEquipmentById(WarehouseAssetsManager manager, String id) {
        EquipmentIterator iterator = manager.getEquipmentIterator();
        while (iterator.hasNext()) {
            WarehouseEquipment equipment = iterator.next();
            if (equipment.getId().equals(id)) return equipment;
        }
        throw new NoSuchElementException("Equipment not found with id: " + id);
    }

    private void testFireAlarm() {
        WarehouseSafetyCenter warehouseSafetyCenter = new WarehouseSafetyCenter();
        SafetyHistory history = new SafetyHistory();

        SmokeDetector sensor = setupWarehouseSafetySystem(warehouseSafetyCenter);
        history.save(warehouseSafetyCenter.save());

        triggerFireAlarmAndLockdown(sensor);
        dispatchFireOrder();

        restoreSafetySystem(warehouseSafetyCenter, history);
        dispatchFireOrder();
    }

    private SmokeDetector setupWarehouseSafetySystem(WarehouseSafetyCenter center) {
        SmokeDetector sensor = new SmokeDetector(center);
        FireGate gate = new FireGate(center);
        SprinklerSystem sprinkler = new SprinklerSystem(center);
        AlarmSiren siren = new AlarmSiren(center);

        center.setDetector(sensor);
        center.setGate(gate);
        center.setSprinkler(sprinkler);
        center.setSiren(siren);

        return sensor;
    }

    private void triggerFireAlarmAndLockdown(SmokeDetector sensor) {
        sensor.detectFire();
        context.getWarehouseAdapter().getBaseBoardZone().setState(new LockedState());
    }

    private void dispatchFireOrder() {
        ProductionOrder fireOrder = ProductionOrderFactory.createOrder(
                context.getStandardPcbBoard(), new OrderContext(FIRE_ORDER_ID, FIRE_ORDER_QUANTITY, context.getPcbSpecification()));
        try {
            context.getWarehouse().storeOrder(fireOrder);
        } catch (IllegalStateException e) {
            SystemLogger.INSTANCE.logError(LOG_CONTEXT, e.getMessage());
        }
    }

    private void restoreSafetySystem(WarehouseSafetyCenter center, SafetyHistory history) {
            SafetySystemMemento lastState = history.undo();
        if (lastState != null) {
            center.restore(lastState);
            context.getWarehouseAdapter().getBaseBoardZone().setState(new OpenState());
        }
    }
}