import java.util.ArrayList;
import java.util.List;

public class BSTSuccessorSearch {
    public static void main(String[] args) {
        BinaryTree bt = createBinaryTree();
        List<Integer> succesors = new ArrayList<Integer>();
        BSTSuccessor(14,bt.root, succesors);
        succesors.sort(null);
        for(int i = 0; i < succesors.size(); i++) {
            System.out.println(succesors.get(i));
        }
        System.out.println(succesors.get(0));
    }

    private static void BSTSuccessor(int target, Node node, List<Integer> succesors) {
        if(node.value > target) {
            succesors.add(node.value);
        }
        if(node.left != null) {
            BSTSuccessor(target, node.left, succesors); 
        } 
        if(node.right != null) {
            BSTSuccessor(target, node.right, succesors);
        }
    }

    private static BinaryTree createBinaryTree() {
        BinaryTree bt = new BinaryTree();
        bt.add(20);
        bt.add(9);
        bt.add(25);
        bt.add(5);
        bt.add(12);
        bt.add(11);
        bt.add(14);

        return bt;
    }
    
}
    class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }
    class BinaryTree {
        Node root;
        private Node addRecursive(Node current, int value) {
            if(current == null) { 
                return new Node(value);
            }
            if(value < current.value) {
                current.left = addRecursive(current.left, value);
            } else if(value > current.value) {
                current.right = addRecursive(current.right, value);
            } else {
                return current;
            }
            return current;
        }
        public void add(int value) {
            root = addRecursive(root, value);
        }
    }

