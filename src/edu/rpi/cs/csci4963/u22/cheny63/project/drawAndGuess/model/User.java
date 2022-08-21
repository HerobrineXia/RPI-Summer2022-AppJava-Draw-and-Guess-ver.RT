package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

/**
* User model
* @author Kevin Xia
* @version 1.0
*/
public class User {
    protected String name;
    protected int score;
    protected int id;

    /**
     * Constructor of the user with name and id
     * @param name the name of the user
     * @param id the id of the user
     */
    public User(String name, int id){
        this(name, id, 0);
    }

    /**
     * Constructor of the user with name, id and score
     * @param name the name of the user
     * @param id the id of the user
     * @param score the score of the user
     */
    public User(String name, int id, int score){
        this.name = name;
        this.score = score;
        this.id = id;
    }

    /**
     * Add the score to the user
     * @param score the score to be added
     */
    public void addScore(int score){
        this.score += score;
    }

    /**
     * Get the score of the user
     * @return the score
     */
    public int getScore(){
        return score;
    }

    /**
     * Initialize the user, which will reset its score
     */
    public void initialize(){
        score = 0;
    }

    /**
     * Get the name of the user
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Get the id of the user
     * @return the id
     */
    public int getId(){
        return id;
    }
}
