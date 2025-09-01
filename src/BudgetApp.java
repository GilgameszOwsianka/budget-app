import java.time.LocalDate;
import java.util.Scanner;

public class BudgetApp {
    public static void main(String[] args) {
        BudgetManager manager = new BudgetManager();
        Scanner scanner = new Scanner(System.in);
        String file = "transaction.txt";

        while (true) {
            System.out.println("\n1. Dodaj/ustaw transakcję");
            System.out.println("2. Wyświetl transakcję");
            System.out.println("3. Zapisz do pliku");
            System.out.println("4. Wczytaj z pliku");
            System.out.println("5. Wyjście");
            System.out.print("Wybór: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        double amount = 0;
                        while (true) {
                            try {
                                System.out.print("Kwota: ");
                                amount = Double.parseDouble(scanner.nextLine());
                                if (amount <= 0) {
                                    System.out.println("❌ Kwota musi być większa od 0. Spróbuj ponownie.");
                                    continue;
                                }
                                break; // poprawna kwota -> wychodzimy z pętli
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Niepoprawny format liczby. Spróbuj ponownie.");
                            }
                        }

                        System.out.print("Kategoria: ");
                        String category = scanner.nextLine();
                        System.out.print("Data (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());
                        System.out.print("Typ (income/expense): ");
                        String type = scanner.nextLine();
                        manager.setTransaction(new Transaction(amount, category, date, type));
                        break;

                    case 2:
                        if (manager.getTransaction() != null) {
                            System.out.println(manager.getTransaction());
                        } else {
                            System.out.println("Brak transakcji.");
                        }
                        break;

                    case 3:
                        manager.saveToFile(file);
                        System.out.println("Zapisano do pliku.");
                        break;

                    case 4:
                        manager.loadFromFile(file);
                        System.out.println("Wczytano z pliku.");
                        break;

                    case 5:
                        System.out.println("Koniec programu.");
                        return;

                    default:
                        System.out.println("Niepoprawna opcja.");
                }
            } catch (Exception e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }
}
