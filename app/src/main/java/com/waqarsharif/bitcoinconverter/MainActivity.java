package com.waqarsharif.bitcoinconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    Spinner btcSpinner;
    Spinner curSpinner;
    Button convertBtn;
    TextView curOutput;
    EditText curInput;
    RequestQueue queue;
    final String URL = "https://blockchain.info/tobtc?currency=%s&value=%d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curSpinner = (Spinner) findViewById(R.id.curSpinner);
        curInput = (EditText) findViewById(R.id.curInput);
        curOutput = (TextView) findViewById(R.id.curOutput);
        convertBtn = (Button) findViewById(R.id.btn_convert);
        convertBtn.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
        loadSpinners();
    }

    public void loadSpinners(){
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currencies_list, android.R.layout.simple_spinner_dropdown_item);
        curSpinner.setAdapter(adapter1);
    }

    @Override
    public void onClick(View v) {
        String currency = curSpinner.getSelectedItem().toString();
        int amount = Integer.valueOf(curInput.getText().toString());
        String url = String.format(URL, currency, amount);
        StringRequest request = new StringRequest(Request.Method.GET, url, this, this);
        queue.add(request);
        convertBtn.setEnabled(false);
    }
    @Override
    public void onResponse(String response) {
        curOutput.setText(response);
        convertBtn.setEnabled(true);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        curOutput.setText(error.getLocalizedMessage());
        convertBtn.setEnabled(true);
    }
}
