package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    Button btnCal, btnReset;
    TextView tvDate, tvBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editText);
        etHeight = findViewById(R.id.editText2);
        btnCal = findViewById(R.id.button3);
        btnReset = findViewById(R.id.button4);
        tvDate = findViewById(R.id.textView);
        tvBMI = findViewById(R.id.textView2);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());

                float BMI = weight/(height*height);

                String resultBMI = String.format("Last Calculated BMI: %.3f", BMI);

                Calendar now = Calendar.getInstance();
                String resultDateTime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                if (BMI < 18.5){
                    resultBMI += ("\nYou are underweight");
                }else if (BMI >= 18.5 && BMI <= 24.9){
                    resultBMI += ("\nYou are normal");
                }else if (BMI >= 25 && BMI <= 29.9){
                    resultBMI += ("\nYou are overweight");
                }else if (BMI > 30){
                    resultBMI += ("\nYou are obese");
                }

                tvDate.setText("Last Calculated Date: " + resultDateTime);
                tvBMI.setText(resultBMI);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                tvDate.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: ");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        String BMI = tvBMI.getText().toString();
        String date = tvDate.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("BMI", BMI);
        prefEdit.putString("Date", date);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String msgDate = prefs.getString("Date", "Last Calculated Date: ");
        String msgBMI = prefs.getString("BMI", "Last Calculated BMI: ");

        tvBMI.setText(msgBMI);
        tvDate.setText(msgDate);
    }
}
