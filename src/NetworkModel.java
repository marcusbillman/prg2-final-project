import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkModel {
    private final boolean isServer;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public NetworkModel(boolean isServer) {
        this.isServer = isServer;
    }

    public Socket getSocket() {
        return socket;
    }

    // Server: Start listening for client connection
    public void listen(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    // Client: Connect to server
    public void connect(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendParcel(String feature, Object payload) throws IOException {
        String type = isServer ? "response" : "request";
        Parcel parcel = new Parcel(type, feature, payload);
        out.writeObject(parcel);
        out.flush();
        out.reset();
    }

    public Parcel receiveParcel() {
        Parcel parcel = null;

        try {
            parcel = (Parcel) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return parcel;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
