package sample;


import java.io.Serializable;

public class Cars implements Serializable {
    private int id;
    private String reg;
    private int year;
    private String color1;
    private String color2;
    private String color3;
    private String maker;
    private String model;
    private int price;
    private int quantity;

    public Cars() {

    }

    public Cars(String reg, int year, String color1, String color2, String color3, String maker, String model, int price, int quantity) {
        this.reg = reg;
        this.year = year;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.maker = maker;
        this.model = model;
        this.price = price;
        this.quantity = quantity;

    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}