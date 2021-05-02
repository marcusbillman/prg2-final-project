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

    public Socket listen() {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
    }

    public void sendParcel(String feature, Object payload) {
        Parcel parcel = new Parcel("request", feature, payload);
        try {
            out.writeObject(parcel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
