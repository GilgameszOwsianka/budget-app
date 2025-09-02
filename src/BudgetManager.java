import java.io.*;

public class BudgetManager {
    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void saveToFile(String fileName) throws IOException {
        if (transaction == null) {
            throw new IllegalStateException("Brak ustawionej transakcji do zapisania.");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(transaction);
        }
    }

    public void loadFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            transaction = (Transaction) ois.readObject();
        }
    }
}
