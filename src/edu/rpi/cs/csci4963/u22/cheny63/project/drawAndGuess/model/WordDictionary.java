package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
/**
 * 
 * @author Jeff Li
 *
 */
public class WordDictionary{
    private HashMap<String, String> wordDictionary;
    private LinkedList<String> remainWord;
    
    /**
     * Constructor of the dictionary
     * @param filename the input file
     * @throws IOException
     */
    public WordDictionary(String filename) throws IOException{
        readWord(filename);
        resetWordList();
    }
    
    /**
     * will get a random word from the remaining word and delete it from the list
     * @return secret word
     */
    public String getRandomWord(){
        int index = Math.abs(new Random().nextInt()) % remainWord.size();
        String word = remainWord.remove(index);
        if(remainWord.size() == 0){
            resetWordList();
        }
        return word;
    }
    
    /**
     * get the category of the word
     * @param word secret
     * @return category
     */
    public String getCategory(String word){
        return wordDictionary.get(word);
    }
    
    /**
     * reset the remaining word to full
     */
    public void resetWordList(){
        remainWord = new LinkedList<>(wordDictionary.keySet());
    }
    
    /**
     * read the .csv file and save the data to the dictionary
     * @param filename user input file 
     * @throws IOException if cannot read the file
     */
    private void readWord(String filename) throws IOException{
        wordDictionary = new HashMap<String,String>();
        FileReader fd = new FileReader(filename);
        BufferedReader reader = new BufferedReader(fd);
        String line;
        boolean first = true;
        String Sep = ",";
		while((line = reader.readLine()) != null) {
			if(first){
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
                first = false;
			}
			line = line.replace("\"", "");
			line = line.replace("\'", "");
			String[] nodes = line.split(Sep);
			if (nodes.length > 2) {
                for (int i = 1; i < nodes.length; i++) {
                	wordDictionary.put(nodes[i], nodes[0]);
                }
            }else{
            	wordDictionary.put(nodes[1], nodes[0]);
            }
		}
        reader.close();
        // Format:
        // Category; Word1; Word2; ...;
        // wordDictionary.put("Word", "Category")
        
    }
}
