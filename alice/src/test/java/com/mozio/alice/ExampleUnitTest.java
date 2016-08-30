package com.mozio.alice;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void ageTestCase() throws Exception {
        // #1
        Patient patient = new Patient();
        assertEquals(-1, patient.getAge());

        // #2
        String birthday = "31-5-1987";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        patient.setBirthday(dateFormat.parse(birthday));
        assertEquals(29, patient.getAge());

        // #3
        birthday = "15-3-1966";
        patient.setBirthday(dateFormat.parse(birthday));
        assertEquals(50, patient.getAge());

        // #4
        birthday = "1-1-1999";
        patient.setBirthday(dateFormat.parse(birthday));
        assertEquals(17, patient.getAge());

        // #5
        birthday = "1-9-2016";
        patient.setBirthday(dateFormat.parse(birthday));
        assertEquals(-1, patient.getAge());

        // #6
        birthday = "1-8-2016";
        patient.setBirthday(dateFormat.parse(birthday));
        assertEquals(0, patient.getAge());
    }

    @Test
    public void healthyTestCase() throws Exception {
        // Healthy - #1
        Patient patient = new Patient();
        MedicalRecord record = new MedicalRecord();
        assertEquals(0, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // Healthy - #2
        String birthday = "3-10-1987";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(false);
        assertEquals(0, Neurologist.diagnoseAlicePossibility(null, patient, record));
    }

    @Test
    public void _25PercentRiskTestCase() throws Exception {
        // 25% Alice Syndrome - #1 Male
        Patient patient = new Patient();
        String birthday = "3-10-1987";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        patient.setBirthday(dateFormat.parse(birthday));
        MedicalRecord record = new MedicalRecord();
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(true);
        assertEquals(25, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 25% Alice Syndrome - #2 Age
        birthday = "3-10-2012";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(false);
        assertEquals(25, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 25% Alice Syndrome - #3 Migraine
        birthday = "3-10-1987";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(false);
        assertEquals(25, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 25% Alice Syndrome - #4 Hallucinogenic Drug Usage
        birthday = "3-10-1987";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(false);
        assertEquals(25, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 25% Alice Syndrome - #5 This year born
        birthday = "3-8-2016";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(false);
        assertEquals(25, Neurologist.diagnoseAlicePossibility(null, patient, record));
    }

    @Test
    public void _50PercentRiskTestCase() throws Exception {
        // 50% Alice Syndrome - #1 Migraine + Hallucinogenic Drug Usage
        Patient patient = new Patient();
        String birthday = "3-10-1987";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        patient.setBirthday(dateFormat.parse(birthday));
        MedicalRecord record = new MedicalRecord();
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(false);
        assertEquals(50, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 50% Alice Syndrome - #2 Age + Hallucinogenic Drug Usage
        birthday = "3-10-2012";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(false);
        assertEquals(50, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 50% Alice Syndrome - #3 Age + Migraine
        birthday = "3-10-2013";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(false);
        assertEquals(50, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 50% Alice Syndrome - #4 Age + Male
        birthday = "3-10-2001";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(true);
        assertEquals(50, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 50% Alice Syndrome - #5 Migraine + Male
        birthday = "3-10-1987";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(true);
        assertEquals(50, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 50% Alice Syndrome - #6 Hallucinogenic Drug Usage + Male
        birthday = "3-10-1987";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(true);
        assertEquals(50, Neurologist.diagnoseAlicePossibility(null, patient, record));
    }

    @Test
    public void _75PercentRiskTestCase() throws Exception {
        // 75% Alice Syndrome - #1 Migraine + Hallucinogenic Drug Usage + Male
        Patient patient = new Patient();
        String birthday = "3-10-1987";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        patient.setBirthday(dateFormat.parse(birthday));
        MedicalRecord record = new MedicalRecord();
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(true);
        assertEquals(75, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 75% Alice Syndrome - #2 Age + Hallucinogenic Drug Usage + Male
        birthday = "3-10-2012";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(false);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(true);
        assertEquals(75, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 75% Alice Syndrome - #3 Age + Migraine + Male
        birthday = "3-10-2013";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(false);
        patient.setMale(true);
        assertEquals(75, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 75% Alice Syndrome - #4 Age + Migraine + Hallucinogenic Drug Usage
        birthday = "3-10-2001";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(false);
        assertEquals(75, Neurologist.diagnoseAlicePossibility(null, patient, record));
    }

    @Test
    public void _100PercentRiskTestCase() throws Exception {
        // 100% Alice Syndrome - #1
        Patient patient = new Patient();
        String birthday = "3-10-2001";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        patient.setBirthday(dateFormat.parse(birthday));
        MedicalRecord record = new MedicalRecord();
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(true);
        assertEquals(100, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 100% Alice Syndrome - #2
        birthday = "3-10-2012";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(true);
        assertEquals(100, Neurologist.diagnoseAlicePossibility(null, patient, record));

        // 100% Alice Syndrome - #3
        birthday = "3-10-2005";
        patient.setBirthday(dateFormat.parse(birthday));
        record.setMigraineIncluded(true);
        record.setHallucinogenicDrugUsage(true);
        patient.setMale(true);
        assertEquals(100, Neurologist.diagnoseAlicePossibility(null, patient, record));
    }
}