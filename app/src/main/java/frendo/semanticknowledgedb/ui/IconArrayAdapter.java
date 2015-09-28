package frendo.semanticknowledgedb.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import frendo.semanticknowledgedb.R;

/**
 * Created by Oliver on 26.09.2015.
 */
public class IconArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] icons;
    private final String[] values;


    public IconArrayAdapter(Context context, String[] icons, String[] values) {
         super(context, R.layout.rowlayout, values);
        this.context = context;
        this.icons = icons;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        //Set text view
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        textView.setText(values[position]);

        //Set icon
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        String icon = icons[position];
        if (icon != null) {
            int rID = context.getResources().getIdentifier(icon , "drawable", context.getPackageName());
            imageView.setImageResource(rID);
        }

        return rowView;
    }
}