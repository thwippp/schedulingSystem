/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author bscha
 */
public class DateAndTime {

    private static Timestamp localTimestamp;
    private static Timestamp utcTimestamp;

    private static ZoneId localZoneId;
    private static ZonedDateTime localZonedDateTime;
    private static ZonedDateTime utcDateTime;

    public DateAndTime() {

    }

    public DateAndTime(ZoneId z) {
        DateAndTime.setLocalZoneId(z);
    }

    public static Timestamp localToUTCTS(Timestamp localTimestamp) {
        return null;
    }

    public static Timestamp UTCToLocalTS(Timestamp UTCTimestamp) {

        return null;
    }

    public static ZonedDateTime localToUTCZDT(ZonedDateTime localZDT) {
        return null;
    }

    public static ZonedDateTime UTCToLocalZTD(ZonedDateTime UTCZDT) {
        return null;
    }

//    Timestamp tsStart = rs.getTimestamp("start");
//
//				ZoneId newzid = ZoneId.systemDefault();
//
//				ZonedDateTime newzdtStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
//
//				ZonedDateTime newLocalStart = newzdtStart.withZoneSameInstant(newzid);
    public static Timestamp getLocalTimestamp() {
        return localTimestamp;
    }

    public static void setLocalTimestamp(Timestamp localTimestamp) {
        DateAndTime.localTimestamp = localTimestamp;
    }

    public static Timestamp getUtcTimestamp() {
        return utcTimestamp;
    }

    public static void setUtcTimestamp(Timestamp utcTimestamp) {
        DateAndTime.utcTimestamp = utcTimestamp;
    }

    public static ZoneId getLocalZoneId() {
        return localZoneId;
    }

    public static void setLocalZoneId(ZoneId localZoneId) {
        DateAndTime.localZoneId = localZoneId;
    }

    public static ZonedDateTime getLocalZonedDateTime() {
        return localZonedDateTime;
    }

    public static void setLocalZonedDateTime(ZonedDateTime localZonedDateTime) {
        DateAndTime.localZonedDateTime = localZonedDateTime;
    }

    public static ZonedDateTime getUtcDateTime() {
        return utcDateTime;
    }

    public static void setUtcDateTime(ZonedDateTime utcDateTime) {
        DateAndTime.utcDateTime = utcDateTime;
    }

}
