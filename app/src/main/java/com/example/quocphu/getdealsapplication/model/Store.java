package com.example.quocphu.getdealsapplication.model;

public class Store {
    private String id_store;
    private String id_user;
    private String nameStore;
    private String address;
    private String phoneStore;
    private String location;

    public Store(){

    }

    public String getId_store() {
        return id_store;
    }

    public void setId_store(String id_store) {
        this.id_store = id_store;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneStore() {
        return phoneStore;
    }

    public void setPhoneStore(String phoneStore) {
        this.phoneStore = phoneStore;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id_store='" + id_store + '\'' +
                ", id_user='" + id_user + '\'' +
                ", nameStore='" + nameStore + '\'' +
                ", address='" + address + '\'' +
                ", phoneStore='" + phoneStore + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
