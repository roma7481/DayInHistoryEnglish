package alisaroma.dayinhistory2;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> listOfEvents = new ArrayList<String>();
    ArrayList<String> listOfBirths = new ArrayList<String>();
    ArrayList<String> listOfDeaths = new ArrayList<String>();
    ArrayList<String> listOfHolidays = new ArrayList<String>();

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listOfEvents= getIntent().getStringArrayListExtra("events");
        listOfBirths= getIntent().getStringArrayListExtra("births");
        listOfDeaths= getIntent().getStringArrayListExtra("deaths");
        listOfHolidays= getIntent().getStringArrayListExtra("holidays");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("events",listOfEvents);
        bundle.putStringArrayList("births",listOfBirths);
        bundle.putStringArrayList("deaths",listOfDeaths);
        bundle.putStringArrayList("holidays",listOfHolidays);

/**
 * Here , we are inflating the TabFragment as the first Fragment
 */
        TabFragment historyTabs = new TabFragment();
        historyTabs.setArguments(bundle);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,historyTabs).commit();


       // buildTableComp();
        //DayEvents task = new DayEvents();
        //task.execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void buildTableComp() {

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(listOfEvents);
        rv.setAdapter(adapter);

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
            Document doc2 = Jsoup.connect("http://en.wikipedia.org/wiki/November_1").get();
            Element body = doc2.body();
            //Element paragraph= doc2.select("p").first();
            Element paragraph= doc2.select("p").first();
            String info = Jsoup.parseBodyFragment(paragraph.outerHtml()).text();
            listOfEvents.add(info);
            Log.e("ikik",info);
            //body.get
            Elements lists = body.getElementsByTag("ul");
            int indexOfEvent = 0;
            for (Element list : lists) {

                Elements li = list.getElementsByTag("li");
                switch(indexOfEvent) {
                    case 1: listOfEvents.add("Events");
                        for (Element singleli : li) {
                            listOfEvents.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                    case 2: listOfBirths.add("Births");
                        for (Element singleli : li) {
                            listOfBirths.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                    case 3: listOfDeaths.add("Deaths");
                        for (Element singleli : li) {
                            listOfDeaths.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                    case 4: listOfHolidays.add("Holidays and observances");
                        for (Element singleli : li) {
                            listOfHolidays.add(Jsoup.parseBodyFragment(singleli.outerHtml()).text());
                        }
                        break;
                }
                indexOfEvent++;
            }


        }


        private void buildTableComp(){
            LinearLayout infoLayout = (LinearLayout)findViewById(R.id.events_layout);
            for(int i=0;i<listOfEvents.size();i++ ) {
                View infoView = getLayoutInflater().inflate(R.layout.layout_template, infoLayout, false);
                TextView event = (TextView) infoView.findViewById(R.id.event);
                event.setText(listOfEvents.get(i));
                event.setTextColor(Color.GRAY);
                infoLayout.addView(infoView);

            }
            for(int i=0;i<listOfBirths.size();i++ ) {
                View infoView = getLayoutInflater().inflate(R.layout.layout_template, infoLayout, false);
                TextView event = (TextView) infoView.findViewById(R.id.event);
                event.setText(listOfBirths.get(i));
                event.setTextColor(Color.GRAY);
                infoLayout.addView(infoView);

            }
            for(int i=0;i<listOfDeaths.size();i++ ) {
                View infoView = getLayoutInflater().inflate(R.layout.layout_template, infoLayout, false);
                TextView event = (TextView) infoView.findViewById(R.id.event);
                event.setText(listOfDeaths.get(i));
                event.setTextColor(Color.GRAY);
                infoLayout.addView(infoView);

            }
            for(int i=0;i<listOfHolidays.size();i++ ) {
                View infoView = getLayoutInflater().inflate(R.layout.layout_template, infoLayout, false);
                TextView event = (TextView) infoView.findViewById(R.id.event);
                event.setText(listOfHolidays.get(i));
                event.setTextColor(Color.GRAY);
                infoLayout.addView(infoView);

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
            buildTableComp();

        }
    }
}


