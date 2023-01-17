import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Population - Provides 6 different ways to sort a list of cities imported from
 *              a file. There are three different sorts used: selection sort,
 *              insertion sort, and merge sort. Each method is contained in the
 *              SortMethods class.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Krishay Bhople
 *	@since	January 10, 2022
 */
public class Population {
    private List<City> cities; 								// List of cities
    private final String DATA_FILE = "usPopData2017.txt"; 	// US data file

    /**
     * Constructs the Population object and initializes cities ArrayList.
     */
    public Population() {
        cities = new ArrayList<City>();
    }

    /**
     * Runs the program. First the cities ArrayList is loaded with the cities
     * from the file, and the user input is taken in. Each time the user does
     * not enter a 9, the program runs. Then, depending on what the user inputted,
     * the program runs 1 of 6 different choices. Finally, the results from each
     * choice are printed
     */
    public void run() {
        loadCities();
        printIntroduction();
        System.out.println(cities.size() + " cities in database\n");
        int selection = -1;
        SortMethods sorter = new SortMethods();

        // Run the program
        while (selection != 9) {
            printMenu();
            selection = Prompt.getInt("Enter selection", 1, 9);
            System.out.println();
            long startMillisec = System.currentTimeMillis();
            long endMillisec = 0;
            List<City> stateCities = new ArrayList<City>();
            List<City> similarCities = new ArrayList<City>();

            // Decide which sort to use
            switch (selection) {
                // Using selection sort for population small to large
                case 1:
                    sorter.leastPopulationSelectionSort(cities);
                    endMillisec = System.currentTimeMillis();
                    System.out.println("Fifty least populous cities");
                    break;
                
                // Using merge sort for population large to small
                case 2:
                    sorter.mergeSortMain(cities, 0, cities.size() - 1, true);
                    endMillisec = System.currentTimeMillis();
                    System.out.println("Fifty most populous cities");
                    break;
                
                // Using insertion sort for city name in alphabetical order
                case 3:
                    sorter.alphabeticalInsertionSort(cities);
                    endMillisec = System.currentTimeMillis();
                    System.out.println("Fifty cities sorted by name");
                    break;
                
                // Using merge sort for city name in reverse alphebetical order
                case 4:
                    sorter.mergeSortMain(cities, 0, cities.size() - 1, false);
                    endMillisec = System.currentTimeMillis();
                    System.out.println("Fifty cities sorted by names descending");
                    break;
                
                // Using merge sort for a specific state by population
                case 5:
                    String state = getValidState();
                    stateCities = findAllCitiesFromState(state);
                    System.out.println("Fifty most populous cities in " + state);
                    startMillisec = System.currentTimeMillis();
                    sorter.mergeSortMain(stateCities, 0, stateCities.size() - 1, true);
                    endMillisec = System.currentTimeMillis();
                    break;
                
                // Using merge sort for a specific city by population
                case 6:
                    String cityName = getValidCity();
                    similarCities = findSameNameCities(cityName);
                    System.out.println("City " + cityName + " by population");
                    startMillisec = System.currentTimeMillis();
                    sorter.mergeSortMain(similarCities, 0, similarCities.size() - 1, true);
                    endMillisec = System.currentTimeMillis();
                    break;
            
                default:
                    break;
            }

            // Print cities if 1 <= user input <= 6
            if (selection >= 1 && selection <= 6) {
                System.out.printf("%4s%-22s %-22s %-12s %12s%n", "", "State", "City", "Type", "Population");
                int numToIncrement = 50;

                if (selection <= 4 && cities.size() < 50)
                    numToIncrement = cities.size();
                else if (selection == 5 && stateCities.size() < 50)
                    numToIncrement = stateCities.size();
                else if (selection == 6 && similarCities.size() < 50)
                    numToIncrement = similarCities.size();

                for (int i = 0; i < numToIncrement; i++)
                    if (selection <= 4)
                        System.out.printf("%3s %68s%n", (i + 1) + ":", cities.get(i));
                    else if (selection == 5)
                        System.out.printf("%3s %68s%n", (i + 1) + ":", stateCities.get(i));
                    else
                        System.out.printf("%3s %68s%n", (i + 1) + ":", similarCities.get(i));
                
                if (selection >= 1 && selection <= 4)
                    System.out.println("\nElapsed time: " + (endMillisec - startMillisec) + " milliseconds\n");
                else
                    System.out.println("\n");
            }
        }
        System.out.println("Thanks for using Population!");
    }

