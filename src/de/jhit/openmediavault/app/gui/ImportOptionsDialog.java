/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jhit.openmediavault.app.gui;

import de.jhit.openmediavault.app.Launcher;
import de.jhit.openmediavault.app.preferences.Constants;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jens
 */
public class ImportOptionsDialog extends javax.swing.JDialog {

    Preferences prefs = Preferences.userNodeForPackage(Launcher.class);

    /**
     * Creates new form ImportOptions
     *
     * @param parent
     */
    public ImportOptionsDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        // not implemented yet
        jTabbedPane1.setEnabledAt(3, false);

        // add change listner for unit conertion        
        hypoThresholdMgSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hypoThresholdMmolSpinner.setValue(Math.round(
                        (Double) hypoThresholdMgSpinner.getValue()
                        * Constants.MG_TO_MMOL_FACTOR
                        * 100) / 100.0);
            }
        });
        hypoThresholdMmolSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hypoThresholdMgSpinner.setValue(((Long) Math.round(
                        (Double) hypoThresholdMmolSpinner.getValue()
                        * Constants.MMOL_TO_MG_FACTOR)).doubleValue());
            }
        });
        hyperThresholdMgSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hyperThresholdMmolSpinner.setValue(Math.round(
                        (Double) hyperThresholdMgSpinner.getValue()
                        * Constants.MG_TO_MMOL_FACTOR
                        * 100) / 100.0);
            }
        });
        hyperThresholdMmolSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                hyperThresholdMgSpinner.setValue(((Long) Math.round(
                        (Double) hyperThresholdMmolSpinner.getValue()
                        * Constants.MMOL_TO_MG_FACTOR)).doubleValue());
            }
        });

        // restore saved credentials        
        hypoThresholdMmolSpinner.setValue(prefs.getDouble(Constants.HYPO_THRESHOLD_MMOL_KEY,
                Constants.HYPO_THRESHOLD_MMOL_DEFAULT));
        hypoThresholdMgSpinner.setValue(prefs.getDouble(Constants.HYPO_THRESHOLD_MG_KEY,
                Constants.HYPO_THRESHOLD_MG_DEFAULT));
        hypoFollowingTimeSpinner.setValue(prefs.getInt(Constants.HYPO_FOLLOW_TIME_KEY,
                Constants.HYPO_FOLLOW_TIME_DEFAULT));
        hypoMealHistorySpinner.setValue(prefs.getInt(Constants.HYPO_MEAL_HISTORY_TIME_KEY,
                Constants.HYPO_MEAL_HISTORY_TIME_DEFAULT) / 60);
        hypoExerciseHistorySpinner.setValue(prefs.getInt(Constants.HYPO_EXERCISE_HISTORY_TIME_KEY,
                Constants.HYPO_EXERCISE_HISTORY_TIME_DEFAULT));
        sleepStartSpinner.setValue(prefs.getInt(Constants.SLEEP_INDICATION_BED_TIME_KEY,
                Constants.SLEEP_INDICATION_BED_TIME_DEFAULT));
        sleepStopSpinner.setValue(prefs.getInt(Constants.SLEEP_INDICATION_WAKEUP_TIME_KEY,
                Constants.SLEEP_INDICATION_WAKEUP_TIME_DEFAULT));
        sleepIndicationThSpinner.setValue(prefs.getInt(Constants.SLEEP_INDICATION_THRESHOLD_KEY,
                Constants.SLEEP_INDICATION_THRESHOLD_DEFAULT));

        hyperThresholdMmolSpinner.setValue(prefs.getDouble(Constants.HYPER_THRESHOLD_MMOL_KEY,
                Constants.HYPER_THRESHOLD_MMOL_DEFAULT));
        hyperThresholdMgSpinner.setValue(prefs.getDouble(Constants.HYPER_THRESHOLD_MG_KEY,
                Constants.HYPER_THRESHOLD_MG_DEFAULT));
        hyperFollowingTimeSpinner.setValue(prefs.getInt(Constants.HYPER_FOLLOW_TIME_KEY,
                Constants.HYPER_FOLLOW_TIME_DEFAULT));
        hyperMealHistorySpinner.setValue(prefs.getInt(Constants.HYPER_MEAL_HISTORY_TIME_KEY,
                Constants.HYPER_MEAL_HISTORY_TIME_DEFAULT) / 60);

        carelinkUserField.setText(prefs.get(Constants.CARELINK_USER_KEY, ""));
        carelinkPasswordField.setText(prefs.get(Constants.CARELINK_PW_KEY, ""));
        googleUserField.setText(prefs.get(Constants.GOOGLE_USER_KEY, ""));
        googlePasswordField.setText(prefs.get(Constants.GOOGLE_PW_KEY, ""));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        saveButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        mmolRadioButton = new javax.swing.JRadioButton();
        mgRadioButton = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        hypoFollowingTimeSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        hypoThresholdMgSpinner = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        hypoMealHistorySpinner = new javax.swing.JSpinner();
        hypoExerciseHistorySpinner = new javax.swing.JSpinner();
        sleepStartSpinner = new javax.swing.JSpinner();
        sleepStopSpinner = new javax.swing.JSpinner();
        sleepIndicationThSpinner = new javax.swing.JSpinner();
        hypoThresholdMmolSpinner = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        hyperFollowingTimeSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        hyperThresholdMgSpinner = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        hyperThresholdMmolSpinner = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        hyperMealHistorySpinner = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        googleTestButton = new javax.swing.JButton();
        googlePasswordField = new javax.swing.JPasswordField();
        googleUserField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        carelinkTestButton = new javax.swing.JButton();
        carelinkPasswordField = new javax.swing.JPasswordField();
        carelinkUserField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Options");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(mmolRadioButton);
        mmolRadioButton.setText("mmol/l");
        mmolRadioButton.setEnabled(false);

        buttonGroup1.add(mgRadioButton);
        mgRadioButton.setSelected(true);
        mgRadioButton.setText("mg/dl");

        jLabel7.setText("Unit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                .addComponent(mmolRadioButton)
                .addGap(18, 18, 18)
                .addComponent(mgRadioButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mgRadioButton)
                    .addComponent(mmolRadioButton)
                    .addComponent(jLabel7))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", jPanel1);

        jLabel5.setText("Follow Up Time Span [minutes]");

        hypoFollowingTimeSpinner.setModel(new javax.swing.SpinnerNumberModel(60, 1, 240, 1));

        jLabel3.setText("Hypo Threshold [mg/dl]");

        hypoThresholdMgSpinner.setModel(new javax.swing.SpinnerNumberModel(71.0d, 70.0d, 100.0d, 1.0d));
        hypoThresholdMgSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setText("Meal History Time Span [hours]");
        jLabel9.setToolTipText("Time span within the system searches for BolusWizard entrys.");

        jLabel10.setText("Exercise History Time Span [minutes]");
        jLabel10.setToolTipText("Time span within the system searches for exercise markers.");

        jLabel11.setText("Usual Sleeping Hour [hour of day] ");

        jLabel12.setText("Usual Wakup Hour [hour of day]");

        jLabel13.setText("Sleep Indication Threshold [minutes]");
        jLabel13.setToolTipText("Time until system interprets user as sleeping within the sleeping time span.");

        hypoMealHistorySpinner.setModel(new javax.swing.SpinnerNumberModel(12, 1, 48, 1));

        hypoExerciseHistorySpinner.setModel(new javax.swing.SpinnerNumberModel(120, 60, 1440, 30));

        sleepStartSpinner.setModel(new javax.swing.SpinnerNumberModel(22, 16, 24, 1));

        sleepStopSpinner.setModel(new javax.swing.SpinnerNumberModel(6, 4, 12, 1));

        sleepIndicationThSpinner.setModel(new javax.swing.SpinnerNumberModel(30, 15, 240, 15));

        hypoThresholdMmolSpinner.setModel(new javax.swing.SpinnerNumberModel(3.9d, 0.0d, 5.6d, 0.1d));

        jLabel14.setText("Hypo Threshold [mmol/l]");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sleepIndicationThSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sleepStopSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sleepStartSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hypoExerciseHistorySpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(hypoMealHistorySpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hypoFollowingTimeSpinner, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hypoThresholdMmolSpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hypoThresholdMgSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hypoThresholdMgSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hypoThresholdMmolSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hypoFollowingTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hypoMealHistorySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hypoExerciseHistorySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sleepStartSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sleepStopSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sleepIndicationThSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hypo", jPanel2);

        jLabel6.setText("Follow Up Time Span [minutes]");

        hyperFollowingTimeSpinner.setModel(new javax.swing.SpinnerNumberModel(1440, 60, 2880, 30));

        jLabel4.setText("Hyper Threshold [mg/dl]");

        hyperThresholdMgSpinner.setModel(new javax.swing.SpinnerNumberModel(180.0d, 120.0d, 301.0d, 1.0d));

        jLabel8.setText("Meal History Time Span [hours]");

        hyperThresholdMmolSpinner.setModel(new javax.swing.SpinnerNumberModel(10.0d, 6.7d, 16.7d, 0.1d));

        jLabel15.setText("Hyper Threshold [mmol/ll]");

        hyperMealHistorySpinner.setModel(new javax.swing.SpinnerNumberModel(12, 1, 48, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hyperThresholdMmolSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(hyperThresholdMgSpinner, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hyperMealHistorySpinner, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hyperFollowingTimeSpinner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hyperThresholdMgSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hyperThresholdMmolSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hyperFollowingTimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hyperMealHistorySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hyper", jPanel3);

        googleTestButton.setText("Test");
        googleTestButton.setEnabled(false);

        googlePasswordField.setEnabled(false);

        googleUserField.setEnabled(false);

        jLabel2.setText("Google Credentials:");
        jLabel2.setEnabled(false);

        carelinkTestButton.setText("Test");
        carelinkTestButton.setEnabled(false);

        carelinkPasswordField.setEnabled(false);

        carelinkUserField.setEnabled(false);

        jLabel1.setText("Carelink Credentials:");
        jLabel1.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(carelinkUserField)
                    .addComponent(carelinkPasswordField)
                    .addComponent(googleUserField)
                    .addComponent(googlePasswordField)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 199, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(carelinkTestButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(googleTestButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carelinkUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carelinkPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carelinkTestButton)
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(googleUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(googlePasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(googleTestButton)
                .addGap(0, 58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Credentials", jPanel4);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton)
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

        prefs.putDouble(Constants.HYPO_THRESHOLD_MG_KEY,
                (Double) hypoThresholdMgSpinner.getValue());
        prefs.putDouble(Constants.HYPO_THRESHOLD_MMOL_KEY,
                (Double) hypoThresholdMmolSpinner.getValue());
        prefs.putInt(Constants.HYPO_FOLLOW_TIME_KEY,
                (Integer) hypoFollowingTimeSpinner.getValue());
        prefs.putInt(Constants.HYPO_MEAL_HISTORY_TIME_KEY,
                (Integer) hypoMealHistorySpinner.getValue() * 60);
        prefs.putInt(Constants.HYPO_EXERCISE_HISTORY_TIME_KEY,
                (Integer) hypoExerciseHistorySpinner.getValue());
        prefs.putInt(Constants.SLEEP_INDICATION_BED_TIME_KEY,
                (Integer) sleepStartSpinner.getValue());
        prefs.putInt(Constants.SLEEP_INDICATION_WAKEUP_TIME_KEY,
                (Integer) sleepStopSpinner.getValue());
        prefs.putInt(Constants.SLEEP_INDICATION_THRESHOLD_KEY,
                (Integer) sleepIndicationThSpinner.getValue());

        prefs.putDouble(Constants.HYPER_THRESHOLD_MG_KEY,
                (Double) hyperThresholdMgSpinner.getValue());
        prefs.putDouble(Constants.HYPER_THRESHOLD_MMOL_KEY,
                (Double) hyperThresholdMmolSpinner.getValue());
        prefs.putInt(Constants.HYPER_FOLLOW_TIME_KEY,
                (Integer) hyperFollowingTimeSpinner.getValue());
        prefs.putInt(Constants.HYPER_MEAL_HISTORY_TIME_KEY,
                (Integer) hyperMealHistorySpinner.getValue() * 60);

        prefs.put(Constants.CARELINK_USER_KEY, carelinkUserField.getText());
        prefs.put(Constants.CARELINK_PW_KEY, new String(carelinkPasswordField.getPassword()));
        prefs.put(Constants.GOOGLE_USER_KEY, googleUserField.getText());
        prefs.put(Constants.GOOGLE_PW_KEY, new String(googlePasswordField.getPassword()));
        this.dispose();
    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPasswordField carelinkPasswordField;
    private javax.swing.JButton carelinkTestButton;
    private javax.swing.JTextField carelinkUserField;
    private javax.swing.JPasswordField googlePasswordField;
    private javax.swing.JButton googleTestButton;
    private javax.swing.JTextField googleUserField;
    private javax.swing.JSpinner hyperFollowingTimeSpinner;
    private javax.swing.JSpinner hyperMealHistorySpinner;
    private javax.swing.JSpinner hyperThresholdMgSpinner;
    private javax.swing.JSpinner hyperThresholdMmolSpinner;
    private javax.swing.JSpinner hypoExerciseHistorySpinner;
    private javax.swing.JSpinner hypoFollowingTimeSpinner;
    private javax.swing.JSpinner hypoMealHistorySpinner;
    private javax.swing.JSpinner hypoThresholdMgSpinner;
    private javax.swing.JSpinner hypoThresholdMmolSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton mgRadioButton;
    private javax.swing.JRadioButton mmolRadioButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JSpinner sleepIndicationThSpinner;
    private javax.swing.JSpinner sleepStartSpinner;
    private javax.swing.JSpinner sleepStopSpinner;
    // End of variables declaration//GEN-END:variables
}
