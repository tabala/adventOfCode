import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day2 {
    public static void main(String[] args) {
        Day2 day2 = new Day2();
        List<String> strings = day2.saveToList();
        int checkSum = day2.calculateCheckSum(strings);
        System.out.println("first answer: " + checkSum);
        String commonBoxId = day2.findCommonBoxId(strings);
        System.out.println("second answer: " + commonBoxId);
    }

    public List<String> saveToList() {
        List<String> Strings = new ArrayList<>();
        Scanner sc = initializeScanner();

        while (sc.hasNextLine()) {
            Strings.add(sc.nextLine());
        }
        return Strings;
    }

    public Scanner initializeScanner() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("secondDayInput.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sc;
    }

    public int calculateCheckSum(List<String> strings) {
        Map<Character, Integer> charMap;
        int localTwoChar = 0;
        int localThreeChar = 0;

        int globalTwoChar = 0;
        int globalThreeChar = 0;

        for (String currentString : strings) {
            charMap = populateCharMap(currentString);

            for (Character letter : charMap.keySet()) {
                Integer howManyTimes = charMap.get(letter);
                if (howManyTimes == 2) {
                    localTwoChar = 1;
                }
                if (howManyTimes == 3) {
                    localThreeChar = 1;
                }
            }
            globalTwoChar += localTwoChar;
            globalThreeChar += localThreeChar;
            localTwoChar = 0;
            localThreeChar = 0;

        }
        return globalTwoChar * globalThreeChar;
    }

    public Map<Character, Integer> populateCharMap(String string) {
        Map<Character, Integer> charMap = new HashMap<>();
        Integer howManyTimes = 0;

        for (int i = 0; i < string.length(); i++) {
            Character letter = string.charAt(i);
            howManyTimes = charMap.get(letter);
            if (howManyTimes == null) {
                charMap.put(letter, 1);
            } else {
                charMap.put(letter, howManyTimes + 1);
            }
        }
        return charMap;
    }

    public String findCommonBoxId(List<String> strings) {
        StringBuilder sb = null;

        for (int i = 0; i < strings.size(); i++) {
            String firstString = strings.get(i);
            for (int j = i + 1; j < strings.size(); j++) {
                String secondString = strings.get(j);
                int fail = 0;
                int wrongChar = 0;
                    for (int k = 0; k < firstString.length(); k++) {
                        if (firstString.charAt(k) != secondString.charAt(k)) {
                            fail++;
                            wrongChar = k;
                        }
                    }
                    if (fail < 2) {
                        sb = new StringBuilder(firstString);
                        sb.deleteCharAt(wrongChar);
                    }

                }
            }

        return sb.toString();
    }
}
