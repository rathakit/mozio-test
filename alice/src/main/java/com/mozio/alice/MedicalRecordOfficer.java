package com.mozio.alice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rathakit.nop on 8/30/16 AD.
 */
public class MedicalRecordOfficer extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String MEDICAL_RECORD_TABLE_NAME = "medical_record";
    private static final String MEDICAL_RECORD_ID_FIELD = "medical_record_id";
    private static final String MIGRAINE_INCLUDED_FIELD = "migraine_included";
    private static final String POSSIBILITY_PERCENTAGE_FIELD = "possibility_percentage";
    private static final String DRUG_USAGE_FIELD = "hallucinogenic_drug_usage";
    private static final String RECORD_DATE_TIME_FIELD = "record_date_time";
    private static final String PATIENT_TABLE_NAME = "patient";
    private static final String PATIENT_ID_FIELD = "patient_id";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";
    private static final String GENDER_FIELD = "gender";
    private static final String BIRTHDAY_FIELD = "birthday";
    private static final String MALE_VALUE = "M";
    private static final String FEMALE_VALUE = "F";
    private static final String DATABASE_NAME = "MozioMedicalCare.db";
    private static final String SQL_CREATE_PATIENT_TABLE =
            "CREATE TABLE " + PATIENT_TABLE_NAME + " (" + PATIENT_ID_FIELD + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                    FIRST_NAME_FIELD + " TEXT NOT NULL, " + LAST_NAME_FIELD + " TEXT NOT NULL, "+
                    BIRTHDAY_FIELD + " TEXT NOT NULL, "+ GENDER_FIELD + " TEXT NOT NULL);";
    private static final String SQL_CREATE_MEDICAL_RECORD_TABLE =
            "CREATE TABLE " + MEDICAL_RECORD_TABLE_NAME + " (" + MEDICAL_RECORD_ID_FIELD + " INTEGER PRIMARY KEY   AUTOINCREMENT, " +
                    MIGRAINE_INCLUDED_FIELD + " INTEGER NOT NULL, " + DRUG_USAGE_FIELD + " INTEGER NOT NULL, " +
                    POSSIBILITY_PERCENTAGE_FIELD + " INTEGER NOT NULL, " +
                    RECORD_DATE_TIME_FIELD + " TEXT NOT NULL, " + PATIENT_ID_FIELD + " INTEGER NOT NULL, "+
                    "FOREIGN KEY(" + PATIENT_ID_FIELD + ") REFERENCES " + PATIENT_TABLE_NAME + "(" + PATIENT_ID_FIELD + "));";
    private static final String SQL_DELETE_PATIENT_TABLE =
            "DROP TABLE IF EXISTS " + PATIENT_TABLE_NAME;
    private static final String SQL_DELETE_MEDICAL_RECORD_TABLE =
            "DROP TABLE IF EXISTS " + MEDICAL_RECORD_TABLE_NAME;

    public MedicalRecordOfficer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override // TODO SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PATIENT_TABLE);
        db.execSQL(SQL_CREATE_MEDICAL_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MEDICAL_RECORD_TABLE);
        db.execSQL(SQL_DELETE_PATIENT_TABLE);
        onCreate(db);
    }

    /** TODO Public Methods
     * Do register the patient into the local storage.
     * @param patient
     * @return the status of registration
     */
    public boolean register(Patient patient) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME_FIELD, patient.getFirstName());
        values.put(LAST_NAME_FIELD, patient.getLastName());
        values.put(BIRTHDAY_FIELD, patient.getDisplayBirthdayShortFormat());
        values.put(GENDER_FIELD, patient.isMale() ? MALE_VALUE : FEMALE_VALUE);

        // Insert the new row, returning the primary key value of the new row.
        return db.insert(PATIENT_TABLE_NAME, null, values) > 0;
    }

    /**
     * Finds the patient from ID.
     * @param patientId
     * @return
     * @throws ParseException - Parsing birthday might cause the exception.
     */
    public Patient find(String patientId) throws ParseException {
        SQLiteDatabase db = getReadableDatabase();

        // The columns from the database will actually use after this query.
        String[] projection = {
                PATIENT_ID_FIELD,
                FIRST_NAME_FIELD,
                LAST_NAME_FIELD,
                BIRTHDAY_FIELD,
                GENDER_FIELD
        };

        // Filter results WHERE "patient_id" = '?'
        String selection = PATIENT_ID_FIELD + " = ?";
        String[] selectionArgs = { patientId };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = PATIENT_ID_FIELD + " DESC";

        // Do query!
        Cursor cursor = db.query(
                PATIENT_TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Patient patient = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            long id = cursor.getLong(cursor.getColumnIndex(PATIENT_ID_FIELD));
            String firstName = cursor.getString(cursor.getColumnIndex(FIRST_NAME_FIELD));
            String lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME_FIELD));
            String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY_FIELD));
            String gender = cursor.getString(cursor.getColumnIndex(GENDER_FIELD));
            patient = new Patient();
            patient.setPatientId(String.valueOf(id));
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setMale(gender.equals(MALE_VALUE));
            patient.setBirthday(birthday);

            // Adds the medical records.
            attachMedicalRecords(patient);
        }
        return patient;
    }

    /**
     * Records the result locally.
     * @param patient - The patient
     * @param record - The medical record
     * @return the record status
     */
    public boolean record(Patient patient, MedicalRecord record) {
        // Insert into the local DB.
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MIGRAINE_INCLUDED_FIELD, record.isMigraineIncluded());
        values.put(DRUG_USAGE_FIELD, record.isHallucinogenicDrugUsage());
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
        values.put(RECORD_DATE_TIME_FIELD, format.format(record.getRecordDate()));
        values.put(POSSIBILITY_PERCENTAGE_FIELD, record.getPercentageOfAlicePossibility());
        values.put(PATIENT_ID_FIELD, patient.getPatientId());

        // Insert the new row, returning the primary key value of the new row.
        return db.insert(MEDICAL_RECORD_TABLE_NAME, null, values) > 0;
    }

    /** TODO Private Methods
     * Attaches the medical record of the patient.
     * @param patient
     * @return the attach result
     */
    private void attachMedicalRecords(Patient patient) throws ParseException {
        SQLiteDatabase db = getReadableDatabase();

        // The columns from the database will actually use after this query.
        String[] projection = {
                MEDICAL_RECORD_ID_FIELD,
                MIGRAINE_INCLUDED_FIELD,
                DRUG_USAGE_FIELD,
                RECORD_DATE_TIME_FIELD,
                POSSIBILITY_PERCENTAGE_FIELD,
                PATIENT_ID_FIELD
        };

        // Filter results WHERE "patient_id" = '?'
        String selection = PATIENT_ID_FIELD + " = ?";
        String[] selectionArgs = { patient.getPatientId() };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = RECORD_DATE_TIME_FIELD + " DESC";

        // Do query!
        Cursor cursor = db.query(
                MEDICAL_RECORD_TABLE_NAME,                // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // Loop each record.
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(MEDICAL_RECORD_ID_FIELD));
            boolean drugUsage = cursor.getInt(cursor.getColumnIndex(DRUG_USAGE_FIELD)) == 1;
            boolean migraineIncluded = cursor.getInt(cursor.getColumnIndex(MIGRAINE_INCLUDED_FIELD)) == 1;
            int percentage = cursor.getInt(cursor.getColumnIndex(POSSIBILITY_PERCENTAGE_FIELD));
            String recordDateStr = cursor.getString(cursor.getColumnIndex(RECORD_DATE_TIME_FIELD));
            DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
            Date recordDate = format.parse(recordDateStr);
            MedicalRecord record = new MedicalRecord();
            record.setMedicalRecordId(String.valueOf(id));
            record.setHallucinogenicDrugUsage(drugUsage);
            record.setMigraineIncluded(migraineIncluded);
            record.setPercentageOfAlicePossibility(percentage);
            record.setRecordDate(recordDate);
            patient.addMedicalRecord(record);
        }
    }
}
