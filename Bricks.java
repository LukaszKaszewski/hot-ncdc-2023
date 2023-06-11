import java.io.File;
import java.net.URL;
import java.util.*;

//Lukasz Kaszewski
//NCDC challenge

public class Bricks {
    public static void main(String[] args) throws Exception
    {
        // time start
        long start = System.currentTimeMillis();

        // < plik.txt
        Scanner readFile = new Scanner(System.in);
//        URL path = Bricks.class.getResource("plik.txt");
//        File file = new File(path.getFile());
//        Scanner readFile = new Scanner(file);

        int partFirstCounter = 0;
        int partSecondCounter = 0;
        int missedBricks = 0;
        int tmp = 0;
        List<String> bricksBox = new ArrayList<>();
        List<String> instructions = new ArrayList<>();
        List<String> wantedBricks = new ArrayList<>();
        Set<Integer> instructionsBuildCounter = new HashSet<>();
        Set<Integer> allInstructions = new HashSet<>();

        // read from file
        while (readFile.hasNextLine())
        {
            String line = readFile.nextLine();
            if (line.isEmpty())
            {
                break;
            }
            String str = line.substring(0, 2);
            if (line.startsWith("0:") && line.length() == 6 && !line.matches("[A-N]+"))
            {
                bricksBox.add(line.substring(2));
            } else if (line.length() == 6 && !line.matches("[A-N]+"))
            {
                switch (str) {
                    case "1:":
                    case "2:":
                    case "3:":
                    case "4:":
                    case "5:":
                    case "6:":
                    case "7:":
                    case "8:":
                    case "9:":
                        instructions.add(line);
                        break;
                    default:
                        System.out.println("klops");
                        System.exit(0);
                        break;
                }
            } else if (line.startsWith(":") && line.length() == 5 && !line.matches("[A-N]+"))
            {
                continue;
            } else {
                System.out.println("klops");
                System.exit(0);
            }

        }
//        System.out.println(bricksBox);
//        System.out.println(instructions);

        // first stage
        for (String item : instructions)
        {
            String[] parts = item.split(":");

            int instructionNumber = Integer.parseInt(parts[0]);
            String instructionCode = parts[1];
//            System.out.println(instructionNumber);
//            System.out.println(instructionCode);
//            System.out.println(parts[1]);

            if (instructionNumber % 3 == 0)
            {
                wantedBricks.add(instructionCode);
                partFirstCounter++;

                if (bricksBox.contains(instructionCode))
                {
                    bricksBox.remove(instructionCode);
                    instructionsBuildCounter.add(instructionNumber);
                } else {
                    partFirstCounter -= wantedBricks.size();
                    wantedBricks.remove(instructionCode);
                    missedBricks++;
                    for (String backTo : wantedBricks)
                    {
                        bricksBox.add(backTo);
                        instructionsBuildCounter.remove(instructionNumber);
                    }
                }
            }
            if (instructionNumber % 3 == 1)
            {
                wantedBricks.clear();
            }
            allInstructions.add(instructionNumber);
        }

        // second stage
        for (String item : instructions)
        {
            String[] parts = item.split(":");
            int instructionNumber = Integer.parseInt(parts[0]);
            String instructionCode = parts[1];
//            System.out.println(instructionNumber);
//            System.out.println(instructionCode);
//            System.out.println(parts[1]);

            if (instructionNumber % 3 != 0)
            {
                if (tmp != instructionNumber)
                {
                    wantedBricks.clear();
                }

                wantedBricks.add(instructionCode);
                partSecondCounter++;

                if (bricksBox.contains(instructionCode))
                {
                    bricksBox.remove(instructionCode);
                    instructionsBuildCounter.add(instructionNumber);
                } else {
                    partSecondCounter -= wantedBricks.size();
                    wantedBricks.remove(instructionCode);
                    missedBricks++;
                    for (String backTo : wantedBricks)
                    {
                        bricksBox.add(backTo);
                        instructionsBuildCounter.remove(instructionNumber);
                    }
                }
                tmp = instructionNumber;
            }
            allInstructions.add(instructionNumber);
        }

        // time end
        long elapsedTime = System.currentTimeMillis() - start;
//        System.out.println("Bricks time: " + elapsedTime/1000F + " seconds");
        System.out.println(partFirstCounter); // pierwszy etap - ilosc klockow
        System.out.println(partSecondCounter); // drugi etap - ilosc klockow
        System.out.println(bricksBox.size()); // ile klockow zostalo w pudelku
        System.out.println(missedBricks); // ??? chyba don't - ile brakowalo ogolnie klockow
        System.out.println(instructionsBuildCounter.size()); // ile budowli zbudowano
        System.out.println(allInstructions.size() - instructionsBuildCounter.size()); // ile budowli niezbudowano
    }
}