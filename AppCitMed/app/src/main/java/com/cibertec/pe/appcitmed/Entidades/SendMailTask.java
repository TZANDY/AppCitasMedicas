package com.cibertec.pe.appcitmed.Entidades;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
//import android.os.AsyncTask;
import android.util.Log;

import com.cibertec.pe.appcitmed.RegistroEspecialista;

public class SendMailTask {

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;

    public SendMailTask(Activity activity, String[] _datos ) {
        sendMailActivity = activity;

        try {
            GMail androidEmail = new GMail(_datos[0], _datos[1],_datos[2],_datos[3],_datos[4]);
            androidEmail.createEmailMessage();
            androidEmail.sendEmail();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
