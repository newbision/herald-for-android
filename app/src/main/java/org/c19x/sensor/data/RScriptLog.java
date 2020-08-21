package org.c19x.sensor.data;

import org.c19x.sensor.SensorDelegate;
import org.c19x.sensor.datatype.Location;
import org.c19x.sensor.datatype.PayloadData;
import org.c19x.sensor.datatype.Proximity;
import org.c19x.sensor.datatype.SensorError;
import org.c19x.sensor.datatype.SensorType;
import org.c19x.sensor.datatype.TargetIdentifier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/// CSV contact log for post event analysis and visualisation
public class RScriptLog implements SensorDelegate {
    private final static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final TextFile textFile;
    private final PayloadData payloadData;
    private final String deviceOS = Integer.toString(android.os.Build.VERSION.SDK_INT);
    private final String deviceName = android.os.Build.MODEL;
    private final Map<String, String> identifierToPayload = new ConcurrentHashMap<>();

    public RScriptLog(String filename, PayloadData payloadData) {
        textFile = new TextFile(filename);
        this.payloadData = payloadData;
        if (textFile.empty()) {
            textFile.write("datetime,payload,devicename,os,osver");
        }
    }

    private String timestamp() {
        return dateFormatter.format(new Date());
    }

    private String csv(String value) {
        return TextFile.csv(value);
    }

    // MARK:- SensorDelegate

    @Override
    public void sensor(SensorType sensor, TargetIdentifier didDetect) {
    }

    @Override
    public void sensor(SensorType sensor, PayloadData didRead, TargetIdentifier fromTarget) {
        identifierToPayload.put(fromTarget.value, didRead.shortName());
        textFile.write(timestamp() + "," + didRead.shortName() + "," + deviceName + ",android," + deviceOS);
    }

    @Override
    public void sensor(SensorType sensor, Proximity didMeasure, TargetIdentifier fromTarget) {
        final String payload = identifierToPayload.get(fromTarget.value);
        if (payload == null) {
            return;
        }
        textFile.write(timestamp() + "," + payload + "," + deviceName + ",android," + deviceOS);
    }

    @Override
    public void sensor(SensorType sensor, List<PayloadData> didShare, TargetIdentifier fromTarget) {
        final String timestamp = timestamp();
        for (PayloadData payload : didShare) {
            if (payload.shortName() == payloadData.shortName()) {
                continue;
            }
            textFile.write(timestamp + "," + payload.shortName() + "," + deviceName + ",android," + deviceOS);
        }
    }

    @Override
    public void sensor(SensorType sensor, Location didVisit) {
    }

    @Override
    public void sensor(SensorType sensor, SensorError didFail) {
    }
}
