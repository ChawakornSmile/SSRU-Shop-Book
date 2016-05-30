package ssru.saranontawat.chawakorn.ssrushopboook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Pc on 30/5/2559.
 */
public class Myalert {

    public void myDialog(Context context, String strTitle, String strmessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.icon_question);
        builder.setCancelable(false);
        builder.setTitle(strTitle);
        builder.setMessage(strmessage);
        builder.setPositiveButton("NICE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}       //myalert class
