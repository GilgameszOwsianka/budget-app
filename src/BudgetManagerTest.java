import java.time.LocalDate;

public class BudgetManagerTest {
    public static void main(String[] args) {
        BudgetManager manager = new BudgetManager();

        // Test 1: Ustawienie transakcji
        Transaction t1 = new Transaction(500, "Praca", LocalDate.of(2025,1,1), "income");
        manager.setTransaction(t1);
        assert manager.getTransaction() != null : "Test 1 nie przeszedł!";

        // Test 2: Zapis i odczyt pliku
        try {
            manager.saveToFile("transaction.txt");

            BudgetManager manager2 = new BudgetManager();
            manager2.loadFromFile("transaction.txt");

            assert manager2.getTransaction() != null : "Test 2 nie przeszedł!";
            assert manager2.getTransaction().getAmount() == 500 : "Test 2 kwota nie zgadza się!";
        } catch (Exception e) {
            System.out.println("Test 2 błąd: " + e.getMessage());
        }

        System.out.println("Wszystkie testy zakończone.");
    }
}
