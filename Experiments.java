/* CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 *  On my honor, Advaith Binu, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ab89658
 *  email address: ab89658@eid.utexas.edu
 *  TA name: Uzair
 *  Number of slip days I am using: 0
 */

import java.util.Random;
import java.util.TreeSet;

/**
 * Driver that runs the timing experiments described in questions.txt for the
 * BinarySearchTree class and the java.util.TreeSet class.
 * <br>
 * The program measures insertion time, tree height, and tree size for both
 * randomly generated integers and integers inserted in ascending sorted order,
 * averaging each configuration over several trials.
 * @author Advaith Binu
 */
public class Experiments {

    private static final int TRIALS = 10;
    private static final int RANDOM_START_N = 1000;
    private static final int RANDOM_MAX_N = 1_024_000;
    private static final int SORTED_START_N = 1000;
    private static final int SORTED_MAX_N = 64_000;

    private final Random random;

    /**
     * Create an experiment driver with its own random number source.
     * <br>pre: none
     * <br>post: this driver is ready to run all experiments
     */
    public Experiments() {
        random = new Random();
    }

    /**
     * Run every experiment described in questions.txt and print the results.
     * <br>pre: none
     * <br>post: all experiment results are written to standard out
     * @param args command line arguments, ignored
     */
    public static void main(String[] args) {
        Experiments driver = new Experiments();
        driver.runRandomBst();
        driver.runRandomTreeSet();
        driver.runSortedBst();
        driver.runSortedTreeSet();
    }

    /**
     * Run the random integer insertion experiment on BinarySearchTree.
     * <br>pre: none
     * <br>post: average time, height, size, and minimum possible height for
     * each value of N are written to standard out
     */
    public void runRandomBst() {
        System.out.println("=== BinarySearchTree: random Integers ===");
        printRandomHeader();
        int n = RANDOM_START_N;
        while (n <= RANDOM_MAX_N) {
            averageRandomBst(n);
            n *= 2;
        }
        System.out.println();
    }

    /**
     * Run the random integer insertion experiment on TreeSet.
     * <br>pre: none
     * <br>post: average time and size for each value of N are written to
     * standard out
     */
    public void runRandomTreeSet() {
        System.out.println("=== TreeSet: random Integers ===");
        System.out.printf("%12s %18s %14s%n", "N", "avgTime(s)", "avgSize");
        int n = RANDOM_START_N;
        while (n <= RANDOM_MAX_N) {
            averageRandomTreeSet(n);
            n *= 2;
        }
        System.out.println();
    }

    /**
     * Run the sorted integer insertion experiment on BinarySearchTree.
     * <br>pre: none
     * <br>post: average time plus the analytically known height and size for
     * each value of N are written to standard out, followed by predictions for
     * larger values of N
     */
    public void runSortedBst() {
        System.out.println("=== BinarySearchTree: sorted integers ===");
        System.out.printf("%12s %18s %10s %12s%n", "N", "avgTime(s)", "height", "size");
        double lastTime = 0.0;
        int lastN = 0;
        int n = SORTED_START_N;
        while (n <= SORTED_MAX_N) {
            lastTime = averageSortedBst(n);
            lastN = n;
            n *= 2;
        }
        predictSorted(lastN, lastTime);
        System.out.println();
    }

    /**
     * Run the sorted integer insertion experiment on TreeSet.
     * <br>pre: none
     * <br>post: average time for each value of N is written to standard out
     */
    public void runSortedTreeSet() {
        System.out.println("=== TreeSet: sorted integers ===");
        System.out.printf("%12s %18s%n", "N", "avgTime(s)");
        int n = SORTED_START_N;
        while (n <= SORTED_MAX_N) {
            averageSortedTreeSet(n);
            n *= 2;
        }
        System.out.println();
    }

    // Average a random BinarySearchTree run over TRIALS and print the row.
    private void averageRandomBst(int n) {
        double totalTime = 0.0;
        long totalHeight = 0;
        long totalSize = 0;
        for (int i = 0; i < TRIALS; i++) {
            BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
            Stopwatch watch = new Stopwatch();
            watch.start();
            fillRandom(tree, n);
            watch.stop();
            totalTime += watch.time();
            totalHeight += tree.height();
            totalSize += tree.size();
        }
        printRandomRow(n, totalTime, totalHeight, totalSize);
    }

