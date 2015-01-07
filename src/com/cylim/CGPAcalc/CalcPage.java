package com.cylim.CGPAcalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalcPage extends Activity {
	View anim;
	TextView tvcurrent, tvmessage;
	RelativeLayout rl;

	double cgpa = 0, totalcredit = 0, totalmultiplications = 0;
	Animation animation, animationshake;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calc_page);
		initialize();

	}

	private void initialize() {
		anim = (View) findViewById(R.id.vCPanim);
		tvcurrent = (TextView) findViewById(R.id.tvCPcurrent);
		tvmessage = (TextView) findViewById(R.id.tvCPmessage);
		rl = (RelativeLayout) findViewById(R.id.rlCPmainlayout);

		DatabaseHandler db = new DatabaseHandler(CalcPage.this);
		totalmultiplications = db.getCreditXPoints();
		totalcredit = db.getCreditSum();

		cgpa = RoundTo2Decimals((totalmultiplications / totalcredit));

		tvmessage.setText(message(cgpa));

		if (cgpa > 0 && cgpa <= 4) {
			tvcurrent.setText("CGPA = " + cgpa);
		} else {
			tvcurrent.setText("Hello there!");
		}

		setbgColor(cgpa);
		anim.setBackgroundResource(backgroundresources(cgpa));

		animation = AnimationUtils
				.loadAnimation(getApplication(), R.anim.scale);
		animationshake = AnimationUtils.loadAnimation(getApplication(),
				R.anim.shake);

		anim.startAnimation(animation);
		animation.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

				anim.startAnimation(animationshake);
			}
		});

		animationshake.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Thread timer = new Thread() {
					public void run() {
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {

							DatabaseHandler db = new DatabaseHandler(
									CalcPage.this);
							String x[][] = db.getGPAGrades();

							if ((cgpa > 0 && cgpa <= 4) || x.length > 0) {
								Intent i = new Intent(CalcPage.this,
										GradeList.class);
								startActivity(i);
							} else {
								Intent i = new Intent(CalcPage.this,
										GPASetting.class);
								i.putExtra("BackToExit", true);
								startActivity(i);
							}

							finish();
						}
					}
				};
				timer.start();
			}
		});

	}

	private void setbgColor(double cgpa) {

		if (cgpa >= 3.75) {
			rl.setBackgroundColor(Color.parseColor("#b2ff59"));
		} else if (cgpa >= 3.0 && cgpa < 3.75) {
			rl.setBackgroundColor(Color.parseColor("#fff176"));
		} else if (cgpa >= 2.0 && cgpa < 3.0) {
			rl.setBackgroundColor(Color.parseColor("#ffab40"));
		} else if (cgpa >= 0 && cgpa < 2.0) {
			rl.setBackgroundColor(Color.parseColor("#f36c60"));
		} else {
			rl.setBackgroundColor(Color.parseColor("#40c4ff"));
		}
	}

	private String message(double cgpa) {
		String message;
		if (cgpa >= 3.75) {
			message = "Excellent!";
		} else if (cgpa >= 3.0 && cgpa < 3.75) {
			message = "Good Job! Keep it up!";
		} else if (cgpa >= 2.0 && cgpa < 3.0) {
			message = "Study harder!";
		} else if (cgpa >= 0 && cgpa < 2.0) {
			message = "You need to work harder.";
		} else {
			message = "";
		}
		return message;
	}

	private int backgroundresources(double cgpa) {
		int res = R.drawable.thumb_green;
		if (cgpa >= 3.75) {
			res = R.drawable.thumb_green;
		} else if (cgpa >= 3.0 && cgpa < 3.75) {
			res = R.drawable.thumb_yellow;
		} else if (cgpa >= 2.0 && cgpa < 3.0) {
			res = R.drawable.thumb_orange;
		} else if (cgpa >= 0 && cgpa < 2.0) {
			res = R.drawable.thumb_red;
		} else {
			res = R.drawable.hello;
		}

		return res;
	}

	double RoundTo2Decimals(double d) {
		DecimalFormat df2 = new DecimalFormat("#.000");
		return Double.valueOf(df2.format(d));
	}

}
