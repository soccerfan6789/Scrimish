import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

/**
 * Project: Scrimish
 * Name: Shariq Syed
 */
public class Scrimish {
    public static List<List<String>> UserPile = new ArrayList<>();
    public static List<List<String>> ComputerPile = new ArrayList<>();
    static int indexOfUserCrown = -1;
    static int indexOfCompCrown = -1;
    static boolean gameOver = false;

    public static void main(String[] args) {
        play();
    }

    public static void play() {
        displayWelcome();
        getUserInput();
        checkValidDeck();
        generateComputerPile();
        do {
            displayDeck();
            UserAttack();
            ComputerAttack();
        } while (gameOver == false);
    }


    public static void getUserInput() {

        //Creates Lists within List
        for (int i = 0; i < 5; i++) {
            UserPile.add(new ArrayList<>(5));
        }

        for (int i = 0; i < 5; i++) {
            ComputerPile.add(new ArrayList<>(5));
        }

        Scanner input = new Scanner(System.in);

        //Puts input into lists
        System.out.println("Enter User Deck: ");
        for (int i = 0; i < 5; i++) {
            while (UserPile.get(i).size() < 5) {
                UserPile.get(i).add(input.next());
            }
        }
    }


    public static void checkValidDeck() {

        boolean invalidDeck = false;
        int shieldCount = 0;
        int archerCount = 0;

        //Sets index of Crown Card
        for (int i = 0, j = 0; i < 5; i++) {
            if (UserPile.get(i).get(j).equalsIgnoreCase("C")) {
                indexOfUserCrown = i;
                break;
            }
        }

        //Checks for incorrect input
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (UserPile.get(i).get(j).length() > 1) {
                    System.out.println("Your entry of " + UserPile.get(i).get(j) + " in the User Deck is invalid");
                    invalidDeck = true;
                    break;
                }
            }
        }

        //Checks for more than two shields and archers
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (UserPile.get(i).get(j).equalsIgnoreCase("S")) {
                    shieldCount++;
                }
                if (UserPile.get(i).get(j).equalsIgnoreCase("A")) {
                    archerCount++;
                }
            }
        }

        //Checks if Crown Card is in deck
        if (indexOfUserCrown == -1) {
            System.out.println("The Crown card must be in the last card in the deck");
            clear();
        }

        if (shieldCount > 2 || archerCount > 2) {
            System.out.println("You are only allowed to input two shield and two archers \n" +
                    "You currently have " + shieldCount + " shields and " + archerCount + " archers");
            clear();
        }

        if (invalidDeck == true) {
            clear();
        }
    }

    public static void clear() {
        UserPile.clear();
        System.out.println();
        getUserInput();
    }

    public static void generateComputerPile() {
        String crown = "C";
        String archer = "A";
        String shield = "S";
        boolean full = false;

        //Creates Lists within List
        for (int i = 0; i < 5; i++) {
            ComputerPile.add(new ArrayList<>(5));
        }

        //Add elements to ComputerPile
        while (!full) {

            //Generate Crown Card
            for (int i = 0; i < 1; i++) {
                ComputerPile.get(randomComputerRow()).add(crown);
            }

            //Generate 1 & 2 Cards
            for (int i = 0; i < 5; i++) {
                for (int j = 1;j <=2; j++){
                    ComputerPile.get(randomComputerRow()).add(String.valueOf(j));
                }
            }

            //Generate 3 & 4 Cards
            for (int i = 0; i < 3; i++) {
                for (int j = 3;j <=4; j++){
                    ComputerPile.get(randomComputerRow()).add(String.valueOf(j));
                }
            }

            //Generate 5 & 6 Cards
            for (int i = 0; i < 2; i++) {
                for(int j = 5; j <= 6; j++){
                    ComputerPile.get(randomComputerRow()).add(String.valueOf(j));
                }
            }

            //Generate Archers and Shields
            for (int i = 0; i < 2; i++) {
                ComputerPile.get(randomComputerRow()).add(String.valueOf(archer));
                ComputerPile.get(randomComputerRow()).add(String.valueOf(shield));
            }
            full = true;
        }

        //Note index of Crown Card
        for (int i = 0, j = 0; i < 5; i++) {
            if (ComputerPile.get(i).get(j).equalsIgnoreCase("C")) {
                indexOfCompCrown = i;
                break;
            }
        }

        //Shuffle Deck
        for (int i = 0; i < 5; i++) {
            if (indexOfCompCrown == i)
                continue;
            else
                Collections.shuffle(ComputerPile.get(i));
        }
    }

    public static int randomComputerRow() {
        int n = (int) (Math.random() * 5);

        if (ComputerPile.get(n).size() >= 5)
            while (ComputerPile.get(n).size() >= 5)
                n = (int) (Math.random() * 5);

        return n;
    }

    public static void displayDeck() {

        System.out.println();

        //Outputs Deck in proper format
        System.out.println("User Deck: ");
        for (int i = 0; i < UserPile.size(); i++) {
            System.out.println(UserPile.get(i).toString().replace(",", "").replace("[", "").replace("]", ""));
        }

        System.out.println();

        System.out.println("Computer Deck: ");
        for (int i = 0; i < 5; i++) {
            System.out.println(ComputerPile.get(i).toString().replace(",", "").replace("[", "").replace("]", ""));
        }

        System.out.println();
    }

    public static void UserAttack() {
        Scanner input = new Scanner(System.in);

        //Checks for invalid input
        try {
            System.out.println("What row would you like to attack? (1-5)");
            int rowAttack = input.nextInt() - 1;

            System.out.println("What row would you like to attack with? (1-5)");
            int attackWith = input.nextInt() - 1;

            if (!(rowAttack >= 0 && rowAttack <= 4) || !(attackWith >= 0 && attackWith <= 4)) {
                System.out.println("Please enter a valid row");
                UserAttack();
            } else {
                attack(rowAttack, attackWith, false);
            }
        } catch (InputMismatchException ex) {
            System.out.println("Please enter an integer only!");
            UserAttack();
        }
    }

    public static void ComputerAttack() {

        //Generate random number to determine which row to attack and with which row to attack with
        int rowAttack = (int) (Math.random() * 5);
        int attackWith = (int) (Math.random() * 5);

        //If the generated numbers row or attacking row is empty, generate another number and check if
        // that row and attacking row is empty
        if (UserPile.get(rowAttack).isEmpty()) {
            while (UserPile.get(rowAttack).isEmpty())
                rowAttack = (int) (Math.random() * 5);
        }

        if (ComputerPile.get(attackWith).isEmpty()) {
            while (ComputerPile.get(attackWith).isEmpty())
                attackWith = (int) (Math.random() * 5);
        }

        attack(rowAttack, attackWith, true);
    }

    public static void attack(int rowAttack, int attackWith, boolean ComputerTurn) {

        if (!ComputerTurn) {
            //If user selects an empty row, allow them to select another row
            try {
                String rowAttackChar = ComputerPile.get(rowAttack).get(ComputerPile.get(rowAttack).size() - 1);
                String attackCharWith = UserPile.get(attackWith).get(UserPile.get(attackWith).size() - 1);

                compareChar(rowAttack, attackWith, rowAttackChar, attackCharWith, ComputerTurn);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("The row you have selected is empty");
                UserAttack();
            }
        } else {
            String rowAttackChar = UserPile.get(rowAttack).get(UserPile.get(rowAttack).size() - 1);
            String attackCharWith = ComputerPile.get(attackWith).get(ComputerPile.get(attackWith).size() - 1);

            compareChar(rowAttack, attackWith, rowAttackChar, attackCharWith, ComputerTurn);
        }
    }


    public static void compareChar(int rowAttack, int attackWith, String rowAttackChar, String attackCharWith, boolean ComputerTurn) {

        //Checks if card attacking is numbered card or lettered
        int validAttack = validNum(rowAttackChar);
        int validRow = validNum(attackCharWith);

        //If User Turn
        if (ComputerTurn == false) {

            //Attack with Shield
            if (attackCharWith.equalsIgnoreCase("S")) {
                System.out.println("Your cannot attack with a shield card");
                UserAttack();

            } else if (validRow != -1 && validAttack != -1) {
                //If attacking card larger than card
                if (validRow > validAttack) {
                    ComputerPile.get(rowAttack).remove(ComputerPile.get(rowAttack).size() - 1);
                    System.out.println(rowAttackChar + " was discarded by " + attackCharWith);
                }
                // If attacking card same size of card attacked or Attacking shield
                else if (validRow == validAttack) {
                    ComputerPile.get(rowAttack).remove(ComputerPile.get(rowAttack).size() - 1);
                    UserPile.get(attackWith).remove(UserPile.get(attackWith).size() - 1);
                    System.out.println("Both cards were discarded since both cards had the same value");
                }

                // If attacking card is smaller than the size of card attacked
                else {
                    UserPile.get(attackWith).remove(UserPile.get(attackWith).size() - 1);
                    System.out.println(attackCharWith + " card discarded");
                }
            } else if (validAttack == -1) {

                //Attack Shield with Regular Card
                if (validRow >= 0 && rowAttackChar.equalsIgnoreCase("S")) {
                    ComputerPile.get(rowAttack).remove(ComputerPile.get(rowAttack).size() - 1);
                    UserPile.get(attackWith).remove(UserPile.get(attackWith).size() - 1);
                    System.out.println("Both cards discarded");
                }

                //Attack Shield with Archer
                else if (attackCharWith.equalsIgnoreCase("A") && rowAttackChar.equalsIgnoreCase("S")) {
                    System.out.println("Neither card discarded");
                }

                //Attack Archer with Archer
                else if (attackCharWith.equalsIgnoreCase("A") && rowAttackChar.equalsIgnoreCase("A")) {
                    ComputerPile.get(rowAttack).remove(ComputerPile.get(rowAttack).size() - 1);
                    UserPile.get(attackWith).remove(UserPile.get(attackWith).size() - 1);
                    System.out.println("Both cards were discarded");
                }

                //Anything else
                else {
                    ComputerPile.get(rowAttack).remove(ComputerPile.get(rowAttack).size() - 1);
                    System.out.println(rowAttackChar + " card discarded");
                }
            }

            //Anything else
            else {
                ComputerPile.get(rowAttack).remove(ComputerPile.get(rowAttack).size() - 1);
                System.out.println(rowAttackChar + " card discarded");
            }
        }
        //Computer turn
        else if (ComputerTurn == true) {
            //Attack with Shield
            if (attackCharWith.equalsIgnoreCase("S")) {
                ComputerAttack();
            } else if (validRow != -1 || validAttack != -1) {
                //If attacking card larger than card
                if (validRow > validAttack) {
                    UserPile.get(rowAttack).remove(UserPile.get(rowAttack).size() - 1);
                    System.out.println("Computer Move: Attacked " + rowAttackChar + " with " + attackCharWith
                            + ", " + rowAttackChar + " was discarded by " + attackCharWith);
                }
                // If attacking card same size of card attacked
                else if (validRow == validAttack) {
                    ComputerPile.get(attackWith).remove(ComputerPile.get(attackWith).size() - 1);
                    UserPile.get(rowAttack).remove(UserPile.get(rowAttack).size() - 1);
                    System.out.println("Computer Move: Attacked " + rowAttackChar + " with " + attackCharWith
                            + ", " + rowAttackChar + " and " + attackCharWith + " cards were discarded");
                }
                // If attacking card is smaller than the size of card attacked
                else if ((validRow < validAttack) && validRow != -1 && validAttack != -1) {
                    ComputerPile.get(attackWith).remove(ComputerPile.get(attackWith).size() - 1);
                    System.out.println("Computer Move: Attacked " + rowAttackChar + " with " + attackCharWith
                            + ", " + attackCharWith + " card discarded");
                } else if ((validRow < validAttack) && validAttack != -1) {
                    UserPile.get(rowAttack).remove(UserPile.get(rowAttack).size() - 1);
                    System.out.println("Computer Move: Attacked " + rowAttackChar + " with " + attackCharWith
                            + ", " + rowAttackChar + " card discarded");
                }
            } else if (validAttack == -1) {
                //Attack Shield with Regular Card
                if (validRow >= 0 && rowAttackChar.equalsIgnoreCase("S")) {
                    ComputerPile.get(attackWith).remove(ComputerPile.get(attackWith).size() - 1);
                    UserPile.get(rowAttack).remove(UserPile.get(rowAttack).size() - 1);
                    System.out.println("Computer Move: " + attackCharWith + " attacked " + rowAttackChar + ", both cards discarded");
                }

                //Attack Shield With Archer
                else if (attackCharWith.equalsIgnoreCase("A") && rowAttackChar.equalsIgnoreCase("S")) {
                    System.out.println("Computer Move: " + attackCharWith + " attacked " + rowAttackChar + ", " + " Neither card discarded");
                }

                //Attack Archer with Archer
                else if (attackCharWith.equalsIgnoreCase("A") && rowAttackChar.equalsIgnoreCase("A")) {
                    ComputerPile.get(attackWith).remove(ComputerPile.get(attackWith).size() - 1);
                    UserPile.get(rowAttack).remove(UserPile.get(rowAttack).size() - 1);
                    System.out.println("Both cards were discarded");
                }
            }

            //Anything else
            else {
                UserPile.get(rowAttack).remove(UserPile.get(rowAttack).size() - 1);
                System.out.println("Computer Move: " + attackCharWith + " attacked " + rowAttackChar + ", " + " Discarded " + rowAttackChar + " Card");
            }
        }
        //Check if player won
        if (UserPile.get(indexOfUserCrown).isEmpty()) {
            gameOver = true;
            gameOver(false);
        }

        //Check if computer won
        if (ComputerPile.get(indexOfCompCrown).isEmpty()) {
            gameOver = true;
            gameOver(true);
        }
    }

    public static int validNum(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public static void gameOver(boolean o) {
        if (o == true) {
            displayWin();
            System.exit(0);
        } else if (o == false) {
            displayLose();
            System.exit(0);
        }
    }

    public static void displayWelcome(){
        System.out.println("                                                                                                                                   \n" +
                "     ##### /    ##   ###              ###                                                                                          \n" +
                "  ######  /  #####    ###              ###                                                                                         \n" +
                " /#   /  /     #####   ###              ##                                                                        #                \n" +
                "/    /  ##     # ##      ##             ##                                                                       ##                \n" +
                "    /  ###     #         ##             ##                                                                       ##                \n" +
                "   ##   ##     #         ##    /##      ##       /###       /###    ### /### /###       /##                    ########    /###    \n" +
                "   ##   ##     #         ##   / ###     ##      / ###  /   / ###  /  ##/ ###/ /##  /   / ###                  ########    / ###  / \n" +
                "   ##   ##     #         ##  /   ###    ##     /   ###/   /   ###/    ##  ###/ ###/   /   ###                    ##      /   ###/  \n" +
                "   ##   ##     #         ## ##    ###   ##    ##         ##    ##     ##   ##   ##   ##    ###                   ##     ##    ##   \n" +
                "   ##   ##     #         ## ########    ##    ##         ##    ##     ##   ##   ##   ########                    ##     ##    ##   \n" +
                "    ##  ##     #         ## #######     ##    ##         ##    ##     ##   ##   ##   #######                     ##     ##    ##   \n" +
                "     ## #      #         /  ##          ##    ##         ##    ##     ##   ##   ##   ##                          ##     ##    ##   \n" +
                "      ###      /##      /   ####    /   ##    ###     /  ##    ##     ##   ##   ##   ####    /                   ##     ##    ##   \n" +
                "       #######/ #######/     ######/    ### /  ######/    ######      ###  ###  ###   ######/                    ##      ######    \n" +
                "         ####     ####        #####      ##/    #####      ####        ###  ###  ###   #####                      ##      ####     \n" +
                "                                                                                                                                   \n" +
                "                                                                                                                                   \n" +
                "                                                                                                                                   \n" +
                "                                                                                                                                   ");
        System.out.println("\t\t                                                                                               \n" +
                "\t\t\t\t      #######                                                                          /       \n" +
                "\t\t\t\t    /       ###                            #                        #                #/        \n" +
                "\t\t\t\t   /         ##                           ###                      ###               ##        \n" +
                "\t\t\t\t   ##        #                             #                        #                ##        \n" +
                "\t\t\t\t    ###                                                                              ##        \n" +
                "\t\t\t\t   ## ###           /###    ###  /###    ###     ### /### /###    ###        /###    ##  /##   \n" +
                "\t\t\t\t    ### ###        / ###  /  ###/ #### /  ###     ##/ ###/ /##  /  ###      / #### / ## / ###  \n" +
                "\t\t\t\t      ### ###     /   ###/    ##   ###/    ##      ##  ###/ ###/    ##     ##  ###/  ##/   ### \n" +
                "\t\t\t\t        ### /##  ##           ##           ##      ##   ##   ##     ##    ####       ##     ## \n" +
                "\t\t\t\t          #/ /## ##           ##           ##      ##   ##   ##     ##      ###      ##     ## \n" +
                "\t\t\t\t           #/ ## ##           ##           ##      ##   ##   ##     ##        ###    ##     ## \n" +
                "\t\t\t\t            # /  ##           ##           ##      ##   ##   ##     ##          ###  ##     ## \n" +
                "\t\t\t\t  /##        /   ###     /    ##           ##      ##   ##   ##     ##     /###  ##  ##     ## \n" +
                "\t\t\t\t /  ########/     ######/     ###          ### /   ###  ###  ###    ### / / #### /   ##     ## \n" +
                "\t\t\t\t/     #####        #####       ###          ##/     ###  ###  ###    ##/     ###/     ##    ## \n" +
                "\t\t\t\t|                                                                                           /  \n" +
                "\t\t\t\t \\)                                                                                        /   \n" +
                "\t\t\t\t                                                                                          /    \n" +
                "\t\t\t\t                                                                                         /     ");
    }

    public static void displayLose(){
        System.out.println("                                                                                                         \n" +
                "      ##### /    ##                                              ###                                     \n" +
                "   ######  /  #####                                               ###                                    \n" +
                "  /#   /  /     #####                                              ##                                    \n" +
                " /    /  ##     # ##                                               ##                                    \n" +
                "     /  ###     #                                                  ##                                    \n" +
                "    ##   ##     #        /###    ##   ####                         ##       /###       /###       /##    \n" +
                "    ##   ##     #       / ###  /  ##    ###  /                     ##      / ###  /   / #### /   / ###   \n" +
                "    ##   ##     #      /   ###/   ##     ###/                      ##     /   ###/   ##  ###/   /   ###  \n" +
                "    ##   ##     #     ##    ##    ##      ##                       ##    ##    ##   ####       ##    ### \n" +
                "    ##   ##     #     ##    ##    ##      ##                       ##    ##    ##     ###      ########  \n" +
                "     ##  ##     #     ##    ##    ##      ##                       ##    ##    ##       ###    #######   \n" +
                "      ## #      #     ##    ##    ##      ##                       ##    ##    ##         ###  ##        \n" +
                "       ###      #     ##    ##    ##      /#                       ##    ##    ##    /###  ##  ####    / \n" +
                "        #########      ######      ######/ ##                      ### /  ######    / #### /    ######/  \n" +
                "          #### ###      ####        #####   ##                      ##/    ####        ###/      #####   \n" +
                "                ###                                                                                      \n" +
                "    ########     ###                                                                                     \n" +
                "  /############  /#                                                                                      \n" +
                " /           ###/                                                                                        ");
    }

    public static void displayWin(){
        System.out.println("                                                                                                                      \n" +
                "      ##### /    ##                                                                                           ###     \n" +
                "   ######  /  #####                                                                       #                    ###    \n" +
                "  /#   /  /     #####                                                                    ###                    ##    \n" +
                " /    /  ##     # ##                                             ##                       #                     ##    \n" +
                "     /  ###     #                                                ##                                             ##    \n" +
                "    ##   ##     #        /###    ##   ####                        ##    ###    ####     ###     ###  /###       ##    \n" +
                "    ##   ##     #       / ###  /  ##    ###  /                     ##    ###     ###  /  ###     ###/ #### /    ##    \n" +
                "    ##   ##     #      /   ###/   ##     ###/                      ##     ###     ###/    ##      ##   ###/     ##    \n" +
                "    ##   ##     #     ##    ##    ##      ##                       ##      ##      ##     ##      ##    ##      ##    \n" +
                "    ##   ##     #     ##    ##    ##      ##                       ##      ##      ##     ##      ##    ##      ### / \n" +
                "     ##  ##     #     ##    ##    ##      ##                       ##      ##      ##     ##      ##    ##       ##/  \n" +
                "      ## #      #     ##    ##    ##      ##                       ##      ##      ##     ##      ##    ##            \n" +
                "       ###      #     ##    ##    ##      /#                       ##      /#      /      ##      ##    ##       #    \n" +
                "        #########      ######      ######/ ##                       ######/ ######/       ### /   ###   ###     ###   \n" +
                "          #### ###      ####        #####   ##                       #####   #####         ##/     ###   ###     #    \n" +
                "                ###                                                                                                   \n" +
                "    ########     ###                                                                                                  \n" +
                "  /############  /#                                                                                                   \n" +
                " /           ###/                                                                                                     ");
    }
}