package pl.lab.factory.pcbboards;

import pl.lab.factory.Pair;
import pl.lab.factory.components.Capacitor;
import pl.lab.factory.components.ComponentDefinitionFactory;
import pl.lab.factory.components.ComponentGroup;
import pl.lab.factory.components.Inductor;
import pl.lab.factory.components.Resistor;

public class QXCBoard extends BaseBoard {

    private static final double DEFAULT_VOLTAGE = 5;
    private static final BoardMaterials DEFAULT_BOARD_MATERIAL = BoardMaterials.COPPER;
    private static final SolderMaterials DEFAULT_SOLDER_MATERIAL = SolderMaterials.WITH_GOLD;
    private static final double DEFAULT_THICKNESS = 2.3;
    private static final Pair<Integer, Integer> DEFAULT_SIZE = new Pair<>(120, 120);
    private static final int DEFAULT_COLOR = 0x007700;
    private static final int DEFAULT_LAYERS = 3;

    private static final int MAIN_RESISTOR_1_OHMS = 20;
    private static final double MAIN_RESISTOR_1_COST = 20.2;
    private static final Pair<Integer, Integer> MAIN_RESISTOR_1_POS = new Pair<>(1, 1);

    private static final int MAIN_CAPACITOR_FARADS = 5;
    private static final double MAIN_CAPACITOR_COST = 12.2;
    private static final Pair<Integer, Integer> MAIN_CAPACITOR_POS = new Pair<>(3, 1);

    private static final int MAIN_RESISTOR_2_OHMS = 20;
    private static final double MAIN_RESISTOR_2_COST = 3.2;
    private static final Pair<Integer, Integer> MAIN_RESISTOR_2_POS = new Pair<>(1, 1);

    private static final int SUB_INDUCTOR_HENRIES = 15;
    private static final double SUB_INDUCTOR_COST = 4.5;
    private static final Pair<Integer, Integer> SUB_INDUCTOR_POS = new Pair<>(21, 20);

    public QXCBoard() {
        configurePhysicalProperties();
        buildComponentsTopology();
    }

    private void configurePhysicalProperties() {
        this.workingVoltage = DEFAULT_VOLTAGE;
        this.boardMaterial = DEFAULT_BOARD_MATERIAL;
        this.solderType = DEFAULT_SOLDER_MATERIAL;
        this.thickness = DEFAULT_THICKNESS;
        this.width = DEFAULT_SIZE.first();
        this.length = DEFAULT_SIZE.second();
        this.boardColor = DEFAULT_COLOR;
        this.layers = DEFAULT_LAYERS;
    }

    private void buildComponentsTopology() {
        ComponentGroup mainGroup = new ComponentGroup("SCQ");
        addMainResistors(mainGroup);
        addMainCapacitors(mainGroup);

        ComponentGroup subGroup = new ComponentGroup("sub CAWD");
        addSubInductors(subGroup);

        mainGroup.add(subGroup);
        this.components.add(mainGroup);
    }

    private void addMainResistors(ComponentGroup group) {
        group.add(new Resistor(
                ComponentDefinitionFactory.getResistorDefinition(MAIN_RESISTOR_1_OHMS, MAIN_RESISTOR_1_COST),
                MAIN_RESISTOR_1_POS
        ));
        group.add(new Resistor(
                ComponentDefinitionFactory.getResistorDefinition(MAIN_RESISTOR_2_OHMS, MAIN_RESISTOR_2_COST),
                MAIN_RESISTOR_2_POS
        ));
    }

    private void addMainCapacitors(ComponentGroup group) {
        group.add(new Capacitor(
                ComponentDefinitionFactory.getCapacitorDefinition(MAIN_CAPACITOR_FARADS, MAIN_CAPACITOR_COST),
                MAIN_CAPACITOR_POS
        ));
    }

    private void addSubInductors(ComponentGroup group) {
        group.add(new Inductor(
                ComponentDefinitionFactory.getInductorDefinition(SUB_INDUCTOR_HENRIES, SUB_INDUCTOR_COST),
                SUB_INDUCTOR_POS
        ));
    }

    @Override
    public String toString() {
        return "QXCBoard{" + width + "x" + length + "mm, " + layers + " layers, " + boardMaterial + "}";
    }
}
