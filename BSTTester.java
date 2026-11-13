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
 *  Number of slip days I am using: 1
 */


import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Random;

/**
 * Some test cases for CS314 Binary Search Tree assignment.
 *
 */
public class BSTTester {

    /**
     * The main method runs the tests.
     * @param args Not used
	 * 
	 * 
	 * 
     */
	public static void experiments() {
		System.out.println("N    ave time  min possible height  ave height   ave size");
        for (int nodes = 1000; nodes <= 1024000; nodes *= 2) {
            int minH = minHeight(nodes);
            String avgT = String.format("%.5f", avgTime(nodes));
            String avgH = String.format("%.2f", avgHeight(nodes));
            String avgS = String.format("%.2f", avgSize(nodes));

            System.out.println(nodes + "  " + avgT + "      " + minH + "               " 
					+ avgH + "       " + avgS);
        }
		System.out.println("\n");
		System.out.println("N(Set)      ave time");
        for (int nodes = 1000; nodes <= 1024000; nodes *= 2) {
            String setTime = String.format("%.5f", avgTimeSet(nodes));
            System.out.println(nodes + "      " + setTime);
        }
		System.out.println("\n");
		System.out.println("N\tave time\tpredicted height\tave size");
		for (int nodes = 1000; nodes <= 64000; nodes *= 2) {
			int[] sortedArray = new int[nodes];
            for (int i = 0; i < nodes; i++) {
                sortedArray[i] = i + 1;
            }

            String sortedTime = String.format("%.5f", avgTime(sortedArray));
            int predHeight = nodes - 1;
            int avgSize = nodes;

            System.out.println(nodes + "      " + sortedTime + "      " + predHeight + "               " + avgSize);
        }
		System.out.println("N(Set)          ave time");
        for (int nodes = 1000; nodes <= 1024000; nodes *= 2) {
            int[] sortedArray = new int[nodes];
            for (int i = 0; i < nodes; i++) {
                sortedArray[i] = i + 1;
            }

            String setSortedTime = String.format("%.5f", avgTimeSet(sortedArray));
            System.out.println(nodes + "      " + setSortedTime);
        }
	}
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
		//test 1+2 add()
		int[] inputValues = {45, 12, 78, 3, 25, 60, 90, 1, 15};
        for (int val : inputValues) {
            tree.add(val);
        }
        showTestResults(tree.size() == 9, 1);
        showTestResults(tree.add(78) == false, 2);

        //test 3+4 remove()
		tree.remove(12);
        showTestResults(tree.get(2) != 12, 3);
        showTestResults(tree.remove(100) == false, 4);

		//test 5+6 isPresent()
        showTestResults(tree.isPresent(25),5);
        showTestResults(tree.isPresent(48) == false, 6);

		//test 7+8 size()
		tree.add(89);
		tree.iterativeAdd(46);
		tree.add(60);
        showTestResults(tree.size() == 10, 7);
		tree.remove(25);
		tree.remove(90);
		tree.remove(101);
        showTestResults(tree.size() == 8, 8);

		//test 9+10 height()
		showTestResults(tree.height() == 3, 9);
		tree.remove(60);
		showTestResults(tree.height() == 2, 10);

		//test 11+12 getAll()
		ArrayList<Integer> treeList = new ArrayList<>();
		treeList.add(1);
		treeList.add(3);
		treeList.add(15);
		treeList.add(45);
		treeList.add(46);
		treeList.add(78);
		treeList.add(89);
		showTestResults(tree.getAll().equals(treeList), 11);
		tree.add(50);
		treeList.add(5, 50);
		showTestResults(tree.getAll().equals(treeList), 12);

		//test 13+14 max()
		BinarySearchTree<Integer> tree2 = new BinarySearchTree<>();
		tree2.add(40);
		showTestResults(tree2.max().equals(40), 13);
		showTestResults(tree.max().equals(89), 14);

		//test 15+16 min()	
		showTestResults(tree2.min().equals(40), 15);
		showTestResults(tree.min().equals(1), 16);
		
		//test 17+18 iterativeAdd()	
		tree2.iterativeAdd(50);
		tree2.iterativeAdd(45);
		tree2.iterativeAdd(55);
		tree2.iterativeAdd(53);
		tree2.iterativeAdd(58);
		tree2.iterativeAdd(60);
		showTestResults(tree2.isPresent(53) && tree2.size() == 7, 17);
		showTestResults(!tree2.iterativeAdd(58), 18);

