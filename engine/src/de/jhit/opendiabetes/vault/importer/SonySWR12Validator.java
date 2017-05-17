/*
 * Copyright (C) 2017 Jens Heuschkel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jhit.opendiabetes.vault.importer;

import com.csvreader.CsvReader;
import de.jhit.opendiabetes.vault.util.TimestampUtils;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author juehv
 */
public class SonySWR12Validator extends CsvValidator {

    //public static final String HEADER_TIMESTAMP = "event_timestamp";
    public static final String HEADER_TYPE = "activity_type";
    public static final String HEADER_VALUE = "activity_data";
    public static final String HEADER_START_TIME = "start_time";
    public static final String HEADER_END_TIME = "end_time";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String[] CARELINK_HEADER_DE = {
        HEADER_START_TIME, HEADER_END_TIME,
        //HEADER_TIMESTAMP, 
        HEADER_TYPE,
        HEADER_VALUE
    };

    public static enum TYPE {
        SLEEP_LIGHT(5), SLEEP_DEEP(6),
        HEART_RATE_VARIABILITY(9), HEART_RATE(8),
        WALK(0), RUN(1);

        final int typeInt;

        TYPE(int typeInt) {
            this.typeInt = typeInt;
        }

        static TYPE fromInt(int typeInt) {
            for (TYPE item : TYPE.values()) {
                if (item.typeInt == typeInt) {
                    return item;
                }
            }
            return null;
        }
    }

    @Override
    public boolean validateHeader(String[] header) {

        boolean result = true;
        Set<String> headerSet = new TreeSet<>(Arrays.asList(header));

        // Check header
        for (String item : CARELINK_HEADER_DE) {
            result &= headerSet.contains(item);
        }
        if (result == true) {
            languageSelection = Language.UNIVERSAL;
        }

        return result;
    }

    public TYPE getSmartbandType(CsvReader creader) throws IOException {
        int typeInt = Integer.parseInt(creader.get(HEADER_TYPE).trim());
        return TYPE.fromInt(typeInt);
    }

    public Date getTimestamp(CsvReader creader) throws IOException, ParseException {
        String timeString = creader.get(
                //HEADER_TIMESTAMP).trim();
                HEADER_START_TIME).trim();
        long timeStampMil = Long.parseLong(timeString);
        //return TimestampUtils.createCleanTimestamp(timeString, TIME_FORMAT);
        return TimestampUtils.createCleanTimestamp(new Date(timeStampMil));
    }

    public int getValue(CsvReader creader) throws IOException {
        return Integer.parseInt(creader.get(HEADER_VALUE));
    }

    long getStartTime(CsvReader creader) throws IOException {
        return Long.parseLong(creader.get(HEADER_START_TIME));
    }

    long getEndTime(CsvReader creader) throws IOException {
        return Long.parseLong(creader.get(HEADER_END_TIME));
    }
}
