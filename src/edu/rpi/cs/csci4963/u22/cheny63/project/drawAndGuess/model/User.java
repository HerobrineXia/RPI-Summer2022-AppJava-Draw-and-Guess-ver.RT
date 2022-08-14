package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

public class User {
    private String name;
    private int score;
    private int id;

    public User(String name, int id){
        this(name, id, 0);
    }

    public User(String name, int id, int score){
        this.name = name;
        this.score = score;
        this.id = id;
    }

    public void addScore(int score){
        this.score += score;
    }

    public int getScore(){
        return score;
    }

    public void resetScore(){
        score = 0;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }
}
