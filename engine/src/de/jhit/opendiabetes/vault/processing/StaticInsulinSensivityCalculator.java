/*
 * Copyright (C) 2017 juehv
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
package de.jhit.opendiabetes.vault.processing;

import de.jhit.opendiabetes.vault.container.VaultEntry;
import de.jhit.opendiabetes.vault.container.VaultEntryType;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.BOLUS_NORMAL;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.EXERCISE_MANUAL;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.GLUCOSE_CGM;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.MEAL_MANUAL;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.BOLUS_SQUARE;
import de.jhit.opendiabetes.vault.processing.filter.DateTimePointFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import java.util.Iterator;
import static java.util.concurrent.TimeUnit.*;

/**
 *
 * @author juehv
 */
public class StaticInsulinSensivityCalculator {

    private final StaticInsulinSensivityCalculatorOptions options;

    public StaticInsulinSensivityCalculator(StaticInsulinSensivityCalculatorOptions options) {
        this.options = options;
    }
    
    /**
     * Calculate insulin sensitivity from given data
     *
     * @param data dataset
     * @return List of calculated insulin sensitivity for a given time point
     * within the data set.
     */
    public List<Pair<Date, Double>> calculateFromData(List<VaultEntry> data) {
        List<Pair<Date, Double>> retVal = new ArrayList<>();
        List<Pair<Date, List<VaultEntry>>> cutTimeSeries = new ArrayList<>();
        int bolusCount;
        boolean bolusFound, cgmBeginFound, otherFound = false;
        Date bolusDate;
        double bolusVal, cgmBegin, cgmEnd;
        double bolusAdd = 0.0;
        FilterEvent eventFilter = new FilterEvent();
        VaultEntryType type = BOLUS_NORMAL;
        // Save timestamp of each bolus event
        List<Date> bolusEvents = eventFilter.filter(data, type, options.range);

        // Cut time series including bolus event in middle
        for (Date date : bolusEvents) {
            DateTimePointFilter filter = new DateTimePointFilter(date, (int) options.range);
            cutTimeSeries.add(new Pair(date, (filter.filter(data)).filteredData));
        }
        
        
        // Merging bolus events within max bolusSpan mins distance
        long span = MILLISECONDS.convert(options.bolusSpan, MINUTES);
        for (Pair<Date, List<VaultEntry>> pair : cutTimeSeries) {
            Date bolus = pair.getKey();
            for (Iterator <VaultEntry> iterator = pair.getValue().iterator(); iterator.hasNext();) {
                VaultEntry entry = iterator.next();
                if (entry.getType().equals(BOLUS_NORMAL)) {
                    long diffInMillies = entry.getTimestamp().getTime() - bolus.getTime();
                    // Next bolus events within the bolusSpan
                    if (diffInMillies < span && diffInMillies > 0) {
                        // Retrive bolus value to be added to previous bolus event
                        double current = bolusAdd;
                        bolusAdd = entry.getValue() + current;
                        // Remove this bolus event inside bolusSpan from the cutTimeSeries
                        iterator.remove();
                        // OR dont remove, later ignore bolus events within bolusSpan
                    }
                }
            }
            // Merge bolus value here and initialize it for next cut time serie (next bolus cluster)
            for (Iterator <VaultEntry> it = pair.getValue().iterator(); it.hasNext();) {
                VaultEntry entry = it.next();
                if (entry.getType().equals(BOLUS_NORMAL) && entry.getTimestamp().equals(bolus)) {
                    double current = entry.getValue();
                    entry.setValue(current + bolusAdd);
                    bolusAdd = 0.0;
                }
            }
        }
 
        //Check each sub timeseries whether they have another bolus or any meal event within the range.
        for (Iterator <Pair<Date, List<VaultEntry>>> iterator = cutTimeSeries.iterator(); iterator.hasNext();) {
            Pair <Date, List<VaultEntry>> pair = iterator.next();
            Date bolus = pair.getKey();
            bolusCount = 0;
            otherFound = false;
            for (VaultEntry entry : pair.getValue()) {
                if (entry.getType().equals(BOLUS_NORMAL)) {
                    bolusCount++;
                }
                if (entry.getType().equals(MEAL_MANUAL) || entry.getType().equals(BOLUS_SQUARE)
                      || entry.getType().equals(EXERCISE_MANUAL)) {
                    otherFound = true;
                }
            }
            if (bolusCount > 1 || otherFound) {
                iterator.remove();
            }
        }
        
        //Remaining sets, elect where cgm is decreasing after bolus event
        //Looking for delta, (cgmT+delayedStart - cgmT+range) < 0 wıth delayed start in mins
        for (Iterator <Pair<Date, List<VaultEntry>>> iterator = cutTimeSeries.iterator(); iterator.hasNext();) {
            Pair <Date, List<VaultEntry>> pair = iterator.next();
            Date bolus = pair.getKey();
            // Initialize variables for next time series
            bolusFound = false;
            cgmBeginFound = false;
            bolusVal = 0.0;
            bolusDate = new Date(0);
            cgmBegin = 0.0;
            cgmEnd = 0.0;
            long delayedStart = MILLISECONDS.convert(options.cgmDelayedStart, MINUTES);
            for (VaultEntry entry : pair.getValue()) {
                // Bolus event is found, save the bolus value for later calculation
                if (entry.getType().equals(BOLUS_NORMAL) && !bolusFound && entry.getTimestamp().equals(bolus)) {
                    bolusFound = true;
                    bolusDate = entry.getTimestamp();
                    bolusVal = entry.getValue();
                }
                // Dont care vault entries after bolus event within delayedstart duration
                if (bolusFound && (entry.getTimestamp().getTime() - bolusDate.getTime()) < delayedStart) {
                    continue;
                }
                // After delayedStart period is over
                if (bolusFound && (entry.getTimestamp().getTime() - bolusDate.getTime()) >= delayedStart) {
                    // save the  first cgm value
                    if (entry.getType().equals(GLUCOSE_CGM) && !cgmBeginFound) {
                        cgmBegin = entry.getValue();
                        cgmBeginFound = true;
                    }
                    // For rest of cgm entries, keep updating cgmEnd value
                    if (entry.getType().equals(GLUCOSE_CGM) && cgmBeginFound) {
                        cgmEnd = entry.getValue();
                    }
                }
            }
            //calculate sensitivity value, only add into list if delta is < 0 (decreasing cgm)
            if (bolusFound && cgmBeginFound && (cgmEnd - cgmBegin < 0) && bolusVal != 0.0) {
                Pair <Date, Double> newPair = new Pair <>(bolusDate, (cgmEnd-cgmBegin)/bolusVal);
                retVal.add(newPair);
            }
        }
        return retVal;
    }
  
}
