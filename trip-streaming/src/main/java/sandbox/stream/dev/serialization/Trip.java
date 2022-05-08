package sandbox.stream.dev.serialization;

import com.google.gson.annotations.SerializedName;

public class Trip {
    @SerializedName("trip_seconds")
    private int tripSeconds;

    @SerializedName("trip_miles")
    private float tripMiles;

    @SerializedName("fare")
    private float fare;

    @SerializedName("tips")
    private float tips;

    @SerializedName("tolls")
    private float tolls;

    @SerializedName("extras")
    private float extras;

    @SerializedName("trip_total")
    private float tripTotal;

    @SerializedName("payment_type")
    private String paymentType;

    @SerializedName("company")
    private String company;

    public int getTripSeconds() {
        return tripSeconds;
    }

    public void setTripSeconds(int tripSeconds) {
        this.tripSeconds = tripSeconds;
    }

    public float getTripMiles() {
        return tripMiles;
    }

    public void setTripMiles(float tripMiles) {
        this.tripMiles = tripMiles;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public float getTips() {
        return tips;
    }

    public void setTips(float tips) {
        this.tips = tips;
    }

    public float getTolls() {
        return tolls;
    }

    public void setTolls(float tolls) {
        this.tolls = tolls;
    }

    public float getExtras() {
        return extras;
    }

    public void setExtras(float extras) {
        this.extras = extras;
    }

    public float getTripTotal() {
        return tripTotal;
    }

    public void setTripTotal(float tripTotal) {
        this.tripTotal = tripTotal;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
