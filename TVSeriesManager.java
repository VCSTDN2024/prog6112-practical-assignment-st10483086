package za.ac.varsity;

import java.util.List;
import java.util.Scanner;

//*TVSeriesManager Main Application
//*This is where we can run our application and where we tested the application, we can also run it on the other .java.

public class TVSeriesManager {
    private static SeriesDAO seriesDAO = new SeriesDAO();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        showWelcomeScreen();
    }
    
    private static void showWelcomeScreen() {
        ValidationUtils.clearConsole();
        System.out.println("LATEST SERIES - 2025");
        System.out.println("**********************************************");
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        
        String input = scanner.nextLine();
        if ("1".equals(input)) {
            showMainMenu();
        } else {
            System.out.println("Thank you for using the TV Series Manager. Goodbye!");
            scanner.close();
        }
    }
    
    private static void showMainMenu() {
        int choice;
        do {
            ValidationUtils.clearConsole();
            System.out.println("LATEST SERIES - 2025");
            System.out.println("**********************************************");
            System.out.println("Please select one of the following menu items:");
            System.out.println("(1) Capture a new series.");
            System.out.println("(2) Search for a series.");
            System.out.println("(3) Update series.");
            System.out.println("(4) Delete a series.");
            System.out.println("(5) Print series report.");
            System.out.println("(6) Exit Application.");
            System.out.print("\nEnter your choice (1-6): ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1-6.");
                scanner.next();
                System.out.print("Enter your choice (1-6): ");
            }
            
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    captureSeries();
                    break;
                case 2:
                    searchSeries();
                    break;
                case 3:
                    updateSeries();
                    break;
                case 4:
                    deleteSeries();
                    break;
                case 5:
                    printSeriesReport();
                    break;
                case 6:
                    exitApplication();
                    break;
                default:
                    System.out.println("Invalid choice! Please select a number between 1-6.");
                    pressEnterToContinue();
            }
        } while (choice != 6);
    }
    
    private static void captureSeries() {
        ValidationUtils.clearConsole();
        System.out.println("CAPTURE A NEW SERIES");
        System.out.println("********************");
        
        Series series = new Series();
        
        // Get series ID
        System.out.print("Enter the series id: ");
        String seriesId = scanner.nextLine();
        series.setSeriesId(seriesId);
        
        // Check if series already exists
        if (seriesDAO.seriesExists(seriesId)) {
            System.out.println("Series with ID " + seriesId + " already exists!");
            pressEnterToContinue();
            return;
        }
        
        // Get series name
        System.out.print("Enter the series name: ");
        String seriesName = scanner.nextLine();
        series.setSeriesName(seriesName);
        
        // Get age restriction with validation
        int ageRestriction = 0;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter the series age restriction (2-18): ");
            String ageInput = scanner.nextLine();
            
            if (ValidationUtils.isNumeric(ageInput)) {
                ageRestriction = Integer.parseInt(ageInput);
                if (ValidationUtils.isValidAgeRestriction(ageRestriction)) {
                    series.setAgeRestriction(ageRestriction);
                    validAge = true;
                } else {
                    System.out.println("Invalid age! Must be between 2 and 18.");
                }
            } else {
                System.out.println("Please enter a valid number for age restriction.");
            }
        }
        
        // Get number of episodes
        int numberOfEpisodes = 0;
        boolean validEpisodes = false;
        while (!validEpisodes) {
            System.out.print("Enter the number of episodes for " + seriesName + ": ");
            String episodesInput = scanner.nextLine();
            
            if (ValidationUtils.isNumeric(episodesInput)) {
                numberOfEpisodes = Integer.parseInt(episodesInput);
                if (ValidationUtils.isValidNumberOfEpisodes(numberOfEpisodes)) {
                    series.setNumberOfEpisodes(numberOfEpisodes);
                    validEpisodes = true;
                } else {
                    System.out.println("Must have at least 1 episode.");
                }
            } else {
                System.out.println("Please enter a valid number for episodes.");
            }
        }
        
        // Save the series
        if (seriesDAO.captureSeries(series)) {
            System.out.println("\nSeries '" + seriesName + "' has been added successfully!");
        } else {
            System.out.println("\nFailed to add series. Please try again.");
        }
        
        pressEnterToContinue();
    }
    
    private static void searchSeries() {
        ValidationUtils.clearConsole();
        System.out.println("SEARCH FOR A SERIES");
        System.out.println("*******************");
        
        System.out.print("Enter the series id to search: ");
        String searchId = scanner.nextLine();
        
        Series foundSeries = seriesDAO.searchSeries(searchId);
        
        if (foundSeries != null) {
            ValidationUtils.printSeparator();
            System.out.println("SERIES FOUND:");
            System.out.println("SERIES ID: " + foundSeries.getSeriesId());
            System.out.println("SERIES NAME: " + foundSeries.getSeriesName());
            System.out.println("SERIES AGE RESTRICTION: " + foundSeries.getAgeRestriction());
            System.out.println("SERIES NUMBER OF EPISODES: " + foundSeries.getNumberOfEpisodes());
            ValidationUtils.printSeparator();
        } else {
            ValidationUtils.printSeparator();
            System.out.println("Series with Series Id: " + searchId + " was not found!");
            ValidationUtils.printSeparator();
        }
        
        pressEnterToContinue();
    }
    
    private static void updateSeries() {
        ValidationUtils.clearConsole();
        System.out.println("UPDATE SERIES");
        System.out.println("*************");
        
        System.out.print("Enter the series id to update: ");
        String updateId = scanner.nextLine();
        
        Series existingSeries = seriesDAO.searchSeries(updateId);
        if (existingSeries == null) {
            System.out.println("Series with ID " + updateId + " was not found!");
            pressEnterToContinue();
            return;
        }
        
        Series updatedSeries = new Series();
        updatedSeries.setSeriesId(updateId);
        
        // Update series name
        System.out.println("Current name: " + existingSeries.getSeriesName());
        System.out.print("Enter new series name (press Enter to keep current): ");
        String newName = scanner.nextLine();
        if (newName.trim().isEmpty()) {
            updatedSeries.setSeriesName(existingSeries.getSeriesName());
        } else {
            updatedSeries.setSeriesName(newName);
        }
        
        // Update age restriction
        System.out.println("Current age restriction: " + existingSeries.getAgeRestriction());
        int newAge = -1;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter new age restriction (2-18, or -1 to keep current): ");
            String ageInput = scanner.nextLine();
            
            if (ageInput.trim().isEmpty() || ageInput.equals("-1")) {
                updatedSeries.setAgeRestriction(existingSeries.getAgeRestriction());
                validAge = true;
            } else if (ValidationUtils.isNumeric(ageInput)) {
                newAge = Integer.parseInt(ageInput);
                if (ValidationUtils.isValidAgeRestriction(newAge)) {
                    updatedSeries.setAgeRestriction(newAge);
                    validAge = true;
                } else {
                    System.out.println("Invalid age! Must be between 2 and 18.");
                }
            } else {
                System.out.println("Please enter a valid number.");
            }
        }
        
        // Update number of episodes
        System.out.println("Current number of episodes: " + existingSeries.getNumberOfEpisodes());
        int newEpisodes = -1;
        boolean validEpisodes = false;
        while (!validEpisodes) {
            System.out.print("Enter new number of episodes (>=1, or -1 to keep current): ");
            String episodesInput = scanner.nextLine();
            
            if (episodesInput.trim().isEmpty() || episodesInput.equals("-1")) {
                updatedSeries.setNumberOfEpisodes(existingSeries.getNumberOfEpisodes());
                validEpisodes = true;
            } else if (ValidationUtils.isNumeric(episodesInput)) {
                newEpisodes = Integer.parseInt(episodesInput);
                if (ValidationUtils.isValidNumberOfEpisodes(newEpisodes)) {
                    updatedSeries.setNumberOfEpisodes(newEpisodes);
                    validEpisodes = true;
                } else {
                    System.out.println("Must have at least 1 episode.");
                }
            } else {
                System.out.println("Please enter a valid number.");
            }
        }
        
        if (seriesDAO.updateSeries(updateId, updatedSeries)) {
            System.out.println("\nSeries updated successfully!");
        } else {
            System.out.println("\nFailed to update series. Please try again.");
        }
        
        pressEnterToContinue();
    }
    
    private static void deleteSeries() {
        ValidationUtils.clearConsole();
        System.out.println("DELETE A SERIES");
        System.out.println("***************");
        
        System.out.print("Enter the series id to delete: ");
        String deleteId = scanner.nextLine();
        
        Series foundSeries = seriesDAO.searchSeries(deleteId);
        if (foundSeries == null) {
            System.out.println("Series with ID " + deleteId + " was not found!");
            pressEnterToContinue();
            return;
        }
        
        System.out.println("\nSeries found:");
        System.out.println("Name: " + foundSeries.getSeriesName());
        System.out.println("Age Restriction: " + foundSeries.getAgeRestriction());
        System.out.println("Episodes: " + foundSeries.getNumberOfEpisodes());
        
        System.out.print("\nAre you sure you want to delete this series? (yes/no): ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            if (seriesDAO.deleteSeries(deleteId)) {
                System.out.println("Series with Series Id: " + deleteId + " WAS deleted!");
            } else {
                System.out.println("Failed to delete series. Please try again.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
        
        pressEnterToContinue();
    }
    
    private static void printSeriesReport() {
        ValidationUtils.clearConsole();
        System.out.println("SERIES REPORT - 2025");
        System.out.println("********************");
        
        List<Series> allSeries = seriesDAO.getAllSeries();
        
        if (allSeries.isEmpty()) {
            System.out.println("No series available in the system.");
            pressEnterToContinue();
            return;
        }
        
        System.out.println("Total series in system: " + allSeries.size());
        ValidationUtils.printSeparator();
        
        for (int i = 0; i < allSeries.size(); i++) {
            Series series = allSeries.get(i);
            System.out.println("Series " + (i + 1));
            System.out.println("SERIES ID: " + series.getSeriesId());
            System.out.println("SERIES NAME: " + series.getSeriesName());
            System.out.println("SERIES AGE RESTRICTION: " + series.getAgeRestriction());
            System.out.println("NUMBER OF EPISODES: " + series.getNumberOfEpisodes());
            System.out.println();
        }
        
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String input = scanner.nextLine();
        if ("1".equals(input)) {
            showMainMenu();
        } else {
            exitApplication();
        }
    }
    
    private static void exitApplication() {
        System.out.println("Thank you for using the TV Series Manager. Goodbye!");
        scanner.close();
        System.exit(0);
    }
    
    private static void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}