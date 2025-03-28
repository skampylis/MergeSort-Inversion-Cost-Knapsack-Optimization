// Full name:Sotirios Loykas Kampylis
// AEM:3805
// academic email:skampylis@csd.auth.gr

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class Knapsack is the main and only class of the program that contains three void methods (main, unboundedCase and
 * boundedCase) that are necessary to solve the problem with dynamic programming.The main method is creating an Arraylist
 * with the characteristics of the container (numOfType, boxCapacity, availableBoxes).First, it tries to read the file.
 * If the file does not exist, it calls an exception with a specific message.If the file exists, it finds the file from
 * the current path (args[0]) and it reads the file.Then, it reads the numbers in the file with the correct order (using
 * the nextInt() method of java).Then, it closes the file and calls the two methods (unboundedCase and boundedCase)
 * with the required parameters.The unboundedCase method is the answer for the unlimited boxes and the boundedCase method
 * is the answer for the limited number of boxes.The array totalSpace is the minimum solution for both methods (and from
 * that array we know if the problem has/hasn't a solution.Then, it initializes every cell in the array with a really big
 * number (1000000000) and then it checks every possible solution for the problem (holding the minimum solution of the
 * problem).With the same logic the boundedCase method works, only that in this method it checks the available boxes
 * (from the variable availableBoxes) so the solution been created from the minimum available boxes of the current.
 */
public class Knapsack
{
    public static void main(String[] args) {
        int freeSpace = 0;
        int boxTypes = 0;
        ArrayList<Integer> numOfType = new ArrayList<>();
        ArrayList<Integer> boxCapacity = new ArrayList<>();
        ArrayList<Integer> availableBoxes = new ArrayList<>();
        try {
            File in = new File(args[0]);
            Scanner stream = new Scanner(in);
            freeSpace = stream.nextInt();
            boxTypes = stream.nextInt();
            while (stream.hasNextInt()) {
                numOfType.add(stream.nextInt());
                boxCapacity.add(stream.nextInt());
                availableBoxes.add(stream.nextInt());
            }
            stream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File does not found!");
            e.printStackTrace();
        }
        unboundedCase(boxCapacity, boxTypes, freeSpace);
        boundedCase(boxCapacity, availableBoxes, boxTypes, freeSpace);
    }

    static void unboundedCase(ArrayList<Integer> boxCapacity, int boxTypes, int freeSpace) {
        int[] totalSpace = new int[freeSpace + 1];
        totalSpace[0] = 0;
        int i = 1;
        while (i <= freeSpace) {
            totalSpace[i] = 1000000000;
            int j = 0;
            while (j < boxTypes) {
                if (boxCapacity.get(j) <= i) {
                    int remains = totalSpace[i - boxCapacity.get(j)];
                    if (remains + 1 < totalSpace[i] && remains != 1000000000) {
                        totalSpace[i] = remains + 1;
                    }
                }
                j++;
            }
            i++;
        }
        if (totalSpace[freeSpace] == 1000000000) {
            System.out.println("There is no solution for the unbounded case.");
        }
        else {
            System.out.println("The minimum number of boxes for the unbounded case is: " + totalSpace[freeSpace]);
        }
    }

    static void boundedCase(ArrayList<Integer> boxCapacity, ArrayList<Integer> availableBoxes, int boxTypes, int freeSpace) {
        int[] totalSpace = new int[freeSpace + 1];
        totalSpace[0] = 0;
        int i, j;
        i = 1;
        while (i <= freeSpace) {
            totalSpace[i] = 1000000000 - 1;
            i++;
        }
        i = 0;
        while (i < boxTypes) {
            while (availableBoxes.get(i) > 0) {
                j = freeSpace;
                while (j >= 0) {
                    int coveredSpace = j + boxCapacity.get(i);
                    if (coveredSpace <= freeSpace) {
                        if (totalSpace[coveredSpace] > totalSpace[j] + 1) {
                            totalSpace[coveredSpace] = totalSpace[j] + 1;
                        }
                    }
                    --j;
                }
                availableBoxes.set(i, availableBoxes.get(i) - 1);
            }
            ++i;
        }
        if(totalSpace[freeSpace] == 1000000000 - 1) {
            System.out.println("There is no solution for the bounded case.");
        }
        else {
            System.out.println("The minimum number of boxes for the bounded case is: " + totalSpace[freeSpace]);
        }
    }
}
