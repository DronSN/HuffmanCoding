package ru.skvrez.stepikalg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class sortFreq implements Comparator<ElementOfQuee> {
    @Override
    public int compare(ElementOfQuee o1, ElementOfQuee o2) {
        if ( o1.getFreq() < o2.getFreq() ) return -1;
        else if ( o1.getFreq() == o2.getFreq() ) return 0;
        else return 1;
    }
}

class Dictionary {
    private char symbl;
    private String code;

    public void setSymbl(char symbl) {
        this.symbl = symbl;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public char getSymbl() {
        return symbl;
    }

    public String getCode() {
        return code;
    }
}

class Node{
    private int freq;
    private Node left;
    private Node right;
    private String strLeft;
    private String strRight;

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setStrLeft(String strLeft) {
        this.strLeft = strLeft;
    }

    public void setStrRight(String strRight) {
        this.strRight = strRight;
    }

    public int getFreq() {
        return freq;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getStrLeft() {
        return strLeft;
    }

    public String getStrRight() {
        return strRight;
    }
}

class ElementOfQuee{

    private char symbl;
    private int freq;
    private Node node;

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setSymbl(char symbl) {
        this.symbl = symbl;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public char getSymbl() {
        return symbl;
    }

    public int getFreq() {
        return freq;
    }

    public Node getNode() {
        return node;
    }
}

public class Main {

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (char c : haystack.toCharArray()) {
            if (c == needle) {
                ++count;
            }
        }
        return count;
    }

    public static void main(String[] args) {
	// write your code here
        PriorityQueue<ElementOfQuee> prQue = new PriorityQueue<ElementOfQuee>(11, new sortFreq());
        ArrayList<Character> charAr = new ArrayList<Character>();
        Scanner input = new Scanner(System.in);
        String inptStr = input.nextLine();
        for (char c:inptStr.toCharArray()
             ) {
                if (!charAr.contains(c)){
                    charAr.add(c);
                    ElementOfQuee elQu = new ElementOfQuee();
                    elQu.setFreq(countOccurrences(inptStr, c));
                    elQu.setSymbl(c);
                    prQue.add(elQu);
                }
        }
        while (true){
            if (prQue.size()>1){
                ElementOfQuee elQu1, elQu2;
                elQu1 = prQue.remove();
                elQu2 = prQue.remove();
                Node node = new Node();
                node.setFreq(elQu1.getFreq() + elQu2.getFreq());
                if (elQu1.getNode()== null & elQu2.getNode() == null){
                    node.setStrLeft(Character.toString(elQu1.getSymbl()));
                    node.setStrRight(Character.toString(elQu2.getSymbl()));
                }
                else if (elQu1.getNode()== null & elQu2.getNode() != null){
                    node.setStrLeft(Character.toString(elQu1.getSymbl()));
                    node.setRight(elQu2.getNode());
                    node.setStrRight(elQu2.getNode().getStrRight() + elQu2.getNode().getStrLeft());
                }
                else if (elQu1.getNode()!= null & elQu2.getNode() == null){
                    node.setStrLeft(Character.toString(elQu2.getSymbl()));
                    node.setRight(elQu1.getNode());
                    node.setStrRight(elQu1.getNode().getStrRight() + elQu1.getNode().getStrLeft());
                }
                else { // (elQu1.getNode()!= null & elQu2.getNode() == !null)
                    node.setRight(elQu1.getNode());
                    node.setStrRight(elQu1.getNode().getStrRight() + elQu1.getNode().getStrLeft());
                    node.setLeft(elQu2.getNode());
                    node.setStrLeft(elQu2.getNode().getStrRight() + elQu2.getNode().getStrLeft());
                }
                ElementOfQuee elQue3 = new ElementOfQuee();
                elQue3.setFreq(node.getFreq());
                elQue3.setNode(node);
                prQue.add(elQue3);
            }
            else break;
        }
        ElementOfQuee elQu;
        elQu = prQue.remove();
        Dictionary[] dicAr = new Dictionary[charAr.size()];
        int i =0;
        for (char c:charAr
        ) {
            StringBuilder outStr = new StringBuilder();
            Node node = elQu.getNode();
            do {
                if (node==null){
                    outStr.append("0");
                }
                else {

                    if (node.getStrRight().contains(Character.toString(c))) {
                        outStr.append("1");
                        node = node.getRight();
                    } else {
                        outStr.append("0");
                        node = node.getLeft();
                    }
                }
            } while (node != null);
            Dictionary dicEl = new Dictionary();
            dicEl.setSymbl(c);
            dicEl.setCode(outStr.toString());
            dicAr[i] = dicEl;
            i++;
        }

        StringBuilder codeStr = new StringBuilder();
        for (char c:inptStr.toCharArray()
             ) {
            for (Dictionary dicEl: dicAr
                 ) {
                if (c == dicEl.getSymbl()) {
                    codeStr.append(dicEl.getCode());
                    break;
                }
            }
        }
        System.out.println(dicAr.length + " " + codeStr.length());
        //System.out.println(codeStr);
        for (Dictionary dicEl: dicAr
             ) {
            System.out.println(dicEl.getSymbl() + ": " + dicEl.getCode());
        }
        System.out.println(codeStr);
    }
}
