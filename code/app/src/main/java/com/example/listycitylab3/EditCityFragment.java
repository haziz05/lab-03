package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    interface EditCityDialogListener {
        void editCity(City city);
    }

    private EditCityDialogListener listener;

    static EditCityFragment newInstance(City city){
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof EditCityDialogListener){
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityText = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceText = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        City city = (City) args.getSerializable("city");

        editCityText.setText(city.getName());
        editProvinceText.setText(city.getProvince());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit " + city.getName())
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", (dialog, which) -> {
                   city.setName(editCityText.getText().toString());
                   city.setProvince(editCityText.getText().toString());
                   listener.editCity(city);
                })
                .create();
    }
}
