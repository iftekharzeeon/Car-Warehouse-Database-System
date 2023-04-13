package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import netUtil.NetworkUtil;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;
    private NetworkUtil networkUtil;

    public void adminLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adminLogin.fxml"));
        Parent root = loader.load();

        AdminLogin controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);
        stage.setScene(new Scene(root, 694, 689));

    }

    public void viewerLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("viewLogin.fxml"));
        Parent root = loader.load();

        ViewLogin controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);
        stage.setScene(new Scene(root, 694, 689));

    }
    public void viewerListView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("viewerTable.fxml"));
        Parent root = loader.load();

        ViewerTable controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);
        controller.showCar();

        stage.setScene(new Scene(root, 850, 750));

    }

    public void adminListView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("adminTable.fxml"));
        Parent root = loader.load();

        AdminListView controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);
        controller.showCar();
        stage.setScene(new Scene(root, 950, 750));
    }
    public void addNew() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addCar.fxml"));
        Parent root = loader.load();

        AddCar controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);

        stage.setScene(new Scene(root, 694, 689));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        networkUtil = new NetworkUtil("localhost", 9992);
        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setMain(this);
        controller.setNetworkUtil(networkUtil);
        primaryStage.setTitle("Car Showroom");
        primaryStage.setScene(new Scene(root, 694, 689));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
