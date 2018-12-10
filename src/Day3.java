import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {
    public static void main(String[] args) {
        Day3 thirdDay = new Day3();
        Scanner sc = thirdDay.initiateScanner();
        List<Claim> claimList = thirdDay.populateClaimList(sc);
        Integer[][] table = thirdDay.populateTable(claimList);
        Integer overlappingFields = thirdDay.calculateOverlappingField(table);
        Integer idealClaim = thirdDay.getIdealClaim(claimList, table);

        System.out.println(overlappingFields);
        System.out.println(idealClaim);
    }

    public Scanner initiateScanner() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("thirdDayInput.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sc;
    }


    public List<Claim> populateClaimList(Scanner sc) {
        StringBuilder sb = new StringBuilder();
        List<Claim> claimList = new ArrayList<>();

        while (sc.hasNextLine()) {
            int id = 0;
            int rowNumber = 0;
            int lineNumber = 0;
            int rowLength = 0;
            int lineLength;

            String currentLine = sc.nextLine();
            for (int i = 0; i < currentLine.length() - 1; i++) {
                char currentChar = currentLine.charAt(i);
                char nextChar = currentLine.charAt(i + 1);
                if (Character.isDigit(currentChar)) {
                    sb.append(currentChar);
                    if (nextChar == ' ') {
                        id = Integer.parseInt(sb.toString());
                        sb.setLength(0);
                    } else if (nextChar == ',') {
                        rowNumber = Integer.parseInt(sb.toString());
                        sb.setLength(0);
                    } else if (nextChar == ':') {
                        lineNumber = Integer.parseInt(sb.toString());
                        sb.setLength(0);
                    } else if (nextChar == 'x') {
                        rowLength = Integer.parseInt(sb.toString());
                        sb.setLength(0);
                    }
                }
            }
            sb.append(currentLine.charAt(currentLine.length() - 1));
            lineLength = Integer.parseInt(sb.toString());
            sb.setLength(0);

            Claim claim = new Claim(id, rowNumber, lineNumber, rowLength, lineLength);
            claimList.add(claim);
        }
        return claimList;
    }

    public Integer[][] populateTable(List<Claim> claimList) {
        Integer[][] table = new Integer[1000][1000];

        for (Claim claim : claimList) {
            int id = claim.getId();
            int lineNumber = claim.getLineNumber();
            int lineLength = claim.getLineLength();
            int rowNumber = claim.getRowNumber();
            int rowLength = claim.getRowLength();

            for (int i = lineNumber; i < lineNumber + lineLength; i++) {
                for (int j = rowNumber; j < rowNumber + rowLength; j++) {
                    if (table[i][j] != null) {
                        claim.setOverlapped(Day3.getOverlappedClaim(claimList, i, j));
                        table[i][j] = 0;
                        claim.setOverlapped(true);
                    } else {
                        table[i][j] = id;
                    }
                }
            }
        }
        return table;
    }

    public static boolean getOverlappedClaim(List<Claim> claimList, int i, int j) {
        for (Claim claim : claimList) {
            int lineNumber = claim.getLineNumber();
            int lineLength = claim.getLineLength();
            int rowNumber = claim.getRowNumber();
            int rowLength = claim.getRowLength();
            if (lineNumber <= i && i <= lineNumber+lineLength && rowNumber <= j && j <= rowNumber+rowLength) {
                claim.setOverlapped(true);
            }
        }
        return true;
    }

    public Integer calculateOverlappingField(Integer[][] table) {
        int counter = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j] == null) {
                    continue;
                } else if (table[i][j] == 0) {
                    counter++;
                }
            }
        }
        return counter;
    }


    public Integer getIdealClaim(List<Claim> claimList, Integer[][] table) {
        Claim theClaim = null;
        for (Claim claim : claimList) {
            if (claim.getOverlapped() == false) {
                theClaim = claim;
            }
        }
        return theClaim.getId();
    }
}


class Claim {
    private int id = 0;
    private int rowNumber = 0;
    private int lineNumber = 0;
    private int rowLength = 0;
    private int lineLength = 0;
    private boolean overlapped = false;

    public Claim(int id, int rowNumber, int lineNumber, int rowLength, int lineLength) {
        this.id = id;
        this.rowNumber = rowNumber;
        this.lineNumber = lineNumber;
        this.rowLength = rowLength;
        this.lineLength = lineLength;
    }

    public int getId() {
        return id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getRowLength() {
        return rowLength;
    }

    public int getLineLength() {
        return lineLength;
    }

    public boolean getOverlapped() {
        return overlapped;
    }

    public void setOverlapped(boolean overlapped) {
        this.overlapped = overlapped;
    }
}
