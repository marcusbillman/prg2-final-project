public class RemoteMain {
    public static void main(String[] args) {
        NetworkModel networkModel = new NetworkModel(false);
        RemoteController controller = new RemoteController(networkModel);
    }
}
