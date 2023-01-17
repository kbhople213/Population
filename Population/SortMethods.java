import java.util.List;

/**
 * SortMethods - Sorts an array of Integers in ascending order.
 *
 * @author Krishay Bhople
 * @since  December 5, 2022	
 */
public class SortMethods {
    /**
     * Swaps two City objects in array arr
     * 
     * @param cities  array of City objects
     * @param x     index of first object to swap
     * @param y     index of second object to swap
     */
    private void swap(List<City> cities, int x, int y) {
        City tempC = cities.get(x);
        cities.set(x, cities.get(y));
        cities.set(y, tempC);
    }

    /**
     * Bubble Sort algorithm - in ascending order
     * 
     * @param cities    array of City objects to sort
     */
    public void bubbleSort(List<City> cities) {
        for (int outer = cities.size() - 1; outer > 0; outer--) {
            for (int inner = 0; inner < outer; inner++) {
                if (cities.get(inner).compareTo(cities.get(inner + 1)) > 0)
                    swap(cities, inner, inner + 1);
            }
        }
    }
    
    /**
     * Selection Sort algorithm - in ascending order
     * 
     * @param cities    array of City objects to sort
     */
    public void leastPopulationSelectionSort(List<City> cities) {
        int indexToSwitch = 0;
        int loopCounter = 0;
        
        for (int j = 0; j < cities.size(); j++) {
            for (int i = 1; i < cities.size() - loopCounter; i++) {
                if (cities.get(indexToSwitch).compareTo(cities.get(i)) < 0)
                    indexToSwitch = i;
            }
            
            swap(cities, indexToSwitch, cities.size() - loopCounter - 1);
            indexToSwitch = 0;
            
            loopCounter++;
        }
    }

    /**
     * Insertion Sort algorithm - names in alphabetical order
     * 
     * @param cities        array of City objects to sort
     */
    public void alphabeticalInsertionSort(List<City> cities) {
        for (int n = 1; n < cities.size(); n++) {
            City temp = cities.get(n);

            int i = n;

            while (i > 0 && temp.getName().compareTo(cities.get(i - 1).getName()) < 0) {
                cities.set(i, cities.get(i - 1));
                i--;
            }

            cities.set(i, temp);
        }
    }

    /**
     * Merge Sort algorithm - population in descending order & names in descending
     *                        order
     * 
     * @param cities        ArrayList of City objects to sort
     * @param start         Start of sorting point
     * @param end           End of sorting point
     * @param sortingPop    True if sorting by population. False if not
     */
    public void mergeSortMain(List<City> cities, int start, int end, boolean sortingPopulation)
    {
        if (start < end) {
            int middle = start + (end - start) / 2;
            mergeSortMain(cities, start, middle, sortingPopulation);
            mergeSortMain(cities, middle + 1, end, sortingPopulation);
            mergeHelper(cities, start, middle, end, sortingPopulation);
        }
    }
    
    /**
     * Helper method for most population and names merge sort (method above). This
     * method will merge the arrays and sort them.
     * 
     * @param cities        ArrayList of City objects to sort
     * @param start         Start of merging point
     * @param middle        Middle of merging point
     * @param end           End of merging point
     * @param sortingPop    True if if sorting by population. False if sorting by
     *                      reverse alphabetical order
     */
    private void mergeHelper(List<City> cities, int start, int middle, int end, boolean sortingPop)
    {
        int size1 = middle - start + 1;
        int size2 = end - middle;
 
        // Temporary arrays to store the left and right merge
        City left[] = new City[size1];
        City right[] = new City[size2];
 
        // left[] and right[] arrays are initialized
        for (int i = 0; i < size1; i++)
            left[i] = cities.get(start + i);
        for (int j = 0; j < size2; j++)
            right[j] = cities.get(middle + 1 + j);
 
        // Now the arrays will be merged
        // Initial indexes of first and second subarrays
        int firstIndex = 0, secondIndex = 0;
 
        int mainIndex = start;
        while (firstIndex < size1 && secondIndex < size2) {
            // Sorting by population descending
            if (sortingPop) {
                if (left[firstIndex].getPopulation() >= right[secondIndex].getPopulation()) {
                    cities.set(mainIndex, left[firstIndex]);
                    firstIndex++;
                } else {
                    cities.set(mainIndex, right[secondIndex]);
                    secondIndex++;
                }
            } else {
                // Sorting by reverse alphabetical order
                if (left[firstIndex].getName().compareTo(right[secondIndex].getName()) > 0 ) {
                    cities.set(mainIndex, left[firstIndex]);
                    firstIndex++;
                } else {
                    cities.set(mainIndex, right[secondIndex]);
                    secondIndex++;
                }
            }
            mainIndex++;
        }
 
        while (firstIndex < size1) {
            cities.set(mainIndex, left[firstIndex]);
            firstIndex++;
            mainIndex++;
        }
 
        while (secondIndex < size2) {
            cities.set(mainIndex, right[secondIndex]);
            secondIndex++;
            mainIndex++;
        }
    }
}
