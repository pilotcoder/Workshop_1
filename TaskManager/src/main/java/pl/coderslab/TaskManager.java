package pl.coderslab;
import org.apache.commons.lang3.ArrayUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TaskManager {
    static final String FILE_NAME = "C:/tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] temp;

    public static void main(String[] args) {
        temp = readFile(FILE_NAME);

        while (true) {
            System.out.print(ConsoleColors.BLUE);
            System.out.println("Please select an option:");
            System.out.print(ConsoleColors.RESET);
            for (String option :
                    OPTIONS) {
                System.out.println(option);
            }

            Scanner scanner = new Scanner(System.in);
            String str = scanner.next();
            switch (str) {
                case "add" -> addTask(temp);
                case "list" -> list(temp);
                case "remove" -> removeTask(temp);
                case "exit" -> exitProgram();
                default -> System.out.println("WRONG OPTION! Please enter correct option from list below:");
            }
        }
    }

    public static String[][] readFile(String file) {
        Path path1 = Paths.get(file);
        String[][] tab = null;

        try {
            List<String> list = Files.readAllLines(path1);
            tab = new String[list.size()][list.get(0).split(",").length];

            for (int i = 0; i < list.size(); i++) {
                String[] split = list.get(i).split(",");
                System.arraycopy(split, 0, tab[i], 0, split.length);
            }
        } catch (IOException e) {
            System.out.println("File read failed.");
        }
        return tab;
    }

    public static void addTask(String[][] tab) {
        String[] tempTask = new String[temp[0].length];
        String[] commands = {"Enter task description:", "Enter due date (YYYY-MM-DD)", "Is your task impotrtant? true/false"};
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < tempTask.length; i++) {
            System.out.println(commands[i]);
            String str = scan.nextLine();
            tempTask[i] = str;
        }

        tab = Arrays.copyOf(tab, tab.length + 1);
        tab[tab.length - 1] =tempTask;

        System.out.println("You just added new task:");
        for (int i = tab.length - 1; i < tab.length; i++) {
            System.out.print((i + 1) + ": ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
        temp = tab;
    }

    public static void removeTask(String[][] tab) {
        int parse ;
        System.out.println("Enter Task number to remove or enter BACK to return to menu.");
        Scanner scan = new Scanner(System.in);

        while (true) {
            String row = scan.next();
            if (row.equalsIgnoreCase("back")) {
                break;
            }
                try {
                    parse = Integer.parseInt(row);
                    if (tab.length == 0 ){
                        System.out.println("There is no any tasks to delete.");
                        break;
                    } else if (parse <= 0) {
                        System.out.println("Enter value between 1 and " + tab.length);
                    } else if (parse > tab.length) {
                        System.out.println("Enter value between 1 and " + tab.length);
                    } else{
                        System.out.println("Task: " + parse + " DELETED");
                        parse = parse - 1; // wyświetla listę Task od 1 a nie 0 wiec to co wprowadził użytkownik pomiejsza o 1 aby był to właściwy index
                        temp = ArrayUtils.remove(tab, parse);
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter number between 1 and " + tab.length);
                }
            }


    }

    public static void list(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print((i + 1) + ": ");    //  i + 1 żeby wyświetlało pierwszy task jako 1: a nie 0:
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void exitProgram() {
        Path path1 = Paths.get(FILE_NAME);
        String[] joined = new String[temp.length];
        for (int i = 0; i < joined.length; i++) {
            joined[i] = String.join(",", temp[i]);
        }

        List<String> toSave = new ArrayList<>();
        Collections.addAll(toSave, joined); //zamiast for (int i = 0; i < joined.length; i++) {toSave.add(joined[i]);}

        try {
            Files.write(path1, toSave);
        } catch (
                IOException ex) {
            System.out.println("File save failed.");
        }
        System.out.print(ConsoleColors.RED);
        System.out.println("Bye Bye");
        System.out.print(ConsoleColors.RESET);
        System.exit(0);
    }
}