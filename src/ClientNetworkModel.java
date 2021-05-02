import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientNetworkModel {
    private String serverAddress;
    private int port;

    private Socket socket;
    private ObjectInputStream in;

    public Socket getSocket() {
        return socket;
    }

    public Socket connect(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;

        try {
            socket = new Socket(serverAddress, port);
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socket;
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
}
