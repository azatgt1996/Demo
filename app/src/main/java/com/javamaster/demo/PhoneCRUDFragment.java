package com.javamaster.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.javamaster.demo.ui.phones.PhonesViewModel;


public class PhoneCRUDFragment extends Fragment implements FabButtonClick {

    private EditText editText;
    private Button deletePhone;
    private Button changePhone;
    private PhonesViewModel phonesViewModel;
    private String phone;
    private String changedPhone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {

        View view = inflater.inflate(R.layout.fragment_phone_crud, container, false);
        ((MainActivity)getActivity()).setListener(this);
        phonesViewModel = ViewModelProviders.of(getActivity()).get(PhonesViewModel.class);
        editText = view.findViewById(R.id.phoneNumber);

        deletePhone = view.findViewById(R.id.button_deletePhone);
        changePhone = view.findViewById(R.id.button_changePhone);

        deletePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonesViewModel.deleteItem(phone);
                getActivity().onBackPressed();
            }
        });

        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changePhone.getText().toString().equals("Change")) {
                    changePhone.setText("Save");
                    editText.setEnabled(true);
                } else {
                    changePhone.setText("Change");
                    editText.setEnabled(false);
                    changedPhone = editText.getText().toString();
                    phonesViewModel.changeItem(changedPhone, phone);
                }
            }
        });

        Bundle bundle = getArguments();

        if (bundle.getBoolean("add")) {
            deletePhone.setVisibility(View.INVISIBLE);
            changePhone.setVisibility(View.INVISIBLE);
            editText.setEnabled(true);
        } else {
            phone = bundle.getString("phone");
            editText.setText(phone);
            deletePhone.setVisibility(View.VISIBLE);
            changePhone.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onFabClicked() {
        phone = editText.getText().toString();
        phonesViewModel.addItem(phone);
        getActivity().onBackPressed();
    }
}
