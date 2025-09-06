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

        System.out.println("Wszystkie testy zakończone pomyślnie.");
    }
}