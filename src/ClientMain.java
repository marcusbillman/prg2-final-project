public class ClientMain {
    public static void main(String[] args) {
        NetworkModel networkModel = new NetworkModel(false);
        ClientController clientController = new ClientController(networkModel);
    }
}
