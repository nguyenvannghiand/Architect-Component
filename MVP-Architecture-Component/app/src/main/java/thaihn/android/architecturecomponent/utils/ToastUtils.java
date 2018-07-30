package thaihn.android.architecturecomponent.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by NgocThai on 16/3/2017.
 */

public class ToastUtils {

    public static void quickToast(Context context, String msg) {
        quickToast(context, msg, false);
    }

    public static void quickToast(Context context, String msg, boolean isLong) {
        Toast toast = Toast.makeText(context, msg, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if (v != null) {
            v.setGravity(Gravity.CENTER);
            v.setPadding(10, 0, 10, 0);
        }
        toast.show();
    }
}
