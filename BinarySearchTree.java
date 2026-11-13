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

import java.util.ArrayList;
import java.util.List;

/**
 * Shell for a binary search tree class.
 * @author scottm
 * @param <E> The data type of the elements of this BinarySearchTree.
 * Must implement Comparable or inherit from a class that implements
 * Comparable.
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

    private BSTNode<E> root;
    // CS314 students. Add any other instance variables you want here
    private int size;
    // CS314 students. Add a default constructor here if you feel it is necessary.
    public BinarySearchTree() {
        size = 0;
    }
    /**
     *  Add the specified item to this Binary Search Tree if it is not already present.
     *  <br>
     *  pre: <tt>value</tt> != null<br>
     *  post: Add value to this tree if not already present. Return true if this tree
     *  changed as a result of this method call, false otherwise.
     *  @param value the value to add to the tree
     *  @return false if an item equivalent to value is already present
     *  in the tree, return true if value is added to the tree and size() = old size() + 1
     */
    public boolean add(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        int oSize = size;
        root = addHelper(root, value);
        return oSize < size;
    }
    /**
     * Add value to the subtree rooted at curr if it is not already present.
     * <br>pre: value != null
     * <br>post: value is present in the subtree; the updated subtree root is
     * returned and size reflects any node that was added
     * @param curr the root of the subtree to add to, may be null
     * @param value the value to add
     * @return the root of the updated subtree
     */
    public BSTNode<E> addHelper(BSTNode<E> curr, E value) {
        if (curr == null) {
            size++;
            return new BSTNode<E>(value);
        }
        int diff = value.compareTo(curr.data);
        if (diff < 0) {
            curr.left = addHelper(curr.left, value);
        }
        else if(diff > 0) {
            curr.right = addHelper(curr.right, value);
        }
        return curr;
    }

    /**
     *  Remove a specified item from this Binary Search Tree if it is present.
     *  <br>
     *  pre: <tt>value</tt> != null<br>
     *  post: Remove value from the tree if present, return true if this tree
     *  changed as a result of this method call, false otherwise.
     *  @param value the value to remove from the tree if present
     *  @return false if value was not present
     *  returns true if value was present and size() = old size() - 1
     */
    public boolean remove(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        int orgSize = size;
        root = removeHelper(value, root);
        return orgSize != size;
    }
    /**
     * Remove value from the subtree rooted at temp if it is present.
     * <br>pre: value != null
     * <br>post: value is absent from the subtree; the updated subtree root is
     * returned and size reflects any node that was removed
     * @param value the value to remove
     * @param temp the root of the subtree to remove from, may be null
     * @return the root of the updated subtree
     */
    private BSTNode<E> removeHelper(E value, BSTNode<E> temp) {
        if(temp == null){
            return null;
        }
        int dir  = value.compareTo(temp.data);
        if (dir < 0) {
            temp.left = removeHelper(value, temp.left);
        }
        else if (dir > 0) {
            temp.right = removeHelper(value, temp.right);
        }
        else {
            size--;
            if(temp.isLeaf()) {
                temp = null;
            }
            else if(temp.right == null) {
                temp = temp.left;
            }
            else if(temp.left == null) {
                temp = temp.right;
            }
            else {
                size++;
                temp.data = maxHelp(temp.left);
                temp.left = removeHelper(temp.data, temp.left);
            }
        }
        return temp;
    } 
    /**
     * Return the largest value in the subtree rooted at node.
     * <br>pre: node != null
     * <br>post: the maximum value in the subtree is returned
     * @param node the root of the subtree to search
     * @return the largest value in the subtree
     */
    private E maxHelp(BSTNode<E> node){
        while(node.right != null) {
            node = node.right;
        }
        return node.data;
    }

    /**
     *  Check to see if the specified element is in this Binary Search Tree.
     *  <br>
     *  pre: <tt>value</tt> != null<br>
     *  post: return true if value is present in tree, false otherwise
     *  @param value the value to look for in the tree
     *  @return true if value is present in this tree, false otherwise
     */
    public boolean isPresent(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (root == null) {
            return false;
        }
        return isPresentHelper(root, value);
    }
    /**
     * Check whether value is in the subtree rooted at node.
     * <br>pre: node != null and value != null
     * <br>post: return true if value is present in the subtree, false otherwise
     * @param node the root of the subtree to search
     * @param value the value to look for
     * @return true if value is present in the subtree, false otherwise
     */
    private boolean isPresentHelper(BSTNode<E> node, E value){
        if (value.compareTo(node.data) < 0) {
            if (node.left == null) {
                return false;
            }
            return isPresentHelper(node.left, value);
        }
        else if(value.compareTo(node.data) > 0) {
            if (node.right == null) {
                return false;
            }
            return isPresentHelper(node.right, value);
        }
        else {
            return true;
        }
    }


    /**
     *  Return how many elements are in this Binary Search Tree.
     *  <br>
     *  pre: none<br>
     *  post: return the number of items in this tree
     *  @return the number of items in this Binary Search Tree
     */
    public int size() {
        return size;
    }

    /**
     *  return the height of this Binary Search Tree.
     *  <br>
     *  pre: none<br>
     *  post: return the height of this tree.
     *  If the tree is empty return -1, otherwise return the
     *  height of the tree
     *  @return the height of this tree or -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return heightHelper(root);
    }

    /**
     * Return the height of the subtree rooted at node.
     * <br>pre: node != null
     * <br>post: the height of the subtree is returned; a single node has
     * height 0
     * @param node the root of the subtree to measure
     * @return the height of the subtree
     */
    public int heightHelper(BSTNode<E> node) {
        if(node.left == null && node.right == null) {
            return 0;
        }
        int leftH = node.left == null ? 0 : heightHelper(node.left);
        int rightH = node.right == null ? 0 : heightHelper(node.right);
        return 1 + Math.max(leftH, rightH);
    }
    /**
     *  Return a list of all the elements in this Binary Search Tree.
     *  <br>
     *  pre: none<br>
     *  post: return a List object with all data from the tree in ascending order.
     *  If the tree is empty return an empty List
     *  @return a List object with all data from the tree in sorted order
     *  if the tree is empty return an empty List
     */
    public List<E> getAll() {
        ArrayList<E> res = new ArrayList<E>();
        if (root == null) {
            return res;
        }
        getAllHelper(root, res);
        return res;
    }
    /**
     * Append every value in the subtree rooted at node to lst in ascending
     * order.
     * <br>pre: node != null and lst != null
     * <br>post: all values in the subtree are appended to lst in ascending
     * order
     * @param node the root of the subtree to traverse
     * @param lst the list the values are appended to
     */
    public void getAllHelper(BSTNode<E> node, List<E> lst) {
        if (node.left != null) {
            getAllHelper(node.left, lst);
        }
        lst.add(node.data);
        if (node.right != null) {
            getAllHelper(node.right, lst);
        }
    }


    /**
     * return the maximum value in this binary search tree.
     * <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the largest value in this Binary Search Tree
     * @return the maximum value in this tree
     */
    public E max() {
        if (root == null) {
            return null;
        }
        return maxHelp(root);
    }

    /**
     * return the minimum value in this binary search tree.
     * <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the smallest value in this Binary Search Tree
     * @return the minimum value in this tree
     */
    public E min() {
        if (root == null) {
            return null;
        }
        BSTNode<E> curr = root;
        while(curr.left != null) {
            curr = curr.left;
        }
        return curr.data;
    }

    /**
     * An add method that implements the add algorithm iteratively 
     * instead of recursively.
     * <br>pre: data != null
     * <br>post: if data is not present add it to the tree, 
     * otherwise do nothing.
     * @param data the item to be added to this tree
     * @return true if data was not present before this call to add, 
     * false otherwise.
     */
    public boolean iterativeAdd(E data) {
        if (data == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (root == null) {
            root = new BSTNode<E>(data);
            size++;
            return true;
        }
        BSTNode<E> node = root;
        int diff = data.compareTo(node.data);
        while(diff != 0) {
            if (diff < 0) {
                if(node.left == null) {
                    node.left = new BSTNode<>(data);
                    size++;
                    return true;
                }
                node = node.left;
            }
            else {
                if (node.right == null) {
                    node.right = new BSTNode<>(data);
                    size++;
                    return true;
                }
                node = node.right;
            }
            diff = data.compareTo(node.data);
        }
        return false;
    }


    /**
     * Return the "kth" element in this Binary Search Tree. If kth = 0 the
     * smallest value (minimum) is returned.
     * If kth = 1 the second smallest value is returned, and so forth.
     * <br>pre: 0 <= kth < size()
     * @param kth indicates the rank of the element to get
     * @return the kth value in this Binary Search Tree
     */
    public E get(int kth) {
        if (root == null) {
            return null;
        }
        if(kth < 0 || kth >= size) {
            throw new IllegalArgumentException("kth must be in bounds");
        }
        int[] cnt = new int[1];
        cnt[0] = kth;
        return getHelper(cnt, root);
    }
    /**
     * Return the value whose ascending rank within the subtree equals cnt[0],
     * where rank 0 is the smallest value.
     * <br>pre: node != null and cnt != null and cnt.length == 1
     * <br>post: cnt[0] is decremented past the values that precede the target;
     * the target value is returned, or null if the subtree has fewer than
     * cnt[0] + 1 values
     * @param cnt a single element array holding the remaining rank to skip
     * @param node the root of the subtree to search
     * @return the value at the requested rank, or null if not found
     */
    public E getHelper(int[] cnt, BSTNode<E> node) {
        E res = null;
        if (node.left != null) {
            res = getHelper(cnt, node.left);
            if (res != null) {
                return res;
            }
        }
        if(cnt[0] == 0) {
            return node.data;
        }
        cnt[0]--;
        if (node.right != null) {
            res = getHelper(cnt, node.right);
            if (res != null) {
                return res;
            }
        }
        return res;
    }
    /**
     * Return a List with all values in this Binary Search Tree 
     * that are less than the parameter <tt>value</tt>.
     * <tt>value</tt> != null<br>
     * @param value the cutoff value
     * @return a List with all values in this tree that are less than 
     * the parameter value. If there are no values in this tree less 
     * than value return an empty list. The elements of the list are 
     * in ascending order.
     */
    public List<E> getAllLessThan(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (root == null) {
            return new ArrayList<>();
        }
        List<E> lst = new ArrayList<>();
        getLessHelper(lst, value, root);
        return lst;
    }
    /**
     * Append every value in the subtree rooted at node that is less than value
     * to lst in ascending order.
     * <br>pre: lst != null and value != null and node != null
     * <br>post: all values in the subtree less than value are appended to lst
     * in ascending order
     * @param lst the list the values are appended to
     * @param value the exclusive upper bound
     * @param node the root of the subtree to traverse
     */
    public void getLessHelper(List<E> lst, E value, BSTNode<E> node) {
        if (node.left != null) {
            getLessHelper(lst, value, node.left);
        }
        if (node.data.compareTo(value) < 0) {
            lst.add(node.data);
            if (node.right != null) {
                getLessHelper(lst, value, node.right);
            }
        }
    }

    /**
     * Return a List with all values in this Binary Search Tree 
     * that are greater than the parameter <tt>value</tt>.
     * <tt>value</tt> != null<br>
     * @param value the cutoff value
     * @return a List with all values in this tree that are greater
     *  than the parameter value. If there are no values in this tree
     * greater than value return an empty list. 
     * The elements of the list are in ascending order.
     */
    public List<E> getAllGreaterThan(E value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        if (root == null) {
            return new ArrayList<>();
        }
        List<E> lst = new ArrayList<>();
        getMoreHelper(lst, value, root);
        return lst;
    }
    /**
     * Append every value in the subtree rooted at node that is greater than
     * value to lst in ascending order.
     * <br>pre: lst != null and value != null and node != null
     * <br>post: all values in the subtree greater than value are appended to
     * lst in ascending order
     * @param lst the list the values are appended to
     * @param value the exclusive lower bound
     * @param node the root of the subtree to traverse
     */
    public void getMoreHelper(List<E> lst, E value, BSTNode<E> node) {
        if (node.data.compareTo(value) > 0) {
            if (node.left != null) {
                getMoreHelper(lst, value, node.left);
            }
            lst.add(node.data);
        }
        if (node.right != null) {
            getMoreHelper(lst, value, node.right);
        }
    }

    /**
     * Find the number of nodes in this tree at the specified depth.
     * <br>pre: none
     * @param d The target depth.
     * @return The number of nodes in this tree at a depth equal to
     * the parameter d.
     */
    public int numNodesAtDepth(int d) {
        if (d < 0) {
            return 0;
        }
        return depthHelper(root, d);
    }
    /**
     * Return the number of nodes at depth d within the subtree rooted at node,
     * where node itself is at depth 0.
     * <br>pre: none
     * <br>post: the number of nodes at depth d in the subtree is returned
     * @param node the root of the subtree to search, may be null
     * @param d the target depth relative to node
     * @return the number of nodes at depth d in the subtree
     */
    private int depthHelper(BSTNode<E> node, int d) {
        if (node == null) {
            return 0;
        }
        else if(d == 0) {
            return 1;
        }
        d -= 1;
        return depthHelper(node.left, d) + depthHelper(node.right, d);
    }
    /**
     * Prints a vertical representation of this tree.
     * The tree has been rotated counter clockwise 90
     * degrees. The root is on the left. Each node is printed
     * out on its own row. A node's children will not necessarily
     * be at the rows directly above and below a row. They will
     * be indented three spaces from the parent. Nodes indented the
     * same amount are at the same depth.
     * <br>pre: none
     */
    public void printTree() {
        printTree(root, "");
    }

    /**
     * Print the subtree rooted at n rotated 90 degrees counter clockwise, with
     * each node on its own line and children indented past their parent.
     * <br>pre: spaces != null
     * <br>post: the subtree is written to standard out; nothing is printed for
     * an empty subtree
     * @param n the root of the subtree to print, may be null
     * @param spaces the leading indentation for this subtree's root
     */
    private void printTree(BSTNode<E> n, String spaces) {
        if(n != null){
            printTree(n.right, spaces + "  ");
            System.out.println(spaces + n.data);
            printTree(n.left, spaces + "  ");
        }
    }

    private static class BSTNode<E extends Comparable<? super E>> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        private BSTNode() {
            this(null);
        }

        private BSTNode(E initValue) {
            this(null, initValue, null);
        }

        private BSTNode(BSTNode<E> initLeft,
                E initValue,
                BSTNode<E> initRight) {
            data = initValue;
            left = initLeft;
            right = initRight;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }
    }
}
