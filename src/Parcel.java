import java.io.Serializable;

/**
 * A simple data structure representing a request or response sent between the server and client.
 */
public class Parcel implements Serializable {
    private final String type;
    private final String feature;
    private final Object payload;

    public String getType() {
        return type;
    }

    public String getFeature() {
        return feature;
    }

    public Object getPayload() {
        return payload;
    }

    public Parcel(String type, String feature, Object payload) {
        this.type = type;
        this.feature = feature;
        this.payload = payload;
    }
}
