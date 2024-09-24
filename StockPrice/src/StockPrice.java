import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StockPrice {
    // Method to read stock data from CSV file for the given company and year
    public List<String[]> readStockData(String companyName, int year) {

        List<String[]> filteredData = new ArrayList<>();
        String fileName = companyName + ".csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            boolean isHeader = true;

            while ((nextLine = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    filteredData.add(nextLine);
                    continue;
                }

                LocalDate date = LocalDate.parse(nextLine[0], formatter);
                if (date.getYear() == year) {
                    filteredData.add(nextLine);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (CsvValidationException e) {
            System.err.println("CSV validation error: " + e.getMessage());
        }
        return filteredData;
    }

    // Method to calculate maximum profit and return ProfitRO object with information of buying and selling stocks
    public ProfitRO maxProfit(String companyName,int year){

        List<String[]> stockData = readStockData(companyName, year);

        if (stockData.size()<=2) {
            System.err.println("Not enough data to compute profit as one cannot buy a stock and sell a stock.");
            return null;
        }

        double maxProfit = Integer.MIN_VALUE;
        double minStockPrice = Integer.MAX_VALUE;
        int buyDateIndex = -1;
        int bestBuyDateIndex = -1;
        int sellDateIndex = -1;

        for (int i=1; i<stockData.size(); i++) {
            String[] currStock = stockData.get(i);
            double currMaxPrice = Double.parseDouble(currStock[2]);
            double currMinPrice = Double.parseDouble(currStock[3]);
            // System.out.println(String.join(", ", currStock));
            // System.out.println(currMaxPrice);
            // System.out.println(currMinPrice);
            double currProfit = currMaxPrice - minStockPrice;
            //System.out.println(currProfit);

            if (currProfit > maxProfit){
                maxProfit = currProfit;
                sellDateIndex = i;
                bestBuyDateIndex = buyDateIndex;
            }

            // maxProfit = Math.max(maxProfit, currProfit);
            // System.out.println(maxProfit);
            // minStockPrice = Math.min(currMinPrice, minStockPrice);
            if (currMinPrice < minStockPrice){
                minStockPrice = currMinPrice;
                buyDateIndex = i;
            }
        }

        if (maxProfit >= 0 && bestBuyDateIndex != -1 && sellDateIndex != -1){
            // System.out.println(stockData.get(bestBuyDateIndex)[0]);
            // System.out.println(stockData.get(bestBuyDateIndex)[3]);
            // System.out.println(stockData.get(sellDateIndex)[0]);
            // System.out.println(stockData.get(sellDateIndex)[2]);
            String buyDate = stockData.get(bestBuyDateIndex)[0];
            double buyPrice = Double.parseDouble(stockData.get(bestBuyDateIndex)[3]);
            String sellDate = stockData.get(sellDateIndex)[0];
            double sellPrice = Double.parseDouble(stockData.get(sellDateIndex)[2]);
            return new ProfitRO(buyDate, buyPrice, sellDate, sellPrice, maxProfit);
        }
        return new ProfitRO(null, 0.0, null, 0.0, 0);
    }
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please provide 2 arguments: <stockName> <year>");
            return;
        }
        String companyName = args[0];
        int year;

        try {
            year = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format. Please provide a valid integer for the year.");
            return;
        }
        StockPrice stockPrice = new StockPrice();
        ProfitRO result = stockPrice.maxProfit(companyName, year);

        System.out.println("Best time to Buy and Sell to make most profit is");
        if (result == null){
            System.out.println("Not enough data to compute profit as one cannot buy a stock and sell a stock.");
        }
        else{
            if (result.getBuyDate() == null && result.getSellDate() == null){
                System.out.println("No profitable transaction");
                System.out.println("Maximum Profit is:" + result.getProfit());
            }
            else {
                System.out.println("Buy Date: " + result.getBuyDate());
                System.out.println("Sell Date: " + result.getSellDate());
                System.out.println("Buy Price: " + result.getBuyPrice());
                System.out.println("Sell Price: " + result.getSellPrice());
                System.out.println("Maximum Profit: " + result.getProfit());
            }
        }
    }
}