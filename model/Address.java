package model;

public class Address {
    private String city;
    private String street;
    private int number;
    private String postalCode;

    public Address(String city, String street, int number, String postalCode){
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
    }

    public String getCity(){
        return city;
    }
    public String getStreet(){
        return street;
    }
    public int getNumber(){
        return number;
    }
    public String getPostalCode(){
        return postalCode;
    }

    @Override
    public String toString(){
        return city + " " + street + " " + number + " " + postalCode;
    }
    
}
