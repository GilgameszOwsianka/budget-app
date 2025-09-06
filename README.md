# Budget App – Etap 1.1 (OOP + pliki, jedna transakcja)

Prosta aplikacja konsolowa do zarządzania **jedną transakcją** (bez kolekcji).  
Na tym etapie ćwiczymy **OOP** i **operacje na plikach**.

## 📂 Struktura projektu

```text
src/
    Transaction.java
    BudgetManager.java
    BudgetApp.java
    BudgetManagerTest.java   # proste testy automatyczne (assert)
transaction.txt              # plik danych (tworzony przy zapisie)
docs/manual-tests/
    budget_app_manual_tests_etap_1_1.csv
    budget_app_manual_tests_etap_1_1.md

▶️ Uruchomienie

Kompilacja:
java -cp out BudgetApp

Uruchomienie aplikacji:
java -cp out BudgetApp

Uruchomienie prostych testów automatycznych (z włączonymi asercjami):
java -ea -cp out BudgetManagerTest

⚠️ Flaga -ea włącza asercje (assert) – bez niej testy nie zadziałają.

🧪 Testy manualne
Pełny zestaw przypadków testowych dla Etapu 1.1 dostępny jest w katalogu:
📄 CSV – do Excela / Google Sheets
📝 Markdown – czytelna tabela na GitHubie

⚠️ Walidacja danych i obsługa wyjątków
Na tym etapie program sprawdza poprawność danych w interfejsie użytkownika (konsoli):

Kwota
- musi być liczbą dodatnią, większą od 0
- maksymalnie 2 miejsca po przecinku
- akceptowany separator dziesiętny: kropka . lub przecinek ,
- przy niepoprawnej kwocie aplikacja wyświetla komunikaty np.:
    Niepoprawny format kwoty. Poprawny format np. 123.45 lub 123,45
    Kwota musi być większa od 0.
    Kwota może mieć maksymalnie 2 miejsca po przecinku.

Data
- format: YYYY-MM-DD
- nie może być w przyszłości
- przy błędnym formacie lub przyszłej dacie komunikat:
    Niepoprawny format daty. Użyj YYYY-MM-DD, np. 2025-01-01
    Data nie może być w przyszłości.

Typ transakcji
- tylko "income" lub "expense"
- przy błędzie komunikat: Niepoprawny typ. Wpisz 'income' lub 'expense'.

Dodatkowe uwagi
Wszystkie operacje (ustawienie transakcji, zapis, odczyt) są potwierdzane komunikatem w konsoli:
    Transakcja została ustawiona
    Zapisano do pliku
    Wczytano z pliku
Program nie kończy się błędem przy niepoprawnym wprowadzeniu danych – użytkownik ma możliwość poprawienia wartości.

🧪 Testy automatyczne (BudgetManagerTest.java)
Testy obejmują:
1. Ustawienie poprawnej transakcji
2. Zapis i odczyt z pliku
3. Walidacja kwoty (ujemna, 0, max. 2 miejsca po przecinku, przecinek/kropka)
4. Zapis bez ustawionej transakcji (powinien rzucić IllegalStateException)
5. Parsowanie daty (poprawna i niepoprawna, np. miesiąc > 12)
6. Parsowanie kwoty z przecinkiem i kropką

⚠️ Walidacja daty i typu transakcji odbywa się tylko w BudgetApp, nie w klasie Transaction.