package com.example.vuki.drustvena_mreza_faks.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vuki on 27.12.2015..
 */
public class SendEmailHelper {


    public static void sendEmail(Context context, String receiver, String subject, String body) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{receiver});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
