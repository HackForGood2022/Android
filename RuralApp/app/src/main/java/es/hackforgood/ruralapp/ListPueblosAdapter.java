package es.hackforgood.ruralapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.hackforgood.ruralapp.async.GetPueblosAsyncTask;

public class ListPueblosAdapter extends BaseAdapter {
    private MapaActivity context; //context
    private List<GetPueblosAsyncTask.Pueblo> items; //data source of the list adapter

    //public constructor
    public ListPueblosAdapter(MapaActivity context, List<GetPueblosAsyncTask.Pueblo> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.row_pueblos_lista, parent, false);
        }

        // get current item to be displayed
        GetPueblosAsyncTask.Pueblo currentItem = (GetPueblosAsyncTask.Pueblo) getItem(position);

        // get the TextView for item name and item description
        TextView puebloNombre = (TextView)
                convertView.findViewById(R.id.puebloNombre);

        //sets the text for item name and item description from the current item object
        puebloNombre.setText(currentItem.name);


        puebloNombre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                context.clickadoPueblo(currentItem.name, currentItem.latitud, currentItem.longitud);
            }
        });

        // returns the view for the current row
        return convertView;
    }
}
