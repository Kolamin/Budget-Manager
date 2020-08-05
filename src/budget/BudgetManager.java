package budget;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;


public class BudgetManager {

    //Сумма покупок
    private double totalSum;
    //Текущий баланс
    private double income;

    //Категории покупок
    Map<String, Double> Food = new HashMap<>();
    Map<String, Double> Clothes = new HashMap<>();
    Map<String, Double> Entertainment = new HashMap<>();
    Map<String, Double> Other = new HashMap<>();
    //Несортированная коллекция
    Map<String, Double> unSortedMap = new HashMap<>();

    //Отсортиврованная коллекция
    Map<String, Double> sortedMap;

    //Сводный массив покупок
    @SuppressWarnings("unchecked")
    Map<String, Double>[] mapsPurchase = new Map[]{Food, Clothes, Entertainment, Other};

    Scanner sc = new Scanner(System.in);

    boolean exit = false;
    private boolean loaded = false;

    public void runAction() {
        while (!exit) {
            menu();
            int choice;
            choice = sc.nextInt();
            switch (choice) {
                //Задаем баланс
                case 1:
                    System.out.println();
                    System.out.println("Enter income");
                    income = sc.nextDouble();
                    System.out.println("Income was added!");
                    System.out.println();
                    break;
                //Добавить покупку
                case 2:
                    System.out.println();
                    loaded = false;
                    sc.nextLine();
                    boolean out = false;
                    while (!out) {
                        typeOfPurchase();
                        int typeChoice = sc.nextInt();
                        sc.nextLine();
                        switch (typeChoice) {
                            case 1: {
                                addPurchase(Food);
                            }
                            break;
                            case 2: {
                                addPurchase(Clothes);
                            }
                            break;
                            case 3: {
                                addPurchase(Entertainment);
                            }
                            break;
                            case 4: {
                                addPurchase(Other);
                            }
                            break;
                            case 5:
                                out = true;
                                break;
                        }
                        System.out.println();
                    }

                    break;
                //Вывести список покупок
                case 3:
                    System.out.println();

                    if (Food.isEmpty() && Clothes.isEmpty() && Entertainment.isEmpty() && Other.isEmpty()) {
                        System.out.println("Purchase list is empty!");
                        System.out.println();
                        break;
                    }
                    boolean out2 = false;
                    while (!out2) {
                        selectTypeOfPurchase();
                        int select = sc.nextInt();
                        switch (select) {
                            case 1:
                                System.out.println();
                                System.out.println("Food:");
                                if (Food.isEmpty()) {
                                    System.out.println("Purchase list is empty!");
                                    System.out.println();
                                } else {

                                    totalSum = Food.values().stream().mapToDouble(i -> i).sum();

                                    Food.forEach((k, v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));
                                    System.out.println("Total sum: $" + String.format("%.2f", totalSum));
                                }
                                System.out.println();
                                break;
                            case 2:
                                System.out.println();
                                System.out.println("Clothes:");
                                if (Clothes.isEmpty()) {
                                    System.out.println("Purchase list is empty!");
                                    System.out.println();
                                } else {


                                    Clothes.forEach((k, v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));
                                    totalSum = Clothes.values().stream().mapToDouble(i -> i).sum();
                                    System.out.println("Total sum: $" + String.format("%.2f", totalSum));
                                    System.out.println();
                                }
                                break;
                            case 3:
                                System.out.println();
                                System.out.println("Entertainment:");
                                if (Entertainment.isEmpty()) {
                                    System.out.println("Purchase list is empty!");
                                    System.out.println();
                                } else {


                                    Entertainment.forEach((k, v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));
                                    totalSum = Entertainment.values().stream().mapToDouble(i -> i).sum();
                                    System.out.println("Total sum: $" + String.format("%.2f", totalSum));
                                    System.out.println();
                                }
                                break;
                            case 4:
                                System.out.println();
                                System.out.println("Other:");
                                if (Other.isEmpty()) {
                                    System.out.println("Purchase list is empty!");
                                    System.out.println();
                                } else {


                                    Other.forEach((k, v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));
                                    totalSum = Other.values().stream().mapToDouble(i -> i).sum();
                                    System.out.println("Total sum: $" + String.format("%.2f", totalSum));
                                    System.out.println();
                                }
                                break;
                            case 5:
                                System.out.println();
                                System.out.println("All:");
                                for (Map<String, Double> map : mapsPurchase) {
                                    if (map.isEmpty()) {
                                        continue;
                                    }

                                    map.forEach((k, v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));

                                }
                                double allSum = 0;
                                for (Map<String, Double> map : mapsPurchase) {
                                    allSum += map.values().stream().mapToDouble(i -> i).sum();
                                }
                                System.out.println("Total sum: $" +
                                        String.format("%.2f", allSum));
                                System.out.println();
                                break;
                            case 6:
                                out2 = true;
                                break;
                        }
                    }

                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    double sum = 0;
                    for (Map<String, Double> map : mapsPurchase) {
                        sum += map.values().stream().mapToDouble(i -> i).sum();
                    }
                    if (income - sum < 0)
                        income = 0;
                    else if (loaded) {
                        System.out.println("Balance: $" + String.format("%.2f", income));
                    } else {
                        System.out.println("Balance: $" + String.format("%.2f", income - sum));
                    }
                    System.out.println();
                    break;
                //Сохранить в файл
                case 5:
                    System.out.println();
                    File file = new File("purchases.txt");
                    try (FileWriter fileWriter = new FileWriter(file)) {


                        for (Map<String, Double> map : mapsPurchase) {
                            if (map.isEmpty()) {
                                continue;
                            } else {

                                if (map.equals(Food)) {
                                    fileWriter.write("Food:" + "\n");
                                } else if (map.equals(Clothes)) {
                                    fileWriter.write("Clothes:" + "\n");
                                } else if (map.equals(Entertainment)) {
                                    fileWriter.write("Entertainment:" + "\n");
                                } else if (map.equals(Other)) {
                                    fileWriter.write("Other:" + "\n");
                                }
                                for (String key : map.keySet()) {
                                    double value = map.get(key);
                                    fileWriter.write(key.trim());
                                    fileWriter.write('\n');
                                    fileWriter.write(String.valueOf(value).trim());
                                    fileWriter.write('\n');
                                }
                                fileWriter.write("==\n");
                            }

                        }
                        sum = 0;
                        for (Map<String, Double> map : mapsPurchase) {
                            sum += map.values().stream().mapToDouble(i -> i).sum();
                        }
                        if (income - sum < 0)
                            income = 0;

                        fileWriter.write("Balance:".trim());
                        fileWriter.write("\n");
                        fileWriter.write(String.valueOf(income - sum).trim());
                        fileWriter.write('\n');


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Purchases were saved!");
                    System.out.println();
                    break;
                //Загрузить из файла
                case 6:
                    System.out.println();
                    String str;
                    String[] temp;
                    String pathFile = "purchases.txt";
                    try {
                        str = readFileAsString(pathFile);
                        temp = str.split("==(\r|\n)", -1);
                        for (String s : temp) {
                            String[] split = s.split(":");
                            if (split[0].equals("Food")) {
                                String[] split1 = split[1].trim().split("\n");

                                for (int i = 0; i < split1.length; i += 2) {
                                    Food.put(split1[i], Double.valueOf(split1[i + 1]));
                                }
                            }
                            if (split[0].equals("Clothes")) {
                                String[] split1 = split[1].trim().split("\n");

                                for (int i = 0; i < split1.length; i += 2) {
                                    Clothes.put(split1[i], Double.valueOf(split1[i + 1]));
                                }
                            }
                            if (split[0].equals("Entertainment")) {
                                String[] split1 = split[1].trim().split("\n");

                                for (int i = 0; i < split1.length; i += 2) {
                                    Entertainment.put(split1[i], Double.valueOf(split1[i + 1]));
                                }
                            }
                            if (split[0].equals("Other")) {
                                String[] split1 = split[1].trim().split("\n");

                                for (int i = 0; i < split1.length; i += 2) {
                                    Other.put(split1[i], Double.valueOf(split1[i + 1]));
                                }
                            }
                            if (split[0].equals("Balance")) {
                                income = Double.valueOf(split[1]);
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Purchases were loaded!");
                    loaded = true;
                    System.out.println();
                    break;

                //Анализ покупок
                case 7:
                    System.out.println();
                    boolean out3 = false;
                    while (!out3) {
                        startMenuSort();
                        int typeSort = sc.nextInt();
                        switch (typeSort) {
                            case 1:
                                unSortedMap.clear();
                                for (Map<String, Double> doubleMap : mapsPurchase) {
                                    doubleMap.forEach((k, v) -> unSortedMap.put(k, v));
                                }
                                if (unSortedMap.isEmpty()) {
                                    System.out.println();
                                    System.out.println("Purchase list is empty!");
                                    System.out.println();
                                    break;
                                }
                                getSortedMap();
                                System.out.println();
                                System.out.println("All:");
                                sortedMap.forEach((k, v) -> System.out.println(k + " $" + String.format("%.2f", v)));
                                sumSortedMap("Total: $");
                                System.out.println();
                                break;

                            case 2:
                                System.out.println();
                                System.out.println("Types:");
                                unSortedMap.clear();

                                for (Map<String, Double> doubleMap : mapsPurchase) {
                                    if (doubleMap.equals(Food)) {
                                        unSortedMap.put("Food", doubleMap.values().stream().mapToDouble(x -> x).sum());
                                    }
                                    if (doubleMap.equals(Entertainment)) {
                                        unSortedMap.put("Entertainment", doubleMap.values().stream().mapToDouble(x -> x).sum());
                                    }
                                    if (doubleMap.equals(Clothes)) {
                                        unSortedMap.put("Clothes", doubleMap.values().stream().mapToDouble(x -> x).sum());
                                    }
                                    if (doubleMap.equals(Other)) {
                                        unSortedMap.put("Other", doubleMap.values().stream().mapToDouble(x -> x).sum());
                                    }
                                }
                                getSortedMap();

                                sortedMap.forEach((k, v) -> System.out.println(k + " - $" + String.format("%.2f", v)));
                                sumSortedMap("Total sum: $");
                                System.out.println();
                                break;

                            case 3:
                                System.out.println();
                                System.out.println("Choose the type of purchase\n" +
                                        "1) Food\n" +
                                        "2) Clothes\n" +
                                        "3) Entertainment\n" +
                                        "4) Other");
                                int chooseTypeofPurchase = sc.nextInt();
                                switch (chooseTypeofPurchase) {
                                    case 1:
                                        System.out.println();
                                        if (Food.isEmpty()) {
                                            System.out.println("Purchase list is empty!");
                                            break;
                                        } else {
                                            unSortedMap = Food;
                                            getSortedMap();
                                            System.out.println("Food:");
                                            sortedMap
                                                    .forEach((k, v) ->
                                                            System.out.println(k + " $" + String
                                                                    .format("%.2f", v)));
                                            sumSortedMap("Total sum: $");
                                        }
                                        break;
                                    case 2:
                                        System.out.println();
                                        if (Clothes.isEmpty()) {
                                            System.out.println("Purchase list is empty!");
                                            break;
                                        } else {
                                            unSortedMap = Clothes;
                                            getSortedMap();
                                            System.out.println("Clothes:");
                                            sortedMap
                                                    .forEach((k, v) ->
                                                            System.out.println(k + " $" + String
                                                                    .format("%.2f", v)));
                                            sumSortedMap("Total sum: $");
                                        }

                                        break;

                                    case 3:
                                        System.out.println();
                                        if (Entertainment.isEmpty()) {
                                            System.out.println("Purchase list is empty!");
                                            break;
                                        } else {
                                            unSortedMap = Entertainment;
                                            getSortedMap();
                                            System.out.println("Entertainment:");
                                            sortedMap
                                                    .forEach((k, v) ->
                                                            System.out.println(k + " $" + String
                                                                    .format("%.2f", v)));
                                            sumSortedMap("Total sum: $");
                                        }
                                        break;

                                    case 4:
                                        System.out.println();
                                        if (Other.isEmpty()) {
                                            System.out.println("Purchase list is empty!");
                                            break;
                                        } else {
                                            unSortedMap = Other;
                                            getSortedMap();
                                            System.out.println("Other:");
                                            sortedMap
                                                    .forEach((k, v) ->
                                                            System.out.println(k + " $" + String
                                                                    .format("%.2f", v)));
                                            sumSortedMap("Total sum: $");
                                        }
                                        break;
                                }
                                System.out.println();
                                break;
                            case 4:
                                out3 = true;
                                break;
                        }
                    }
                    System.out.println();
                    break;
                case 0:
                    System.out.println();
                    exit = true;
                    System.out.println("Bye!");
                    break;
            }
        }
    }

    private void sumSortedMap(String s2) {
        System.out.println(s2 + String.format("%.2f", sortedMap
                .values()
                .stream()
                .mapToDouble(x -> x).sum()));
    }

    private void getSortedMap() {
        sortedMap = unSortedMap
                .entrySet()
                .stream()
                .sorted(Map
                        .Entry
                        .comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private void addPurchase(Map<String, Double> food) {
        System.out.println();
        System.out.println("Enter purchase name:");
        String name = sc.nextLine();
        System.out.println("Enter its price");
        double price = sc.nextDouble();
        food.put(name, price);
        System.out.println("Purchase was added!");
    }

    public static void menu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");
    }

    public static void typeOfPurchase() {
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");
    }

    public static void selectTypeOfPurchase() {
        System.out.println("Choose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back");
    }

    public static void startMenuSort() {
        System.out.println("How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");
    }


    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}
