# Proiect_PAOJ

## Descriere
Aplicatie bancara de tip consola ce permite gestionarea clientilor, conturilor bancare, tranzactiilor si cardurilor, avand ca scop simularea actiunilor din cadrul unui sistem bancar. Aplicatia utilizeaza o baza de date pentru a stoca informatiile despre clienti, conturile si cardurile lor, tranzactii si notificari. Sunt implementate operatii de tip CRUD pentru tabele clients, accounts, cards, transactions si notifications create. Efectuarea primelor 20 de operatii este retinuta intr-un fisier de tip csv.

## Arhitectura
- model - contine totalitatea claselor ce definesc entitatile sistemului:
    - Client - reprezinta un client bancar cu nume, adresa si o colectie de conturi
    - Address - clasa auxiliara folosita pentru crearea unei adrese a unui client
    - Account - clasa abstracta ce reprezinta un cont bancar generic
    - CurrentAccount - Cont curent cu functionalitatea de overdraft
    - SavingsAccount - Cont de economii cu dobanda
    - Card - clasa abstracta ce reprezinta un card bancar de credit sau de debit, legat de un cont
    - DebitCard - permite plati directe, transferuri, accesand si modificand fondurile contului
    - CreditCard - permite plati intr-o limita de credit, cu dobanda lunara si ramburs
    - Transaction - model pentru o tranzactie
    - TransactionType - enum - contine tipurile de tranzactii: DEPOSIT, WITHDRAWAL, TRANSFER, CARD_PAYMENT, INTEREST
    - AccountStatement - permite generarea unui extras de cont
- service - contine clasele de tip serviciu ce implementeaza functionalitatile aplicatiei bancare
    - ClientService - operatii specifice clientilor precum creare clienti, adaugare conturi, afisari si trimiteri de notificari
    - AccountService - operatii specifice conturilor precum transferuri, generare extras de cont, cautare tranzactii si afisari de plati
    - CardService - gestionarea cardurilor, crearea de carduri de debit si de credit pentru un anumit cont
    - TransactionService - gestionarea tranzactiilor, crearea tranzactiilor si cautare
    - CreditService - rambursare credit pe card
    - NotificationService - gestionarea notificarilor
    - AuditService - crearea logicii pentru scrierea intr-un fisier csv
- util - contine clase utilizate pentru generari de identificatori unici pentru conturi, carduri, tranzactii, clienti si iban-uri
- main - punctul central al aplicatiei cu meniu interactiv de tip consola ce expune functionalitatile aplicatiei
- dao - contine clase care asigura memorarea si actualizarea datelor specifice in tabelele client, accounts, cards, transactions si notifications
- sql - Seed.sql - contine codul sql pentru crearea tabelelor clients, accounts, cards, transactions, notifications specifice bazei de date
- audit.csv - fisier pentru retinerea primelor 20 de actiuni

## Functionalitati implementate
1. Create new client - adaugarea unui nou client in sistem
2. Add account to client - crearea si atribuirea de cont unui client
3. Show all clients - afisarea tuturor clientilor alaturi de atributele lor specifice
4. Show client accounts - expunerea conturilor unui client ales
5. Show account with maximum balance - redarea contului unui client cu cele mai mari fonduri
6. Show client's active/inactive cards - afisarea cardurilor active si inactive ale unui client
7. Send notification to client - trimiterea unei notificari unui client
8. Show client's notifications - expunerea notificarilor primite de catre un client
9. Transfer between accounts - realizarea de transfer intre conturile aceluiasi client sau intre doi clienti
10. Check inactive accounts - verificarea conturilor inactive (care nu au realizat vreo tranzactie in ultima luna)
11. Generate account statement - generarea unui extras de cont
12. Search transactions after date - gasirea tranzactiilor realizate dupa o data exacta
13. Show debit card payments - expunerea platilor realizate de un client cu cardul
14. Increase overdraft limit - cresterea valorii overdraft-ului pentru un cont curent
15. Generate debit card - crearea unui card de debit pentru un client
16. Generate credit card - crearea unui card de credit pentru un client
17. Activate/deactivate card - activarea si dezactivarea unui card al unui client
18. Make a transaction - realizarea unei tranzactii din tipurile posibile
19. Search transactions on exact date - gasirea tranzactiilor realizate pe o data exacta
20. Repay credit - rambursarea creditului

## Operatii CRUD
Aceste operatii au fost implementate pentru a intregi functionalitatile aplicatiei in raport cu baza de date. Celelalte operatii CRUD se regasesc deja in prima parte a functionalitatilor implementate.
21. Update client - actualizarea datelor unui client
22. Delete client - stergerea unui client din sistem
23. Update account - actualizarea unui cont existent
24. Delete account - stergerea unui cont existent
25. Update transaction - actualizarea unei tranzactii
26. Delete transaction - stergerea unei tranzactii
27. Update notification - actualizarea unei notificari
28. Delete notification - stergerea unei notificari
29. Update card - actualizarea unui card
30. Delete card - stergerea unui card
