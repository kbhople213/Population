/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Krishay Bhople
 *	@since	January 10, 2022
 */
public class City implements Comparable<City> {
    private String name;        // Name of location
    private String state;       // Location's state
    private String designation; // Type of location
    private int population;     // Population of location
    
    /**
     * Constructor for City objects.
     * 
     * @param cityIn        Location's name
     * @param stateIn       Location's state
     * @param cityTypeIn    Location type
     * @param popIn         Location's population
     */
    public City(String cityIn, String stateIn, String cityTypeIn, int popIn) {
        name = cityIn;
        state = stateIn;
        designation = cityTypeIn;
        population = popIn;
    }
    
    /**	
     * Compare two cities populations
     * - If populations are different, then returns (this.population - other.population)
     * - else if states are different, then returns (this.state - other.state)
     * - else returns (this.name - other.name)
     * 
     * @param other        the other City to compare
     * @return             the following value:
     */
    public int compareTo(City other) {
        if (this.population != other.population)
            return this.population - other.population;
        else if (!this.state.equals(other.state))
            return this.state.compareTo(other.state);
        else
            return this.name.compareTo(other.name);
    }
    
    /**
     * Equal method for city name and state name
     * @param other     The other City to compare
     * @return          True if city name and state name equal; false otherwise
     */
    public boolean equals(City other) {
        return this.compareTo(other) == 0;
    }
    
    /**
     * Finds City name.
     * @return  The location's name
     */
    public String getName() {
        return name;
    }

    /**
     * Finds City state.
     * @return  The location's state
     */
    public String getState() {
        return state;
    }

    /**
     * Finds City designation.
     * @return  The location's type
     */
    public String getType() {
        return designation;
    }

    /**
     * Finds City population.
     * @return  The location's population
     */
    public int getPopulation() {
        return population;
    }
    
    /** toString() method */
    @Override
    public String toString() {
        return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
                        population);
    }
}
