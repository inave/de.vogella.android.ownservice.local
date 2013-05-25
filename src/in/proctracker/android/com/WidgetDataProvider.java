package in.proctracker.android.com;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.MatrixCursor;
import java.util.ArrayList;


/**
 * A dummy class that we are going to use internally to store weather data.  Generally, this data
 * will be stored in an external and persistent location (ie. File, Database, SharedPreferences) so
 * that the data can persist if the process is ever killed.  For simplicity, in this sample the
 * data will only be stored in memory.
 */
class WeatherDataPoint {
    String day;
    int degrees;

    WeatherDataPoint(String d, int deg) {
        day = d;
        degrees = deg;
    }
}

public class WidgetDataProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.android.weatherlistwidget.provider");
        public static class Columns {
            public static final String ID = "_id";
            public static final String DAY = "day";
            public static final String TEMPERATURE = "temperature";
        }	
	/**
	 * Generally, this data will be stored in an external and persistent location (ie. File,
	 * Database, SharedPreferences) so that the data can persist if the process is ever killed.
	 * For simplicity, in this sample the data will only be stored in memory.
	 */
    private static final ArrayList<WeatherDataPoint> sData = new ArrayList<WeatherDataPoint>();
        
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
        return "vnd.android.cursor.dir/vnd.weatherlistwidget.temperature";
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
        // We are going to initialize the data provider with some default values
        sData.add(new WeatherDataPoint("Monday", 13));
        sData.add(new WeatherDataPoint("Tuesday", 1));
        sData.add(new WeatherDataPoint("Wednesday", 7));
        sData.add(new WeatherDataPoint("Thursday", 4));
        sData.add(new WeatherDataPoint("Friday", 22));
        sData.add(new WeatherDataPoint("Saturday", -10));
        sData.add(new WeatherDataPoint("Sunday", -13));
        sData.add(new WeatherDataPoint("Monday", 8));
        sData.add(new WeatherDataPoint("Tuesday", 11));
        sData.add(new WeatherDataPoint("Wednesday", -1));
        sData.add(new WeatherDataPoint("Thursday", 27));
        sData.add(new WeatherDataPoint("Friday", 27));
        sData.add(new WeatherDataPoint("Saturday", 27));
        sData.add(new WeatherDataPoint("Sunday", 27));
        return true;
	}

	@Override
	public synchronized Cursor query(Uri uri, String[] projection, String selection, 
			String[] selectionArgs, String sortOrder) {
        assert(uri.getPathSegments().isEmpty());

        // In this sample, we only query without any parameters, so we can just return a cursor to
        // all the weather data.
        final MatrixCursor c = new MatrixCursor(
                new String[]{ Columns.ID, Columns.DAY, Columns.TEMPERATURE });
        for (int i = 0; i < sData.size(); ++i) {
            final WeatherDataPoint data = sData.get(i);
            c.addRow(new Object[]{ new Integer(i), data.day, new Integer(data.degrees) });
        }
        return c;		// TODO Auto-generated method stub
	}

	@Override
	public synchronized int update(Uri uri, ContentValues values, String selection, 
			String[] selectionArgs) {
	
		// TODO Auto-generated method stub
        assert(uri.getPathSegments().size() == 1);

        // In this sample, we only update the content provider individually for each row with new
        // temperature values.
        final int index = Integer.parseInt(uri.getPathSegments().get(0));
        final MatrixCursor c = new MatrixCursor(
                new String[]{ Columns.ID, Columns.DAY, Columns.TEMPERATURE });
        assert(0 <= index && index < sData.size());
        final WeatherDataPoint data = sData.get(index);
        data.degrees = values.getAsInteger(Columns.TEMPERATURE);

        // Notify any listeners that the data backing the content provider has changed, and return
        // the number of rows affected.
        getContext().getContentResolver().notifyChange(uri, null);
        return 1;
	}

}







