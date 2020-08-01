package budget;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double total = 0.00;
        double income = 0.00;
        StringBuilder stringBuilder = new StringBuilder();

        //categories
        Map<String, Double> Food = new HashMap<>();
        Map<String, Double> Clothers = new HashMap<>();
        Map<String, Double> Entertainment = new HashMap<>();
        Map<String, Double> Other = new HashMap<>();

        Map<String, Double>[] arrayMap = new Map[]{Food, Clothers, Entertainment, Other};


        boolean exit = false;

        while (!exit){
            menu();
            int choice;
            choice = sc.nextInt();
            switch (choice){
                case 1:
                    System.out.println();
                    System.out.println("Enter income");
                    income += sc.nextDouble();
                    System.out.println("Income was added!");
                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    sc.nextLine();
                    boolean out = false;
                    while (!out){
                        typeOfPurchase();
                        int typeChoice = sc.nextInt();
                        sc.nextLine();
                        switch (typeChoice){
                            case 1: {
                                System.out.println("Enter purchase name:");
                                String name = sc.nextLine();
                                System.out.println("Enter its price");
                                double price = sc.nextDouble();
                                Food.put(name, price);
                                System.out.println("Purchase was added");
                            }
                            break;
                            case 2: {
                                System.out.println("Enter purchase name:");
                                String name = sc.nextLine();
                                System.out.println("Enter its price");
                                double price = sc.nextDouble();
                                Clothers.put(name, price);
                                System.out.println("Purchase was added");
                            }
                            break;
                            case 3: {
                                System.out.println("Enter purchase name:");
                                String name = sc.nextLine();
                                System.out.println("Enter its price");
                                double price = sc.nextDouble();
                                Entertainment.put(name, price);
                                System.out.println("Purchase was added");
                            }
                            break;
                            case 5:{
                                System.out.println("Enter purchase name:");
                                String name = sc.nextLine();
                                System.out.println("Enter its price");
                                double price = sc.nextDouble();
                                Other.put(name, price);
                                System.out.println("Purchase was added");
                            }
                            break;
                            case 6:
                              out = true;
                              break;
                        }

                        for(Map map : arrayMap){
                            total += map.values().stream().mapToDouble(i-> (double) i).sum();
                        }

                        System.out.println();
                        break;

                    }

//                    System.out.println("Add purchase");
//
//                    stringBuilder.append(sc.nextLine() + " ");
//                    System.out.println("Enter purchase price");
//                    stringBuilder.append("$" + sc.nextLine()).append("\n");
//                    String[] arrText = stringBuilder.toString().split("\n");
//                    String[] temp = null;
//                    for (int i = 0; i < arrText.length; i++) {
//                        //String s = new ArrayDeque<>(Arrays.asList(arrText[i].split(" "))).getLast().replace("$", "");
//                        temp = arrText[i].split("\\$");
//
//                    }
//                    total += Double.parseDouble(temp[1]);
//
//                    System.out.println("Purchase was added!");
//                    System.out.println();
                    break;
                case 3:
                    System.out.println();

                    if(Food.isEmpty() && Clothers.isEmpty() && Entertainment.isEmpty() && Other.isEmpty()){
                        System.out.println("Purchase list is empty!");
                        break;
                    }
                    boolean out2 = false;
                    while (!out2){
                        selectTypeOfPurchase();
                        int select = sc.nextInt();
                        switch (select){
                            case 1:
                                if(Food.isEmpty()){
                                    System.out.println("Purchase list is empty!");
                                }else{
                                    Food.forEach((k,v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));
                                    System.out.println("Total sum: $" + String.format("%.2f", total));
                                }
                                break;
                            case 2:
                                if(Clothers.isEmpty()){
                                    System.out.println("Purchase list is empty!");
                                }else{
                                    Clothers.forEach((k,v) -> System.out.println(k + " $" +
                                            String.format("%.2f", v)));
                                }
                                break;
                            case 6:
                                out2 = true;
                                break;
                        }
                    }
//                    if(stringBuilder == null || stringBuilder.toString().equals("")){
//                        System.out.println("Purchase list is empty");
//                    }else {
//                        System.out.print(stringBuilder);
//                        System.out.println("Total sum: $" + String.format("%.2f", total));
//                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println();
                    income -= total;
                    if (income < 0)
                        income = 0;
                    System.out.println("Balance: $" + String.format("%.2f", income) );
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

    public static void menu(){
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "0) Exit");
    }

    public static void typeOfPurchase(){
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");
    }

    public static void selectTypeOfPurchase(){
        System.out.println("Choose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back");
    }
}


