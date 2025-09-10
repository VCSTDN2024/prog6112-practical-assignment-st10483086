package za.ac.varsity;

import java.util.ArrayList;
import java.util.List;

//*SeriesDataaccessobject where

public class SeriesDAO {
    private List<Series> seriesList;
    
    public SeriesDAO() {
        this.seriesList = new ArrayList<>();
        // Add some sample data for testing
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        seriesList.add(new Series("101", "Extreme Sports", 12, 10));
        seriesList.add(new Series("102", "Bargain Hunters", 10, 10));
        seriesList.add(new Series("103", "Home Cooking", 10, 20));
    }
    
    // Capture new series
    public boolean captureSeries(Series series) {
        if (series == null || seriesExists(series.getSeriesId())) {
            return false;
        }
        seriesList.add(series);
        return true;
    }
    
    // Search for series by ID
    public Series searchSeries(String seriesId) {
        for (Series series : seriesList) {
            if (series.getSeriesId().equals(seriesId)) {
                return series;
            }
        }
        return null;
    }
    
    // Update existing series
    public boolean updateSeries(String seriesId, Series updatedSeries) {
        for (int i = 0; i < seriesList.size(); i++) {
            Series existingSeries = seriesList.get(i);
            if (existingSeries.getSeriesId().equals(seriesId)) {
                seriesList.set(i, updatedSeries);
                return true;
            }
        }
        return false;
    }
    
    // Delete series
    public boolean deleteSeries(String seriesId) {
        return seriesList.removeIf(series -> series.getSeriesId().equals(seriesId));
    }
    
    // Get all series for report
    public List<Series> getAllSeries() {
        return new ArrayList<>(seriesList);
    }
    
    // Check if series exists
    public boolean seriesExists(String seriesId) {
        return searchSeries(seriesId) != null;
    }
    
    // Get series count
    public int getSeriesCount() {
        return seriesList.size();
    }
}