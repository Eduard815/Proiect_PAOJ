# Proiect_PAOJ

## Descriere
Aplicatie bancara de tip consola ce permite gestionarea clientilor, conturilor bancare, tranzactiilor si cardurilor, avand ca scop simularea actiunilor din cadrul unui sistem bancar.

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
- service - contine clasele de tip serviciu ce implementeaza functionalitatile aplicatiei bancare
    - ClientService - operatii specifice clientilor precum creare clienti, adaugare conturi, afisari si trimiteri de notificari
    - AccountService - operatii specifice conturilor precum transferuri, generare extras de cont, cautare tranzactii si afisari de plati
    - CardService - gestionarea cardurilor, crearea de carduri de debit si de credit pentru un anumit cont
    - TransactionService - gestionarea tranzactiilor, crearea tranzactiilor si cautare
    - CreditService - rambursare credit pe card
- util - contine clase utilizate pentru generari de identificatori unici pentru conturi, carduri, tranzactii, clienti si iban-uri
- main - punctul central al aplicatiei cu meniu interactiv de tip consola ce expune functionalitatile aplicatiei

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
