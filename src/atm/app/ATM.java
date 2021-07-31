package atm.app;

import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank bank = new Bank("Standard Chartered");

        //Let's add a user to the bank which also creates a savings account
        User u1 = bank.addUser("Tejas", "Kudva", "1234");

        //Let's add a checking account
        Account ac1 = new Account("Checking", u1, bank);
        u1.addAccount(ac1);
        bank.addAccount(ac1);

        User curUser;
        while(true){

            //stay in login prompt until successful
            curUser = ATM.mainMenuPrompt(bank, sc);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);
        }
    }

    //Print menu for User actions
    private static void printUserMenu(User curUser, Scanner sc) {
        //print a summary of the User's accounts
        curUser.printAccountSummary();

        //init
        int choice;

        //User Menu
        do{
            System.out.println("\nWelcome! What would you like to do");
            System.out.println("\n  1. Show transaction history\n  2. Withdrawal\n  3. Deposit\n  4. Transfer\n  5. Quit\n");
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            if(choice < 1 || choice > 5){
                System.out.println("\nWrong choice!");
            }

        }while(choice < 1 || choice > 5);

        //process the choice
        switch(choice){
            case 1:
                ATM.showTransHistory(curUser, sc);
                break;
            case 2:
                ATM.withdraw(curUser, sc);
                break;
            case 3:
                ATM.deposit(curUser, sc);
                break;
            case 4:
                ATM.transfer(curUser, sc);
                break;
        }

        //redisplay the menu unless the user chooses otherwise
        if(choice != 5){
            ATM.printUserMenu(curUser, sc);
        }
    }

    //Deposit amount into Account
    private static void deposit(User curUser, Scanner sc) {
        //init
        int toAcc;
        double amount;
        double balance;
        String memo;

        //get the acc to transfer to
        do{
            System.out.printf("\nEnter the (1-%d) of the account to deposit to: ", curUser.numAccounts());
            toAcc = sc.nextInt()-1;

            if(toAcc < 0 || toAcc >= curUser.numAccounts()){
                System.out.println("Invalid choice. Please try again!");
            }
        }while(toAcc < 0 || toAcc >= curUser.numAccounts());
        balance = curUser.getAccBalance(toAcc);

        //get the amount to transfer
        do {
            System.out.print("\nEnter the amount to deposit: ");
            amount = sc.nextDouble();
            if (amount <= 0) {
                System.out.println("\n Invalid Amount! Must be greater than ZERO!");
            }
        }while (amount <= 0);

        sc.nextLine();

        System.out.print("Enter any deposit memo: ");
        memo = sc.nextLine();

        curUser.addAccTrans(toAcc, amount, memo);
    }

    //Withdraw amount from account
    private static void withdraw(User curUser, Scanner sc) {
        //init
        int fromAcc;
        double amount;
        double balance;
        String memo;

        //get the acc to transfer from
        do{
            System.out.printf("\nEnter the (1-%d) of the account to withdraw from: ", curUser.numAccounts());
            fromAcc = sc.nextInt()-1;

            if(fromAcc < 0 || fromAcc >= curUser.numAccounts()){
                System.out.println("Invalid choice. Please try again!");
            }
        }while(fromAcc < 0 || fromAcc >= curUser.numAccounts());
        balance = curUser.getAccBalance(fromAcc);

        //get the amount to transfer
        do {
            System.out.print("\nEnter the amount to withdraw: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("\n Invalid Amount! Must be greater than ZERO!");
            } else if (amount > balance) {
                System.out.println("\nInvalid amount! Insufficient Balance for that transaction!");
            }
        }while (amount < 0 || amount > balance);

        sc.nextLine();

        System.out.print("Enter any withdrawal memo: ");
        memo = sc.nextLine();

        curUser.addAccTrans(fromAcc, -1*amount, memo);
    }

    //Transferring amount from 1 account to another
    private static void transfer(User curUser, Scanner sc) {
        //init
        int fromAcc;
        int toAcc;
        double amount;
        double balance;

        //get the acc to transfer from
        do{
            System.out.printf("\nEnter the (1-%d) of the account to transfer from: ", curUser.numAccounts());
            fromAcc = sc.nextInt()-1;

            if(fromAcc < 0 || fromAcc >= curUser.numAccounts()){
                System.out.println("Invalid choice. Please try again!");
            }
        }while(fromAcc < 0 || fromAcc >= curUser.numAccounts());
        balance = curUser.getAccBalance(fromAcc);

        //get the acc to transfer to
        do{
            System.out.printf("\nEnter the (1-%d) of the account to transfer to: ", curUser.numAccounts());
            toAcc = sc.nextInt()-1;

            if(toAcc < 0 || toAcc >= curUser.numAccounts()){
                System.out.println("Invalid choice. Please try again!");
            }
        }while(toAcc < 0 || toAcc >= curUser.numAccounts());

        //get the amount to transfer
        do {
            System.out.print("\nEnter the amount to transfer: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("\n Invalid Amount! Must be greater than ZERO!");
            } else if (amount > balance) {
                System.out.println("\nInvalid amount! Insufficient Balance for that transaction!");
            }
        }while (amount < 0 || amount > balance);

        //Finally, do the transfer
        curUser.addAccTrans(fromAcc, -1*amount, String.format("Transfer to account %s", curUser.getAccUid(toAcc)));
        curUser.addAccTrans(toAcc, amount, String.format("Transfer from account %s", curUser.getAccUid(fromAcc)));

    }

    //prints transaction history of selected account
    private static void showTransHistory(User curUser, Scanner sc) {
        int acc;
        do{
            System.out.printf("\nEnter the number (1-%d) of the account whose transactions you want to see: ", curUser.numAccounts());
            acc = sc.nextInt()-1;

            if(acc < 0 || acc >= curUser.numAccounts()){
                System.out.println("Invalid choice. Please try again!");
            }
        }while(acc < 0 || acc >= curUser.numAccounts());

        //print transaction history
        curUser.printAccTransHistory(acc);
    }

    //Prints main menu of ATM
    private static User mainMenuPrompt(Bank bank, Scanner sc) {
        String userId;
        String pin;
        User authUser;
        //prompt user for id/ pin until correct one is reached
        do{
            System.out.println("\n\n---------Welcome to the Bank!---------------");
            System.out.print("\nEnter User ID: ");
            userId = sc.nextLine();
            System.out.print("Enter the PIN: ");
            pin = sc.nextLine();

            //try to get the User Object corresponding to the ID and PIN combo
            authUser = bank.userLogIn(userId, pin);
            if(authUser == null){
                System.out.println("Incorrect User ID/ PIN Combo!");
            }

        }while(authUser == null); //continue looping until successful login

        return authUser;
    }

}
