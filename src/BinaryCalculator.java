import java.util.Scanner;
import java.lang.Exception;


/**
 * Created by jacobmenke on 10/16/16.
 */


public class BinaryCalculator {

    //bit length can be changed if desired
    public static final int NUMBER_BITS = 10;
    public static final int UPPER_BOUND = (int)Math.pow(2.0, NUMBER_BITS-1)-1;
    public static final int LOWER_BOUND = (int)Math.pow(2.0, NUMBER_BITS-1)*-1;

    public String bitFlipper(String binaryNumberUserInput) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < binaryNumberUserInput.toCharArray().length; i++) {
            char atIndex = binaryNumberUserInput.charAt(i);
            char flippedAtIndex;

            if (atIndex == '0') {
                flippedAtIndex = '1';

            } else {
                flippedAtIndex = '0';
            }

            sb.append(flippedAtIndex);

        }
        return sb.toString();
    }




    public void printBinaryToDecimal(String notationUserInput, String decimalResult) {
        System.out.println("Your " + notationUserInput + " binary number is in decimal format: " + decimalResult);

    }

    public void printDecimalToBinary(String notationUserInput, String binaryString) {
        System.out.println("Your binary number in " + notationUserInput + " format: is " + binaryString);

    }

    private void binaryToDecimal(BinaryCalculator binaryCalculator, String binaryNumberUserInput) {

        Integer signedMagnitude = 0;

        //"signed magnitude"
        signedMagnitude = Integer.parseInt(binaryNumberUserInput.substring(1), 2);
        if (binaryNumberUserInput.charAt(0) == '1') {
            signedMagnitude *= -1;
        }
        binaryCalculator.printBinaryToDecimal("signed magnitude", signedMagnitude + "");

        // "ones complement":

        if (binaryNumberUserInput.charAt(0) == '1') {
            String flippedBinaryString = binaryCalculator.bitFlipper(binaryNumberUserInput);
            signedMagnitude = Integer.parseInt(flippedBinaryString, 2);
            signedMagnitude *= -1;
        } else {
            signedMagnitude = Integer.parseInt(binaryNumberUserInput, 2);
        }
        binaryCalculator.printBinaryToDecimal("ones complement", signedMagnitude + "");
        if (binaryNumberUserInput.charAt(0) == '1') {
            String flippedBinaryString = binaryCalculator.bitFlipper(binaryNumberUserInput);
            signedMagnitude = Integer.parseInt(flippedBinaryString, 2);
            signedMagnitude++;
            signedMagnitude *= -1;
        } else {
            signedMagnitude = Integer.parseInt(binaryNumberUserInput, 2);
        }
        binaryCalculator.printBinaryToDecimal("twos complement", signedMagnitude + "");

        signedMagnitude = Integer.parseInt(binaryNumberUserInput.substring(1), 2);
        signedMagnitude -= 512;
        binaryCalculator.printBinaryToDecimal("excess-512 notation", signedMagnitude + "");
    }


    private void decimalToBinary(BinaryCalculator binaryCalculator, Integer binaryNumberUserInput) {

        Integer signedMagnitude = 0;
        String binaryString = "";


        //"signed magnitude"
        if (binaryNumberUserInput >= 0) {
            binaryString = String.format("%" + NUMBER_BITS + "s", Integer.toBinaryString(binaryNumberUserInput)).replace(" ", "0");
        } else {
            Integer flippedSignInt = binaryNumberUserInput*-1;
            binaryString = "1" + String.format("%" + (NUMBER_BITS-1) +"s", Integer.toBinaryString(flippedSignInt)).replace(" ", "0");
        }
        binaryCalculator.printDecimalToBinary("signed magnitude", binaryString);

        //"ones complement"
        if (binaryNumberUserInput >= 0) {
            binaryString = String.format("%" + NUMBER_BITS + "s", Integer.toBinaryString(binaryNumberUserInput)).replace(" ", "0");
        } else {
            String onesComplementString = Integer.toBinaryString(binaryNumberUserInput-1);
            binaryString = onesComplementString.substring(onesComplementString.length()-NUMBER_BITS);

        }
        binaryCalculator.printDecimalToBinary("ones complement", binaryString);

        //"twos complement"
        if (binaryNumberUserInput >= 0) {
            binaryString = String.format("%" + NUMBER_BITS + "s", Integer.toBinaryString(binaryNumberUserInput)).replace(" ", "0");
        } else {
            String twosComplementString = Integer.toBinaryString(binaryNumberUserInput);
            binaryString = twosComplementString.substring(twosComplementString.length()-NUMBER_BITS);
        }
        binaryCalculator.printDecimalToBinary("twos complement", binaryString);

        //"excess-512 notation"
        binaryNumberUserInput+=512;
        binaryString = String.format("%" + NUMBER_BITS + "s", Integer.toBinaryString(binaryNumberUserInput)).replace(" ", "0");

        binaryCalculator.printDecimalToBinary("excess-512 notation", binaryString);



    }

    void printDivider() {
        for (int i = 0; i < 50; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    void getUserInputInLoop(BinaryCalculator binaryCalculator) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Converting from decimal to binary? Y or N.  Q to quit");
            String decToBinary = scanner.next();

            if (decToBinary.equalsIgnoreCase("n")) {

                System.out.println("Enter a " + NUMBER_BITS + " bit binary string");
                scanner = new Scanner(System.in);
                String binaryNumberUserInput = scanner.nextLine();
                System.out.println("Your binary number is " + binaryNumberUserInput + ".");


                if (binaryNumberUserInput.length() != NUMBER_BITS) {
                    System.out.println("Invalid length of " + binaryNumberUserInput.length() + ".");
                    binaryCalculator.printDivider();
                    continue;
                }

                System.out.println("Calculating from binary to decimal...");
                binaryCalculator.binaryToDecimal(binaryCalculator, binaryNumberUserInput);

            } else if (decToBinary.equalsIgnoreCase("y")) {

                scanner = new Scanner(System.in);

                System.out.println("Enter a decimal with the bounds of a signed integer of " + NUMBER_BITS + " bits.");
                Integer binaryNumberUserInput = Integer.parseInt(scanner.nextLine());
                System.out.println("Your integer is " + binaryNumberUserInput + ".");

                if (binaryNumberUserInput > UPPER_BOUND || binaryNumberUserInput < LOWER_BOUND) {
                    System.out.println("Invalid value.  Bounds are " + LOWER_BOUND + " to " + UPPER_BOUND + ".");
                    binaryCalculator.printDivider();
                    continue;
                }

                System.out.println("Calculating from decimal to binary...");

                binaryCalculator.decimalToBinary(binaryCalculator, binaryNumberUserInput);


            } else if (decToBinary.equalsIgnoreCase("q")) {
                System.out.println("Bye");
                System.exit(0);
            } else {
                System.out.println("Invalid Choice.");
            }
            binaryCalculator.printDivider();

        }
    }

    public static void main(String[] args) {

        System.out.println("This is a " + NUMBER_BITS + " bit calculator.");

        BinaryCalculator binaryCalculator = new BinaryCalculator();

        binaryCalculator.getUserInputInLoop(binaryCalculator);


    }


}
