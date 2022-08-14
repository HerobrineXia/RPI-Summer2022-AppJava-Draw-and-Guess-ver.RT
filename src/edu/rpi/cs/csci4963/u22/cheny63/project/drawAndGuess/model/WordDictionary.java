package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class WordDictionary {
    HashMap<String, String> wordDictionary;
    LinkedList<String> remainWord;

    public WordDictionary(String filename){
        readWord(filename);
        resetWordList();
    }

    public String getRandomWord(){
        int index = new Random().nextInt() % remainWord.size();
        String word = remainWord.remove(index);
        if(remainWord.size() == 0){
            resetWordList();
        }
        return word;
    }

    public void resetWordList(){
        remainWord = new LinkedList<>(wordDictionary.keySet());
    }

    private void readWord(String filename){
        wordDictionary = new HashMap<>();
        // TODO: Read CSV
        // Format:
        // Category; Word1; Word2; ...;
        // wordDictionary.put("Word", "Category")
    }
}
