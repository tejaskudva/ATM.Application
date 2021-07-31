package atm.app;

import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, Account inAccount, String memo){
        this(amount, inAccount);
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if(amount >= 0){
            return String.format("\n  %s : $%.02f : %s", this.timestamp.toString(), amount, memo);
        }
        else{
            return String.format("\n  %s : $(%.02f) : %s", this.timestamp.toString(), amount, memo);
        }
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setMemo(String memo){
        this.memo = memo;
    }
}
