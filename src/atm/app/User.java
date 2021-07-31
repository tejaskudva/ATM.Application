package atm.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String firstName;
    private String lastName;
    private String uid;
    private byte pinHash[];                     //MD5 Hash of the User's Pin
    private ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank bank){
        this.firstName = firstName;
        this.lastName = lastName;

        //Hashing PIN using MD5 algorithm
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error! NoSuchAlgorithm Exception");
            e.printStackTrace();
            System.exit(1);
        }

        //Generate UID
        this.uid = bank.getNewUserId();

        //Create an empty list of accounts
        this.accounts = new ArrayList<Account>();

        //Print log message
        System.out.printf("\n\nNew user %s %s with ID %s created.", this.firstName, this.lastName, uid);
    }

    public void addAccount(Account act){
        this.accounts.add(act);
    }

    public String getUid() {
        return this.uid;
    }

    //Validates PIN
    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error! NoSuchAlgorithm Exception");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountSummary() {
        System.out.println("\n\n" + firstName + "'s Account Summary");

        for(int i=0; i<accounts.size(); i++){
            System.out.printf("  %d) %s\n", i+1, this.accounts.get(i).getSummaryLine());
        }
    }

    public int numAccounts() {
        return accounts.size();
    }


    public void printAccTransHistory(int accIndex) {
        accounts.get(accIndex).printTransHistory();
    }

    public double getAccBalance(int fromAcc) {
        return accounts.get(fromAcc).getBalance();
    }

    public String getAccUid(int accIndex) {
        return accounts.get(accIndex).getUid();
    }

    public void addAccTrans(int accIndex, double amount, String format) {
        accounts.get(accIndex).addTrans(amount, format);
    }
}
