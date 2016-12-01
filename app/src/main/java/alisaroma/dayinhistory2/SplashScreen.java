package alisaroma.dayinhistory2;

/**
 * Created by Roma-Alisa on 29-Nov-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        DayEvents task = new DayEvents();
        task.execute();
        /*Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private class DayEvents extends AsyncTask<Void, Void, Void> {
        ArrayList<String> listOfEvents = new ArrayList<String>();
        ArrayList<String> listOfBirths = new ArrayList<String>();
        ArrayList<String> listOfDeaths = new ArrayList<String>();
        ArrayList<String> listOfHolidays = new ArrayList<String>();
        int index=0;
        protected void onPreExecute() {
            super.onPreExecute();
        }
        private void getWikiData() throws IOException {

            String infobox = "";
            String allInfo = "";
            //Connection.Response res = Jsoup.connect("http://en.wikipedia.org/wiki/November_1").execute();
            //String html = res.body();
            //Document doc2 = Jsoup.parseBodyFragment(html);
            Document doc2 = Jsoup.connect("http://en.m.wikipedia.org/wiki/November_1").get();
            Element body = doc2.body();
            //Element paragraph= doc2.select("p").first();
            Element paragraph= doc2.select("p").first();
            String info = Jsoup.parseBodyFragment(paragraph.outerHtml()).text();
            //listOfEvents.add(info);
            //body.get
            Elements lists = body.getElementsByTag("ul");
            int indexOfEvent = 0;
            for (Element list : lists) {

                Elements li = list.getElementsByTag("li");
                switch(indexOfEvent) {
                    case 1: //listOfEvents.add("Events");
                        for (Element singleli : li) {
                            listOfEvents.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                    case 2: //listOfBirths.add("Births");
                        for (Element singleli : li) {
                            listOfBirths.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                    case 3: //listOfDeaths.add("Deaths");
                        for (Element singleli : li) {
                            listOfDeaths.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                    case 4: //listOfHolidays.add("Holidays and observances");
                        for (Element singleli : li) {
                            listOfHolidays.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                }
                indexOfEvent++;
            }


        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                getWikiData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // buildTableComp();
            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            Log.e("blabla","done");
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putStringArrayListExtra("events", listOfEvents);
            intent.putStringArrayListExtra("births", listOfBirths);
            intent.putStringArrayListExtra("deaths", listOfDeaths);
            intent.putStringArrayListExtra("holidays", listOfHolidays);
            startActivity(intent);

        }
    }
}



