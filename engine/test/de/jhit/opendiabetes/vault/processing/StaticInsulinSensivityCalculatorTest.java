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
import de.jhit.opendiabetes.vault.testhelper.SensitivityDataset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author juehv
 */
public class StaticInsulinSensivityCalculatorTest extends Assert {

    public StaticInsulinSensivityCalculatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateFromData method, of class
     * StaticInsulinSensivityCalculator.
     */
    @Test
    public void testCalculateFromData() throws ParseException{
        System.out.println("calculateFromData");
        long range = 120;
        long bolusSpan = 30;
        long cgmDelayedStart = 15;
        List<VaultEntry> data = SensitivityDataset.getSensitivityDataset();
        StaticInsulinSensivityCalculatorOptions options = new StaticInsulinSensivityCalculatorOptions(range, bolusSpan,cgmDelayedStart);
        StaticInsulinSensivityCalculator instance = new StaticInsulinSensivityCalculator(options);
        Date bolusDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2016-04-18 09:33:00");
        Pair <Date, Double> pair = new Pair <>(bolusDate, -1.6666666666666667);
        List<Pair<Date, Double>> expResult = new ArrayList<>();
        expResult.add(pair);
        List<Pair<Date, Double>> result = instance.calculateFromData(data);
        assertEquals(expResult, result);
    }
}
