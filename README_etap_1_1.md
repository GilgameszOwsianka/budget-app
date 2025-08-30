# Budget App – Etap 1.1 (OOP + pliki, jedna transakcja)

Prosta aplikacja konsolowa do zarządzania **jedną transakcją** (bez kolekcji).
Na tym etapie ćwiczymy OOP i operacje na plikach.

## Struktura
```
src/
 ├─ Transaction.java
 ├─ BudgetManager.java
 ├─ BudgetApp.java
 └─ BudgetManagerTest.java   # proste testy automatyczne (assert)
transaction.txt             # plik danych (tworzony przy zapisie)
```

## Uruchomienie
Skompiluj i uruchom w katalogu głównym projektu:

### Kompilacja
```bash
javac src/*.java -d out
```

### Uruchomienie aplikacji
```bash
java -cp out BudgetApp
```

### Uruchomienie prostych testów automatycznych (z włączonymi asercjami)
```bash
java -ea -cp out BudgetManagerTest
```

> Flaga `-ea` włącza asercje (`assert`) – bez niej testy nie zadziałają.

## Git – podstawowe komendy
```bash
git init
git add .
git commit -m "Etap 1.1 – OOP + pliki (jedna transakcja)"
git remote add origin https://github.com/<twoj_login>/budget-app.git
git branch -M main
git push -u origin main
```

## Testy manualne
Zobacz plik CSV z kompletem przypadków testowych dla Etapu 1.1:
- `docs/manual-tests/budget_app_manual_tests_etap_1_1.csv` (zalecana lokalizacja w repo)

## Uwaga
Brak walidacji kategorii i typu transakcji jest **świadomym ograniczeniem** Etapu 1.1.
Walidacje dodamy w Etapie 1.2e.
