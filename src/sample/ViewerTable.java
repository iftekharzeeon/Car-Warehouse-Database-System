package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import netUtil.NetworkUtil;

import java.util.ArrayList;

public class ViewerTable {
    @FXML
    public TextField makerField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField regField;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<Cars> carView;
    private Main main;
    private NetworkUtil networkUtil;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void showCar(){

        Task<ObservableList<Cars>> task = new Task<ObservableList<Cars>>() {
            @Override
            protected ObservableList<Cars> call() throws Exception {
                networkUtil.write("VC");
                ArrayList<Cars> list = new ArrayList<>();
                list = (ArrayList<Cars>) networkUtil.read();
                return FXCollections.observableArrayList(list);
            }
        };
        carView.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();

        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event -> progressBar.setVisible(false));
        task.setOnFailed(event -> progressBar.setVisible(false));

    }

    public void searchReg(ActionEvent event) {
        String reg = regField.getText();
        if (reg.isEmpty()) {
            return;
        }
        Task<ObservableList<Cars>> task = new Task<ObservableList<Cars>>() {
            @Override
            protected ObservableList<Cars> call() throws Exception {
                networkUtil.write("VCSR#" + reg);
                ArrayList<Cars> list = new ArrayList<>();
                list = (ArrayList<Cars>) networkUtil.read();

                return FXCollections.observableArrayList(list);
            }
        };
        carView.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();

    }

    public void searchMaker(ActionEvent event) {
        String maker = makerField.getText();
        String model = modelField.getText();

        if (maker.isEmpty() || model.isEmpty()) {
            return;
        }
        Task<ObservableList<Cars>> task = new Task<ObservableList<Cars>>() {
            @Override
            protected ObservableList<Cars> call() throws Exception {
                networkUtil.write("VCSM#" + maker + "#" + model);
                ArrayList<Cars> list = new ArrayList<>();
                list = (ArrayList<Cars>) networkUtil.read();

                return FXCollections.observableArrayList(list);
            }
        };
        carView.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

    public void carBuy(ActionEvent event) {
        Cars car = carView.getSelectionModel().getSelectedItem();
        if (car == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a car to buy");
            alert.showAndWait();
            return;
        }
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                String rec;
                networkUtil.write("VCB#" + car.getReg());
                rec = networkUtil.read().toString();
                System.out.println(rec);
                return rec;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event1 -> {
            String ret = task.getValue().toString();
            Alert alert;
            if (ret.equalsIgnoreCase("BuySuccessful")) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("You have successfully bought your car!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Sorry the product is not available anymore! Refreshing the list");
            }
            alert.showAndWait();
            showCar();
        });
    }

    public void refreshTable(ActionEvent event) {
        showCar();
    }
}
