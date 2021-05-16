package com.wind.yuanbin.daily;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.wind.yuanbin.daily.utils.ShareUtils;
import com.wind.yuanbin.daily.utils.VersionUtils;

public class AutherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auther);
        setTitle();


        findViewById(R.id.tv_auther).setOnClickListener(__->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(getString(R.string.app_auther_weibo)));
            startActivity(intent);
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void setTitle() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(getString(R.string.app_name) + " " + VersionUtils.getAppVersionName(this)
                + "\n" + VersionUtils.getAppVersionCode(this));

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_item_share:
                ShareUtils.shareThisAPP(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
