package za.ac.varsity;

/**
 * Utility class for input validation
 * Contains methods to validate various inputs
 * 
 * @author Your Name
 * @studentNumber Your Student Number
 * @version 1.0
 */
public class ValidationUtils {
    
    public static boolean isValidSeriesId(String seriesId) {
        return seriesId != null && !seriesId.trim().isEmpty();
    }
    
    public static boolean isValidSeriesName(String seriesName) {
        return seriesName != null && !seriesName.trim().isEmpty();
    }
    
    public static boolean isValidAgeRestriction(int ageRestriction) {
        return ageRestriction >= 2 && ageRestriction <= 18;
    }
    
    public static boolean isValidNumberOfEpisodes(int numberOfEpisodes) {
        return numberOfEpisodes >= 1;
    }
    
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.println("\n\n\n\n\n");
        }
    }
    
    public static void printSeparator() {
        System.out.println("----------------------------------------");
    }
}