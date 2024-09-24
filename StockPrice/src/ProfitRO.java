public class ProfitRO {
    private String buyDate;
    private double buyPrice;
    private String sellDate;
    private double sellPrice;
    private double profit;

    public ProfitRO(String buyDate, double buyPrice, String sellDate, double sellPrice, double profit) {
        this.buyDate = buyDate;
        this.buyPrice = buyPrice;
        this.sellDate = sellDate;
        this.sellPrice = sellPrice;
        this.profit = profit;
    }

    // Getters
    public String getBuyDate() {
        return buyDate;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public String getSellDate() {
        return sellDate;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getProfit() {
        return profit;
    }
}
