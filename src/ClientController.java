import javax.swing.*;

public class ClientController {
    private ClientNetworkModel clientNetworkModel;

    public ClientController(ClientNetworkModel clientNetworkModel) {
        this.clientNetworkModel = clientNetworkModel;

        String serverAddress = JOptionPane.showInputDialog("Server address", "localhost");
        this.clientNetworkModel.connect(serverAddress, 1337);
        System.out.println("Connected to server");

        startReceiving();
    }

    private void startReceiving() {
        while (clientNetworkModel.getSocket().isConnected()) {
            Parcel parcel = this.clientNetworkModel.receiveParcel();

            String type = parcel.getType();
            String feature = parcel.getFeature();
            Object payload = parcel.getPayload();

            System.out.println(type + " | " + feature + " | " + payload);

            switch (feature) {
                case "popup":
                    handlePopupParcel(payload);
                    break;
                default:
                    throw new IllegalArgumentException("'" + feature + "' is not a valid feature name");
            }
        }
    }

    private void handlePopupParcel(Object payload) {
        String[] payloadArray = (String[]) payload;
        JOptionPane.showMessageDialog(
                null, payloadArray[0], payloadArray[1], JOptionPane.INFORMATION_MESSAGE);
    }
}
