import javax.swing.*;

public class ClientController {
    private ClientNetworkModel clientNetworkModel;

    public ClientController(ClientNetworkModel clientNetworkModel) {
        this.clientNetworkModel = clientNetworkModel;

        String serverAddress = JOptionPane.showInputDialog("Server address", "localhost");
        this.clientNetworkModel.connect(serverAddress, 1337);
        System.out.println("Connected to server");
    }
}
