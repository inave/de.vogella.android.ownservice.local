package in.proctracker.android.com;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String> {
	  private final Context context;
	  private final List<String> values;
	  
	  public MyAdapter(Context thecontext, int resource, 
			  int textViewResourceId, List<String> objects) {
		  super(thecontext, resource, textViewResourceId, objects);
		  this.context = thecontext;
		  this.values = objects;
	  }
/*	  
	  public MyAdapter(Context context, ArrayList[] values) {
	    super(context, R.layout.customtext, values);
	    this.context = context;
	    this.values = values;
	  }
*/
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.customtext, parent, false);
	    TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(values.get(position));

	    if(MainActivity.statEntries != null)
	    {
	    	PackageManager pm = context.getPackageManager();
	    	String apppackage = MainActivity.statEntries[position].apppackage;
	    	try
	    	{
	    		Drawable dr = pm.getApplicationIcon(apppackage);
	    		imageView.setImageDrawable(dr);
	    	}
	    	catch (PackageManager.NameNotFoundException e)
	    	{
	    		imageView.setImageResource(R.drawable.icon);
	    	}
	    }

	    return rowView;
	  }
}
