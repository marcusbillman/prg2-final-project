/**
 * Entry point for the remote-side application.
 */
public class RemoteMain {
    public static void main(String[] args) {
        NetworkModel networkModel = new NetworkModel(true);
        new RemoteController(networkModel);
    }
}
