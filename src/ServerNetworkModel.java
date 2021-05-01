import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkModel {
    private int port;

    private ServerSocket serverSocket;
    private Socket socket;

    public ServerNetworkModel(int port) {
        this.port = port;
    }

    public Socket listen() {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
