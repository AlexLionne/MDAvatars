package com.alexlionne.apps.avatars;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import com.alexlionne.apps.avatars.fragments.EditionFragment;
import com.alexlionne.apps.avatars.objects.Kit;
import com.alexlionne.apps.avatars.objects.kits.GoogleKitOne;
import java.util.ArrayList;


public class AvatarActivity extends AppCompatActivity {
    private static int accentPreselect;
    private int currentFragment ;
    private WebView webview;
    public static Kit kit;
    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;
    private EditionFragment fragment[];
    private ArrayList<ArrayList<String>> list;
    private EditionFragment pre;
    private EditionFragment post;
    private EditionFragment current;
    public static FragmentManager fragmentManager ;
    private long backPressedTime = 0;



    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.md_grey_900), PorterDuff.Mode.SRC_IN);

        webview = (WebView) findViewById(R.id.webView1);
        webview.getSettings().setJavaScriptEnabled(true);


        if (getIntent().getStringExtra("kit").equals("Google-Kit I")){
            kit = new GoogleKitOne(this);
            attachKit(kit);
        }

        webview.loadUrl(kit.getSvg());
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }


        });

        fragmentManager = getFragmentManager();
        kit.attachWebView(webview);
        list = kit.getAllcategories();
        fragment  = new EditionFragment[list.size()];

        for(int i = 0;i<list.size();i++) {
           addFragment(i);

        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment[0]);
        fragmentTransaction.commit();
        currentFragment=0;
        setCurrentFragment(fragment[currentFragment]);
        setNextFragment(fragment[getCurrentFragment().getPosition() + 1]);


        progressBar.setProgress(getCurrentFragment().getProgress());
        final Button button = (Button)findViewById(R.id.change);
        final Button back= (Button)findViewById(R.id.change_back);
        assert button != null;
        assert back != null;
        button.setText(getNextFragment().getTitle());
        back.setVisibility(View.INVISIBLE);
        button.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        back.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.VISIBLE);
                setNextFragment(fragment[getCurrentFragment().getPosition() + 1]);
                switchFragment(getNextFragment());

                for (int i = 0; i < fragment.length; i++) {
                    if (getCurrentFragment().getPosition() == 6) {
                        button.setText("Save");
                        back.setText(fragment[getCurrentFragment().getPosition() - 1].getTitle());
                    } else {
                        back.setText(fragment[getCurrentFragment().getPosition() - 1].getTitle());
                        button.setText(fragment[getCurrentFragment().getPosition() + 1].getTitle());
                    }


                }

                if (getCurrentFragment().getPosition() != 0) {
                    setPreviousFragment(fragment[getCurrentFragment().getPosition() - 1]);
                }
                progressBar.setProgress(getNextFragment().getProgress());


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextFragment(fragment[getCurrentFragment().getPosition() - 1]);
                switchBackFragment(getNextFragment());

                for(int i=0;i<fragment.length;i++) {
                    if(getCurrentFragment().getPosition() == 0) {
                        button.setText(fragment[getCurrentFragment().getPosition() + 1].getTitle());
                        back.setVisibility(View.INVISIBLE);
                    } else {
                        back.setText(fragment[getCurrentFragment().getPosition() - 1].getTitle());
                        button.setText(fragment[getCurrentFragment().getPosition() + 1].getTitle());
                    }


                }

                if(getCurrentFragment().getPosition() != 0) {
                    setPreviousFragment(fragment[getCurrentFragment().getPosition() + 1]);
                }
                progressBar.setProgress(getNextFragment().getProgress());


            }
        });
    }


    public void addFragment(int i){
        int UNIT_SIZE = (i+1)*(100 / fragment.length) ;
        String title = list.get(i).get(0);
        fragment[i] = new EditionFragment();
        fragment[i].setPosition(i);
        fragment[i].setListener(kit.getListeners());
        fragment[i].setTitle(title);
        fragment[i].setList(list.get(i));
        fragment[i].setProgress(UNIT_SIZE);
        list.get(i).remove(0);

    }
    public void switchFragment(EditionFragment to){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right, 0, 0);
        transaction.replace(R.id.container, to);
        transaction.commit();
        setCurrentFragment(to);


    }
    public void switchBackFragment(EditionFragment to){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_out_left,R.anim.slide_in_right,
                 0, 0);
        transaction.replace(R.id.container, to);
        transaction.commit();
        setCurrentFragment(to);


    }

    public void attachKit(Kit kit){
        AvatarActivity.kit = kit;
    }
    public static Kit getKit(){
        return AvatarActivity.kit;
    }

    public void setNextFragment(EditionFragment post){
        this.post = post;
    }
    public EditionFragment getNextFragment(){
        return this.post;
    }
    public void setCurrentFragment(EditionFragment current){
        this.current = current;
    }
    public EditionFragment getCurrentFragment(){
        return  this.current;
    }
    public void setPreviousFragment(EditionFragment pre){
        if(pre.getPosition()!=0) {
            this.pre = pre;
        }

    }
    public EditionFragment getPreviousFragment(){
        return this.pre;

    }
    @Override
    public void onBackPressed(){


    }




}