		//test 19+20 get()	
		showTestResults(tree2.get(3) == 53, 19);
		showTestResults(tree.get(3) == 45, 20);
		//test 21+22 getAllLessThan()	
		ArrayList<Integer> arr = new ArrayList<Integer>();
		showTestResults(tree2.getAllLessThan(40).equals(arr), 21);
		arr.add(1);
		showTestResults(tree.getAllLessThan(3).equals(arr), 22);
		//test 23+24 getAllMoreThan()	
		arr = new ArrayList<Integer>();
		showTestResults(tree2.getAllGreaterThan(60).equals(arr), 23);
		arr.add(53);
		arr.add(55);
		arr.add(58);
		arr.add(60);
		showTestResults(tree2.getAllGreaterThan(50).equals(arr), 24);
		//test 23+24 numNodesAtDepth()
		showTestResults(tree.numNodesAtDepth(0) == 1, 23);
		showTestResults(tree2.numNodesAtDepth(4) == 1, 24);
    }

    private static void showTestResults(boolean passed, int testNum) {
        if (passed) {
            System.out.println("Test " + testNum + " passed.");
        } else {
            System.out.println("TEST " + testNum + " FAILED.");
        }
    } 
	private static double avgTime(int nodes){
		final int TRIALS = 10;
		double totaltime = 0.0;
        Random r = new Random();
		Stopwatch stop = new Stopwatch();
		for(int trial = 0; trial < TRIALS; trial++) {
			BinarySearchTree<Integer> b = new BinarySearchTree<>();
			stop.start();
			for(int i = 0; i < nodes; i++) {
				b.add(r.nextInt());
			}
			stop.stop();
			totaltime += stop.time();
		}
		return totaltime / TRIALS;
	}
	private static double avgTime(int[] lst){
		final int TRIALS = 10;
		double totaltime = 0.0;
		Stopwatch stop = new Stopwatch();
		for(int trial = 0; trial < TRIALS; trial++) {
			BinarySearchTree<Integer> b = new BinarySearchTree<>();
			stop.start();
			for(int i = 0; i < lst.length; i++) {
				b.iterativeAdd(lst[i]);
			}
			stop.stop();
			totaltime += stop.time();
		}
		return totaltime / TRIALS;
	}
	private static double avgHeight(int nodes){
		final int TRIALS = 10;
        Random r = new Random();
		double totalHeight = 0;
		for(int trial = 0; trial < TRIALS; trial++) {
			BinarySearchTree<Integer> b = new BinarySearchTree<>();
			for(int i = 0; i < nodes; i++) {
				b.add(r.nextInt());
			}
			totalHeight += b.height();
		}
		return totalHeight / TRIALS;
	}
	private static double avgSize(int nodes){
		final int TRIALS = 10;
        Random r = new Random();
		double totalSize = 0;
		for(int trial = 0; trial < TRIALS; trial++) {
			BinarySearchTree<Integer> b = new BinarySearchTree<>();
			for(int i = 0; i < nodes; i++) {
				b.add(r.nextInt());
			}
			totalSize += b.size();
		}
		return totalSize / TRIALS;
	}
	private static int minHeight(int nodes){
		final int TRIALS = 10;
        Random r = new Random();
		int minHeight = nodes;
		for(int trial = 0; trial < TRIALS; trial++) {
			BinarySearchTree<Integer> b = new BinarySearchTree<>();
			for(int i = 0; i < nodes; i++) {
				b.add(r.nextInt());
			}
			minHeight = Math.min(minHeight, b.height());
		}
		return minHeight;
	}
	private static double avgTimeSet(int nodes){
		final int TRIALS = 10;
		double totaltime = 0.0;
        Random r = new Random();
		Stopwatch stop = new Stopwatch();
		for(int trial = 0; trial < TRIALS; trial++) {
			Set<Integer> b = new TreeSet<>();
			stop.start();
			for(int i = 0; i < nodes; i++) {
				b.add(r.nextInt());
			}
			stop.stop();
			totaltime += stop.time();
		}
		return totaltime / TRIALS;
	}
	private static double avgTimeSet(int[] lst){
		final int TRIALS = 10;
		double totaltime = 0.0;
		Stopwatch stop = new Stopwatch();
		for(int trial = 0; trial < TRIALS; trial++) {
			Set<Integer> b = new TreeSet<>();
			stop.start();
			for(int i = 0; i < lst.length; i++) {
				b.add(lst[i]);
			}
			stop.stop();
			totaltime += stop.time();
		}
		return totaltime / TRIALS;
	}
}
