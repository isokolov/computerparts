package com.isokolov.parts.entity;

import javax.persistence.*;

@Entity
@Table(name = "part", schema = "test")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "required")
    private boolean required;

    @Column(name = "amount")
    private int amount;

    public Item() {
    }

    public Item(String name, boolean required, int amount) {
        this.name = name;
        this.required = required;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
