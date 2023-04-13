package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import netUtil.NetworkUtil;

import java.io.IOException;

public class AddCar {
    private Main main;
    private NetworkUtil networkUtil;
    @FXML
    private TextField color1;

    @FXML
    private TextField color2;

    @FXML
    private TextField quantity;

    @FXML
    private TextField regNum;

    @FXML
    private TextField year;

    @FXML
    private TextField price;

    @FXML
    private TextField maker;

    @FXML
    private TextField model;

    @FXML
    private TextField color3;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void addTheCar(ActionEvent event) {
        try {
            String reg = regNum.getText();
            int yearMade = year.getText().isEmpty() ? -1 : Integer.parseInt(year.getText());
            String col1 = color1.getText().isEmpty() ? "" : color1.getText();
            String col2 = color2.getText().isEmpty() ?  "" : color2.getText();
            String col3 = color3.getText().isEmpty() ?  "" : color3.getText();
            String carMaker = maker.getText();
            String carModel = model.getText();
            int carPrice = price.getText().isEmpty() ? -1 : Integer.parseInt(price.getText());
            int carQuantity = price.getText().isEmpty() ? -1 : Integer.parseInt(quantity.getText());
            if (reg.isEmpty() || yearMade < 0 || carMaker.isEmpty() || carModel.isEmpty() || carPrice < 0 || carQuantity < 0) {
                Alert alert  = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fields can not be empty");
                alert.showAndWait();
                return;
            }

            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    networkUtil.write("AAC#" + reg + "#" + yearMade + "#" + col1 + "#" + col2 + "#" + col3 + "#" + carMaker +
                            "#" + carModel + "#" + carPrice + "#" + carQuantity);
                    String rec = networkUtil.read().toString();
                    return rec;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                Alert alert;
                if (task.getValue().toString().equalsIgnoreCase("Added")) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Car added successfully");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There was an error adding the car. Try again later");
                }
                alert.showAndWait();
                try {
                    main.adminListView();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (NumberFormatException e){
            System.out.println(e.getMessage());
            Alert alert  = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid input in place of integer");
            alert.showAndWait();
        }

    }

    public void back(ActionEvent event) throws IOException {
        main.adminListView();
    }
}
