package com.example.basdatmobileuts;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView bioId;
    EditText bioNim, bioNama, bioAlamat;
    RadioGroup bioGJk;
    RadioButton bioBJk;
    Spinner bioAgama;
    ListView bioList;

    String nim, nama, jk, agama, alamat;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bioId = (TextView) findViewById(R.id.bioId);
        bioNim = (EditText) findViewById(R.id.bioNim);
        bioNama = (EditText) findViewById(R.id.bioNama);
        bioAlamat = (EditText) findViewById(R.id.bioAlamat);
        bioAgama = (Spinner) findViewById(R.id.bioAgama);
        bioList = (ListView) findViewById(R.id.bioList);
        bioGJk = (RadioGroup) findViewById(R.id.bioJk);
        bioGJk.check(R.id.bioJkPria);
        showList();
    }

    public void showList() {
        Handler handler = new Handler(this, null, null, 1);
        final ArrayList<HashMap<String, String>> productList = handler.allProducts();
        String[] fromArray = {"nim", "nama"};
        int[] to = {R.id.bio_nim, R.id.bio_nama};
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, productList, R.layout.biodata_list, fromArray, to);
        bioList.setAdapter(adapter);
        bioList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                HashMap<String, String> list = (HashMap<String, String>) bioList.getItemAtPosition(position);
                bioId.setText(String.valueOf(list.get("id")));
                bioNim.setText(String.valueOf(list.get("nim")));
                bioNama.setText(String.valueOf(list.get("nama")));
                bioAlamat.setText(String.valueOf(list.get("alamat")));
                //set jk radio
                String jkValue = String.valueOf(list.get("jk"));
                if (jkValue.equals("Pria")) {
                    bioGJk.check(R.id.bioJkPria);
                } else {
                    bioGJk.check(R.id.bioJkWanita);
                }
                //set agama Spinner
                String agamaValue = String.valueOf(list.get("agama"));
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                        R.array.agama_list, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bioAgama.setAdapter(adapter);
                if (agamaValue != null) {
                    int spinnerPosition = adapter.getPosition(agamaValue);
                    bioAgama.setSelection(spinnerPosition);
                }
            }
        });
    }

    //reset form + refresh list
    public void clear() {
        bioNim.setText("");
        bioNama.setText("");
        bioGJk.check(R.id.bioJkPria);
        bioAgama.setSelection(0);
        bioAlamat.setText("");
    }

    public void getData() {
        //read radio
        int selectedJk = bioGJk.getCheckedRadioButtonId();
        bioBJk = (RadioButton) findViewById(selectedJk);
        //get form for set db
        nim = bioNim.getText().toString();
        nama = bioNama.getText().toString();
        jk = bioBJk.getText().toString();
        agama = bioAgama.getSelectedItem().toString();
        alamat = bioAlamat.getText().toString();
    }

    public void addBio(View view) {
        Handler handler = new Handler(this, null, null, 1);
        //set value for db
        getData();
        //push data to db via handler
        Biodata biodata = new Biodata(nim, nama, jk, agama, alamat);
        handler.addBio(biodata);
        //reset form + refresh list
        clear();
        bioId.setText("Record Added!");
        showList();
    }

    public void editBio(View view) {
        Handler handler = new Handler(this, null, null, 1);
        int id = Integer.parseInt(bioId.getText().toString());
        getData();
        Biodata biodata = new Biodata(id, nim, nama, jk, agama, alamat);
        boolean result = handler.editBio(biodata);
        if (result) {
            clear();
            bioId.setText("Record Updated!");
            showList();
        } else
            bioId.setText("No Match Found!");
    }

    public void delBio(View view) {
        Handler handler = new Handler(this, null, null, 1);
        String nim = bioNim.getText().toString();
        boolean result = handler.deleteBio(nim);
        if (result) {
            clear();
            bioId.setText("Record deleted!");
            showList();
        } else
            bioId.setText("No Match Found!");
    }
}
