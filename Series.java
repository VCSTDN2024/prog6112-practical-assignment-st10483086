package za.ac.varsity;


public class Series {
    // Private variales for encapsulation
    private String seriesId;
    private String seriesName;
    private int ageRestriction;
    private int numberOfEpisodes;
    
    // Default constructor
    public Series() {
    }
    
    // Parameterized constructor
    public Series(String seriesId, String seriesName, int ageRestriction, int numberOfEpisodes) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.ageRestriction = ageRestriction;
        this.numberOfEpisodes = numberOfEpisodes;
    }
    
    // Getter and setter methods with validation
    public String getSeriesId() {
        return seriesId;
    }
    
    public void setSeriesId(String seriesId) {
        if (seriesId == null || seriesId.trim().isEmpty()) {
            throw new IllegalArgumentException("Series ID cannot be null or empty");
        }
        this.seriesId = seriesId;
    }
    
    public String getSeriesName() {
        return seriesName;
    }
    
    public void setSeriesName(String seriesName) {
        if (seriesName == null || seriesName.trim().isEmpty()) {
            throw new IllegalArgumentException("Series name cannot be null or empty");
        }
        this.seriesName = seriesName;
    }
    
    public int getAgeRestriction() {
        return ageRestriction;
    }
    
    public void setAgeRestriction(int ageRestriction) {
        if (ageRestriction < 2 || ageRestriction > 18) {
            throw new IllegalArgumentException("Age restriction must be between 2 and 18");
        }
        this.ageRestriction = ageRestriction;
    }
    
    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }
    
    public void setNumberOfEpisodes(int numberOfEpisodes) {
        if (numberOfEpisodes < 1) {
            throw new IllegalArgumentException("Number of episodes must be at least 1");
        }
        this.numberOfEpisodes = numberOfEpisodes;
    }
    
    @Override
    public String toString() {
        return "Series{" +
                "seriesId='" + seriesId + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", ageRestriction=" + ageRestriction +
                ", numberOfEpisodes=" + numberOfEpisodes +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Series series = (Series) obj;
        return seriesId.equals(series.seriesId);
    }
}