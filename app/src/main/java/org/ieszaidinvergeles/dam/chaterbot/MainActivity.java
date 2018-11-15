package org.ieszaidinvergeles.dam.chaterbot;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBot;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotFactory;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotSession;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

//https://github.com/pierredavidbelanger/chatter-bot-api

public class MainActivity extends AppCompatActivity {

    private static Button btSend;
    private static EditText etTexto;
    private static ScrollView svScroll;
    private static TextView tvTexto;
    private showMessage hilo1;
    private setEvent hilo2;
    private ChatterBot bot;
    private ChatterBotFactory factory;
    private ChatterBotSession botSession;
    Gestor gestor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btSend = findViewById(R.id.btSend);
        etTexto = findViewById(R.id.etTexto);
        svScroll = findViewById(R.id.svScroll);
        tvTexto = findViewById(R.id.tvTexto);
        gestor = new Gestor(this);

        if(startBot()) {
            setEvents();
        }
    }

    private void chat(final String text) {
        String response;
        String bot=getString(R.string.bot);

        try {
            String think=botSession.think(text);
            response = bot+ " " + think;
            putInformation(bot,think);
        } catch (final Exception e) {
            response = getString(R.string.exception) + " " + e.toString();
        }

        hilo1=new showMessage(response);
        hilo1.execute();
    }

    private void setEvents() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = getString(R.string.you) + " " + etTexto.getText().toString().trim();
                btSend.setEnabled(false);
                String ett=etTexto.getText().toString();
                etTexto.setText("");
                tvTexto.append(text + "\n");
                hilo2=new setEvent(text);
                hilo2.execute();

                putInformation("you",ett);
            }
        });
    }

    private boolean startBot() {
        boolean result = true;
        String initialMessage;
        factory = new ChatterBotFactory();
        try {
            bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            botSession = bot.createSession();
            initialMessage = getString(R.string.messageConnected) + "\n";
        } catch(Exception e) {
            initialMessage = getString(R.string.messageException) + "\n" + getString(R.string.exception) + " " + e.toString();
            result = false;
        }
        tvTexto.setText(initialMessage);
        return result;
    }



    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    public class showMessage extends AsyncTask<String, Integer, Boolean> {
private String message;

        public showMessage(String message){
            this.message=message;

        }

        @Override
        protected Boolean doInBackground(String... params) {


            return true;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onPostExecute(Boolean result) {
            etTexto.requestFocus();
            tvTexto.append(message + "\n");
            svScroll.fullScroll(View.FOCUS_DOWN);
            btSend.setEnabled(true);
            hideKeyboard();

        }
        @Override
        protected void onCancelled() {
        }
    }
    public class setEvent extends AsyncTask<String, Integer, Boolean> {
        private String text;

        public setEvent(String text){
            this.text=text;

        }

        @Override
        protected Boolean doInBackground(String... params) {

            chat(text);
            return true;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onPostExecute(Boolean result) {

        }
        @Override
        protected void onCancelled() {
        }
    }

    private void putInformation(String nombre, String message) {
        Contacto c = new Contacto(nombre, message);
        long r = gestor.insert(c);
        Log.v("ZZT",""+r);
    }
}