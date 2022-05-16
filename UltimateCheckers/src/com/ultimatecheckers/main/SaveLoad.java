package com.ultimatecheckers.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.paint.Color;

public class SaveLoad {
    
    public static void save(Serializable data, String fileName) throws Exception{
        try 
            (ObjectOutputStream outdata = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
                outdata.writeObject(data);
            }
    }
    public static Object Objload (String fileName) throws Exception{
        try (ObjectInputStream indata = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
            return indata.readObject();
        }
    }
    

}