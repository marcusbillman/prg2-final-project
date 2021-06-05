import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles the network connection between server and client. Stores the socket and object streams and provides
 * methods for sending and receiving Parcels.
 */
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

    /**
     * Remote (server): Starts listening for client connection on a specified port and sets up input and output streams.
     * @param port port to listen on
     * @throws IOException error while establishing connection
     */
    public void listen(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Viewer (client): Attempts to connect to a server at a specified address and sets up input and output streams.
     * @param serverAddress server IP address to connect to
     * @param port port to connect on
     */
    public void connect(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a Parcel to the other peer.
     * @param feature string representing a valid feature name, such as "terminal"
     * @param payload content to send
     * @throws IOException error while writing to the output stream
     */
    public void sendParcel(String feature, Object payload) throws IOException {
        String type = isServer ? "response" : "request";
        Parcel parcel = new Parcel(type, feature, payload);
        out.writeObject(parcel);
        out.flush();
        out.reset();
    }

    /**
     * Waits for a Parcel to arrive over the socket and returns the Parcel.
     * @return received Parcel
     */
    public Parcel receiveParcel() {
        Parcel parcel = null;

        try {
            parcel = (Parcel) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return parcel;
    }

    /**
     * Closes the connection to the other peer.
     */
    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
