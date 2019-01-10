package com.workera.apps.starwars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SWAdapter extends ArrayAdapter<SWCharacter> {

    /**
     * Constructs a new {@link SWAdapter}.
     *
     * @param context of the app
     * @param swCharacters is the list of star war characters
     */
    public SWAdapter(Context context, List<SWCharacter> swCharacters) {
        super(context, 0, swCharacters);
    }



    /**
     * Returns a list item view that displays information about a star war character at the given position
     * in the list.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the character at the given position in the list of characters

        SWCharacter currentcharacter = getItem(position);

        TextView nameView = (TextView) listItemView.findViewById(R.id.starwar_name);
        nameView.setText(currentcharacter.getName());
        return listItemView;
    }


}
