package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import netUtil.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;

public class AdminListView {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<Cars> carView;

    private Main main;
    private NetworkUtil networkUtil;

    @FXML
    private TableColumn<Cars, String> color1;

    @FXML
    private TableColumn<Cars, String> color2;

    @FXML
    private TableColumn<Cars, String> color3;

    @FXML
    private TableColumn<Cars, Integer> carPrice;

    @FXML
    private TableColumn<Cars, Integer> quantity;

    @FXML
    private TableColumn<Cars, String> carMaker;

    @FXML
    private TableColumn<Cars, String> regNum;

    @FXML
    private TableColumn<Cars, Integer> year;

    @FXML
    private TableColumn<Cars, String> carModel;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void initialize(){
        carView.setEditable(true);
        regNum.setCellFactory(TextFieldTableCell.forTableColumn());
        carMaker.setCellFactory(TextFieldTableCell.forTableColumn());
        carModel.setCellFactory(TextFieldTableCell.forTableColumn());
        carPrice.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        year.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        color1.setCellFactory(TextFieldTableCell.forTableColumn());
        color2.setCellFactory(TextFieldTableCell.forTableColumn());
        color3.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    public void showCar(){
        Task<ObservableList<Cars>> task = new Task<ObservableList<Cars>>() {
            @Override
            protected ObservableList<Cars> call() throws Exception {
                networkUtil.write("AC");
                ArrayList<Cars> list = new ArrayList<>();
                list = (ArrayList<Cars>) networkUtil.read();
                System.out.println("YO23");
                System.out.println("YO");
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

    public void addCar(ActionEvent event) throws IOException {
        main.addNew();
    }

    @FXML
    void editReg(TableColumn.CellEditEvent<Cars, String> event) {
        if (event.getNewValue().isEmpty()) {
            errorMessage("Field cannot be empty!");
            carView.refresh();
            return;
        }
        
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        String newReg = event.getNewValue();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AER#" + id + "#" + newReg);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setReg(newReg);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });
    }

    @FXML
    void editMaker(TableColumn.CellEditEvent<Cars, String> event) {
        if (event.getNewValue().isEmpty()) {
            errorMessage("Field cannot be empty!");
            carView.refresh();
            return;
        }
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        String newMaker = event.getNewValue();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEMA#" + id + "#" + newMaker);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setMaker(newMaker);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });
    }

    @FXML
    void editModel(TableColumn.CellEditEvent<Cars, String> event) {
        if (event.getNewValue().isEmpty()) {
            errorMessage("Field cannot be empty!");
            carView.refresh();
            return;
        }
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        String newModel = event.getNewValue();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEMO#" + id + "#" + newModel);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setModel(newModel);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });

    }

    @FXML
    void editPrice(TableColumn.CellEditEvent<Cars, Integer> event) {
        try {
            if (event.getNewValue() == null) {
                errorMessage("Field cannot be empty!");
                carView.refresh();
                return;
            }
            Cars car = carView.getSelectionModel().getSelectedItem();
            int id = car.getId();
            int newPrice = event.getNewValue();
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    networkUtil.write("AEP#" + id + "#" + newPrice);
                    String rec = networkUtil.read().toString();
                    return rec;
                }
            };
            new Thread(task).start();
            progressBar.progressProperty().bind(task.progressProperty());
            progressBar.setVisible(true);
            task.setOnSucceeded(event1 -> {
                progressBar.setVisible(false);
                if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                    confirmationMessage("Database updated successfully!");
                    car.setPrice(newPrice);
                } else {
                    errorMessage("There was an error updating the database. Try again later");
                }
                carView.refresh();
            });
            task.setOnFailed(event1 -> {
                progressBar.setVisible(false);
                errorMessage("There was an unexpected error. Try again later");
                carView.refresh();
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorMessage("Invalid format for integer type data!");
        }

    }

    @FXML
    void editQuantity(TableColumn.CellEditEvent<Cars, Integer> event) {
        if (event.getNewValue() == null) {
            errorMessage("Field cannot be empty!");
            carView.refresh();
            return;
        }
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        int newQuantity = event.getNewValue();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEQ#" + id + "#" + newQuantity);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setQuantity(newQuantity);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });

    }

    @FXML
    void editYear(TableColumn.CellEditEvent<Cars, Integer> event) {
        if (event.getNewValue() == null) {
            errorMessage("Field cannot be empty!");
            carView.refresh();
            return;
        }
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        int newYear = event.getNewValue();
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEY#" + id + "#" + newYear);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setYear(newYear);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });
    }

    @FXML
    void editColor1(TableColumn.CellEditEvent<Cars, String> event) {
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        String newCol1;
        if (event.getNewValue().isEmpty()) {
            newCol1 = "---";
        } else {
            newCol1 = event.getNewValue();
        }
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEC1#" + id + "#" + newCol1);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setColor1(newCol1);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });

    }

    @FXML
    void editColor2(TableColumn.CellEditEvent<Cars, String> event) {
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        String newCol2;
        if (event.getNewValue().isEmpty()) {
            newCol2 = "---";
        } else {
            newCol2 = event.getNewValue();
        }
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEC2#" + id + "#" + newCol2);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setColor2(newCol2);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });

    }

    @FXML
    void editColor3(TableColumn.CellEditEvent<Cars, String> event) {
        Cars car = carView.getSelectionModel().getSelectedItem();
        int id = car.getId();
        String newCol3;
        if (event.getNewValue().isEmpty()) {
            newCol3 = "---";
        } else {
            newCol3 = event.getNewValue();
        }
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                networkUtil.write("AEC3#" + id + "#" + newCol3);
                String rec = networkUtil.read().toString();
                return rec;
            }
        };
        new Thread(task).start();
        progressBar.progressProperty().bind(task.progressProperty());
        progressBar.setVisible(true);
        task.setOnSucceeded(event1 -> {
            progressBar.setVisible(false);
            if (task.getValue().toString().equalsIgnoreCase("RegUpdated")) {
                confirmationMessage("Database updated successfully!");
                car.setColor3(newCol3);
            } else {
                errorMessage("There was an error updating the database. Try again later");
            }
            carView.refresh();
        });
        task.setOnFailed(event1 -> {
            progressBar.setVisible(false);
            errorMessage("There was an unexpected error. Try again later");
            carView.refresh();
        });
    }

    private void errorMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void confirmationMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void deleteCar(ActionEvent event) {
        Cars car = carView.getSelectionModel().getSelectedItem();
        if (car == null) {
            return;
        }
        if (Confirmation.display()) {
            String reg = car.getReg();
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    networkUtil.write("ADC#" + reg);
                    String rec = networkUtil.read().toString();
                    return rec;
                }
            };
            new Thread(task).start();
            task.setOnSucceeded(event1 -> {
                if (task.getValue().toString().equalsIgnoreCase("deleted")) {
                    confirmationMessage("Car successfully removed!");
                    ObservableList<Cars> allCars, singleCar;
                    singleCar = carView.getSelectionModel().getSelectedItems();
                    allCars = carView.getItems();
                    singleCar.forEach(allCars::remove);
                } else {
                    errorMessage("Could not delete the car!");
                }
            });
        }
    }

    public void refreshTable(ActionEvent event) {
        showCar();
    }
}
