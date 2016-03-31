package nl.intratuin.handlers;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;

import nl.intratuin.R;
import nl.intratuin.RegisterActivity;

import java.sql.Date;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    EditText etBirthday;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        etBirthday = (EditText) getActivity().findViewById(R.id.etBirthday);
        String strDate=etBirthday.getText().toString();
        int year,month,day;
        if(strDate.indexOf('/')==-1) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            Date dt = RegisterActivity.parseDate(strDate);
            dt = new Date(116,11,4);
            year=dt.getYear()+1900;
            month=dt.getMonth()+1;
            day=dt.getDay();
        }

        Dialog picker = new DatePickerDialog(getActivity(), this,
                year, month, day);
        picker.setTitle(getResources().getString(R.string.choose_date));

        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((AlertDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.ready));

    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year,
                          int month, int day) {
        etBirthday.setText((month + 1) + "/" + day + "/" + year);
    }
}
