package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class WordDictionary{
    private HashMap<String, String> wordDictionary;
    private LinkedList<String> remainWord;

    public WordDictionary(String filename) throws IOException{
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

    private void readWord(String filename) throws IOException{
        wordDictionary = new HashMap<String,String>();
        FileReader fd = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fd);
        String line;
        Boolean first = true;
        String Sep = ",";
		while((line = reader.readLine()) != null) {
			if(first) {
				if(line.contains(",")) {
					Sep = ",";
				}
				else if(line.contains(";")) {
					Sep = ";";
				}
				else if(line.contains("|")) {
					Sep = "|";
				}
				else if(line.contains(" ")) {
					Sep = " ";
				}
			}
			String[] nodes = line.split(Sep);
			if (nodes.length > 2) {
                for (int i = 1; i < nodes.length; i++) {
                	wordDictionary.put(nodes[i], nodes[0]);
                }
            } else {
            	wordDictionary.put(nodes[1], nodes[0]);
            }
		}
        reader.close();
        // Format:
        // Category; Word1; Word2; ...;
        // wordDictionary.put("Word", "Category")
        
    }
}
