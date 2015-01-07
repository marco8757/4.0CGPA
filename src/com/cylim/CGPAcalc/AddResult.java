package com.cylim.CGPAcalc;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddResult extends Activity implements OnClickListener {

	Button badd, breset;
	EditText etcourse, etcredit;
	Spinner spingrade;
	ArrayList<String> gradelist = new ArrayList<String>();
	ArrayList<String> gradepoint = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_result);

		badd = (Button) findViewById(R.id.bARadd);
		breset = (Button) findViewById(R.id.bARreset);
		etcourse = (EditText) findViewById(R.id.etARcourse);
		etcredit = (EditText) findViewById(R.id.etARcredithour);
		spingrade = (Spinner) findViewById(R.id.spinARgrade);

		badd.setOnClickListener(this);
		breset.setOnClickListener(this);

		DatabaseHandler db = new DatabaseHandler(AddResult.this);

		String[][] dbgrades = db.getGPAGrades();

		gradelist.add("Select Grade");
		gradepoint.add("");

		for (int i = 0; i < dbgrades.length; i++) {
			if (dbgrades[i][2].matches("1")) {
				gradelist.add(dbgrades[i][0]);
				gradepoint.add(dbgrades[i][1]);
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddResult.this,
				android.R.layout.simple_spinner_item, gradelist);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spingrade.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.bARadd) {
			if (spingrade.getSelectedItemPosition() != 0
					&& !etcourse.getText().toString().matches("")
					&& !etcredit.getText().toString().matches("")) {

				DatabaseHandler db = new DatabaseHandler(AddResult.this);
				db.addResult(etcourse.getText().toString().trim(), etcredit
						.getText().toString().trim(), spingrade
						.getSelectedItem().toString(), gradepoint.get(spingrade
						.getSelectedItemPosition()));

				Toast.makeText(AddResult.this,
						"Added " + etcourse.getText().toString(),
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(AddResult.this,
						"Please fill up all fields to add result.",
						Toast.LENGTH_LONG).show();
			}

		}

		if (v.getId() == R.id.bARreset) {
			etcourse.setText("");
			etcredit.setText("");
			spingrade.setSelection(0);
		}

	}

}
