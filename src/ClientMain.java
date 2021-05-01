public class ClientMain {
    public static void main(String[] args) {
        ClientNetworkModel clientNetworkModel = new ClientNetworkModel();
        ClientController clientController = new ClientController(clientNetworkModel);
    }
}
