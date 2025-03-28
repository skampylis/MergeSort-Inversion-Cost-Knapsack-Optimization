// Full name:Sotirios Loykas Kampylis
// AEM:3805
// academic email:skampylis@csd.auth.gr

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

/**
 * The class Mergesort is the main and only class of the program that first creates an Arraylist with the numbers of the
 * file.If the file does not exist, it calls an exception with a specific message.If the file exists, it finds the file
 * from the current path (args[0]) and it reads the file.Then, it reads one by one the integers in the file (ignoring the
 * white spaces according to the methods hasNextInt() and nextInt()) adding them to the Arraylist.Later, it transforms
 * the Arraylist to an array of integers (for convenience to do the mergeSort).Then the next 2 methods (mergeSort and
 * merge) do the necessarily inversions retrospectively for the Merge and the sorting of the array.So we have a perfect
 * total cost from all the methods working perfectly together.The long result is for the big numbers (total cost) and
 * the algorithm is working in O(n * log(n)) complexity time as the prototype mergesort does.
 */
public class MergeSort
{
    public static void main(String[] args) {
        Integer[] array;
        try {
            File in = new File(args[0]);
            Scanner stream = new Scanner(in);
            ArrayList<Integer> numbers = new ArrayList<>();
            while (stream.hasNextInt()) {
                numbers.add(stream.nextInt());
            }
            stream.close();
            array = numbers.toArray(new Integer[0]);
            System.out.println("Total cost: " + mergeSort(array));
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not found!");
            e.printStackTrace();
        }
    }

    public static long mergeSort(Integer[] a) {
        if (a.length == 1) {
            return 0;
        }
        long result1, result2, result3;
        Integer[] l = Arrays.copyOfRange(a, 0, a.length/2);
        Integer[] r = Arrays.copyOfRange(a, a.length/2, a.length);
        result1 = mergeSort(l);
        result2 = mergeSort(r);
        result3 = merge(l, r);
        return result1 + result2 + result3;
    }

    public static long merge(Integer[] l, Integer[] r) {
        Arrays.sort(l);
        Arrays.sort(r);
        long result = 0;
        int i=0, j=0;
        while(i < l.length) {
            if (l[i] <= r[j]) {
                i++;
            } else {
                boolean flag = false;
                int k = i;
                while(k < l.length && !flag) {
                    if(l[k] == r[j] + 1) {
                        result += 2;
                        k++;
                    }
                    else {
                        result += (l.length - k) * 3;
                        flag = true;
                    }
                }
                j++;
                if(j == r.length)
                    break;
            }
        }
        return result;
    }
}
