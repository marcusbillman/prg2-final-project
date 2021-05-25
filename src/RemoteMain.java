public class RemoteMain {
    public static void main(String[] args) {
        NetworkModel networkModel = new NetworkModel(true);
        RemoteController controller = new RemoteController(networkModel);
    }
}
