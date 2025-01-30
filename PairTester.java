import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class PairTester {
    public static void main(String[] args) {
        ArrayList<Pair> pairs = new ArrayList<>();
        String fileName = "LettersProbability.txt";

        //Try catch loop for to read file or return an error message
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                //handles tabs
                String[] parts = line.split("\\t");
                char letter = parts[0].charAt(0);
                double probability = Double.parseDouble(parts[1]);

                pairs.add(new Pair(letter, probability));
            }
        } catch (IOException e) {
            System.out.println("Error with the file: " + e.getMessage());
            return;
        }

        //Sort!
        Collections.sort(pairs);


        //Make binary tree and build Huffman tree
        HuffmanTree huffmanTree = new HuffmanTree(pairs);
        huffmanTree.buildTree();

        //Get Huffman Codes
        Map<Character, String> huffmanCodes = huffmanTree.generateCodes();
        System.out.println("\nHuffman Codes:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        //Test ecoding
        Scanner kb = new Scanner(System.in);
        System.out.println("\nEnter a line of text: ");
        String input = kb.nextLine();

        String encoded = huffmanTree.encode(input);
        System.out.println("\nEncoded: " + encoded);

        String decoded = huffmanTree.decode(encoded);
        System.out.println("Decoded: " + decoded);
        kb.close();
    }
}
