import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawna opcja, wpisz liczbę od 1 do 5.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        // --- Kwota ---
                        double amount = 0;
                        while (true) {
                            System.out.print("Kwota: ");
                            String input = scanner.nextLine().trim().replace(",", ".");
                            try {
                                amount = Double.parseDouble(input);
                                if (amount <= 0) {
                                    System.out.println("Kwota musi być większa od 0.");
                                    continue;
                                }
                                String[] parts = input.split("\\.");
                                if (parts.length == 2 && parts[1].length() > 2) {
                                    System.out.println("Kwota może mieć maksymalnie 2 miejsca po przecinku.");
                                    continue;
                                }
                                break;
                            } catch (NumberFormatException ex) {
                                System.out.println("Niepoprawny format kwoty. Poprawny format np. 123.45 lub 123,45");
                            }
                        }

                        // --- Kategoria ---
                        System.out.print("Kategoria: ");
                        String category = scanner.nextLine();

                        // --- Data ---
                        LocalDate date;
                        while (true) {
                            System.out.print("Data (YYYY-MM-DD): ");
                            try {
                                date = LocalDate.parse(scanner.nextLine());
                                if (date.isAfter(LocalDate.now())) {
                                    System.out.println("Data nie może być w przyszłości.");
                                    continue;
                                }
                                break;
                            } catch (DateTimeParseException ex) {
                                System.out.println("Niepoprawny format daty. Użyj YYYY-MM-DD, np. 2025-01-01");
                            }
                        }

                        // --- Typ ---
                        String type;
                        while (true) {
                            System.out.print("Typ (income/expense): ");
                            type = scanner.nextLine().trim().toLowerCase();
                            if (!type.equals("income") && !type.equals("expense")) {
                                System.out.println("Niepoprawny typ. Wpisz 'income' lub 'expense'.");
                                continue;
                            }
                            break;
                        }

                        manager.setTransaction(new Transaction(amount, category, date, type));
                        System.out.println("Transakcja została ustawiona.");
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