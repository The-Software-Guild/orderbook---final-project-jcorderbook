/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s;

/**
 *
 * @author recyn
 */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author recyn
 */
public class S {
    public static void main(String[] args){
    
    }    
    public static String prompt(String message){
        Scanner sc= new Scanner(System.in);
        print(message);
        String input = sc.nextLine();
        return input;
        
    }
    public static void print(String message){
        System.out.println(message);
    }
    public static double doublestring(String string){
        return Double.parseDouble(string);
    }
    public static int integize(String string){
        return Integer.parseInt(string);
    }
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();
 
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
    }
    public static int randomint(int min, int max){
    Random sc= new Random();
    int range=max-min+1;
    int randomnumber= sc.nextInt(range)+min;
    return randomnumber;
}
    public static int[] append(int[] array, int num){
       array= Arrays.copyOf(array, array.length+1);
       array[array.length-1]=num;
       return array; 
    }
    public static int sum(int[] array){
       int sum=0;
       for (int i=0; i <array.length; i++){
       sum+=array[i];    
       }
       
       return sum;
    }
    public static float randomfloat(int min, int max){
    Random sc= new Random();
    float randomnumber= sc.nextFloat()*(max-min)+min;
    return randomnumber;    
    }
}
