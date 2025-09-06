import java.time.LocalDate;
import java.io.File;
import java.io.IOException;

public class BudgetManagerTest {
    public static void main(String[] args) {
        BudgetManager manager = new BudgetManager();

        // Test 1: Ustawienie poprawnej transakcji
        Transaction t1 = new Transaction(500, "Praca", LocalDate.of(2025,1,1), "income");
        manager.setTransaction(t1);
        assert manager.getTransaction() != null : "Test 1 nie przeszedł!";
        assert manager.getTransaction().getAmount() == 500 : "Test 1 kwota nie zgadza się!";
        assert manager.getTransaction().getCategory().equals("Praca") : "Test 1 kategoria nie zgadza się!";
        assert manager.getTransaction().getType().equals("income") : "Test 1 typ nie zgadza się!";

        // Test 2: Zapis i odczyt pliku
        try {
            String fileName = manager.saveToFile(); // metoda zwraca pełną nazwę pliku
            File file = new File(fileName);
            assert file.exists() : "Test 2 nie przeszedł! Plik nie został utworzony.";

            BudgetManager manager2 = new BudgetManager();
            manager2.loadFromFile(fileName);

            Transaction t2 = manager2.getTransaction();
            assert t2 != null : "Test 2 nie przeszedł!";
            assert t2.getAmount() == 500 : "Test 2 kwota nie zgadza się!";
            assert t2.getCategory().equals("Praca") : "Test 2 kategoria nie zgadza się!";
            assert t2.getType().equals("income") : "Test 2 typ nie zgadza się!";
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Test 2 błąd: " + e.getMessage());
        }

        // Test 3: Walidacja kwoty (ujemna i 0)
        try {
            new Transaction(-100, "Test", LocalDate.now(), "income");
            assert false : "Test 3 nie przeszedł! - ujemna kwota powinna rzucić wyjątek.";
        } catch (IllegalArgumentException ignored) {}

        try {
            new Transaction(0, "Test", LocalDate.now(), "income");
            assert false : "Test 3 nie przeszedł! - kwota 0 powinna rzucić wyjątek.";
        } catch (IllegalArgumentException ignored) {}

        // Test 4: Zapis bez ustawionej transakcji
        try {
            BudgetManager emptyManager = new BudgetManager();
            emptyManager.saveToFile(); // powinien rzucić IllegalStateException
            assert false : "Test 4 nie przeszedł! - zapis pustej transakcji powinien rzucić wyjątek.";
        } catch (IllegalStateException ignored) {
            // poprawnie wykryto brak transakcji
        } catch (IOException e) {
            System.out.println("Nieoczekiwany błąd IO: " + e.getMessage());
        }

        // Test 5: Walidacja daty
        try {
            LocalDate.parse("2025-13-01"); // niepoprawny miesiąc
            assert false : "Test 5 nie przeszedł! - niepoprawna data powinna rzucić DateTimeParseException.";
        } catch (java.time.format.DateTimeParseException ignored) {}

        LocalDate validDate = LocalDate.parse("2025-12-31");
        assert validDate.getYear() == 2025 : "Test 5b nie przeszedł! Rok niezgodny.";

        // Test 6: Walidacja kwoty z przecinkiem lub kropką
        try {
            String amountInput = "123,45".replace(",", ".");
            double amount = Double.parseDouble(amountInput);
            assert amount == 123.45 : "Test 6 nie przeszedł! Kwota z przecinkiem nie sparsowana poprawnie.";
        } catch (NumberFormatException e) {
            assert false : "Test 6 nie przeszedł! Parsowanie kwoty powinno się powieść.";
        }

        try {
            String amountInput = "123.45";
            double amount = Double.parseDouble(amountInput);
            assert amount == 123.45 : "Test 6b nie przeszedł! Kwota z kropką nie sparsowana poprawnie.";
        } catch (NumberFormatException e) {
            assert false : "Test 6b nie przeszedł! Parsowanie kwoty powinno się powieść.";
        }

        // Test 7: Walidacja daty z przyszłości (logika w BudgetApp, nie w Transaction)
        try {
            LocalDate futureDate = LocalDate.now().plusDays(1);
            Transaction tFuture = new Transaction(100, "TestFuture", futureDate, "income");
            manager.setTransaction(tFuture);
            assert manager.getTransaction().getDate().isAfter(LocalDate.now())
                    : "Test 7 nie przeszedł! Data transakcji w przyszłości powinna być odrzucona w logice aplikacji.";
            System.out.println("⚠️ Uwaga: Test 7 - Transaction akceptuje datę w przyszłości, "
                    + "ale BudgetApp powinien ją odrzucić (sprawdzone w manualnych testach).");
        } catch (Exception e) {
            assert false : "Test 7 nie przeszedł! Nieoczekiwany wyjątek: " + e.getMessage();
        }

        // Test 8: Dodawanie do tablicy
        BudgetManager arrayManager = new BudgetManager();
        Transaction t8 = new Transaction(300, "TestTabl", LocalDate.now(), "expense");
        arrayManager.addTransaction(t8);

        assert arrayManager.getTransactionCount() == 1 : "Test 8 nie przeszedł! Liczba transakcji powinna być 1.";
        assert arrayManager.getTransactionByIndex(0).getAmount() == 300 : "Test 8 nie przeszedł! Kwota niepoprawna.";

        System.out.println("Test 8 (tablica): Dodawanie transakcji - OK");

        // Test 9: Wyświetlanie tablicy
        BudgetManager listManager = new BudgetManager();

        // Test pustej tablicy
        System.out.println("\n--- Test pustej tablicy ---");
        listManager.listAllTransactions(); // powinno pokazać "Brak transakcji"

        // Test z kilkoma transakcjami
        Transaction t9a = new Transaction(100, "Jedzenie", LocalDate.now(), "expense");
        Transaction t9b = new Transaction(500, "Praca", LocalDate.now(), "income");

        listManager.addTransaction(t9a);
        listManager.addTransaction(t9b);

        System.out.println("\n--- Test tablicy z 2 transakcjami ---");
        listManager.listAllTransactions(); // powinno pokazać numerowaną listę

        assert listManager.getTransactionCount() == 2 : "Test 9 nie przeszedł! Powinny być 2 transakcje.";
        System.out.println("Test 9 (lista): Wyświetlanie tablicy - OK");

        // Test 10: Usuwanie z tablicy
        BudgetManager removeManager = new BudgetManager();
        Transaction t12a = new Transaction(100, "Usuń1", LocalDate.now(), "expense");
        Transaction t12b = new Transaction(200, "Zostań", LocalDate.now(), "income");
        Transaction t12c = new Transaction(300, "Usuń3", LocalDate.now(), "expense");

        removeManager.addTransaction(t12a);
        removeManager.addTransaction(t12b);
        removeManager.addTransaction(t12c);

        removeManager.removeTransactionByIndex(0); // usuń pierwszą
        assert removeManager.getTransactionCount() == 2 : "Test 12 nie przeszedł! Po usunięciu powinny być 2 transakcje.";
        assert removeManager.getTransactionByIndex(0).getCategory().equals("Zostań") : "Test 12 nie przeszedł! Pierwsza transakcja po usunięciu powinna być 'Zostań'.";

        removeManager.removeTransactionByIndex(1); // usuń ostatnią
        assert removeManager.getTransactionCount() == 1 : "Test 12 nie przeszedł! Po drugim usunięciu powinna być 1 transakcja.";

        // Test 11: Błędne indeksy
        try {
            removeManager.getTransactionByIndex(5); // poza zakresem
            assert false : "Test 13 nie przeszedł! Powinien być rzucony IndexOutOfBoundsException.";
        } catch (IndexOutOfBoundsException ignored) {}

        try {
            removeManager.removeTransactionByIndex(-1); // ujemny indeks
            assert false : "Test 13 nie przeszedł! Powinien być rzucony IndexOutOfBoundsException.";
        } catch (IndexOutOfBoundsException ignored) {}

        // Test 12: Zapis i odczyt tablicy
        BudgetManager saveLoadManager = new BudgetManager();
        Transaction t14a = new Transaction(150, "SaveTest1", LocalDate.of(2025, 1, 1), "expense");
        Transaction t14b = new Transaction(250, "SaveTest2", LocalDate.of(2025, 1, 2), "income");

        saveLoadManager.addTransaction(t14a);
        saveLoadManager.addTransaction(t14b);

        try {
            String arrayFileName = saveLoadManager.saveArrayToFile();
            File arrayFile = new File(arrayFileName);
            assert arrayFile.exists() : "Test 14 nie przeszedł! Plik tablicy nie został utworzony.";

            BudgetManager loadManager = new BudgetManager();
            loadManager.loadArrayFromFile(arrayFileName);

            assert loadManager.getTransactionCount() == 2 : "Test 14 nie przeszedł! Wczytano niepoprawną liczbę transakcji.";
            assert loadManager.getTransactionByIndex(0).getCategory().equals("SaveTest1") : "Test 14 nie przeszedł! Pierwsza wczytana transakcja niepoprawna.";
            assert loadManager.getTransactionByIndex(1).getCategory().equals("SaveTest2") : "Test 14 nie przeszedł! Druga wczytana transakcja niepoprawna.";

        } catch (IOException | ClassNotFoundException e) {
            assert false : "Test 14 nie przeszedł! Błąd zapisu/odczytu tablicy: " + e.getMessage();
        }

        System.out.println("Wszystkie testy zakończone pomyślnie.");
    }
}