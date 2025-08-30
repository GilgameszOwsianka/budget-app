# Budget App – Etap 1.1 (OOP + pliki, jedna transakcja)

Prosta aplikacja konsolowa do zarządzania **jedną transakcją** (bez kolekcji).
Na tym etapie ćwiczymy **OOP** i **operacje na plikach**.

---

## 📂 Struktura projektu

src/
├─ Transaction.java
├─ BudgetManager.java
├─ BudgetApp.java
└─ BudgetManagerTest.java # proste testy automatyczne (assert)
transaction.txt # plik danych (tworzony przy zapisie)
docs/
└─ manual-tests/
├─ budget_app_manual_tests_etap_1_1.csv
└─ budget_app_manual_tests_etap_1_1.md


---

## ▶️ Uruchomienie

### Kompilacja
```bash
javac src/*.java -d out

Uruchomienie aplikacji:
java -cp out BudgetApp

Uruchomienie prostych testów automatycznych (z włączonymi asercjami):
java -ea -cp out BudgetManagerTest
⚠️ Flaga -ea włącza asercje (assert) – bez niej testy nie zadziałają.

🧪 Testy manualne

Pełny zestaw przypadków testowych dla Etapu 1.1 dostępny jest w katalogu:
📄 CSV
📝 Markdown

ℹ️ Uwaga
Brak walidacji kategorii i typu transakcji jest świadomym ograniczeniem Etapu 1.1.
Walidacje zostaną dodane w Etapie 1.2e.
