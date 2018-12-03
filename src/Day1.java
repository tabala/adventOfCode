import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {

    public static void main(String[] args) {
        Scanner sc = initalizeScanner();
        List<Integer> frequencyList = saveFromFileToSet(sc);

        Integer resultingFrequency = calculateFrequency(frequencyList);
        System.out.println("resulting frequency is: " + resultingFrequency);

        Integer firstRepeatedFrequency = findFirstRepeatedFrequency(frequencyList);
        System.out.println("first repeated frequency is: " + firstRepeatedFrequency);

    }


    public static Scanner initalizeScanner() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sc;
    }

    public static List<Integer> saveFromFileToSet(Scanner sc) {
        List<Integer> frequencyList = new ArrayList<>();
        while (sc.hasNextLine()) {
            frequencyList.add(Integer.parseInt(sc.nextLine()));
        }
        sc.close();
        return frequencyList;
    }

    public static Integer calculateFrequency(List<Integer> frequencySet) {
        Integer frequency = 0;

        for (Integer currentFrequency : frequencySet) {
            frequency += currentFrequency;
        }

        return frequency;
    }

    public static Integer findFirstRepeatedFrequency(List<Integer> frequencyList) {
        Integer frequency = 0;
        Integer firstRepeatedFrequency = 0;
        Set<Integer> frequencySet = new HashSet<>();
        boolean foundRepeatedFrequency = false;
        while (!foundRepeatedFrequency) {
            for (Integer currentFrequency : frequencyList) {
                frequency += currentFrequency;
                if (frequencySet.contains(frequency)) {
                    firstRepeatedFrequency = frequency;
                    foundRepeatedFrequency = true;
                    break;
                } else {
                    frequencySet.add(frequency);
                }
            }
        }

        return firstRepeatedFrequency;
    }
}
