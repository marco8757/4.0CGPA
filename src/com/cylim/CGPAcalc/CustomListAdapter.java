package com.cylim.CGPAcalc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder")
public class CustomListAdapter extends ArrayAdapter<String> {

	Context context;
	String[] course;
	String[] credit;
	String[] grade;
	String[] points;

	public CustomListAdapter(Context context, int resource, String[] course,
			String[] credithour, String[] grade, String[] points) {
		super(context, resource, course);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.course = course;
		this.credit = credithour;
		this.grade = grade;
		this.points = points;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View gradeview = inflater
				.inflate(R.layout.list_template, parent, false);
		TextView tvCourse = (TextView) gradeview.findViewById(R.id.tvLTCourse);
		TextView tvCreditHour = (TextView) gradeview
				.findViewById(R.id.tvLTCredit);
		TextView tvGrade = (TextView) gradeview.findViewById(R.id.tvLTGrade);
		TextView tvPoints = (TextView) gradeview.findViewById(R.id.tvLTPoint);

		tvCourse.setText(course[position]);
		tvCreditHour.setText("Credit: " + credit[position]);
		tvGrade.setText("Grade: " + grade[position]);
		tvPoints.setText("GPA: " + points[position]);

		return gradeview;
	}
}
