package com.wind.yuanbin.daily;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.wind.yuanbin.daily.mvp.V.HomeFragment;
import com.wind.yuanbin.daily.utils.L;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {

    HomeFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setTitle();

        fragment = HomeFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_main,fragment,"list").commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //toAutherActivity
                Intent intent = new Intent(this,AutherActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        L.o(uri);
    }
}
