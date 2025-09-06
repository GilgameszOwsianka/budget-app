# Budget App – Etap 1.2a (OOP + pliki + tablice)

Aplikacja konsolowa do zarządzania **pojedynczą transakcją** oraz **tablicą do 10 transakcji**.  
Na tym etapie ćwiczymy **OOP**, **operacje na plikach** i **tablice**.

## 📂 Struktura projektu

```text
src/
    Transaction.java
    BudgetManager.java
    BudgetApp.java
    BudgetManagerTest.java   # testy automatyczne (assert)
transaction_YYYYMMDD_HHmmss.txt         # pliki pojedynczych transakcji
transactions_array_YYYYMMDD_HHmmss.txt  # pliki tablic transakcji
docs/manual-tests/
    budget_app_manual_tests_etap_1_1.csv
    budget_app_manual_tests_etap_1_1.md
```

## ▶️ Uruchomienie

### Kompilacja:
```bash
javac -encoding UTF-8 -d out src/*.java
```

### Uruchomienie aplikacji:
```bash
java -Dfile.encoding=UTF-8 -cp out BudgetApp
```

### Uruchomienie testów automatycznych (z włączonymi asercjami):
```bash
java -ea -cp out BudgetManagerTest
```

⚠️ Flaga `-ea` włącza asercje (assert) – bez niej testy nie zadziałają.

## 🖥️ Menu aplikacji

```
0. Pomoc
1. Dodaj/ustaw transakcję (pojedyncza)
2. Wyświetl transakcję (pojedyncza)  
3. Zapisz do pliku (pojedyncza)
4. Wczytaj z pliku (pojedyncza)
5. Dodaj transakcję do tablicy
6. Wyświetl wszystkie transakcje z tablicy
7. Usuń transakcję z tablicy
8. Zapisz tablicę do pliku
9. Wczytaj tablicę z pliku
10. Wyjście
```

## 🎯 Funkcjonalności

### **Pojedyncza transakcja (opcje 1-4):**
- ✅ Dodawanie/edycja jednej transakcji w pamięci
- ✅ Zapis/odczyt do pliku `transaction_YYYYMMDD_HHmmss.txt`
- ✅ Pełna walidacja danych

### **Tablica transakcji (opcje 5-9):**
- ✅ Dodawanie do tablicy (max 10 transakcji)
- ✅ Wyświetlanie wszystkich transakcji z numeracją
- ✅ Usuwanie transakcji z tablicy (z przesuwaniem elementów)
- ✅ Zapis/odczyt tablicy do pliku `transactions_array_YYYYMMDD_HHmmss.txt`
- ✅ Obsługa błędów (pełna tablica, niepoprawny indeks)

## ⚠️ Walidacja danych

### Kwota
- musi być liczbą dodatnią, większą od 0
- maksymalnie 2 miejsca po przecinku
- akceptowany separator dziesiętny: kropka `.` lub przecinek `,`
- przy niepoprawnej kwocie komunikaty:
    - `Niepoprawny format kwoty. Poprawny format np. 123.45 lub 123,45`
    - `Kwota musi być większa od 0.`
    - `Kwota może mieć maksymalnie 2 miejsca po przecinku.`

### Data
- format: YYYY-MM-DD
- nie może być w przyszłości
- przy błędzie komunikaty:
    - `Niepoprawny format daty. Użyj YYYY-MM-DD, np. 2025-01-01`
    - `Data nie może być w przyszłości.`

### Typ transakcji
- tylko `"income"` lub `"expense"`
- przy błędzie: `Niepoprawny typ. Wpisz 'income' lub 'expense'.`

### Tablica transakcji
- maksymalnie 10 transakcji
- przy przekroczeniu: `Brak miejsca - maksymalnie 10 transakcji`
- przy błędnym indeksie: `Niepoprawny indeks: X. Dostępne indeksy: 0-Y`

## 🔔 Logowanie akcji

Wszystkie operacje są potwierdzane komunikatem w konsoli:
- `Transakcja została ustawiona`
- `Zapisano do pliku: transaction_20250906_120000.txt`
- `Wczytano z pliku: transaction_20250906_120000.txt`
- `Transakcja dodana do tablicy (3/10).`
- `Transakcja usunięta. Pozostało: 2/10`
- `Tablica transakcji zapisana do pliku: transactions_array_20250906_120000.txt`

## 🧪 Testy manualne

Pełny zestaw przypadków testowych dla Etapu 1.2a dostępny w katalogu `docs/manual-tests/`:
- 📄 **CSV** – do importu w Excel/Google Sheets
- 📝 **Markdown** – czytelna tabela na GitHubie

### Pokrycie testowe:
- ✅ **TC-1.X:** Pojedyncza transakcja (8 przypadków)
- ✅ **TC-2.X:** Wyświetlanie (2 przypadki)
- ✅ **TC-3.X:** Zapis do pliku (2 przypadki)
- ✅ **TC-4.X:** Odczyt z pliku (3 przypadki)
- ✅ **TC-5.X:** Wyjście z aplikacji (1 przypadek)
- ✅ **TC-1.2a-X:** Operacje na tablicy (5 przypadków)

## 🧪 Testy automatyczne (BudgetManagerTest.java)

### Pokrycie testowe:
1. **Pojedyncza transakcja:** ustawienie, zapis/odczyt, walidacja kwoty
2. **Walidacja danych:** ujemna kwota, niepoprawna data, parsing
3. **Obsługa błędów:** zapis bez transakcji, deserializacja
4. **Tablica transakcji:** dodawanie, usuwanie, zapis/odczyt tablicy
5. **Edge cases:** pełna tablica, błędne indeksy, pusta tablica

⚠️ **Uwaga:** Walidacja daty w przyszłości i typu transakcji odbywa się tylko w `BudgetApp`, nie w klasie `Transaction`.

## 📋 Następne etapy

- **Etap 1.2b:** Zamiana tablicy na `ArrayList<Transaction>`
- **Etap 1.2c:** Generyki - klasa `FileManager<T>`
- **Etap 1.2d:** Stream API i programowanie funkcyjne
- **Etap 1.2e:** Lokalizacja (ResourceBundle) i zaawansowana walidacja