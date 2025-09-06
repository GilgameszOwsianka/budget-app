import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BudgetManager {
    // STARA funkcjonalność - zostaje bez zmian
    private Transaction transaction;

    // NOWE
    private Transaction[] transactions = new Transaction[10];
    private int transactionCount = 0;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public String saveToFile() throws IOException {
        if (transaction == null) {
            throw new IllegalStateException("Brak ustawionej transakcji do zapisania.");
        }

        // Tworzymy znacznik czasu w formacie YYYYMMDD_HHmmss
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "transaction_" + timestamp + ".txt";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(transaction);
        }

        System.out.println("Transakcja zapisana do pliku: " + fileName);
        return fileName;
    }

    public void loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            transaction = (Transaction) ois.readObject();
        }
    }

    // NOWE METODY dla tablicy

    public void addTransaction(Transaction transaction) {
        if (transactionCount >= transactions.length) {
            throw new IllegalStateException("Brak miejsca - maksymalnie 10 transakcji");
        }
        transactions[transactionCount] = transaction;
        transactionCount++;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public Transaction getTransactionByIndex(int index) {
        if (index < 0 || index >= transactionCount) {
            throw new IndexOutOfBoundsException("Niepoprawny indeks: " + index);
        }
        return transactions[index];
    }

    public void listAllTransactions() {
        if (transactionCount == 0) {
            System.out.println("Brak transakcji w tablicy.");
            return;
        }

        System.out.println("\n=== Wszystkie transakcje (" + transactionCount + "/10) ===");
        for (int i = 0; i < transactionCount; i++) {
            System.out.println((i + 1) + ". " + transactions[i]);
        }
        System.out.println("=========================================");
    }

    public void removeTransactionByIndex(int index) {
        if (index < 0 || index >= transactionCount) {
            throw new IndexOutOfBoundsException("Niepoprawny indeks: " + index + ". Dostępne indeksy: 0-" + (transactionCount - 1));
        }

        // Przesuń wszystkie elementy w lewo (od usuwanego indeksu)
        for (int i = index; i < transactionCount - 1; i++) {
            transactions[i] = transactions[i + 1];
        }

        // Wyczyść ostatni element i zmniejsz licznik
        transactions[transactionCount - 1] = null;
        transactionCount--;

        System.out.println("Transakcja usunięta. Pozostało: " + transactionCount + "/10");
    }

    public String saveArrayToFile() throws IOException {
        if (transactionCount == 0) {
            throw new IllegalStateException("Brak transakcji w tablicy do zapisania.");
        }

        // Tworzymy nazwę pliku z prefiksem "array"
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "transactions_array_" + timestamp + ".txt";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            // Zapisz liczbę transakcji
            oos.writeInt(transactionCount);
            // Zapisz wszystkie transakcje
            for (int i = 0; i < transactionCount; i++) {
                oos.writeObject(transactions[i]);
            }
        }

        System.out.println("Tablica transakcji zapisana do pliku: " + fileName);
        return fileName;
    }

    public void loadArrayFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Wczytaj liczbę transakcji
            int count = ois.readInt();

            // Wczytaj wszystkie transakcje
            transactionCount = count;
            for (int i = 0; i < transactionCount; i++) {
                transactions[i] = (Transaction) ois.readObject();
            }
        }
    }
}
