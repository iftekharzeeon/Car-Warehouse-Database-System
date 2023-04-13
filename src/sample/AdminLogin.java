package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import netUtil.NetworkUtil;

import java.io.IOException;

public class AdminLogin {
    private Main main;
    private NetworkUtil networkUtil;

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void adminListView(ActionEvent event) throws IOException {
        String user = username.getText();
        String pass = password.getText();
        if (user.isEmpty() || pass.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter your username and password");
            alert.showAndWait();
            return;
        }
        networkUtil.write("AL#" + user + "#" + pass);
        String rec = networkUtil.read().toString();
        if (rec.equalsIgnoreCase("Success")) {
            main.adminListView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username or password incorrect");
            alert.showAndWait();

        }
    }
}
