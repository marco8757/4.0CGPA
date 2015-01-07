package com.cylim.CGPAcalc;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class GPASetting extends Activity implements OnCheckedChangeListener {

	EditText a1, a, a2, b1, b, b2, c1, c, c2, d1, d, d2, e1, e, e2, f1, f, f2;
	CheckBox cba1, cba, cba2, cbb1, cbb, cbb2, cbc1, cbc, cbc2, cbd1, cbd,
			cbd2, cbe1, cbe, cbe2, cbf1, cbf, cbf2;
	Bundle bundle;
	boolean backtoexit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gpa_setting);
		initialize();
		bundle = getIntent().getExtras();
		backtoexit = bundle.getBoolean("BackToExit");
	}

	private String boolStringConvert(Boolean x) {
		String result = "0";

		if (x) {
			result = "1";
		}

		return result;
	}

	private String stringValidator(String x) {
		String result = "-1";

		if (x.length() != 0) {
			result = x;
		}

		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.gradesetting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.gradesettingsave) {

			updateGrades();
			if (backtoexit) {
				Intent i = new Intent(GPASetting.this, GradeList.class);
				startActivity(i);
			}
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void updateGrades() {

		Thread thread = new Thread() {
			public void run() {
				DatabaseHandler db = new DatabaseHandler(GPASetting.this);

				db.updateGrade("A+", stringValidator(a1.getText().toString()),
						boolStringConvert(cba1.isChecked()));
				db.updateGrade("A", stringValidator(a.getText().toString()),
						boolStringConvert(cba.isChecked()));
				db.updateGrade("A-", stringValidator(a2.getText().toString()),
						boolStringConvert(cba2.isChecked()));

				db.updateGrade("B+", stringValidator(b1.getText().toString()),
						boolStringConvert(cbb1.isChecked()));
				db.updateGrade("B", stringValidator(b.getText().toString()),
						boolStringConvert(cbb.isChecked()));
				db.updateGrade("B-", stringValidator(b2.getText().toString()),
						boolStringConvert(cbb2.isChecked()));

				db.updateGrade("C+", stringValidator(c1.getText().toString()),
						boolStringConvert(cbc1.isChecked()));
				db.updateGrade("C", stringValidator(c.getText().toString()),
						boolStringConvert(cbc.isChecked()));
				db.updateGrade("C-", stringValidator(c2.getText().toString()),
						boolStringConvert(cbc2.isChecked()));

				db.updateGrade("D+", stringValidator(d1.getText().toString()),
						boolStringConvert(cbd1.isChecked()));
				db.updateGrade("D", stringValidator(d.getText().toString()),
						boolStringConvert(cbd.isChecked()));
				db.updateGrade("D-", stringValidator(d2.getText().toString()),
						boolStringConvert(cbd2.isChecked()));

				db.updateGrade("E+", stringValidator(e1.getText().toString()),
						boolStringConvert(cbe1.isChecked()));
				db.updateGrade("E", stringValidator(e.getText().toString()),
						boolStringConvert(cbe.isChecked()));
				db.updateGrade("E-", stringValidator(e2.getText().toString()),
						boolStringConvert(cbe2.isChecked()));

				db.updateGrade("F+", stringValidator(f1.getText().toString()),
						boolStringConvert(cbf1.isChecked()));
				db.updateGrade("F", stringValidator(f.getText().toString()),
						boolStringConvert(cbf.isChecked()));
				db.updateGrade("F-", stringValidator(f2.getText().toString()),
						boolStringConvert(cbf2.isChecked()));
			}
		};
		thread.start();

	}

	private boolean[] convertiontoBoolean(ArrayList<String> e) {

		boolean[] x = new boolean[e.size()];

		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).equals("1")) {
				x[i] = true;
			} else {
				x[i] = false;
			}
		}

		return x;

	}

	private String[] stringValidate(ArrayList<String> s) {
		String[] x = new String[s.size()];

		for (int i = 0; i < s.size(); i++) {
			if (s.get(i).isEmpty() || s.get(i).equals("-1")) {
				x[i] = "";
			} else {
				x[i] = s.get(i);
			}
		}

		return x;

	}

	private void initialize() {

		DatabaseHandler db = new DatabaseHandler(GPASetting.this);

		ArrayList<String> gradepoint = new ArrayList<String>();
		ArrayList<String> enabled = new ArrayList<String>();

		String[][] dbgrades = db.getAllGPAGrades();

		for (int i = 0; i < dbgrades.length; i++) {

			gradepoint.add(dbgrades[i][1]);
			enabled.add(dbgrades[i][2]);

		}

		boolean x[] = convertiontoBoolean(enabled);

		String y[] = stringValidate(gradepoint);

		a1 = (EditText) findViewById(R.id.etGPAa1);
		a = (EditText) findViewById(R.id.etGPAa2);
		a2 = (EditText) findViewById(R.id.etGPAa3);

		b1 = (EditText) findViewById(R.id.etGPAb1);
		b = (EditText) findViewById(R.id.etGPAb2);
		b2 = (EditText) findViewById(R.id.etGPAb3);

		c1 = (EditText) findViewById(R.id.etGPAc1);
		c = (EditText) findViewById(R.id.etGPAc2);
		c2 = (EditText) findViewById(R.id.etGPAc3);

		d1 = (EditText) findViewById(R.id.etGPAd1);
		d = (EditText) findViewById(R.id.etGPAd2);
		d2 = (EditText) findViewById(R.id.etGPAd3);

		e1 = (EditText) findViewById(R.id.etGPAe1);
		e = (EditText) findViewById(R.id.etGPAe2);
		e2 = (EditText) findViewById(R.id.etGPAe3);

		f1 = (EditText) findViewById(R.id.etGPAf1);
		f = (EditText) findViewById(R.id.etGPAf2);
		f2 = (EditText) findViewById(R.id.etGPAf3);

		cba1 = (CheckBox) findViewById(R.id.cbGPAEnable1);
		cba = (CheckBox) findViewById(R.id.cbGPAEnable2);
		cba2 = (CheckBox) findViewById(R.id.cbGPAEnable3);

		cbb1 = (CheckBox) findViewById(R.id.cbGPAEnable4);
		cbb = (CheckBox) findViewById(R.id.cbGPAEnable5);
		cbb2 = (CheckBox) findViewById(R.id.cbGPAEnable6);

		cbc1 = (CheckBox) findViewById(R.id.cbGPAEnable7);
		cbc = (CheckBox) findViewById(R.id.cbGPAEnable8);
		cbc2 = (CheckBox) findViewById(R.id.cbGPAEnable9);

		cbd1 = (CheckBox) findViewById(R.id.cbGPAEnable10);
		cbd = (CheckBox) findViewById(R.id.cbGPAEnable11);
		cbd2 = (CheckBox) findViewById(R.id.cbGPAEnable12);

		cbe1 = (CheckBox) findViewById(R.id.cbGPAEnable13);
		cbe = (CheckBox) findViewById(R.id.cbGPAEnable14);
		cbe2 = (CheckBox) findViewById(R.id.cbGPAEnable15);

		cbf1 = (CheckBox) findViewById(R.id.cbGPAEnable16);
		cbf = (CheckBox) findViewById(R.id.cbGPAEnable17);
		cbf2 = (CheckBox) findViewById(R.id.cbGPAEnable18);

		cba1.setOnCheckedChangeListener(this);
		cba.setOnCheckedChangeListener(this);
		cba2.setOnCheckedChangeListener(this);
		cbb1.setOnCheckedChangeListener(this);
		cbb.setOnCheckedChangeListener(this);
		cbb2.setOnCheckedChangeListener(this);
		cbc1.setOnCheckedChangeListener(this);
		cbc.setOnCheckedChangeListener(this);
		cbc2.setOnCheckedChangeListener(this);
		cbd1.setOnCheckedChangeListener(this);
		cbd.setOnCheckedChangeListener(this);
		cbd2.setOnCheckedChangeListener(this);
		cbe1.setOnCheckedChangeListener(this);
		cbe.setOnCheckedChangeListener(this);
		cbe2.setOnCheckedChangeListener(this);
		cbf1.setOnCheckedChangeListener(this);
		cbf.setOnCheckedChangeListener(this);
		cbf2.setOnCheckedChangeListener(this);

		a1.setEnabled(x[0]);
		a.setEnabled(x[1]);
		a2.setEnabled(x[2]);
		b1.setEnabled(x[3]);
		b.setEnabled(x[4]);
		b2.setEnabled(x[5]);
		c1.setEnabled(x[6]);
		c.setEnabled(x[7]);
		c2.setEnabled(x[8]);
		d1.setEnabled(x[9]);
		d.setEnabled(x[10]);
		d2.setEnabled(x[11]);
		e1.setEnabled(x[12]);
		e.setEnabled(x[13]);
		e2.setEnabled(x[14]);
		f1.setEnabled(x[15]);
		f.setEnabled(x[16]);
		f2.setEnabled(x[17]);

		a1.setText(y[0]);
		a.setText(y[1]);
		a2.setText(y[2]);

		b1.setText(y[3]);
		b.setText(y[4]);
		b2.setText(y[5]);

		c1.setText(y[6]);
		c.setText(y[7]);
		c2.setText(y[8]);

		d1.setText(y[9]);
		d.setText(y[10]);
		d2.setText(y[11]);

		e1.setText(y[12]);
		e.setText(y[13]);
		e2.setText(y[14]);

		f1.setText(y[15]);
		f.setText(y[16]);
		f2.setText(y[17]);

		cba1.setChecked(x[0]);
		cba.setChecked(x[1]);
		cba2.setChecked(x[2]);

		cbb1.setChecked(x[3]);
		cbb.setChecked(x[4]);
		cbb2.setChecked(x[5]);

		cbc1.setChecked(x[6]);
		cbc.setChecked(x[7]);
		cbc2.setChecked(x[8]);

		cbd1.setChecked(x[9]);
		cbd.setChecked(x[10]);
		cbd2.setChecked(x[11]);

		cbe1.setChecked(x[12]);
		cbe.setChecked(x[13]);
		cbe2.setChecked(x[14]);

		cbf1.setChecked(x[15]);
		cbf.setChecked(x[16]);
		cbf2.setChecked(x[17]);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		backToWhere(backtoexit);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		if (buttonView.getId() == R.id.cbGPAEnable1) {
			a1.setEnabled(isChecked);
			a1.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable2) {
			a.setEnabled(isChecked);
			a.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable3) {
			a2.setEnabled(isChecked);
			a2.requestFocus();
		}

		if (buttonView.getId() == R.id.cbGPAEnable4) {
			b1.setEnabled(isChecked);
			b1.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable5) {
			b.setEnabled(isChecked);
			b.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable6) {
			b2.setEnabled(isChecked);
			b2.requestFocus();
		}

		if (buttonView.getId() == R.id.cbGPAEnable7) {
			c1.setEnabled(isChecked);
			c1.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable8) {
			c.setEnabled(isChecked);
			c.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable9) {
			c2.setEnabled(isChecked);
			c2.requestFocus();
		}

		if (buttonView.getId() == R.id.cbGPAEnable10) {
			d1.setEnabled(isChecked);
			d1.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable11) {
			d.setEnabled(isChecked);
			d.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable12) {
			d2.setEnabled(isChecked);
			d2.requestFocus();
		}

		if (buttonView.getId() == R.id.cbGPAEnable13) {
			e1.setEnabled(isChecked);
			e1.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable14) {
			e.setEnabled(isChecked);
			e.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable15) {
			e2.setEnabled(isChecked);
			e2.requestFocus();
		}

		if (buttonView.getId() == R.id.cbGPAEnable16) {
			f1.setEnabled(isChecked);
			f1.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable17) {
			f.setEnabled(isChecked);
			f.requestFocus();
		}
		if (buttonView.getId() == R.id.cbGPAEnable18) {
			f2.setEnabled(isChecked);
			f2.requestFocus();
		}

	}

	private void backToWhere(boolean b) {
		if (b) {
			final Dialog dialog = new Dialog(GPASetting.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog);
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			final TextView tvTitle = (TextView) dialog
					.findViewById(R.id.tvDialogTitle);
			final TextView tvMessage = (TextView) dialog
					.findViewById(R.id.tvDialogContent);

			final Button bOK = (Button) dialog.findViewById(R.id.bDialogOK);
			final Button bCancel = (Button) dialog
					.findViewById(R.id.bDialogCancel);
			tvTitle.setText("Leaving so soon?");
			tvMessage
					.setText("Please add grade points to respective grades to start using this app.\nClick OK to enter, Cancel to leave.");
			bOK.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			bCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					dialog.dismiss();
				}
			});

			dialog.show();

		} else {
			finish();
		}
	}
}
