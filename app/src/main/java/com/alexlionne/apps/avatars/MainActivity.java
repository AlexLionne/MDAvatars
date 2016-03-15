package com.alexlionne.apps.avatars;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private boolean a;
    private int currentItem = -1;
    public Drawer result = null;
    public String version;
    final String directory ="/MDAvatar/";
    final String subdirectory1 ="/Original/";
    final String subdirectory2 ="/Brick/";
    final String subdirectory3 ="/Galaxy/";
    final String subdirectory4 ="/Googlish/";


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(3);

        //creat the sdcard directory
        File md_folder = new File(Environment.getExternalStorageDirectory()+directory);
        boolean sucess =true;
        if (!md_folder.exists()) {
            sucess = md_folder.mkdir();
        }
        //creat the sdcard directory
        File md_subfolder1 = new File(Environment.getExternalStorageDirectory()+directory+subdirectory1);
        boolean sucess1 =true;
        if (!md_subfolder1.exists()) {
            sucess = md_subfolder1.mkdir();
        }
        //creat the sdcard directory
        File md_subfolder2 = new File(Environment.getExternalStorageDirectory()+directory+subdirectory2);
        boolean sucess2 =true;
        if (!md_subfolder2.exists()) {
            sucess = md_subfolder2.mkdir();
        }
        //creat the sdcard directory
        File md_subfolder3 = new File(Environment.getExternalStorageDirectory()+directory+subdirectory3);
        boolean sucess3 =true;
        if (!md_subfolder3.exists()) {
            sucess = md_subfolder3.mkdir();
        }
        //creat the sdcard directory
        File md_subfolder4 = new File(Environment.getExternalStorageDirectory()+directory+subdirectory4);
        boolean sucess4 =true;
        if (!md_subfolder4.exists()) {
            sucess = md_subfolder4.mkdir();
        }

        final AccountHeader headerResult = new AccountHeaderBuilder().withHeaderBackground(getResources().getDrawable(R.drawable.header)).withActivity(this).addProfiles(new ProfileDrawerItem().withName("MD Avatar").withIcon(GoogleMaterial.Icon.gmd_face).withIdentifier(1)).build();
        // Picasso.with(this).load(background_pic).into(headerResult.getHeaderBackgroundView());

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("Kits").withIdentifier(1).withIcon(CommunityMaterial.Icon.cmd_key);//.withSelectable(false);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("My Avatars").withIdentifier(2).withIcon(CommunityMaterial.Icon.cmd_tag_faces);
        this.result = new DrawerBuilder().withActivity(this).withToolbar(toolbar).withAccountHeader(headerResult).addDrawerItems(item1, item2, new DividerDrawerItem(), new SecondaryDrawerItem().withName("Settings").withIdentifier(4)).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem != null) {
                    MainActivity.this.a = true;
                    switch (drawerItem.getIdentifier()) {
                        case 1:
                            MainActivity.this.switchFragment(1, "Kit", "Kit");
                            break;


                        case 2:

                            break;

                    }
                }
                MainActivity.this.a = false;
                return MainActivity.this.a;
            }
        }).build();
        if (savedInstanceState == null) {
            this.currentItem = -1;
            this.result.setSelection(1);
        }
    }

    public void switchFragment(int itemId, String title, String fragment) {
        if (this.currentItem != itemId) {
            this.currentItem = itemId;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
            getSupportFragmentManager().beginTransaction()/*.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)*/.replace(R.id.main,
                    Fragment.instantiate(this, "com.alexlionne.apps.avatars.fragments." + fragment + "Fragment")).commit();
            if (this.result.isDrawerOpen()) {
                this.result.closeDrawer();
            }
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(this.result.saveInstanceState(outState));
    }

    public void onBackPressed() {
        if (this.result != null && this.result.isDrawerOpen()) {
            this.result.closeDrawer();
        } else if (this.result != null && this.currentItem != 1) {
            this.result.setSelection(2);
        } else if (this.result != null) {
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


}