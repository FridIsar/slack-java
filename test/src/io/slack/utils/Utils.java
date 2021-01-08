package io.slack.utils;

public class Utils {
    public static void wait(int x) {
        for(int i=0; i<x; i++){
            //System.out.println(".");
            try{ Thread.sleep(1000);
                System.out.println(""+(x-i)); }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
