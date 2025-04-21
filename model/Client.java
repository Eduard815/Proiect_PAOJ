package model;

import util.ClientIdGenerator;

import java.util.Set;
import java.util.HashSet;

public class Client{
    private String id;
    private String name;
    private Address address;
    private Set<Account> accounts;

    public Client(String name, Address address){
        this.id = ClientIdGenerator.generateId();
        this.name = name;
        this.address = address;
        this.accounts = new HashSet<>();
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Address getAddress(){
        return address;
    }

    public Set<Account> getAccounts(){
        return accounts;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    @Override
    public String toString(){
        return " The client " + name + " address " + address + " owning " + accounts.size() + " accounts.";
    }
}