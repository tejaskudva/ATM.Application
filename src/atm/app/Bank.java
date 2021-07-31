package atm.app;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserId(){
        String uid;
        Random rng = new Random();
        int len = 6;
        Boolean nonUnique;

        do{
            uid = "";
            for(int i=0; i<len; i++){
                uid += ((Integer)rng.nextInt(10)).toString();
            }

            //check if it's unique
            nonUnique = false;
            for(User u : this.users){
                if(uid.compareTo(u.getUid()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        }while(nonUnique);

        return uid;
    }

    public String getNewAccountId(){
        String uid;
        Random rng = new Random();
        int len = 10;
        Boolean nonUnique;

        do{
            uid = "";
            for(int i=0; i<len; i++){
                uid += ((Integer)rng.nextInt(10)).toString();
            }

            //check if it's unique
            nonUnique = false;
            for(Account a : this.accounts){
                if(uid.compareTo(a.getUid()) == 0){
                    nonUnique = true;
                    break;
                }
            }

        }while(nonUnique);

        return uid;
    }

    public void addAccount(Account act){
        this.accounts.add(act);
    }

    public User addUser(String firstName, String lastName, String pin){
        //Create a new User object and add it to the list of Users in Bank
        User newUser = new User(firstName, lastName,pin,this);
        this.users.add(newUser);

        //Create a Savings Account for the User and adds it to Bank and Holder lists
        Account newAccount = new Account("Savings", newUser, this);
        this.accounts.add(newAccount);
        newUser.addAccount(newAccount);

        return newUser;
    }

    //Login
    public User userLogIn(String userId, String pin){
        //search through users for ID
        for(User u: users){
            //check for appropriate UID
            if(u.getUid().compareTo(userId) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }

}
