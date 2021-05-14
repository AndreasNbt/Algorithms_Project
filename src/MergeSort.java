// Name: Andreas Nalmpantis
// AEM: 3699
// email: andreasn@csd.auth.gr


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class which takes in the arguments and calls the cost calculator.
 */
public class MergeSort {
    /**
     * The entry point of application.
     * The program takes in a file name as an argument and reads the context of it.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            File file = new File(args[0]);
            Scanner scanner = new Scanner(file);

            ArrayList<Integer> numbers = new ArrayList<>();

            while (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            }


            Cost_Calculator a = new Cost_Calculator();
            a.mergeSort(numbers,numbers.size()); //The function which will print the necessary cost is called

            System.out.println("Total cost: " + a.getTotalCost());
        }
        catch  (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Cost_Calculator {

    private long specialInversions; //inversions at which left[i] == right[j] + 1
    private long normalInversions;

    public Cost_Calculator() {
        specialInversions = 0;
        normalInversions = 0;
    }


    /**
     * Calculates the total cost of all the inversions. The cost of a special case inversion is 3 and of
     * a normal inversion 2.
     * @return the total cost.
     */
    public long  getTotalCost() {
        return normalInversions *3 + specialInversions *2;
    }



    /**
     * The mergeSort function is a recursive function that breaks the arraylist in half repetitively until its size becomes
     * 1, then stores each half in the temporary left and right arrays and calls the merge function in which the
     * sorting happens.
     * The code was taken by this website https://www.baeldung.com/java-merge-sort with  modifications to take an ArrayList
     * instead of an array and also to calculate the number of both normal and special case inversions.
     *
     * @param a    the arraylist which is getting sorted
     * @param size the arraylist's size.
     */
    public void mergeSort(ArrayList<Integer> a,int size) {
        if (size < 2) {
            return ;
        }

        int mid = size / 2;


        ArrayList<Integer> left = new ArrayList<>(a.subList(0, mid));
        ArrayList<Integer> right = new ArrayList<>(a.subList(mid,size));

        mergeSort(left, mid); //Recursive call for the left half
        mergeSort(right, size - mid); //Recursive call the right half

        merge(a, left, right, mid, size - mid); //Merges the two halves.

    }

    /**
     * The merge function is responsible for merging the two arraylists created in the mergeSort function
     * and, in the same time, sorting them.
     * It calculates the number of normal and special case inversions by incrementing the normalInversions and
     * specialInversions member variables.
     ** The code was taken by this website https://www.baeldung.com/java-merge-sort with  modifications to take an ArrayList
     *  instead of an array and also to calculate the number of both normal and special case inversions.
     *
     * @param a         the array which is getting sorted
     * @param left      left array created in the mergeSort function
     * @param right     right array created in the mergeSort function
     * @param sizeLeft  the size of the left array
     * @param sizeRight the size of the right array
     */
    public void merge(ArrayList<Integer> a, ArrayList<Integer> left, ArrayList<Integer> right, int sizeLeft, int sizeRight) {
        int i = 0, j = 0, k = 0;

        while (i < sizeLeft && j < sizeRight) {
            if (left.get(i) <= right.get(j)) {
                a.set(k++,left.get(i++));
            }
            else if (left.get(i) == (right.get(j) + 1)) { //special case inversion
                a.set(k++,right.get(j++));
                int t = i;
                while (t<sizeLeft && left.get(t).equals(left.get(i))) { //checks if more elements are equal to left[i] to the right and increments the specialInversions variable.
                    specialInversions++;
                    t++;
                }
                normalInversions+=(sizeLeft-t); //Rest of the elements are bigger than left[i] so they count as normal inversions.
            }
            else {
                a.set(k++, right.get(j++));
                normalInversions += (sizeLeft-i); //all elements to the right of left[i] are bigger than right[j] since the array is sorted
                // so they count as inversions.
            }
        }

        while (i < sizeLeft) {
            a.set(k++, left.get(i++));
        }
        while (j < sizeRight) {
            a.set(k++,right.get(j++));
        }


    }
}
