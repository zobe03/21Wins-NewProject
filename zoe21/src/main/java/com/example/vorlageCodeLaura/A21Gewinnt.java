package com.example.vorlageCodeLaura;

import java.util.Scanner;
import java.util.Stack;

// main basis of the game, as orientation for implementing the game logic

public class A21Gewinnt {
    public static void main(String[] args) {
        Stack<Integer> myStack = new Stack<Integer>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the amount of players: ");
        int nrPlayers = Integer.parseInt(sc.nextLine()); //ggf. try catch
        boolean stop = false;
        int round = 1;
        while (!stop) { // stops when stack top nr is > or 21
            System.out.println("--- ROUND " + (round) + " ---");
            if (round == 4) {
                System.out.println("-- Congrats, you have arrived to round 4! You have unlocked another input option: You can now press 'enter' and the sum of the upper two numbers will be added on top.");
            }
            for (int i = 1; i <= nrPlayers; i++) {
                System.out.print("> PlAYER " + (i) + ", it's your turn: ");
                boolean inputValid = false;
                do { // do-while until the input given by the player is not valid
                    String input = sc.nextLine();
                    inputValid = true; //will be set to false in 'else'
                    try {
                        int inputNr = Math.abs(Integer.parseInt(input));
                        if (inputNr < 10 && inputNr > 0) { // might throw a NumberFormatException
                            if (myStack.size() < 6) { // wenn stack weniger als 6 elemente besitzt
                                myStack.add(inputNr);
                            } else { // wenn stack 6 elemente besitzt
                                System.out.println("... Creating sum of input & top number ...");
                                myStack.push(myStack.pop() + inputNr);
                            }
                        } else {
                            System.out.println("Please provide a number between 1-9.");
                            inputValid = false;
                        }
                    } catch (NumberFormatException e) { // if input is not a number
                        if (input.isEmpty() && round >= 4 && myStack.size() > 1) { // bei eingabe 'nichts' (erst ab runde 4 freigeschaltet) und wenn mehr als 1 element im Stack liegt
                            System.out.println("... Creating sum of last two numbers ...");
                            int topNr = myStack.pop();
                            int secTopNr = myStack.pop();
                            myStack.push(topNr + secTopNr);
                            inputValid = true;
                        } else {
                            System.out.println("Please enter a valid input!");
                            inputValid = false;
                        }
                    }
                }
                while (!inputValid);
                // test whether the top number resembles 21 or higher
                if (myStack.peek() >= 21) {
                    System.out.println("\n!!!! We have a winner !!!! PLAYER " + i);
                    stop = true;
                    break;
                }
                System.out.println("Current version of your stack: " + myStack);
            }
            System.out.println("\n");
            round++;
        }
        sc.close();

    }
}


