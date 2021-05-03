import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetworkModel {
    private int port;

    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream out;

    public ServerNetworkModel(int port) {
        this.port = port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void listen() throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendParcel(String feature, Object payload) throws IOException {
        Parcel parcel = new Parcel("request", feature, payload);
        out.writeObject(parcel);
    }
}
