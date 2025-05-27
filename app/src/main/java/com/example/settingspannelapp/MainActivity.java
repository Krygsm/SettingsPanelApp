package com.example.settingspannelapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
    ListView settingsListView;
    TextView settingsLabel;
    SeekBar valueSeekBar;
    TextView seekBarValueLabel;

    ArrayList<String> settingNames;
    ArrayList<Integer> settingValues;
    ArrayList<String> settingUnits;
    ArrayList<String> displayItemsForListView;

    int selectedItemPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingsListView = findViewById(R.id.settingsListView);
        settingsLabel = findViewById(R.id.editingLabelTextView);
        valueSeekBar = findViewById(R.id.valueSeekBar);
        seekBarValueLabel = findViewById(R.id.seekBarValueTextView);

        if(savedInstanceState == null)
        {
            valueSeekBar.setEnabled(false);
            seekBarValueLabel.setEnabled(false);
            settingsLabel.setEnabled(false);
        }

        settingNames  = new ArrayList<>(Arrays.asList("Jasność", "Saturacja", "Kontrast"));
        settingValues = new ArrayList<>(Arrays.asList(50, 50, 50));
        settingUnits = new ArrayList<>(Arrays.asList("%", "px", "(&_&)"));
         
        displayItemsForListView = new ArrayList<>();
        displayItemsForListView.add(settingNames.get(0) + ": " + settingValues.get(0) + settingUnits.get(0));
        displayItemsForListView.add(settingNames.get(1) + ": " + settingValues.get(1) + settingUnits.get(1));
        displayItemsForListView.add(settingNames.get(2) + ": " + settingValues.get(2) + settingUnits.get(2));


        ArrayAdapter<String> settingsAdapter = new ArrayAdapter<>(this, R.layout.list_item_setting, R.id.itemTextView, displayItemsForListView);

        settingsListView.setAdapter(settingsAdapter);

        settingsListView.setOnItemClickListener((parent, view, position, id) ->
        {
            selectedItemPos = position;

            String selectedSetting = settingNames.get(selectedItemPos);
            settingsLabel.setText("Edytujesz: " + selectedSetting);

            int settingValue = settingValues.get(selectedItemPos);



            valueSeekBar.setProgress(settingValue);
            seekBarValueLabel.setText("Wartość: " + settingValues.get(selectedItemPos));
            if(!valueSeekBar.isEnabled())
            {
                valueSeekBar.setEnabled(true);
                settingsLabel.setEnabled(true);
                seekBarValueLabel.setEnabled(true);
            }
        });

        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(fromUser == false && selectedItemPos == -1) return;

                //settingValues.set(selectedItemPos, progress);

                seekBarValueLabel.setText("Wartość: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                String msg = "Edytujesz teraz " + settingNames.get(selectedItemPos);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                if(selectedItemPos == -1) return;

                settingValues.set(selectedItemPos, seekBar.getProgress());

                String displayMsg = settingNames.get(selectedItemPos) + ": " +
                        settingValues.get(selectedItemPos) + settingUnits.get(selectedItemPos);

                displayItemsForListView.set(selectedItemPos, displayMsg);

                settingsListView.setAdapter(settingsAdapter);
            }
        });
    }
}