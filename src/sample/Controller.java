package sample;

import javafx.event.ActionEvent;
import netUtil.NetworkUtil;

import java.io.IOException;

public class Controller {
    private Main main;

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    private NetworkUtil networkUtil;

    public void setMain(Main main) {
        this.main = main;
    }

    public Controller() {
    }

    public void manfPage(ActionEvent event) throws IOException {
        main.adminLogin();
    }

    public void viewerPage(ActionEvent event) throws IOException {
        main.viewerLogin();
    }
}
