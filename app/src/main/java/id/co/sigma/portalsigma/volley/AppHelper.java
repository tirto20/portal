package id.co.sigma.portalsigma.volley;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Aries Satriana on 27/09/2016.
 */
public class AppHelper {
    /**
     * Turn drawable resource into byte array.
     *
     * @param context parent context
     * @param id      drawable resource id
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static String formatStringToDate(String date, String format) {
//        TimeZone timeZone1 = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat calFormat = new SimpleDateFormat(format);

        try {
//            calendar.setTimeZone(timeZone1);
            calendar.setTime(calFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calFormat.applyPattern("dd MMMM yyyy");
        return calFormat.format(calendar.getTime()) ;// 09 Jan 2016
    }

    public static String formateCurrentDateToString(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat calFormat = new SimpleDateFormat("yyyy-MM-dd");
        return calFormat.format(calendar.getTime());
    }
}
