package com.ultimatecheckers.main;

public class BoardUtils {
    

    public static int convertToBoardLoc(double loc){
       return (int) ((loc - 2) / 50);
    }

}
