package main.java.exercise;

import main.java.framework.InstanceSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class InstanceSetImplementation extends InstanceSet<InstanceImplementation, StudentSolutionImplementation, ResultImplementation, VerifierImplementation, int[]> {

    public InstanceSetImplementation(Path instanceSetPath, Path outputPath) {
        super(instanceSetPath, outputPath, ResultImplementation.class);
    }

    @Override
    protected InstanceImplementation instanceFromCsv(String line) {
        String[] splitLine = line.split(",");
        int number = Integer.parseInt(splitLine[0]);
        String groupName = splitLine[1];
        boolean testOnlySimple = false;
        if (groupName.equals("Insert Simple")) {
            testOnlySimple = true;
        }
        boolean testOnlyAVL = false;
        if (groupName.equals("Insert AVL")) {
            testOnlyAVL = true;
        }
        String keyGroup = splitLine[2];
        int[] keys;
        int numberOfKeys = 0;
        if (keyGroup.equals("ordered")) {
            keys = this.getAdditionalInput("keys-ordered.txt");
            numberOfKeys = Integer.parseInt(splitLine[3]);
        } else if (keyGroup.equals("shuffled")) {
            keys = this.getAdditionalInput("keys-shuffled.txt");
            numberOfKeys = Integer.parseInt(splitLine[3]);
        } else if (keyGroup.startsWith("debug ")) {
            String debugType = keyGroup.split(" ")[1];
            switch (debugType) {
                case "right":
                    keys = new int[] {6, 3, 9, 2, 4, 1};
                    numberOfKeys = keys.length;
                    break;
                case "left-right":
                    keys = new int[] {6, 3, 9, 2, 4, 5};
                    numberOfKeys = keys.length;
                    break;
                case "left":
                    keys = new int[] {6, 3, 9, 8, 10, 11};
                    numberOfKeys = keys.length;
                    break;
                case "right-left":
                default:
                    keys = new int[] {6, 3, 9, 8, 10, 7};
                    numberOfKeys = keys.length;
                    break;
            }
        } else {
            keys = this.requestKeys();
            numberOfKeys = Integer.parseInt(splitLine[3]);
        }
        return new InstanceImplementation(groupName, number, keys, numberOfKeys, keyGroup, testOnlySimple, testOnlyAVL);
    }

    @Override
    protected StudentSolutionImplementation provideStudentSolution() {
        return new StudentSolutionImplementation();
    }

    @Override
    protected VerifierImplementation provideVerifier() {
        return new VerifierImplementation();
    }

    @Override
    protected int[] parseAdditionalInput(BufferedReader reader) {
        List<String> keys = new ArrayList<String>();
        try {
            String key;
            while ((key = reader.readLine()) != null) {
                keys.add(key);
            }
        } catch (IOException e) {
            return new int[0];
        }
        String[] keyArray = keys.toArray(new String[keys.size()]);
        int[] parsedKeys = new int[keyArray.length];
        for (int i = 0; i < keys.size(); i++) {
            parsedKeys[i] = Integer.parseInt(keyArray[i]);
        }
        return parsedKeys;
    }

    private int[] requestKeys() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 10 integer keys (one key per line):");
        int[] keys = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 10; i++) {
            boolean validOption = false;
            while(!validOption) {
                while(!scanner.hasNextInt()) {
                    scanner.next();
                    System.err.println("Please enter an integer number.");
                }
                int key = scanner.nextInt();
                validOption = true;
                if (key <= 0) {
                    System.err.println("Please enter an integer larger than 0.");
                    validOption = false;
                }
                for (int j = 0; j < i; j++) {
                    if (key == keys[j]) {
                        validOption = false;
                        System.err.println("Please choose a key not already entered.");
                        String selectedKeys = this.selectedKeysToString(keys, i);
                        System.err.println("(Already entered: " + selectedKeys + ")");
                    }
                }
                if (validOption) {
                    keys[i] = key;
                }
            }
        }
        System.out.println("Entered keys: " + this.selectedKeysToString(keys, 10));
        return keys;
    }

    private String selectedKeysToString(int[] keys, int number) {
        String selectedKeys = "";
        for (int k = 0; k < number; k++) {
            if (k > 0) {
                selectedKeys += ", ";
            }
            selectedKeys += keys[k];
        }
        return selectedKeys;
    }

}
