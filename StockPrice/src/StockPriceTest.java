import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StockPriceTest {

    // Helper method to create a StockPrice object with predefined data
    private StockPrice createStockPriceWithData(List<String[]> stockData) {
        return new StockPrice() {
            @Override
            public List<String[]> readStockData(String companyName, int year) {
                return stockData;
            }
        };
    }

    @Test
    public void testMaxProfitWithProfit() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        stockData.add(new String[]{"2023-01-01", "100", "150", "90", "140", "1000"});
        stockData.add(new String[]{"2023-01-02", "110", "160", "100", "150", "2000"});
        stockData.add(new String[]{"2023-01-03", "90", "170", "85", "160", "1500"});

        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNotNull(result);
        assertEquals("2023-01-01", result.getBuyDate());
        assertEquals(90.0, result.getBuyPrice());
        assertEquals("2023-01-03", result.getSellDate());
        assertEquals(170.0, result.getSellPrice());
        assertEquals(80.0, result.getProfit());
    }

    @Test
    public void testMaxProfitWithNegativeProfit() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        stockData.add(new String[]{"2023-01-01", "150", "150", "130", "140", "1000"});
        stockData.add(new String[]{"2023-01-02", "140", "120", "100", "130", "2000"});
        stockData.add(new String[]{"2023-01-03", "130", "90", "80", "120", "1500"});

        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNotNull(result);
        assertNull(result.getBuyDate());
        assertNull(result.getSellDate());
        assertEquals(0.0, result.getBuyPrice());
        assertEquals(0.0, result.getSellPrice());
        assertEquals(0.0, result.getProfit());
    }

    @Test
    public void testMaxProfitWithSingleDayData() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        stockData.add(new String[]{"2023-01-01", "100", "150", "90", "140", "1000"});

        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNull(result);
    }

    @Test
    public void testMaxProfitWithEmptyData() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNull(result);
    }

    @Test
    public void testMaxProfitWithSamePrice() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        stockData.add(new String[]{"2023-01-01", "100", "100", "100", "100", "1000"});
        stockData.add(new String[]{"2023-01-02", "100", "100", "100", "100", "2000"});
        stockData.add(new String[]{"2023-01-03", "100", "100", "100", "100", "1500"});

        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNotNull(result);
        assertEquals("2023-01-01", result.getBuyDate());
        assertEquals(100.0, result.getBuyPrice());
        assertEquals("2023-01-02", result.getSellDate());
        assertEquals(100.0, result.getSellPrice());
        assertEquals(0.0, result.getProfit());
    }

    @Test
    public void testMaxProfitWithZeroProfit() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        stockData.add(new String[]{"2023-01-01", "100", "150", "120", "100", "1000"});
        stockData.add(new String[]{"2023-01-02", "100", "120", "120", "100", "2000"});
        stockData.add(new String[]{"2023-01-03", "100", "120", "100", "100", "1500"});

        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNotNull(result);
        assertEquals("2023-01-01", result.getBuyDate());
        assertEquals(120.0, result.getBuyPrice());
        assertEquals("2023-01-02", result.getSellDate());
        assertEquals(120.0, result.getSellPrice());
        assertEquals(0.0, result.getProfit());
    }

    @Test
    public void testMaxProfitWithHighVolatility() {
        List<String[]> stockData = new ArrayList<>();
        stockData.add(new String[]{"Date", "Open", "High", "Low", "Close", "Volume"});
        stockData.add(new String[]{"2023-01-01", "100", "200", "50", "140", "1000"});
        stockData.add(new String[]{"2023-01-02", "150", "180", "130", "160", "2000"});
        stockData.add(new String[]{"2023-01-03", "90", "170", "60", "160", "1500"});
        stockData.add(new String[]{"2023-01-04", "160", "200", "150", "190", "1200"});

        StockPrice stockPrice = createStockPriceWithData(stockData);

        ProfitRO result = stockPrice.maxProfit("AAPL", 2023);

        assertNotNull(result);
        assertEquals("2023-01-01", result.getBuyDate());
        assertEquals(50.0, result.getBuyPrice());
        assertEquals("2023-01-04", result.getSellDate());
        assertEquals(200.0, result.getSellPrice());
        assertEquals(150.0, result.getProfit());
    }
}
