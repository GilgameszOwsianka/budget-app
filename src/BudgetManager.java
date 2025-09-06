import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BudgetManager {
    private Transaction transaction;

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
}
