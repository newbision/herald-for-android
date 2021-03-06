//  Copyright 2020 VMware, Inc.
//  SPDX-License-Identifier: MIT
//

package com.vmware.herald.sensor.ble;

import com.vmware.herald.sensor.data.SensorLoggerLevel;
import com.vmware.herald.sensor.datatype.TimeInterval;

import java.util.UUID;

/// Defines BLE sensor configuration data, e.g. service and characteristic UUIDs
public class BLESensorConfiguration {
    public final static SensorLoggerLevel logLevel = SensorLoggerLevel.debug;
    /**
     * Service UUID for beacon service. This is a fixed UUID to enable iOS devices to find each other even
     * in background mode. Android devices will need to find Apple devices first using the manufacturer code
     * then discover services to identify actual beacons.
     */
    public final static UUID serviceUUID = UUID.fromString("FFFFFFFF-EEEE-DDDD-0000-000000000000");
    /// Signaling characteristic for controlling connection between peripheral and central, e.g. keep each other from suspend state
    public final static UUID androidSignalCharacteristicUUID = UUID.fromString("FFFFFFFF-EEEE-DDDD-0000-000000000001");
    /// Signaling characteristic for controlling connection between peripheral and central, e.g. keep each other from suspend state
    public final static UUID iosSignalCharacteristicUUID = UUID.fromString("FFFFFFFF-EEEE-DDDD-0000-000000000002");
    /// Primary payload characteristic (read) for distributing payload data from peripheral to central, e.g. identity data
    public final static UUID payloadCharacteristicUUID = UUID.fromString("FFFFFFFF-EEEE-DDDD-0000-000000000003");
    /// Expiry time for shared payloads, to ensure only recently seen payloads are shared
    public final static TimeInterval payloadSharingExpiryTimeInterval = new TimeInterval(5 * TimeInterval.minute.value);
    /// Manufacturer data is being used on Android to store pseudo device address
    public final static int manufacturerIdForSensor = 65530;
    /// Advert refresh time interval
    public final static TimeInterval advertRefreshTimeInterval = TimeInterval.minutes(15);


    /// Signal characteristic action code for write payload, expect 1 byte action code followed by 2 byte little-endian Int16 integer value for payload data length, then payload data
    public final static byte signalCharacteristicActionWritePayload = (byte) 1;
    /// Signal characteristic action code for write RSSI, expect 1 byte action code followed by 4 byte little-endian Int32 integer value for RSSI value
    public final static byte signalCharacteristicActionWriteRSSI = (byte) 2;
    /// Signal characteristic action code for write payload, expect 1 byte action code followed by 2 byte little-endian Int16 integer value for payload sharing data length, then payload sharing data
    public final static byte signalCharacteristicActionWritePayloadSharing = (byte) 3;

    // BLE advert manufacturer ID for Apple, for scanning of background iOS devices
    public final static int manufacturerIdForApple = 76;
}
