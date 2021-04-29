// Name: Andreas Nalmpantis
// AEM: 3699
// email: andreasn@csd.auth.gr


/**
 * The main class which takes in the arguments and calls the cost calculator.
 */
public class MergeSort {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String arr = args[0].trim(); //Removes white spaces from the front and the end

        String[] sArr = arr.split(" "); //Argument is turned into a string array using the str.split() function.

        int[] iArr = new int[sArr.length];
        for (int i=0;i<iArr.length;i++)
            iArr[i] = Integer.parseInt(sArr[i]); //Using the Integer.parseInt(string) function, the corresponding int array is created


        Cost_Calculator a = new Cost_Calculator();
        a.mergeSort(iArr, iArr.length); //The function which will print the necessary cost is called

        System.out.println("Total cost: " + a.getTotalCost());

    }
}

class Cost_Calculator {

    private int specialInversions; //inversions at which left[i] == right[j] + 1
    private int normalInversions;

    public Cost_Calculator() {
        specialInversions = 0;
        normalInversions = 0;
    }


    /**
     * Calculates the total cost of all the inversions. The cost of a special case inversion is 3 and of
     * a normal inversion 3.
     * @return the total cost.
     */
    public int getTotalCost() {
        return normalInversions *3 + specialInversions *2;
    }



    /**
     * The mergeSort function is a recursive function that breaks the array in half repetitively until its size becomes
     * 1, then stores each half in the temporary left and right arrays and calls the merge function in which the
     * sorting happens.
     * The code was taken by this website https://www.baeldung.com/java-merge-sort with a few modifications.
     *
     * @param a    the array which is getting sorted
     * @param size the array's size.
     */
    public void mergeSort(int[] a, int size) {
        if (size < 2) {
            return ;
        }
        int mid = size / 2;
        int[] left = new int[mid];
        int[] right = new int[size - mid];

        System.arraycopy(a, 0, left, 0, mid);
        if (size - mid >= 0)
            System.arraycopy(a, mid, right, 0, size - mid);

        mergeSort(left, mid); //Recursive call for the left half
        mergeSort(right, size - mid); //Recursive call the right half

        merge(a, left, right, mid, size - mid); //Merges the two halves.

    }

    /**
     * The merge function is responsible for merging the two arrays created in the mergeSort function
     * and, in the same time, sorting them.
     * It also calculates the cost that is required via incrementing the cost member variable.
     * The code was taken by this website https://www.baeldung.com/java-merge-sort with a few modifications
     * in order to calculate the cost.
     *
     * @param a         the array which is getting sorted
     * @param left      left array created in the mergeSort function
     * @param right     right array created in the mergeSort function
     * @param sizeLeft  the size of the left array
     * @param sizeRight the size of the right array
     */
    public void merge(int[] a, int[] left, int[] right, int sizeLeft, int sizeRight) {
        int i = 0, j = 0, k = 0;
        while (i < sizeLeft && j < sizeRight) {
            if (left[i] <= right[j]) {
                a[k++] = left[i++];
            }
            else if (left[i] == right[j] + 1) { //special case inversion
                a[k++] = right[j++];
                int t = i;
                while (t<sizeLeft && left[t] == left[i]) { //checks if more elements are equal to left[i] to the right and increments the specialInversions variable.
                    specialInversions++;
                    t++;
                }
                normalInversions+=(sizeLeft-t); //Rest of the elements are bigger than left[i] so they count as normal inversions.
            }
            else {
                a[k++] = right[j++];
                normalInversions += (sizeLeft-i); //all elements to the right of left[i] are bigger than right[j] since the array is sorted
                // so they count as inversions.
            }
        }
        while (i < sizeLeft) {
            a[k++] = left[i++];
        }
        while (j < sizeRight) {
            a[k++] = right[j++];
        }
    }
}
