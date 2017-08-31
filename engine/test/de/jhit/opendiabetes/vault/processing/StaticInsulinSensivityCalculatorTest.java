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
        Date bolusDate1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2017-08-02 06:16:00");
        Date bolusDate2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2017-08-02 14:04:00");
        Date bolusDate3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2017-08-03 04:25:00");
        Pair <Date, Double> pair1 = new Pair <>(bolusDate1, -18.333333333333332);
        Pair <Date, Double> pair2 = new Pair <>(bolusDate2, -15.679012345679013);
        Pair <Date, Double> pair3 = new Pair <>(bolusDate3, -1.6666666666666667);
        List<Pair<Date, Double>> expResult = new ArrayList<>();
        expResult.add(pair1);
        expResult.add(pair2);
        expResult.add(pair3);
        List<Pair<Date, Double>> result = instance.calculateFromData(data);
        assertEquals(expResult, result);
    }
}
