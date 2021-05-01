import java.io.IOException;
import java.net.Socket;

public class ClientNetworkModel {
    private String serverAddress;
    private int port;

    private Socket socket;

    public Socket connect(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;

        try {
            socket = new Socket(serverAddress, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }
}
