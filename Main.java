package sorting;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.*;


public class Main {
    static String sortingType = "default";
    static String dataType = "default";
    static String inputFile = "default";
    static String outputFile = "default";
    static Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {

        for (int i = 0; i < args.length; i++) {
            switch (args[i]){
                case "-dataType":
                    if (i < args.length - 1){
                        switch (args[i + 1]){
                            case "long": dataType = "long"; break;
                            case "line": dataType = "line"; break;
                            case "word": dataType = "word"; break;
                            default:
                                System.out.println("-\"" + args[i + 1] + "\"is not a valid parameter. It will be skipped.");
                        }
                    } else {
                        System.out.println("No dataType");
                    }
                    i++;
                    break;

                case "-sortingType":
                    if (i < args.length - 1){
                        switch (args[i + 1]){
                            case "natural": sortingType = "natural"; break;
                            case "byCount": sortingType = "byCount"; break;
                            default:
                            System.out.println("-\"" + args[i + 1] + "\"is not a valid parameter. It will be skipped.");
                        }
                    } else {
                        System.out.println("No sortingType");
                    }
                    i++;
                    break;
                case "-inputFile":
                    if (i < args.length - 1){
                        inputFile = args[i+1];
                        // Changing console input to file input
                            try {
                                scanner = new Scanner(Paths.get(inputFile));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    } else {
                        System.out.println("No InputFile");
                    }
                    i++;
                    break;
                case "-outputFile":
                    if (i < args.length - 1){
                        outputFile = args[i+1];
                    } else {
                        System.out.println("No OutputFile");
                    }
                    i++;
                    break;
                default:
                    System.out.println("-\"" + args[i] + "\"is not a valid parameter. It will be skipped.");
                    break;
            }
        }

        if (sortingType.equals("default")) {
            sortingType = "natural";
        }
        if (dataType.equals("default")) {
            dataType = "long";
        }

        perform();
    }


    static class MyList <T> {
        T elem;
        int count;

        public MyList(T elem, int count) {
            this.elem = elem;
            this.count = count;
        }
    }

    public static void perform(){
        switch (dataType){
            case "long":
                parseInteger();
                break;
            case "line":
                parseLine();
                break;
            case "word":
                parseWord();
                break;
            default:
                System.out.println("Wrong command line argument");
        }
    }


    public static void parseInteger(){
        ArrayList<Long> list = new ArrayList<>();
        while (scanner.hasNextLong()) {
            long number = scanner.nextLong();
            list.add(number);
        }

        ArrayList<String> listS = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] result = line.split("[\\s]+");

            if (!result[0].isBlank()){
                try {
                    long l = Long.parseLong(result[0]);
                    list.add(l);
                } catch (NumberFormatException nfe) {
                    System.out.println("\"" + result[0] + "\" is not a long. It will be skipped.");
                }
            }
            for (int i = 1; i < result.length; i++) {
                try {
                    long l = Long.parseLong(result[i]);
                    list.add(l);
                } catch (NumberFormatException nfe) {
                    System.out.println("\"" + result[i] + "\" is not a long. It will be skipped.");
                }
            }
        }
        // Changing console output to file output
        if (!outputFile.equals("default")){
            try {
                System.setOut(new PrintStream(new File(outputFile)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total numbers: " + list.size() + ".");
        Collections.sort(list);

        if (sortingType.equals("byCount")) {
            TreeSet<Long> sortedSet = new TreeSet<Long>(list);
            List<MyList<Long>> myList = new ArrayList<>();
            for (Long l : sortedSet) {
                int freq = Collections.frequency(list, list.get(list.indexOf(l)));
                myList.add(new MyList<>(l, freq));
            }
            Collections.sort(myList, new Comparator<MyList<Long>>() {
                @Override
                public int compare(MyList<Long> o1, MyList<Long> o2) {
                    return o1.count - o2.count;
                }
            });

            for (MyList<Long> mT: myList) {
                System.out.println(mT.elem + ": " + mT.count + " time(s), " + mT.count * 100 / list.size() + "%");
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Long l: list) {
                stringBuilder.append(l).append(" ");
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
            System.out.println("Sorted data: " + stringBuilder);
        }
    }

    public static void parseLine(){
        // Changing console output to file output
        if (!outputFile.equals("default")){
            try {
                System.setOut(new PrintStream(new File(outputFile)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            list.add(line);
        }

        Collections.sort(list);
        System.out.println("Total lines: " + list.size() + ".");

        if (sortingType.equals("byCount")){
            TreeSet<String> sortedSet = new TreeSet<String>(list);
            List<MyList<String>> myList = new ArrayList<>();
            for (String l : sortedSet) {
                int freq = Collections.frequency(list, list.get(list.indexOf(l)));
                myList.add(new MyList<>(l, freq));
            }
            Collections.sort(myList, new Comparator<MyList<String>>() {
                @Override
                public int compare(MyList<String> o1, MyList<String> o2) {
                    return o1.count - o2.count;
                }
            });

            for (MyList<String> mT: myList) {
                System.out.println(mT.elem + ": " + mT.count + " time(s), " + mT.count * 100 / list.size() + "%");
            }
        } else {
            System.out.println("Sorted data: ");
            for (String str : list) {
                System.out.println(str);
            }
        }

    }

    public static void parseWord(){
        // Changing console output to file output
        if (!outputFile.equals("default")){
            try {
                System.setOut(new PrintStream(new File(outputFile)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> list = new ArrayList<>();
        // Splitting line into the words and checking split mistake
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] result = line.split("[\\s]+");
            if (!result[0].isBlank()){
                list.add(result[0]);
            }
            for (int i = 1; i < result.length; i++) {
                list.add(result[i]);
            }
        }
        System.out.println("Total words: " + list.size() + ".");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        if (sortingType.equals("natural")) {
            int freq = Collections.frequency(list, list.get(list.size() - 1));
            System.out.println("The longest word: " + list.get(list.size() - 1) +
                    " (" + freq + " time(s), " + freq * 100 / list.size() + "%).");
        } else {
            TreeSet<String> sortedSet = new TreeSet<String>(list);
            List<MyList<String>> myList = new ArrayList<>();
            for (String l : sortedSet) {
                int freq = Collections.frequency(list, list.get(list.indexOf(l)));
                myList.add(new MyList<>(l, freq));
            }
            Collections.sort(myList, new Comparator<MyList<String>>() {
                @Override
                public int compare(MyList<String> o1, MyList<String> o2) {
                    return o1.count - o2.count;
                }
            });

            for (MyList<String> mT: myList) {
                System.out.println(mT.elem + ": " + mT.count + " time(s), " + mT.count * 100 / list.size() + "%");
            }
        }

    }
}
