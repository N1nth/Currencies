package com.n1nth.currencies.Data;

public class Valute {

    private String id;
    private String charCode;
    private String name;
    private int numCode;
    private int nominal;
    private double value;


    public void setId(String id) {
        this.id = id;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public String getId() {
        return id;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public int getNumCode() {
        return numCode;
    }

    public int getNominal() {
        return nominal;
    }

    public double getValue() {
        return value;
    }
}
