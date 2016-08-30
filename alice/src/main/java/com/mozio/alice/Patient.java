package com.mozio.alice;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Created by rathakit.nop on 8/30/16 AD.
 */
public class Patient {

    private String patientId;
    private String firstName;
    private String lastName;
    private Date birthday;
    private boolean male;
    private LinkedList<MedicalRecord> records;

    // TODO Constructor
    public Patient() {
        records = new LinkedList<>();
    }

    /**
     * TODO GETTER/SETTER
     * @return
     */
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /** TODO Public Methods
     * Calculates the age of patient.
     * @return The current age if valid, otherwise -1 generated
     */
    public int getAge() {
        int age = -1;

        // The birthday object must be set.
        if (birthday != null) {
            Calendar dateOfBirth = Calendar.getInstance();
            dateOfBirth.setTime(birthday);
            Calendar current = Calendar.getInstance();

            // Cannot be born in the future.
            if (dateOfBirth.before(current)) {
                age = current.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
            }
        }

        return age;
    }

    /**
     * Converts the date object to string in order to display on the UI.
     * @return the display string
     */
    public String getDisplayBirthdayShortFormat() {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        return birthday != null ? format.format(birthday) : "";
    }

    /**
     * Converts the birthday string to date object.
     * @param birthdayStr - The string of birthday
     */
    public void setBirthday(String birthdayStr) throws ParseException{
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        birthday = format.parse(birthdayStr);
    }

    /**
     * Gets full name.
     * @return the full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Adds the medical record.
     * @param record
     */
    public void addMedicalRecord(MedicalRecord record) {
        records.add(0, record); // Always insert at the first index.
    }

    /**
     * Gets all medical records.
     * @return The medical record, null if not found
     */
    public MedicalRecord[] getAllMedicalRecord() {
        return records.toArray(new MedicalRecord[records.size()]);
    }
}
