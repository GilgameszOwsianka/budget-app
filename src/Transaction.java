import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    private double amount;
    private String category;
    private LocalDate date;
    private String type;

    public Transaction(double amount, String category, LocalDate date, String type) {
        // Walidacja kwoty
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        this.amount = amount;
        this.category = category;
        this.date = date;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + ": " + amount + " , kategoria: " + category + ", data: " + date;
    }
}
