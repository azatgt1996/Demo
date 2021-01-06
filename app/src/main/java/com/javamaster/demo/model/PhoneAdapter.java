package com.javamaster.demo.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.javamaster.demo.R;

import java.util.List;

public class PhoneAdapter extends ArrayAdapter<Phone> {

    private LayoutInflater inflater;
    private int layout;
    private List<Phone> phones;

    public PhoneAdapter(@NonNull Context context, int resource, @NonNull List<Phone> phones) {
        super(context, resource, phones);
        this.phones = phones;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView idView = (TextView) view.findViewById(R.id.textView_id);
        TextView phoneNumView = (TextView) view.findViewById(R.id.textView_phoneNumber);

        Phone phone = phones.get(position);

        idView.setText(String.valueOf(phone.getId()));
        phoneNumView.setText(phone.getPhoneNumber());

        return view;
    }
}
