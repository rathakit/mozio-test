package com.mozio.todd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mozio.alice.MedicalRecord;

public class MedicalRecordAdapter extends ArrayAdapter<MedicalRecord> {
	
	// The items
    private MedicalRecord[] items;
	
    // Constructor
	public MedicalRecordAdapter(Context context, MedicalRecord[] items) {
        super(context, R.layout.list_medical_record_item, items);
        this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_medical_record_item, null);
		}

		// Gets the context.
		Context context = getContext();
		
		// Review
		MedicalRecord record = items[position];
		
		// Conclusion
		TextView conclusionTextView = (TextView) convertView.findViewById(R.id.conclusion_text_view);
		int percentage = record.getPercentageOfAlicePossibility();
		int zeroPossibility = 0;
		if (percentage == zeroPossibility) {
			conclusionTextView.setText(context.getString(R.string.zero_percent_risk_message));
		} else {
			conclusionTextView.setText(String.format(context.getString(R.string.n_percent_risk_message), percentage));
		}

		// Drug Usage
		TextView drugUsageTextView = (TextView) convertView.findViewById(R.id.drug_usage_text_view);
		drugUsageTextView.setText(String.format(context.getString(R.string.drug_usage_format_text),
				record.isHallucinogenicDrugUsage() ? context.getString(R.string.uppercase_yes_title) :
						context.getString(R.string.uppercase_no_title)));


		// Migraine
		TextView migraineTextView = (TextView) convertView.findViewById(R.id.migraine_text_view);
		migraineTextView.setText(String.format(context.getString(R.string.migraine_format_text),
				record.isMigraineIncluded() ? context.getString(R.string.uppercase_yes_title) :
						context.getString(R.string.uppercase_no_title)));


		// Date & Time
		TextView recordDateTextView = (TextView) convertView.findViewById(R.id.record_date_time_text_view);
		recordDateTextView.setText(record.getDisplayRecordDateTime());

		return convertView;
	}
}