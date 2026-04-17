package pl.lab.factory.orders.loyalty;

public record TierConfig(
        int basePoints,     // punkty bazowe
        int bulkThreshold,  // próg ilościowy do bonusu
        int bulkBonus       // wartość bonusu za przekroczenie progu
) {}
