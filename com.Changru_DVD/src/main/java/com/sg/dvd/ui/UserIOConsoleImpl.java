/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dvd.ui;

import java.util.Scanner;

/**
 *
 * @author recyn
 */
public class UserIOConsoleImpl implements UserIO {
    @Override
    public int readInt(String prompt){
            System.out.println(prompt);
            Scanner sc = new Scanner(System.in);
            return Integer.parseInt(sc.nextLine());
        }
        
    @Override
    public int readInt (String prompt, int min, int max){
            boolean valid=true;
            int input=0;
            while (valid==true){
                System.out.println(prompt+min+" "+max);
                Scanner sc = new Scanner(System.in);
                input=Integer.parseInt(sc.nextLine());
                if (input>=min && input<=max){
                   valid=false;
                   return input;
                }
            }
            return input;
        }
        
    @Override
        public double readDouble(String prompt){
            System.out.println(prompt);
            Scanner sc = new Scanner(System.in);
            return Double.parseDouble(sc.nextLine());
        }
    @Override
        public double readDouble (String prompt, double min, double max){
            boolean valid=true;
            double input=0;
            while (valid==true){
                System.out.println(prompt+min+" "+max);
                Scanner sc = new Scanner(System.in);
                input=Double.parseDouble(sc.nextLine());
                if (input>min && input<max){
                   valid=false;
                   return input;
                }
            }
            return input;
        }
        
    @Override
        public float readFloat(String prompt){
            System.out.println(prompt);
            Scanner sc = new Scanner(System.in);
            return Float.parseFloat(sc.nextLine());
        }
    @Override
        public float readFloat (String prompt, float min, float max){
            boolean valid=true;
            float input=0;
            while (valid==true){
                System.out.println(prompt+min+" "+max);
                Scanner sc = new Scanner(System.in);
                input=Float.parseFloat(sc.nextLine());
                if (input>min && input<max){
                   valid=false;
                   return input;
                }
            }
            return input;
        }
        
    @Override
        public long readLong(String prompt){
            System.out.println(prompt);
            Scanner sc = new Scanner(System.in);
            return Long.parseLong(sc.nextLine());
        }
        public long readLong(String prompt, long min, long max){
            boolean valid=true;
            long input=0;
            while (valid==true){
                System.out.println(prompt+min+" "+max);
                Scanner sc = new Scanner(System.in);
                input=Long.parseLong(sc.nextLine());
                if (input>min && input<max){
                   valid=false;
                   return input;
                }
            }
            return input;
        }
    @Override
        public String readString(String prompt){
            System.out.println(prompt);
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        }
    @Override
    public void print(String message){
        System.out.println(message);
    }
}
