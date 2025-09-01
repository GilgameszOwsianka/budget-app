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
Kompilacja

javac src/*.java -d out

Uruchomienie aplikacji

java -cp out BudgetApp

Uruchomienie prostych testów automatycznych (z włączonymi asercjami)

java -ea -cp out BudgetManagerTest

⚠️ Flaga -ea włącza asercje (assert) – bez niej testy nie zadziałają.
🧪 Testy manualne

Pełny zestaw przypadków testowych dla Etapu 1.1 dostępny jest w katalogu:

    📄 CSV – do Excela / Google Sheets

    📝 Markdown – czytelna tabela na GitHubie

⚠️ Uwaga dotycząca walidacji danych

Na tym etapie program sprawdza poprawność tylko kwoty transakcji.
Przy niepoprawnej kwocie użytkownik otrzyma komunikat z informacją o prawidłowym formacie.

Walidacja kwoty obejmuje:

    Kwota musi być liczbą dodatnią

    Maksymalnie 2 miejsca po przecinku

    Akceptowany separator dziesiętny: kropka lub przecinek

Brak walidacji kategorii i typu transakcji (income/expense) jest świadomym ograniczeniem Etapu 1.1.
Walidacja daty i typu transakcji zostanie dodana w kolejnych etapach.
ℹ️ Uwagi dodatkowe

    Przy wprowadzaniu niepoprawnych danych (np. kwota ≤ 0 lub niepoprawny format liczby) aplikacja wyświetla przyjazny komunikat i pozwala ponowić próbę.

    Wszystkie operacje są potwierdzane komunikatem w konsoli (np. "Transakcja została ustawiona", "Zapisano do pliku").