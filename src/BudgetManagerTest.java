import java.time.LocalDate;

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
            manager.saveToFile("transaction.txt");

            BudgetManager manager2 = new BudgetManager();
            manager2.loadFromFile("transaction.txt");

            Transaction t2 = manager2.getTransaction();
            assert t2 != null : "Test 2 nie przeszedł!";
            assert t2.getAmount() == 500 : "Test 2 kwota nie zgadza się!";
            assert t2.getCategory().equals("Praca") : "Test 2 kategoria nie zgadza się!";
            assert t2.getType().equals("income") : "Test 2 typ nie zgadza się!";
        } catch (Exception e) {
            System.out.println("Test 2 błąd: " + e.getMessage());
        }

        // Test 3: Walidacja kwoty (ujemna i niepoprawny format)
        try {
            new Transaction(-100, "Test", LocalDate.now(), "income");
            assert false : "Test 3 nie przeszedł! - ujemna kwota powinna rzucić wyjątek.";
        } catch (IllegalArgumentException ignored) {}

        try {
            new Transaction(0, "Test", LocalDate.now(), "income");
            assert false : "Test 3 nie przeszedł! - kwota 0 powinna rzucić wyjątek.";
        } catch (IllegalArgumentException ignored) {}

        System.out.println("Wszystkie testy zakończone pomyślnie.");
    }
}
