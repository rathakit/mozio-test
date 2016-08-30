package com.mozio.todd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mozio.alice.MedicalRecord;
import com.mozio.alice.MedicalRecordOfficer;
import com.mozio.alice.Neurologist;
import com.mozio.alice.Patient;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    // The full name text view
    private TextView fullNameTextView;

    // The patient's ID text view
    private EditText patientIdEditText;

    // The patient info layout
    private LinearLayout patientInfoLayout;

    // The drug usage check box
    private CheckBox drugUsageCheckBox;

    // The migraine check box
    private CheckBox migraineCheckBox;

    // The medical record list view
    private ListView medicalRecordListView;

    // The officer
    private MedicalRecordOfficer officer;

    // The patient
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieves the elements from views.
        fullNameTextView = (TextView) findViewById(R.id.full_name_text_view);
        patientIdEditText = (EditText) findViewById(R.id.patient_id_edit_text);
        patientInfoLayout = (LinearLayout) findViewById(R.id.patient_info_layout);
        drugUsageCheckBox = (CheckBox) findViewById(R.id.drug_usage_checkbox);
        migraineCheckBox = (CheckBox) findViewById(R.id.migraine_checkbox);
        medicalRecordListView = (ListView) findViewById(R.id.medical_record_list_view);

        // Initials objects.
        officer = new MedicalRecordOfficer(this);
    }

    /** TODO UI's Methods
     * Called when the registration button clicked.
     * @param v
     */
    public void onRegistration(View v) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the find button clicked.
     * @param v
     */
    public void onSearch(View v) {
        String id = patientIdEditText.getText().toString().trim();
        try {
            patient = officer.find(id);
            if (patient != null) {
                fullNameTextView.setText(getString(R.string.patient_details_format_text, patient.getFullName(),
                        patient.getAge(), patient.isMale() ? getString(R.string.male_title) : getString(R.string.female_title)));
                makePatientInfoLayoutVisible(true);

                // Refresh the list of records.
                medicalRecordListView.setAdapter(new MedicalRecordAdapter(this, patient.getAllMedicalRecord()));
            } else {
                Toast.makeText(this, getString(R.string.patient_id_not_found_message), Toast.LENGTH_SHORT).show();
                makePatientInfoLayoutVisible(false);
            }
        } catch (ParseException e) {
            Toast.makeText(this, getString(R.string.search_patient_unsuccessfully_error_message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called when the diagnose button clicked.
     * @param v
     */
    public void onDiagnose(View v) {
        MedicalRecord record = new MedicalRecord();
        record.setHallucinogenicDrugUsage(drugUsageCheckBox.isChecked());
        record.setMigraineIncluded(migraineCheckBox.isChecked());
        int percentage = Neurologist.diagnoseAlicePossibility(this, patient, record);
        int zeroPossibility = 0;
        if (percentage == zeroPossibility) {
            Toast.makeText(this, getString(R.string.zero_percent_risk_message), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, String.format(getString(R.string.n_percent_risk_message), percentage), Toast.LENGTH_SHORT).show();
        }

        // Refresh the list of records.
        medicalRecordListView.setAdapter(new MedicalRecordAdapter(this, patient.getAllMedicalRecord()));
    }

    @Override // TODO AnimationListener
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        patientInfoLayout.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    /**
     * TODO Private Methods
     * Show/hide the patient's details.
     */
    private void makePatientInfoLayoutVisible(boolean shown) {
        if (shown) {
            patientInfoLayout.setVisibility(View.VISIBLE);
            Animation formAnimation = AnimationUtils.loadAnimation(this, R.anim.alp_in);
            patientInfoLayout.startAnimation(formAnimation);
        } else if (patientInfoLayout.getVisibility() == View.VISIBLE) {
            Animation formAnimation = AnimationUtils.loadAnimation(this, R.anim.alp_out);
            patientInfoLayout.startAnimation(formAnimation);
            formAnimation.setAnimationListener(this);
        }
    }
}
