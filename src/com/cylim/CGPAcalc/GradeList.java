package com.cylim.CGPAcalc;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GradeList extends Activity implements OnClickListener {

	Button bshare;
	ListView lvgrade;
	String[] course = {}, credithour = {}, grade = {}, points = {};
	LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grade_list);
		bshare = (Button) findViewById(R.id.bGLshare);
		bshare.setOnClickListener(this);
		lvgrade = (ListView) findViewById(R.id.lvGLgrade);
		ll = (LinearLayout) findViewById(R.id.llGLemptylist);
		if (calcCGPA() >= 0) {
			setTitle("CGPA : " + calcCGPA());
		} else {
			setTitle("4.0 CGPA");
		}

		loadListView();

		lvgrade.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				final Dialog dialog = new Dialog(GradeList.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dialog_contextmenu);
				dialog.getWindow().setLayout(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);

				final String selectedcourse = course[position];
				final String selectedcoursecredit = credithour[position];
				final String selectedcoursegrade = grade[position];

				final Button bEdit = (Button) dialog
						.findViewById(R.id.bDCMedit);
				final Button bRemove = (Button) dialog
						.findViewById(R.id.bDCMremove);

				bEdit.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						updateResult(selectedcourse, selectedcoursecredit,
								selectedcoursegrade);
						dialog.dismiss();
					}
				});

				bRemove.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						ShowDialog("Delete " + selectedcourse, "Are you sure?",
								selectedcourse, selectedcoursecredit,
								selectedcoursegrade);
						dialog.dismiss();
					}
				});

				dialog.show();

			}

		});
	}

	private void ShowDialog(String title, String message, final String course,
			final String credit, final String grade) {
		final Dialog dialog = new Dialog(GradeList.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		final TextView tvTitle = (TextView) dialog
				.findViewById(R.id.tvDialogTitle);
		final TextView tvMessage = (TextView) dialog
				.findViewById(R.id.tvDialogContent);

		final Button bOK = (Button) dialog.findViewById(R.id.bDialogOK);
		final Button bCancel = (Button) dialog.findViewById(R.id.bDialogCancel);
		tvTitle.setText(title);
		tvMessage.setText(message);
		bOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHandler db = new DatabaseHandler(GradeList.this);
				db.deleteResult(course, credit, grade);
				loadListView();
				Toast.makeText(dialog.getContext(), "Removed " + course,
						Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		});
		bCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialog.show();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadListView();
	}

	private void loadListView() {

		course = getCourse();
		credithour = getCredit();
		grade = getGrade();
		points = getPoints();
		CustomListAdapter adapter = new CustomListAdapter(GradeList.this,
				R.layout.list_template, course, credithour, grade, points);
		lvgrade.setAdapter(adapter);
		lvgrade.setEmptyView(ll);

		if (course.length == 0) {
			bshare.setVisibility(View.GONE);
		} else {
			bshare.setVisibility(View.VISIBLE);
		}

		if (calcCGPA() >= 0) {
			setTitle("CGPA : " + calcCGPA());
		} else {
			setTitle("4.0 CGPA");
		}

	}

	private double calcCGPA() {
		DatabaseHandler db = new DatabaseHandler(GradeList.this);
		double totalmultiplications = db.getCreditXPoints();
		double totalcredit = db.getCreditSum();
		return RoundTo2Decimals((totalmultiplications / totalcredit));
	}

	double RoundTo2Decimals(double d) {
		DecimalFormat df2 = new DecimalFormat("#.000");
		return Double.valueOf(df2.format(d));
	}

	private String[] getCourse() {
		DatabaseHandler db = new DatabaseHandler(GradeList.this);
		return db.getResultCourse();
	}

	private String[] getCredit() {
		DatabaseHandler db = new DatabaseHandler(GradeList.this);
		return db.getResultCredit();
	}

	private String[] getGrade() {
		DatabaseHandler db = new DatabaseHandler(GradeList.this);
		return db.getResultGrade();
	}

	private String[] getPoints() {
		DatabaseHandler db = new DatabaseHandler(GradeList.this);
		return db.getResultPoints();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bGLshare) {
			String message = "My cgpa is " + calcCGPA() + "\n";

			for (int i = 0; i < course.length; i++) {
				message = message + course[i] + " - " + grade[i] + "\n";
			}

			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, message);
			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My CGPA");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menulist, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.menuAddGrade) {
			addResult();
		}
		if (item.getItemId() == R.id.menuGradeSetting) {
			Intent i = new Intent(GradeList.this, GPASetting.class);
			i.putExtra("BackToExit", false);
			startActivity(i);

		}

		return super.onOptionsItemSelected(item);
	}

	private void updateResult(final String course, final String credit,
			final String grade) {

		final Dialog dialog = new Dialog(GradeList.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.add_result);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		final TextView tvTitle = (TextView) dialog.findViewById(R.id.tvARtitle);
		final EditText etcourse = (EditText) dialog
				.findViewById(R.id.etARcourse);
		final EditText etcredit = (EditText) dialog
				.findViewById(R.id.etARcredithour);

		tvTitle.setText("Update " + course);

		final Button badd = (Button) dialog.findViewById(R.id.bARadd);
		final Button breset = (Button) dialog.findViewById(R.id.bARreset);
		badd.setText("Update");
		final Spinner spingrade = (Spinner) dialog
				.findViewById(R.id.spinARgrade);

		final ArrayList<String> gradelist = new ArrayList<String>();
		final ArrayList<String> gradepoint = new ArrayList<String>();
		DatabaseHandler db = new DatabaseHandler(this);

		String[][] dbgrades = db.getGPAGrades();

		gradelist.add("Select Grade");
		gradepoint.add("");

		for (int i = 0; i < dbgrades.length; i++) {
			if (dbgrades[i][2].matches("1")) {
				gradelist.add(dbgrades[i][0]);
				gradepoint.add(dbgrades[i][1]);
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, gradelist);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spingrade.setAdapter(adapter);

		badd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (spingrade.getSelectedItemPosition() != 0
						&& !etcourse.getText().toString().matches("")
						&& !etcredit.getText().toString().matches("")) {

					DatabaseHandler db = new DatabaseHandler(dialog
							.getContext());
					db.updateResult(course, credit, grade, etcourse.getText()
							.toString().trim(), etcredit.getText().toString()
							.trim(), spingrade.getSelectedItem().toString(),
							gradepoint.get(spingrade.getSelectedItemPosition()));

					Toast.makeText(dialog.getContext(),
							"Updated " + etcourse.getText().toString(),
							Toast.LENGTH_LONG).show();
					loadListView();
					dialog.dismiss();
				} else {
					Toast.makeText(dialog.getContext(),
							"Please fill up all fields to update result.",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		breset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etcourse.setText("");
				etcredit.setText("");
				spingrade.setSelection(0);
			}
		});

		dialog.show();

	}

	private void addResult() {

		final Dialog dialog = new Dialog(GradeList.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.add_result);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		final TextView tvTitle = (TextView) dialog.findViewById(R.id.tvARtitle);
		final EditText etcourse = (EditText) dialog
				.findViewById(R.id.etARcourse);
		final EditText etcredit = (EditText) dialog
				.findViewById(R.id.etARcredithour);

		tvTitle.setText("Add Result");

		final Button badd = (Button) dialog.findViewById(R.id.bARadd);
		final Button breset = (Button) dialog.findViewById(R.id.bARreset);
		final Spinner spingrade = (Spinner) dialog
				.findViewById(R.id.spinARgrade);

		final ArrayList<String> gradelist = new ArrayList<String>();
		final ArrayList<String> gradepoint = new ArrayList<String>();
		DatabaseHandler db = new DatabaseHandler(this);

		String[][] dbgrades = db.getGPAGrades();

		gradelist.add("Select Grade");
		gradepoint.add("");

		for (int i = 0; i < dbgrades.length; i++) {
			if (dbgrades[i][2].matches("1")) {
				gradelist.add(dbgrades[i][0]);
				gradepoint.add(dbgrades[i][1]);
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, gradelist);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spingrade.setAdapter(adapter);

		badd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (spingrade.getSelectedItemPosition() != 0
						&& !etcourse.getText().toString().matches("")
						&& !etcredit.getText().toString().matches("")) {

					DatabaseHandler db = new DatabaseHandler(dialog
							.getContext());
					db.addResult(etcourse.getText().toString().trim(), etcredit
							.getText().toString().trim(), spingrade
							.getSelectedItem().toString(), gradepoint
							.get(spingrade.getSelectedItemPosition()));

					Toast.makeText(dialog.getContext(),
							"Added " + etcourse.getText().toString(),
							Toast.LENGTH_LONG).show();
					loadListView();
					dialog.dismiss();
				} else {
					Toast.makeText(dialog.getContext(),
							"Please fill up all fields to add result.",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		breset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etcourse.setText("");
				etcredit.setText("");
				spingrade.setSelection(0);
			}
		});

		dialog.show();

	}

}
