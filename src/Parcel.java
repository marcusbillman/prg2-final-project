import java.io.Serializable;

public class Parcel implements Serializable {
    private String type;
    private String feature;
    private Object payload;

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
