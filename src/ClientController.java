public class ClientController {
    private ClientNetworkModel clientNetworkModel;

    public ClientController(ClientNetworkModel clientNetworkModel) {
        this.clientNetworkModel = clientNetworkModel;

        this.clientNetworkModel.connect("localhost", 1337);

        System.out.println("Connected to server");
    }
}
