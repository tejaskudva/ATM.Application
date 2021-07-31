package atm.app;

import java.util.ArrayList;

public class Account {

    private String name;
    private String uid;
    private User holder;
    private ArrayList<Transaction> transactions;

    //Constructor
    public Account(String name, User holder, Bank bank){
        this.name = name;
        this.holder = holder;

        //Generate uid
        this.uid = bank.getNewAccountId();

        //init transactions
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUid() {
        return this.uid;
    }

    //Get the summary line for the account
    public String getSummaryLine() {
        //get balance
        double balance = this.getBalance();

        //format the summary line, depending on whether the account is negative
        if(balance >= 0){
            return String.format("%s : $%.02f : %s", uid, balance, name);
        }
        else{
            return String.format("%s : ($%.02f) : %s", uid, balance, name);
        }
    }

    public double getBalance() {
        double balance = 0;

        for(Transaction t : transactions){
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction History for account %s", uid);

        for(int i = transactions.size() - 1; i > -1; i--){
            System.out.printf(transactions.get(i).getSummaryLine());
        }

        System.out.println();
    }

    public void addTrans(double amount, String format) {
        Transaction t = new Transaction(amount, this, format);
        transactions.add(t);
    }
}
