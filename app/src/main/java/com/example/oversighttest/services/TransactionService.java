package com.example.oversighttest.services;

import com.example.oversighttest.entities.Category;
import com.example.oversighttest.entities.Transaction;
import com.example.oversighttest.network.DummyNetwork;
import com.example.oversighttest.pages.TransactionsPage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class TransactionService {
    DummyNetwork dm;
    private Transaction t;
    private List<Transaction> mTransactionList;

    public TransactionService(DummyNetwork dm){
        this.dm = dm;
    }

    public Transaction addTransaction(Transaction t){
        return dm.createTransaction(t);
    }
    //todo seeTransactions(User user)
    public ArrayList<Transaction> seeTransactions(){
        return dm.getTransactions();
    }
    public void deleteTransaction(Transaction t){
        dm.deleteTransaction(t);
    }


    /**
     * return a sorted list detemined by the code
     * @param code the code that determines how the list is sorted
     * @return a sorted list
     */
    public ArrayList<Transaction> seeSortedTransaction(int code){

        //get all transactions
        ArrayList<Transaction> transactions = dm.getTransactions();

        //create a treemap, contains an key and the value is an arraylist og transactions
        TreeMap<Object, ArrayList<Transaction>> map = new TreeMap<>();


        if (code == TransactionsPage.SORT_BY_AMOUNT){
            //sort by amount
            for (Transaction t : transactions){
                ArrayList<Transaction> transactionsOfAmount = new ArrayList<>();
                if (map.containsKey(t.getAmount())){
                    //there is another transaction with the same amount
                    //update the arraylist so that it contains all transactions of that amount
                    transactionsOfAmount = map.get(t.getAmount());
                }
                //put the arraylist in the map
                transactionsOfAmount.add(t);
                map.put(t.getAmount(), transactionsOfAmount);
            }
        }
        else if (code == TransactionsPage.SORT_BY_CATEGORY){
            //sort by category
            //this is the same as by amount
            for (Transaction t : transactions){
                ArrayList<Transaction> transactionsOfCategory = new ArrayList<>();
                if (map.containsKey(t.getCategory())){
                    transactionsOfCategory = map.get(t.getCategory());
                }
                transactionsOfCategory.add(t);
                map.put(t.getCategory(), transactionsOfCategory);
            }
        }
        else{
            //sort by date
            //this is the same as by amount
            for (Transaction t : transactions){
                ArrayList<Transaction> transactionsOfDate = new ArrayList<>();
                if (map.containsKey(t.getDate())){
                    transactionsOfDate = map.get(t.getDate());
                }
                transactionsOfDate.add(t);
                map.put(t.getDate(), transactionsOfDate);
            }
        }

        //this is the sorted list
        ArrayList<Transaction> sorted = new ArrayList<>();

        //loop through treemap and for each arraylist in there, we put it in the sorted list
        //the nice thing about treemaps is that they automatically sort the data for you according to the key
        for (Object list: map.values()){
            for (Object o: (ArrayList<Object>)list){
                Transaction t = (Transaction) o;
                if (code == TransactionsPage.SORT_BY_AMOUNT  || code == TransactionsPage.SORT_BY_DATE){
                    sorted.add(0,t);
                }
                else{
                    sorted.add(t);
                }
            }
        }
        return sorted;
    }
}
