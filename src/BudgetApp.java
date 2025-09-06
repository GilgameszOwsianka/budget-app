// nazwa pliku: BudgetApp.java
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.io.IOException;

public class BudgetApp {
    public static void main(String[] args) {
        BudgetManager manager = new BudgetManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n0. Pomoc");
            System.out.println("1. Dodaj/ustaw transakcję");
            System.out.println("2. Wyświetl transakcję");
            System.out.println("3. Zapisz do pliku");
            System.out.println("4. Wczytaj z pliku");
            System.out.println("5. Wyjście");
            System.out.print("Wybór: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawna opcja, wpisz liczbę od 0 do 5.");
                continue;
            }

            try {
                switch (choice) {
                    case 0:
                        printHelp();
                        break;

                    case 1:
                        handleAddTransaction(scanner, manager);
                        break;

                    case 2:
                        if (manager.getTransaction() != null) {
                            System.out.println(manager.getTransaction());
                        } else {
                            System.out.println("Brak transakcji.");
                        }
                        break;

                    case 3:
                        try {
                            String fileName = manager.saveToFile();
                            System.out.println("Zapisano do pliku: " + fileName);
                        } catch (IllegalStateException | IOException e) {
                            System.out.println("Błąd przy zapisie: " + e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Podaj nazwę pliku do wczytania: ");
                        String fileName = scanner.nextLine().trim();
                        try {
                            manager.loadFromFile(fileName);
                            System.out.println("Wczytano z pliku: " + fileName);
                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println("Błąd przy wczytywaniu: " + e.getMessage());
                        }
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

    private static void printHelp() {
        System.out.println("\nInstrukcja obsługi aplikacji Budget App:");
        System.out.println("1. Dodaj/ustaw transakcję – wprowadź kwotę, kategorię, datę i typ transakcji.");
        System.out.println("   - Kwota > 0, maks. 2 miejsca po przecinku, separator: kropka lub przecinek");
        System.out.println("   - Data w formacie YYYY-MM-DD, nie może być w przyszłości");
        System.out.println("   - Typ: income lub expense");
        System.out.println("2. Wyświetl transakcję – pokaż aktualną transakcję.");
        System.out.println("3. Zapisz do pliku – zapis aktualnej transakcji do pliku z timestampem.");
        System.out.println("4. Wczytaj z pliku – wczytaj transakcję z pliku.");
        System.out.println("5. Wyjście – zakończ działanie programu.\n");
    }

    private static void handleAddTransaction(Scanner scanner, BudgetManager manager) {
        double amount = 0;
        while (true) {
            System.out.print("Kwota: ");
            String amountInput = scanner.nextLine().trim().replace(",", ".");
            try {
                amount = Double.parseDouble(amountInput);
                if (amount <= 0) {
                    System.out.println("Kwota musi być większa od 0.");
                    continue;
                }
                String[] parts = amountInput.split("\\.");
                if (parts.length == 2 && parts[1].length() > 2) {
                    System.out.println("Kwota może mieć maksymalnie 2 miejsca po przecinku.");
                    continue;
                }
                break; // poprawna kwota
            } catch (NumberFormatException ex) {
                System.out.println("Niepoprawny format kwoty. Poprawny format np. 123.45 lub 123,45");
            }
        }

        System.out.print("Kategoria: ");
        String category = scanner.nextLine();

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
    }
}
