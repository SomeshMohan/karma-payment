package karmachallenge.com.karmapay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressBar(String message, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

    }

    public void hideProgress() {
        progressDialog.hide();
    }

    public void showDialog(String title, String message, Context context) {
        if(message == null || message != null && message.isEmpty())
            message = "Something went wrong";
        builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void showToastMessage(String message, Context context) {
        if(message == null || message != null && message.isEmpty())
            message = "Something went wrong";
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
