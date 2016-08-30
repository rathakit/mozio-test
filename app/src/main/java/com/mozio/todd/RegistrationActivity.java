package com.mozio.todd;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mozio.alice.MedicalRecordOfficer;
import com.mozio.alice.Patient;

import java.util.Calendar;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    // The date picker
    private DatePickerDialog datePicker;

    // The first name edit text
    private EditText firstNameEditText;

    // The last name edit text
    private EditText lastNameEditText;

    // The birthday edit text
    private EditText birthdayEditText;

    // The gender radio group
    private RadioGroup genderRadioGroup;

    // The birthday
    private Date birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Retrieve the objects from view.
        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);
        birthdayEditText = (EditText) findViewById(R.id.birthday_edit_text);
        genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
    }

    /** TODO UI's Methods
     * Called when the birthday clicked.
     * @param v
     */
    public void onBirthdaySelected(View v) {
        Calendar cal = Calendar.getInstance();
        if (birthday != null) cal.setTime(birthday);
        datePicker = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        datePicker.show();
    }

    /**
     * Called when the register button clicked.
     * @param v
     */
    public void onRegister(View v) {
        String message = null;
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();

        // Validate the data input.
        if (firstName.length() == 0) {
            message = getString(R.string.first_name_not_set_error_message);
        } else if (lastName.length() == 0) {
            message = getString(R.string.last_name_not_set_error_message);
        } else if (birthday == null) {
            message = getString(R.string.birthday_not_set_error_message);
        }
        if (message != null) {
            // Show error message and reset the birthday.
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            // Do register!
            register(firstName, lastName);
        }
    }

    /**
     * TODO OnDateSetListener
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DATE, dayOfMonth);
        Date target = cal.getTime();
        if (target.before(today)) {
            // Set the birthday.
            birthday = target;

            // Update the new birthday on UI.
            birthdayEditText.setText(Util.getDisplayBirthday(birthday));
        } else {
            // Show error message and reset the birthday.
            Toast.makeText(this, getString(R.string.date_birthday_set_error_message), Toast.LENGTH_LONG).show();
            birthday = null;
        }
    }

    /** TODO Private Methods
     * Registers new patient.
     * @param firstName
     * @param lastName
     */
    private void register(String firstName, String lastName) {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setBirthday(birthday);
        patient.setMale(genderRadioGroup.getCheckedRadioButtonId() == R.id.male_radio_button);
        MedicalRecordOfficer officer = new MedicalRecordOfficer(this);
        if (officer.register(patient)) {
            // Show message and back to the previous page.
            Toast.makeText(this, getString(R.string.register_successfully_message), Toast.LENGTH_LONG).show();
            finish();
        } else {
            // Show error message and reset the birthday.
            Toast.makeText(this, getString(R.string.register_unsuccessfully_error_message), Toast.LENGTH_LONG).show();
        }
    }
}
