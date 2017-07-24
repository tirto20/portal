package id.co.sigma.portalsigma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import id.co.sigma.portalsigma.adapter.HomePagerAdapter;
import id.co.sigma.portalsigma.fragment.CorporateNewsFragment;
import id.co.sigma.portalsigma.fragment.MindFragment;
import id.co.sigma.portalsigma.fragment.PostNowFragment;
import id.co.sigma.portalsigma.fragment.ProfileFragment;
import id.co.sigma.portalsigma.volley.Config;

/**
 * Created by Aries Satriana on 16/09/2016.
 */

/**
 * public class HomeActivity extends AppCompatActivity {
 * private Toolbar toolbar;
 * private ViewPager viewPager;
 * private TabLayout tabLayout;
 * private int[] tabIcons = {
 * R.drawable.ic_pages,R.drawable.ic_location_city,R.drawable.ic_people,R.drawable.ic_party_mode
 * };
 *
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * setContentView(R.layout.activity_home);
 * <p/>
 * //set up toolbar
 * toolbar=(Toolbar)findViewById(R.id.toolbar);
 * setSupportActionBar(toolbar);
 * <p/>
 * getSupportActionBar().setTitle("Portal Telkomsigma");
 * getSupportActionBar().setDisplayHomeAsUpEnabled(true);
 * //getSupportActionBar().setIcon(tabIcons[0]);
 * <p/>
 * <p/>
 * //inisialisasi tab dan pager
 * viewPager=(ViewPager)findViewById(R.id.pager);
 * tabLayout=(TabLayout)findViewById(R.id.tabs);
 * <p/>
 * //set object adapter kedalam ViewPager
 * viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
 * <p/>
 * //Manipulasi sedikit untuk set TextColor pada Tab
 * tabLayout.setTabTextColors(getResources().getColor(R.color.colorPrimary),
 * getResources().getColor(android.R.color.white));
 * tabLayout.setSmoothScrollingEnabled(true);
 * tabLayout.setFillViewport(true);
 * <p/>
 * //set tab ke ViewPager
 * tabLayout.setupWithViewPager(viewPager);
 * <p/>
 * // set icon tab
 * tabLayout.getTabAt(0).setIcon(tabIcons[1]);
 * tabLayout.getTabAt(1).setIcon(tabIcons[2]);
 * tabLayout.getTabAt(2).setIcon(tabIcons[3]);
 * <p/>
 * //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
 * tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
 * }
 * @Override public boolean onCreateOptionsMenu(Menu menu) {
 * // Inflate the menu; this adds items to the action bar if it is present.
 * getMenuInflater().inflate(R.menu.menu_main, menu);
 * return true;
 * }
 * @Override public boolean onOptionsItemSelected(MenuItem item) {
 * // Handle action bar item clicks here. The action bar will
 * // automatically handle clicks on the Home/Up button, so long
 * // as you specify a parent activity in AndroidManifest.xml.
 * int id = item.getItemId();
 * <p/>
 * //noinspection SimplifiableIfStatement
 * if (id == R.id.action_settings) {
 * return true;
 * }
 * <p/>
 * return super.onOptionsItemSelected(item);
 * }
 * }
 */

public class HomeActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {
    private AHBottomNavigation bottomNavigation;
    private String[] titles = new String[]{"News", "Socials", "Post-It", "Profile"};
    private Fragment fragment;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.myBottomNavigation_ID);
        bottomNavigation.setOnTabSelectedListener(this);
        this.createNavItems();

        //Fetching Account Info from shared preferences
        sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF, "Not Available");

    }

    private void createNavItems() {
        //CREATE ITEMS
        AHBottomNavigationItem newsItem = new AHBottomNavigationItem(titles[0], R.drawable.ic_location_city);
        AHBottomNavigationItem whatsItem = new AHBottomNavigationItem(titles[1], R.drawable.ic_people);
        AHBottomNavigationItem captureItem = new AHBottomNavigationItem(titles[2], R.drawable.ic_party_mode);
        AHBottomNavigationItem profileItem = new AHBottomNavigationItem(titles[3], R.drawable.ic_assignment_ind);

        //ADD THEM to bar
        bottomNavigation.addItem(newsItem);
        bottomNavigation.addItem(whatsItem);
        bottomNavigation.addItem(captureItem);
        bottomNavigation.addItem(profileItem);

        //set properties
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        //set current item
        bottomNavigation.setCurrentItem(0);
    }

    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        String title = null;
        switch (position) {
            case 0:
                fragment = new CorporateNewsFragment();
                title = titles[0];
                break;
            case 1:
                fragment = new MindFragment();
                title = titles[1];
                break;
            case 2:
                fragment = new PostNowFragment();
                title = titles[2];
                break;
            case 3:
                fragment = new ProfileFragment();
                title = titles[3];
                break;
            default:
                break;
        }
        // set the toolbar title
        getSupportActionBar().setTitle(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id, fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
