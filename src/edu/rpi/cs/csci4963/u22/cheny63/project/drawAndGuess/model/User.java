package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

public class User {
    private String name;
    private int score;

    public User(String name){
        this.name = name;
        score = 0;
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
}