    // Insert n random Integer objects into the given tree.
    private void fillRandom(BinarySearchTree<Integer> tree, int n) {
        for (int i = 0; i < n; i++) {
            tree.add(Integer.valueOf(random.nextInt()));
        }
    }

    // Average a random TreeSet run over TRIALS and print the row.
    private void averageRandomTreeSet(int n) {
        double totalTime = 0.0;
        long totalSize = 0;
        for (int i = 0; i < TRIALS; i++) {
            TreeSet<Integer> set = new TreeSet<Integer>();
            Stopwatch watch = new Stopwatch();
            watch.start();
            fillRandom(set, n);
            watch.stop();
            totalTime += watch.time();
            totalSize += set.size();
        }
        System.out.printf("%12d %18.6f %14.1f%n", n, totalTime / TRIALS,
                (double) totalSize / TRIALS);
    }

    // Insert n random Integer objects into the given set.
    private void fillRandom(TreeSet<Integer> set, int n) {
        for (int i = 0; i < n; i++) {
            set.add(Integer.valueOf(random.nextInt()));
        }
    }

    // Average a sorted BinarySearchTree run over TRIALS and print the row.
    // Returns the average time so the caller can build predictions.
    private double averageSortedBst(int n) {
        double totalTime = 0.0;
        for (int i = 0; i < TRIALS; i++) {
            BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
            Stopwatch watch = new Stopwatch();
            watch.start();
            fillSorted(tree, n);
            watch.stop();
            totalTime += watch.time();
        }
        double avgTime = totalTime / TRIALS;
        // Sorted input degenerates into a linked list, so height = n - 1
        // and size = n without calling the height or size methods.
        System.out.printf("%12d %18.6f %10d %12d%n", n, avgTime, n - 1, n);
        return avgTime;
    }

    // Insert the integers 1..n in ascending order using iterativeAdd to avoid
    // the deep recursion a sorted sequence would otherwise cause.
    private void fillSorted(BinarySearchTree<Integer> tree, int n) {
        for (int i = 1; i <= n; i++) {
            tree.iterativeAdd(Integer.valueOf(i));
        }
    }

    // Average a sorted TreeSet run over TRIALS and print the row.
    private void averageSortedTreeSet(int n) {
        double totalTime = 0.0;
        for (int i = 0; i < TRIALS; i++) {
            TreeSet<Integer> set = new TreeSet<Integer>();
            Stopwatch watch = new Stopwatch();
            watch.start();
            fillSorted(set, n);
            watch.stop();
            totalTime += watch.time();
        }
        System.out.printf("%12d %18.6f%n", n, totalTime / TRIALS);
    }

    // Insert the integers 1..n in ascending order into the given set.
    private void fillSorted(TreeSet<Integer> set, int n) {
        for (int i = 1; i <= n; i++) {
            set.add(Integer.valueOf(i));
        }
    }

    // Print a row for the random experiment including minimum possible height.
    private void printRandomRow(int n, double totalTime, long totalHeight,
            long totalSize) {
        System.out.printf("%12d %18.6f %10.1f %12.1f %16d%n", n,
                totalTime / TRIALS, (double) totalHeight / TRIALS,
                (double) totalSize / TRIALS, minPossibleHeight(n));
    }

    // Print the column header for the random BinarySearchTree experiment.
    private void printRandomHeader() {
        System.out.printf("%12s %18s %10s %12s %16s%n", "N", "avgTime(s)",
                "avgHeight", "avgSize", "minHeight");
    }

    // The minimum possible height of a tree with n unique values is the height
    // of a complete binary tree, which is floor(log base 2 of n).
    private int minPossibleHeight(int n) {
        if (n <= 0) {
            return -1;
        }
        int height = 0;
        int capacity = 1;
        while (capacity * 2 <= n) {
            capacity *= 2;
            height++;
        }
        return height;
    }

    // Predict insertion time for larger sorted inputs. Sorted insertion is
    // O(n^2), so doubling N multiplies the time by about four. The prediction
    // uses the last measured (N, time) pair as the scaling baseline.
    private void predictSorted(int lastN, double lastTime) {
        System.out.println("--- Predicted sorted BST times (O(n^2) scaling) ---");
        int[] targets = {128_000, 256_000, 512_000};
        for (int i = 0; i < targets.length; i++) {
            double ratio = (double) targets[i] / lastN;
            double predicted = lastTime * ratio * ratio;
            System.out.printf("%12d %18.6f (predicted)%n", targets[i], predicted);
        }
    }
}
