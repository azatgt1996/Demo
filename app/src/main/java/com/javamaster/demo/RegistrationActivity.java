package com.javamaster.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.javamaster.demo.model.Model;
import com.javamaster.demo.model.address.City;
import com.javamaster.demo.model.address.District;
import com.javamaster.demo.model.address.House;
import com.javamaster.demo.model.address.Street;
import com.javamaster.demo.model.api.AbstractAPIListener;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private View parentLayout;
    private Model model;
    private final ArrayList<District> districtsList = new ArrayList<>();
    private final ArrayList<City> citiesList = new ArrayList<>();
    private final ArrayList<Street> streetsList = new ArrayList<>();
    private final ArrayList<House> housesList = new ArrayList<>();
    private int districtId = 0;
    private int cityId = 0;
    private int streetId = 0;
    private int houseId = 0;
    private Spinner spinnerDistricts;
    private Spinner spinnerCities;
    private Spinner spinnerStreets;
    private Spinner spinnerHouses;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        parentLayout = findViewById(android.R.id.content).getRootView();

        final EditText nameText = findViewById(R.id.textName);
        final EditText loginText = findViewById(R.id.textLogin);
        final EditText emailText = findViewById(R.id.textEmail);

        final EditText passwordText = findViewById(R.id.textPassword1);
        final EditText passwordConfirmText = findViewById(R.id.textPassword2);

        spinnerDistricts = findViewById(R.id.spinner_districts);
        spinnerCities = findViewById(R.id.spinner_cities);
        spinnerStreets = findViewById(R.id.spinner_streets);
        spinnerHouses = findViewById(R.id.spinner_houses);

        districtsList.add(new District(0, " "));
        citiesList.add(new City(0, " "));
        streetsList.add(new Street(0, " "));
        housesList.add(new House(0, " "));

        model = Model.getInstance(RegistrationActivity.this.getApplication());

        spinnerDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                District district = (District) spinnerDistricts.getSelectedItem();
                districtId = district.getId();

                if (districtId == 0) {
                    clearSpinnerCities();
                    clearSpinnerStreets();
                    clearSpinnerHouses();
                } else {
                    if (model.isOnline(RegistrationActivity.this)) {
                        model.loadCities(districtId, new AbstractAPIListener() {
                            @Override
                            public void onCitiesLoaded(List<City> cities) {
                                clearSpinnerCities();
                                citiesList.addAll(cities);
                                fillSpinnerCities();
                            }

                            @Override
                            public void onFailed(String mes) {
                                Snackbar.make(parentLayout, mes, 2000).show();
                            }
                        });
                    } else {
                        Snackbar.make(parentLayout, "No internet connection!", 2000).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) spinnerCities.getSelectedItem();
                cityId = city.getId();

                if (cityId == 0) {
                    clearSpinnerStreets();
                    clearSpinnerHouses();
                } else {
                    if (model.isOnline(RegistrationActivity.this)) {
                        model.loadStreets(districtId, cityId, new AbstractAPIListener() {
                            @Override
                            public void onStreetsLoaded(List<Street> streets) {
                                clearSpinnerStreets();
                                streetsList.addAll(streets);
                                fillSpinnerStreets();
                            }

                            @Override
                            public void onFailed(String mes) {
                                Snackbar.make(parentLayout, mes, 2000).show();
                            }
                        });
                    } else {
                        Snackbar.make(parentLayout, "No internet connection!", 2000).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerStreets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Street street = (Street) spinnerStreets.getSelectedItem();
                streetId = street.getId();

                if (streetId == 0) {
                    clearSpinnerHouses();
                } else {
                    if (model.isOnline(RegistrationActivity.this)) {
                        model.loadHouses(districtId, cityId, streetId, new AbstractAPIListener() {
                            @Override
                            public void onHousesLoaded(List<House> houses) {
                                clearSpinnerHouses();
                                housesList.addAll(houses);
                                fillSpinnerHouses();
                            }

                            @Override
                            public void onFailed(String mes) {
                                Snackbar.make(parentLayout, mes, 2000).show();
                            }
                        });
                    } else {
                        Snackbar.make(parentLayout, "No internet connection!", 2000).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHouses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                House house = (House) spinnerHouses.getSelectedItem();
                houseId = house.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fillSpinnerDistricts();
        fillSpinnerCities();
        fillSpinnerStreets();
        fillSpinnerHouses();

        Button registrationButton = findViewById(R.id.button_registration);
        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                hideKeyboard();
                String name = nameText.getText().toString().trim();
                String login = loginText.getText().toString().trim();
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                String confirmPassword = passwordConfirmText.getText().toString().trim();

                if (validate(name, login, email, password, confirmPassword)) {

                    if (houseId == 0) {
                        Snackbar.make(parentLayout, "Choose a house!", 2000).show();
                        return;
                    }
                    if (model.isOnline(RegistrationActivity.this)) {

                        model.register(login, name, email, password, houseId, new AbstractAPIListener() {
                            @Override
                            public void onRegistered(String mes) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                builder.setMessage(mes);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                builder.show();
                            }

                            @Override
                            public void onFailed(String mes) {
                                Snackbar.make(parentLayout, mes, 2000).show();
                            }
                        });
                    } else {
                        Snackbar.make(parentLayout, "No internet connection!", 2000).show();
                    }
                }
            }
        });
    }

    private void fillSpinnerDistricts() {
        if (model.isOnline(RegistrationActivity.this)) {
            model.loadDistricts(new AbstractAPIListener() {
                @Override
                public void onDistrictsLoaded(List<District> districts) {
                    districtsList.addAll(districts);
                }

                @Override
                public void onFailed(String mes) {
                    Snackbar.make(parentLayout, mes, 2000).show();
                }
            });
        } else {
            Snackbar.make(parentLayout, "No internet connection!", 2000).show();
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, districtsList);
        spinnerDistricts.setAdapter(adapter);
    }

    private void fillSpinnerCities() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, citiesList);
        spinnerCities.setAdapter(adapter);
    }

    private void fillSpinnerStreets() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, streetsList);
        spinnerStreets.setAdapter(adapter);
    }

    private void fillSpinnerHouses() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, housesList);
        spinnerHouses.setAdapter(adapter);
    }

    private void clearSpinnerCities() {
        citiesList.clear();
        citiesList.add(new City(0, " "));
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, citiesList);
        spinnerCities.setAdapter(null);
        spinnerCities.setAdapter(adapter);
    }

    private void clearSpinnerStreets() {
        streetsList.clear();
        streetsList.add(new Street(0, " "));
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, streetsList);
        spinnerStreets.setAdapter(null);
        spinnerStreets.setAdapter(adapter);
    }

    private void clearSpinnerHouses() {
        housesList.clear();
        housesList.add(new House(0, " "));
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, housesList);
        spinnerHouses.setAdapter(null);
        spinnerHouses.setAdapter(adapter);
    }

    private boolean validate(String name, String login, String email, String password, String confirmPassword) {
        if (login == null || login.length() < 5) {
            Snackbar.make(parentLayout, "Login is small (min 5 symbols)", 2000).show();
            return false;
        }
        if (password == null || !password.matches("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,15})$")) {
            Snackbar.make(parentLayout, "Password is insecure", 2000).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Snackbar.make(parentLayout, "You entered two different passwords", 2000).show();
            return false;
        }
        if (email == null || !email.matches("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")) {
            Snackbar.make(parentLayout, "Email is not valid", 2000).show();
            return false;
        }
        if (name == null || name.length() < 2) {
            Snackbar.make(parentLayout, "Name is small (min 2 symbols)", 2000).show();
            return false;
        }
        return true;
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(parentLayout.getWindowToken(), 0);
    }
}
