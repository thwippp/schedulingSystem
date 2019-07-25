///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Model;
//
//import java.io.IOException;
//import java.util.logging.FileHandler;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
//
///**
// *
// * @author bscha
// */
//public class Log extends Logger {
//
//    private static java.util.logging.Logger log = java.util.logging.Logger.getLogger("log.txt");
//    private static FileHandler fh;
//    private static SimpleFormatter sf;
//
//    public Log() {
//        try {
//            Log.fh = new FileHandler("log.txt", true);
//            Log.sf = new SimpleFormatter();
//            Log.fh.setFormatter(sf);
//            Log.log.addHandler(fh);
//        } catch (IOException | SecurityException ex) {
//            java.util.logging.Logger.getLogger(java.util.logging.Logger.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public void setLogLevel(){
//        Log.log.setLevel(Level.INFO);
//    }
//
//}
