public class RemoteMain {
    public static void main(String[] args) {
        NetworkModel networkModel = new NetworkModel(true);
        new RemoteController(networkModel);
    }
}
