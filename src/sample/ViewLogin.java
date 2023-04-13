package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import netUtil.NetworkUtil;

import java.io.IOException;

public class ViewLogin {
    @FXML
    private TextField username;
    private Main main;
    private NetworkUtil networkUtil;

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void viewerList(ActionEvent event) throws IOException {
        String uname = username.getText();
        if (uname.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Field is empty");
            alert.showAndWait();
        } else {
            networkUtil.write("VL#" + uname);
            String received = networkUtil.read().toString();
            if (received.equalsIgnoreCase("Success")) {
                main.viewerListView();
            } else if (received.equalsIgnoreCase("failure")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username does not exit");
                alert.showAndWait();
            }
        }
    }
}
