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
            // Nowe opcje - dodawanie transakcji do tablicy i wyświetlanie wszystkich transakcji z tablicy
            System.out.println("5. Dodaj transakcję do tablicy");
            System.out.println("6. Wyświetl wszystkie transakcje z tablicy");
            System.out.println("7. Usuń transakcję z tablicy");
            System.out.println("8. Zapisz tablicę do pliku");
            System.out.println("9. Wczytaj tablicę z pliku");
            System.out.println("10. Wyjście");
            System.out.print("Wybór: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Niepoprawna opcja, wpisz liczbę od 0 do 10.");
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
                        handleAddTransactionToArray(scanner, manager);
                        break;

                    case 6:
                        manager.listAllTransactions();
                        break;

                    case 7:
                        handleRemoveTransaction(scanner, manager);
                        break;

                    case 8:
                        try {
                            String arrayFileName = manager.saveArrayToFile();
                            System.out.println("Zapisano tablicę do pliku: " + arrayFileName);
                        } catch (IllegalStateException | IOException e) {
                            System.out.println("Błąd przy zapisie tablicy: " + e.getMessage());
                        }
                        break;

                    case 9:
                        System.out.print("Podaj nazwę pliku tablicy do wczytania: ");
                        String arrayFileName = scanner.nextLine().trim();
                        try {
                            manager.loadArrayFromFile(arrayFileName);
                            System.out.println("Wczytano tablicę z pliku: " + arrayFileName);
                            System.out.println("Liczba transakcji: " + manager.getTransactionCount());
                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println("Błąd przy wczytywaniu tablicy: " + e.getMessage());
                        }
                        break;

                    case 10:  // Wyjście - zmień z case 8 na case 10
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

    private static void handleAddTransactionToArray(Scanner scanner, BudgetManager manager) {
        try {
            // Używamy tej samej logiki co w handleAddTransaction
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
                    break;
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

            manager.addTransaction(new Transaction(amount, category, date, type));
            System.out.println("Transakcja dodana do tablicy (" + manager.getTransactionCount() + "/10).");

        } catch (IllegalStateException e) {
            System.out.println("Błąd: " + e.getMessage()); // wyłapie "Brak miejsca - maksymalnie 10 transakcji"
        }
    }

    private static void handleRemoveTransaction(Scanner scanner, BudgetManager manager) {
        if (manager.getTransactionCount() == 0) {
            System.out.println("Brak transakcji do usunięcia.");
            return;
        }

        // Wyświetl dostępne transakcje
        manager.listAllTransactions();

        while (true) {
            System.out.print("Podaj numer transakcji do usunięcia (1-" + manager.getTransactionCount() + "): ");
            try {
                int userChoice = Integer.parseInt(scanner.nextLine().trim());
                int arrayIndex = userChoice - 1; // użytkownik podaje 1-based, tablica to 0-based

                manager.removeTransactionByIndex(arrayIndex);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Wprowadź poprawną liczbę.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Błąd: " + e.getMessage());
            }
        }
    }
}
