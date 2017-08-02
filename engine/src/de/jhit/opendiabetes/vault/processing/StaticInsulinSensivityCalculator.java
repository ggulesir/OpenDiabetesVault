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
import static de.jhit.opendiabetes.vault.container.VaultEntryType.BOLUS_NORMAL;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.GLUCOSE_CGM;
import static de.jhit.opendiabetes.vault.container.VaultEntryType.MEAL_MANUAL;
import de.jhit.opendiabetes.vault.processing.filter.TimePointFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Iterator;

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
        List<Date> bolusEvents = new ArrayList<>();
        List<List<VaultEntry>> cuttenTimeSeries = new ArrayList<>();
        int bolusCount = 0;
        boolean bolusFound = false;
        //initialize date
        Date bolusDate = new Date(0);
        double bolusVal= 0;
        double cgmBegin = 0;
        double cgmEnd = Double.MAX_VALUE;
        
        // Add time series with time point filter where bolus event happened.
        for (VaultEntry entry : data) {
            if (entry.getType().equals(BOLUS_NORMAL)) {
                bolusEvents.add(entry.getTimestamp()); 
            }
        }
        for (Date date : bolusEvents) {
            System.out.println(date);
            LocalTime localTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
            TimePointFilter filter = new TimePointFilter(localTime, (int) options.range);
            cuttenTimeSeries.add((filter.filter(data)).filteredData);
        }
        //Check each sub timeseries whether they have another bolus or any meal event within the range.
        for(Iterator<List<VaultEntry>> iterator = cuttenTimeSeries.iterator(); iterator.hasNext();){
            bolusCount = 0;
            List<VaultEntry> listEntry = iterator.next();
            for (VaultEntry entry : listEntry) {
              if (entry.getType().equals(BOLUS_NORMAL)) {
                    bolusCount++;
              }
              if (entry.getType().equals(MEAL_MANUAL)) {
                iterator.remove();
              }
            }
            if (bolusCount > 1) {
                iterator.remove();
            }
            
        }
        
        //TODO Boundary checking, first bolus should have enough reading before, last bolus
        // also should have enough reading after
        //TODO merging bolus events within max 30 min distance
        
        // remaining sets, elect where cgm is decreasing after bolus event
        for (List<VaultEntry> listEntry : cuttenTimeSeries) {
            bolusFound = false;
            bolusVal = 0;
           //set date to zero
           bolusDate = new Date(0);
            cgmBegin = 0;
            cgmEnd = Double.MAX_VALUE;
            for (VaultEntry entry : listEntry) {
                if (entry.getType().equals(BOLUS_NORMAL) && !bolusFound) {
                    bolusFound = true;
                    bolusDate = entry.getTimestamp();
                    bolusVal = entry.getValue();
                    System.out.println("Bolus FOUND, value: " + bolusVal);
                }
                if (entry.getType().equals(GLUCOSE_CGM) && !bolusFound) {
                    cgmBegin = entry.getValue();
                    System.out.println("cgm begin: " + cgmBegin);
                }
                if (bolusFound && entry.getType().equals(GLUCOSE_CGM) && entry.getValue() <= cgmEnd) {
                    cgmEnd = entry.getValue();
                    System.out.println("cgm end: " + cgmEnd);
                }
                if (bolusFound && entry.getType().equals(GLUCOSE_CGM) && entry.getValue() > cgmEnd) {
                    System.out.println("Before break");
                    break;
                }
                
            }
            if (bolusFound && cgmBegin != 0 && cgmEnd != Double.MAX_VALUE && bolusVal != 0) {
                //calculate sensitivity value
                Pair <Date, Double> pair = new Pair <Date, Double>(bolusDate, (cgmBegin-cgmEnd)/bolusVal);
                retVal.add(pair);
                System.out.println("Sensitivity " + (cgmBegin-cgmEnd)/bolusVal + " at " + bolusDate);
            }
        }
        
        return retVal;
    }
  
}
