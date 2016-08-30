package com.mozio.alice;

import android.content.Context;

import java.util.Date;

/**
 * Created by rathakit.nop on 8/30/16 AD.
 */
public class Neurologist {

    /**
     * The method to diagnose possibility of Todd's Syndrome.
     * @param context - The context is required for local record.
     * @param patient - The patient
     * @param record - The medical record object
     * @return the percentage of Todd's Syndrome possibility.
     */
    public static int diagnoseAlicePossibility(Context context, Patient patient, MedicalRecord record) {
        int numberOfSymptom = 4;
        int agedRisk = 15;

        // Migraine?
        double point = record.isMigraineIncluded() ? 1 : 0;

        // People 15 years old or younger?
        int age = patient.getAge();
        point = age >= 0 && age <= agedRisk ? ++point : point;

        // Male?
        point = patient.isMale() ? ++point : point;

        // Hallucinogenic Drug Usage?
        point = record.isHallucinogenicDrugUsage() ? ++point : point;

        // Calculates the percentage.
        int percentage = (int) (point / numberOfSymptom * 100);
        record.setPercentageOfAlicePossibility(percentage);

        // Record Date & Time
        record.setRecordDate(new Date());

        // Attach the record to patient.
        patient.addMedicalRecord(record);

        // Record!
        if (context != null) {
            MedicalRecordOfficer officer = new MedicalRecordOfficer(context);
            officer.record(patient, record);
        }

        return percentage;
    }
}
