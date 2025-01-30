import java.util.*;
public class HuffmanTree {
    private BinaryTree<Pair> root;
    private Map<Character, String> huffmanCodes = new HashMap<>();
    private List<Pair> pairs;

    public HuffmanTree(List<Pair> pairs){
        this.pairs = pairs;
        this.root = null;
    }
    //Make two trees as queues with Pairs in them
    public void buildTree(){
        Queue<BinaryTree<Pair>> queueS = new LinkedList<>();
        Queue<BinaryTree<Pair>> queueT = new LinkedList<>();
        //adding to trees to queueS
        for(Pair pair : pairs){
            queueS.add(new BinaryTree<>(pair));
        }
        //Tree building
        while(queueS.size() + queueT.size() > 1){
            //get the smallest
            BinaryTree<Pair> smallest = dequeueSmallest(queueS, queueT);
            BinaryTree<Pair> secondSmallest = dequeueSmallest(queueS, queueT);
            //makes a new pair set to null as a root node
            //probability is also sum of the smallest two trees
            Pair combinedPair = new Pair('\0', smallest.getData().getProbability()+secondSmallest.getData().getProbability());
            BinaryTree<Pair> newTree = new BinaryTree<>(combinedPair);
            newTree.setLeft(smallest);
            newTree.setRight(secondSmallest);
            //add to queue
            queueT.add(newTree);
        }
        root = queueT.poll();
    }

    //Chooses smallest tree, uses peek to compare probabilities
    private BinaryTree<Pair> dequeueSmallest(Queue<BinaryTree<Pair>> queueS, Queue<BinaryTree<Pair>> queueT){
        if(queueS.isEmpty()){
            return queueT.poll();
        }
        if(queueT.isEmpty()){
            return queueS.poll();
        }
        if(queueS.peek().getData().getProbability() <= queueT.peek().getData().getProbability()){
            return queueS.poll();
        }else{
            return queueT.poll();
        }
    }
    private void generateCodesHelper(BinaryTree<Pair> node, String code){
        if(node == null) return;
        if (node.getLeft() == null && node.getRight() == null) {
            huffmanCodes.put(node.getData().getValue(), code);
        } else {
            generateCodesHelper(node.getLeft(), code + "0");
            generateCodesHelper(node.getRight(), code + "1");
        }
    }
    public Map<Character, String> generateCodes(){
        generateCodesHelper(root,"");
        return huffmanCodes;
    }

    public String encode(String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c != ' ') {
                encoded.append(huffmanCodes.get(c));
            } else {
                encoded.append(" ");
            }
        }
        return encoded.toString();
    }

    public String decode(String encoded) {
        StringBuilder decoded = new StringBuilder();
        BinaryTree<Pair> current = root;

        for (char bit : encoded.toCharArray()) {
            if (bit == '0') {
                current = current.getLeft();
            } else if (bit == '1') {
                current = current.getRight();
            } else if (bit == ' ') {
                decoded.append(" ");
                current = root;
                continue;
            }

            if (current.getLeft() == null && current.getRight() == null) {
                decoded.append(current.getData().getValue());
                current = root;
            }
        }
        return decoded.toString();
    }

}
