package pojo;

import java.util.Date;

public class StopEta {
    private int stopId;
    private Date eta;

    StopEta(int stopId,Date eta) {
        this.eta=eta;
        this.stopId=stopId;
    }

    public Date getEta() {
        return eta;
    }

    public int getStopId() {
        return stopId;
    }
}