    /**
     * Finds the state that the user would like to get information for. It always
     * guarantees proper input, since it runs until the user has entered a valid
     * answer.
     * 
     * @return  A valid state the user entered
     */
    public String getValidState() {
        boolean hasValidInput = false;
        String state = "";
        while (!hasValidInput) {
            state = Prompt.getString("Enter state name (ie. Alabama)");

            for (int i = 0; i < cities.size(); i++)
                if (cities.get(i).getState().equals(state))
                    hasValidInput = true;
            
            if (!hasValidInput)
                System.out.println("ERROR: " + state + " is not valid");
        }

        System.out.println();
        return state;
    }

    /**
     * Finds the city that the user would like to get information for. It always
     * guarantees proper input, since it runs until the user has entered a valid
     * answer.
     * 
     * @return  A valid city the user entered
     */
    public String getValidCity() {
        boolean hasValidInput = false;
        String city = "";
        while (!hasValidInput) {
            city = Prompt.getString("Enter city name");

            for (int i = 0; i < cities.size(); i++)
                if (cities.get(i).getName().equals(city))
                    hasValidInput = true;
            
            if (!hasValidInput)
                System.out.println("ERROR: " + city + " is not valid");
        }

        System.out.println();
        return city;
    }

    /**
     * Creates a new list of cities matching a given state. Loops through the
     * cities ArrayList and creates a new ArrayList that has all the cities from
     * the given state. This new list is sorted later.
     * 
     * @param state A state to find cities from
     * @return      An ArrayList of all the cities from the state
     */
    public List<City> findAllCitiesFromState(String state) {
        List<City> newCities = new ArrayList<City>();
        for (int i = 0; i < cities.size(); i++)
            if (cities.get(i).getState().equals(state))
                newCities.add(cities.get(i));
        
        return newCities;
    }

    /**
     * Creates a new list of cities that all have the same name as the given name.
     * Loops through the cities ArrayList and creates a new ArrayList that has
     * all the cities having the same name.
     * 
     * @param cityName  A given name to match the cities to
     * @return          An ArrayList of all cities having the same name
     */
    public List<City> findSameNameCities(String cityName) {
        List<City> newCities = new ArrayList<City>();
        for (int i = 0; i < cities.size(); i++)
            if (cities.get(i).getName().equals(cityName))
                newCities.add(cities.get(i));
        
        return newCities;
    }

    /**
     * Loads the cities from the DATA_FILE. This is done with the Scanner.next()
     * method. The first time it is run, it stores the state name. The next time,
     * it stores the first part of the city name. It then checks whether the city
     * name has ended, which if it has not, it continues adding to the city name.
     * If the name has ended, it moves onto storing the designation. Finally, the
     * population is stored, the city is created, and the city is added to the
     * cities ArrayList.
     */
    public void loadCities() {
        Scanner fileReader = FileUtils.openToRead(DATA_FILE);
        fileReader.useDelimiter("[\t\n]");
        while (fileReader.hasNext()) {
            String state, name, type, pop;
            state = name = type = pop = "";

            state = fileReader.next();
            String word = fileReader.next();
            while (!word.equals("city") &&
                !word.equals("town") &&
                !word.equals("township") &&
                !word.equals("village") &&
                !word.equals("municipality") &&
                !word.equals("government")) {
                    name += word;
                    word = fileReader.next();
            }

            type = word;
            pop = fileReader.next();

            City myCity = new City(name, state, type, Integer.parseInt(pop));
            cities.add(myCity);
        }
    }
    
    /**
     * Prints the introduction to Population.
     */
    public void printIntroduction() {
        System.out.println("   ___                  _       _   _");
        System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
        System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
        System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
        System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
        System.out.println("           |_|");
        System.out.println();
    }
    
    /**
     * Prints out the choices for population sorting.
     */
    public void printMenu() {
        System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
        System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
        System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
        System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
        System.out.println("5. Fifty most populous cities in named state");
        System.out.println("6. All cities matching a name sorted by population");
        System.out.println("9. Quit");
    }
    
    /**
     * Main method.
     * @param args  Terminal arguments (not necessary for this program)
     */
    public static void main(String[] args) {
        Population pop = new Population();
        pop.run();
    }
}
