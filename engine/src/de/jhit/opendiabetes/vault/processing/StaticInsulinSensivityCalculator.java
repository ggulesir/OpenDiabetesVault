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
import static de.jhit.opendiabetes.vault.importer.validator.MedtronicCsvValidator.TYPE.BOLUS_SQUARE;
import de.jhit.opendiabetes.vault.processing.filter.TimePointFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
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
        List<List<VaultEntry>> cutTimeSeries = new ArrayList<>();
        List<Pair<Date, Double>> bolusInSpan = new ArrayList<>();
        int bolusCount;
        boolean bolusFound;
        Date bolusDate;
        double bolusVal, cgmBegin, cgmEnd;
        
        FilterEvent eventFilter = new FilterEvent();
        VaultEntryType type = BOLUS_NORMAL;
        // Save timestamp of each bolus event
        List<Date> bolusEvents = eventFilter.filter(data, type, options.range);
        
        // Cut time series including bolus event in middle
        for (Date date : bolusEvents) {
            LocalTime localTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
            TimePointFilter filter = new TimePointFilter(localTime, (int) options.range);
            cutTimeSeries.add((filter.filter(data)).filteredData);
        }
        
        // Merging bolus events within max 30 min distance
        long span = MILLISECONDS.convert(options.bolusSpan, MINUTES);
        for (List<VaultEntry> timeSerie : cutTimeSeries) {
            for (Date blsDate : bolusEvents) {
                for (Iterator<VaultEntry> iterator = timeSerie.iterator(); iterator.hasNext();) {
                    VaultEntry entry = iterator.next();
                    if (entry.getType().equals(BOLUS_NORMAL)) {
                        long diffInMillies = entry.getTimestamp().getTime() - blsDate.getTime();
                        if (diffInMillies < span && diffInMillies > 0) {
                            // Retrive bolus value to be added to previous bolus event, create pair with previous
                            // bolus date and add it to bolusInSpan list.
                            Pair <Date, Double> pair = new Pair <>(blsDate, entry.getValue());
                            bolusInSpan.add(pair);
                            // Remove this bolus event inside bolusSpan from the cutTimeSeries
                            // OR dont remove, later ignore bolus events within bolusSpan
                            iterator.remove();
                        }
                    }
                }
            }
        }
        //TODO Find the actual bolus event inside cutTimeSeries, by intersecting the date from blousInSpan and add bolus
        // value to vaultEntry value.
        
        //Check each sub timeseries whether they have another bolus or any meal event within the range.
        for(Iterator<List<VaultEntry>> iterator = cutTimeSeries.iterator(); iterator.hasNext();){
            bolusCount = 0;
            List<VaultEntry> listEntry = iterator.next();
            for (VaultEntry entry : listEntry) {
              if (entry.getType().equals(BOLUS_NORMAL)) {
                    bolusCount++;
              }
              if (entry.getType().equals(MEAL_MANUAL) || entry.getType().equals(BOLUS_SQUARE)
                      || entry.getType().equals(EXERCISE_MANUAL)) {
                iterator.remove();
              }
            }
            if (bolusCount > 1) {
                iterator.remove();
            }
            
        }
        
        //TODO remaining sets, elect where cgm is decreasing after bolus event
        // Correction: instead of monoton decreasing, look for delta, (cgmT - cgmT+2h) < 0
        for (List<VaultEntry> listEntry : cutTimeSeries) {
            // Initialize variables for next time series
            bolusFound = false;
            bolusVal = 0;
            bolusDate = new Date(0);
            cgmBegin = Double.MIN_VALUE;
            cgmEnd = Double.MAX_VALUE;
            for (VaultEntry entry : listEntry) {
                if (entry.getType().equals(BOLUS_NORMAL) && !bolusFound) {
                    bolusFound = true;
                    bolusDate = entry.getTimestamp();
                    bolusVal = entry.getValue();
                }
                if (entry.getType().equals(GLUCOSE_CGM) && !bolusFound) {
                    cgmBegin = entry.getValue();
                }
                if (bolusFound && entry.getType().equals(GLUCOSE_CGM) && entry.getValue() <= cgmEnd) {
                    cgmEnd = entry.getValue();
                }
                if (bolusFound && entry.getType().equals(GLUCOSE_CGM) && entry.getValue() > cgmEnd) {
                    break;
                }
                
            }
            //calculate sensitivity value
            if (bolusFound && cgmBegin != Double.MIN_VALUE && cgmEnd != Double.MAX_VALUE && bolusVal != 0) {
                Pair <Date, Double> pair = new Pair <>(bolusDate, (cgmBegin-cgmEnd)/bolusVal);
                retVal.add(pair);
            }
        }
        
        return retVal;
    }
  
}
