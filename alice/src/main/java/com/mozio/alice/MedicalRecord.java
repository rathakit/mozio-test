package com.mozio.alice;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rathakit.nop on 8/30/16 AD.
 */
public class MedicalRecord {

    // The medical record ID
    private String medicalRecordId;

    // The migraine included
    private boolean migraineIncluded;

    // Hallucinogenic Drug Usage
    private boolean hallucinogenicDrugUsage;

    // The percentage of being Alice
    private int percentageOfAlicePossibility;

    // The record date
    private Date recordDate;

    // TODO GETTER/SETTER
    public boolean isMigraineIncluded() {
        return migraineIncluded;
    }

    public void setMigraineIncluded(boolean migraineIncluded) {
        this.migraineIncluded = migraineIncluded;
    }

    public boolean isHallucinogenicDrugUsage() {
        return hallucinogenicDrugUsage;
    }

    public void setHallucinogenicDrugUsage(boolean hallucinogenicDrugUsage) {
        this.hallucinogenicDrugUsage = hallucinogenicDrugUsage;
    }

    public String getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(String medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getPercentageOfAlicePossibility() {
        return percentageOfAlicePossibility;
    }

    public void setPercentageOfAlicePossibility(int percentageOfAlicePossibility) {
        this.percentageOfAlicePossibility = percentageOfAlicePossibility;
    }

    /**
     * Converts the date object to string in order to display on the UI.
     * @return the display string
     */
    public String getDisplayRecordDateTime() {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault());
        return recordDate != null ? format.format(recordDate) : "";
    }
}
