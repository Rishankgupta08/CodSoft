import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGame {

    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();
    static int highScore = Integer.MAX_VALUE;

    public static void main(String[] args) {
        System.out.println("Welcome to the Advanced Number Guessing Game!");

        int roundsPlayed = 0;
        int roundsWon = 0;

        boolean playAgain = true;

        while (playAgain) {
            roundsPlayed++;
            int maxRange = chooseDifficulty();
            boolean result = playRound(maxRange);
            if (result) {
                roundsWon++;
            }

            playAgain = askReplay();
        }

        System.out.println("\nGAME SUMMARY");
        System.out.println("---------------------------");
        System.out.println("Total rounds played: " + roundsPlayed);
        System.out.println("Rounds won: " + roundsWon);
        if (highScore != Integer.MAX_VALUE) {
            System.out.println("Best attempt: " + highScore + " guesses");
        }
        System.out.println("Thanks for playing! Goodbye!");
        sc.close();
    }

    static int chooseDifficulty() {
        System.out.println("\nSelect Difficulty Level:");
        System.out.println("1. Easy (1–50)");
        System.out.println("2. Medium (1–100)");
        System.out.println("3. Hard (1–200)");
        int choice = 2; // default medium
        while (true) {
            try {
                System.out.print("Enter your choice (1/2/3): ");
                choice = sc.nextInt();
                if (choice == 1) return 50;
                else if (choice == 2) return 100;
                else if (choice == 3) return 200;
                else System.out.println("Choose 1, 2 or 3.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 1, 2 or 3.");
                sc.next();
            }
        }
    }

    static boolean playRound(int maxRange) {
        int numberToGuess = rand.nextInt(maxRange) + 1;
        int attempts = 0;
        int maxAttempts = 5;

        System.out.println("\nROUND START");
        System.out.println("I have picked a number between 1 and " + maxRange + ". Can you guess it?");
        System.out.println("You have " + maxAttempts + " attempts.");

        while (attempts < maxAttempts) {
            int userGuess = getUserGuess(maxRange);
            attempts++;

            int diff = Math.abs(userGuess - numberToGuess);

            if (userGuess == numberToGuess) {
                System.out.println("Correct! You guessed it in " + attempts + " attempts.");
                if (attempts < highScore) {
                    highScore = attempts;
                    System.out.println("New High Score!");
                }
                return true;
            } else {
                if (diff <= 5) {
                    System.out.println("Very close!");
                } else if (diff <= 10) {
                    System.out.println("Close!");
                } else if (diff <= 20) {
                    System.out.println("Little far.");
                } else if (userGuess < numberToGuess) {
                    if (diff > 40) {
                        System.out.println("Way too low!");
                    } else {
                        System.out.println("Too low!");
                    }
                } else {
                    if (diff > 40) {
                        System.out.println("Way too high!");
                    } else {
                        System.out.println("Too high!");
                    }
                }
            }
        }

        System.out.println("You've used all attempts. The number was: " + numberToGuess);
        return false;
    }

    static int getUserGuess(int maxRange) {
        int guess = -1;
        while (true) {
            try {
                System.out.print("Enter your guess: ");
                guess = sc.nextInt();
                if (guess >= 1 && guess <= maxRange) {
                    break;
                } else {
                    System.out.println("Please enter a number between 1 and " + maxRange + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
                sc.next();
            }
        }
        return guess;
    }

    static boolean askReplay() {
        System.out.print("\nDo you want to play again? (yes/no): ");
        String response = sc.next();
        return response.equalsIgnoreCase("yes");
    }
}
