package com.example.home.sourcecodeweb;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.home.sourcecodeweb.R.id.spinner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView tv;
    Spinner zspinner;
    Button zbtnGet;
    EditText zetInput;
    public String url = null;
    ProgressBar zpbLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        zspinner = (Spinner) findViewById(spinner);
        zbtnGet = (Button) findViewById(R.id.btnGet);
        zetInput = (EditText) findViewById(R.id.etInput);
        zpbLoad = (ProgressBar) findViewById(R.id.pbLoad);

        zpbLoad.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Spinner, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        zspinner.setAdapter(adapter);

        zbtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = zspinner.getSelectedItem() + zetInput.getText().toString();
                boolean valid = Patterns.WEB_URL.matcher(url).matches();

                if (valid){
                    getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                    zpbLoad.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                } else {
                    Loader loader = getSupportLoaderManager().getLoader(0);
                    if (loader != null) {
                        loader.cancelLoad();
                    }
                    zpbLoad.setVisibility(View.GONE);
                    tv.setVisibility(View.VISIBLE);
                    tv.setText("This Website is not Valid");
                }
            }
        });


        //tv.setText(getWebsite("http://radefffactory.free.bg/")
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoaderWeb(this, url);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        zpbLoad.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        tv.setText(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
