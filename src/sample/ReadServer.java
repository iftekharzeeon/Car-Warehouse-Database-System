package sample;

import javafx.application.Platform;
import netUtil.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class ReadServer implements Runnable{
    private NetworkUtil networkUtil;
    public ReadServer(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        Thread thr = new Thread(this);
        thr.start();

    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = networkUtil.read().toString();
                String[] messageArr = message.split("#");
                if (messageArr[0].equalsIgnoreCase("VL")) {
                    String username = messageArr[1];
                    System.out.println(username);
                    boolean flag = Datasource.getInstance().userSearch(username);
                    if (flag) {
                        networkUtil.write("Success");
                    } else {
                        networkUtil.write("Failure");
                    }

                } else if (messageArr[0].equalsIgnoreCase("VC")) {
                    System.out.println("Show car time");
                    ArrayList<Cars> list = (ArrayList<Cars>) Datasource.getInstance().showCars();
                    System.out.println(list.toString());
                    networkUtil.write(list);
                    System.out.println(list.get(0).getReg());
                    System.out.println("Show car sent");
                } else if (messageArr[0].equalsIgnoreCase("VCB")) {
                    System.out.println("Car buying");
                    String reg = messageArr[1];
                    System.out.println("Registration number to buy " + reg);
                    boolean buyFlag = Datasource.getInstance().buyCar(reg);
                    if (buyFlag) {
                        networkUtil.write("BuySuccessful");
                    } else {
                        networkUtil.write("BuyUnsuccessful");
                    }

                } else if (messageArr[0].equalsIgnoreCase("VCSR")) {
                    System.out.println("Reg Searching");
                    String reg = messageArr[1];
                    System.out.println("Registration number to Search " + reg);
                    ArrayList<Cars> list = (ArrayList<Cars>) Datasource.getInstance().regSearch(reg);
                    networkUtil.write(list);
                } else if (messageArr[0].equalsIgnoreCase("VCSM")) {
                    System.out.println("Maker Searching");
                    String maker = messageArr[1];
                    String model = messageArr[2];
                    System.out.println("Car maker " + maker + " and model to search " + model);
                    ArrayList<Cars> list = (ArrayList<Cars>) Datasource.getInstance().makerSearch(maker, model);
                    networkUtil.write(list);
                } else if (messageArr[0].equalsIgnoreCase("AL")) {
                    System.out.println("Admin login");
                    String username = messageArr[1];
                    String password = messageArr[2];
                    boolean flag = Datasource.getInstance().adminSearch(username, password);
                    if (flag) {
                        networkUtil.write("Success");
                    } else {
                        networkUtil.write("Failure");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AC")) {
                    System.out.println("Admin Car show");
                    ArrayList<Cars> list = (ArrayList<Cars>) Datasource.getInstance().showCars();
                    networkUtil.write(list);
                    System.out.println(list.get(0).getReg());
                    System.out.println("Show car sent");
                } else if (messageArr[0].equalsIgnoreCase("AAC")) {
                    System.out.println("Admin adding a car");
                    String reg = messageArr[1];
                    int year = Integer.parseInt(messageArr[2]);
                    String color1 = messageArr[3];
                    String color2 = messageArr[4];
                    String color3 = messageArr[5];
                    String maker = messageArr[6];
                    String model = messageArr[7];
                    int price = Integer.parseInt(messageArr[8]);
                    int quantity = Integer.parseInt(messageArr[9]);
                    boolean flag = Datasource.getInstance().addCar(reg, year, color1, color2, color3, maker, model, price, quantity);
                    System.out.println(flag);
                    if (flag) {
                        networkUtil.write("Added");
                    } else {
                        networkUtil.write("Failed");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AER")) {
                    System.out.println("Reg Num updating");
                    int id = Integer.parseInt(messageArr[1]);
                    String newReg = messageArr[2];
                    boolean flag = Datasource.getInstance().updateReg(id, newReg);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEMA")) {
                    System.out.println("Car maker updating");
                    int id = Integer.parseInt(messageArr[1]);
                    String newMaker = messageArr[2];
                    boolean flag = Datasource.getInstance().updateMaker(id, newMaker);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEMO")) {
                    System.out.println("Car model updating");
                    int id = Integer.parseInt(messageArr[1]);
                    String newModel = messageArr[2];
                    boolean flag = Datasource.getInstance().updateModel(id, newModel);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEP")) {
                    System.out.println("Car price updating");
                    int id = Integer.parseInt(messageArr[1]);
                    int newPrice = Integer.parseInt(messageArr[2]);
                    boolean flag = Datasource.getInstance().updatePrice(id, newPrice);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEQ")) {
                    System.out.println("Car quantity updating");
                    int id = Integer.parseInt(messageArr[1]);
                    int newQuantity = Integer.parseInt(messageArr[2]);
                    boolean flag = Datasource.getInstance().updateQuantity(id, newQuantity);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEY")) {
                    System.out.println("Car year made updating");
                    int id = Integer.parseInt(messageArr[1]);
                    int newYear = Integer.parseInt(messageArr[2]);
                    boolean flag = Datasource.getInstance().updateYear(id, newYear);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEC1")) {
                    System.out.println("Car color 1 updating");
                    int id = Integer.parseInt(messageArr[1]);
                    String newCol1 = messageArr[2];
                    boolean flag = Datasource.getInstance().updateColor1(id, newCol1);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEC2")) {
                    System.out.println("Car color 2 updating");
                    int id = Integer.parseInt(messageArr[1]);
                    String newCol2 = messageArr[2];
                    boolean flag = Datasource.getInstance().updateColor2(id, newCol2);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("AEC3")) {
                    System.out.println("Car color 3 updating");
                    int id = Integer.parseInt(messageArr[1]);
                    String newCol3 = messageArr[2];
                    boolean flag = Datasource.getInstance().updateColor3(id, newCol3);
                    if (flag) {
                        networkUtil.write("RegUpdated");
                    } else {
                        networkUtil.write("NotUpdated");
                    }
                } else if (messageArr[0].equalsIgnoreCase("ADC")) {
                    String reg = messageArr[1];
                    boolean flag = Datasource.getInstance().deleteCar(reg);
                    if (flag) {
                        networkUtil.write("deleted");
                    } else {
                        networkUtil.write("notDeleted");
                    }
                }
            }
        } catch (Exception e) {

        }
    }
}
