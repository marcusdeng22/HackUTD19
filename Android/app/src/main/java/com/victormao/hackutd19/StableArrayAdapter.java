package com.victormao.hackutd19;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StableArrayAdapter extends ArrayAdapter {
    List<String> name, availability, location, details;

    // Constructor
    public StableArrayAdapter(Context context, List<String> name, List<String> availability, List<String> location, List<String> details)
    {
        super(context, R.layout.row_layout, name);

        this.name = name;
        this.availability = availability;
        this.location = location;
        this.details = details;
    }

    /****************************************************************************
     * This overridden function is called for each line in the list.  Split the
     * data string on tabs and put each component into the TextView in the View
     * we return, which is then displayed.
     *
     * Since it does not seem possible to assign each of the components of the
     * ListView line a percentage of the screen width, that is done in the code
     * below.  These look reasonably good on both my Asus tablet and my Galaxy
     * S5 phone.
     * @param position
     * @param view
     * @param parent
     * @return
     ****************************************************************************/
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        int width = parent.getWidth();

        // Inflate row layout for 7 columns
        Context cx = this.getContext();
        LayoutInflater inflater = (LayoutInflater) cx.getSystemService(cx.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView tvName = rowView.findViewById(R.id.name);
        tvName.setText("Name: " + name.get(position));

        TextView tvAvailability = rowView.findViewById(R.id.availability);
        String av = availability.get(position);
        tvAvailability.setText("Availability: " + av);
        if (av.equals("Available")) {
            tvAvailability.setTextColor(Color.GREEN);
        }
        else {
            tvAvailability.setTextColor(Color.RED);
        }

        TextView tvLocation = rowView.findViewById(R.id.location);
        tvLocation.setText("Location: " + location.get(position));

        TextView tvDetails = rowView.findViewById(R.id.details);
        tvDetails.setText("Details: " + details.get(position));

        return rowView;
    }

}
