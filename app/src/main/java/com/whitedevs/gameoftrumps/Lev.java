package com.whitedevs.gameoftrumps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Mehrnaz on 1/21/2017.
 */

public class Lev extends Activity {
    private LinearLayout container;
    private String vGatTag;
    InterstitialAd mInterstitialAd;
    private AdView mAdView;
    private int starsin;;
    private SharedPre sp;;

    //az icon play baraye mosalasha estfade shode

    //items of header
    TextView txtStarBg;
    TextView txtIconStar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level);
        container = (LinearLayout) findViewById(R.id.activity_main);
        sp = new SharedPre(getApplicationContext());

        try {
            starsin=Integer.parseInt(sp.Get("stars").toString());
            Log.e("sp.Get(stars)", starsin+"");
        } catch(NumberFormatException nfe) {
            //	System.out.println("Could not parse " + nfe);
            starsin=0;
        }
        TextView tv1 = (TextView)findViewById(R.id.starsNo);
        tv1.setText(Integer.toString(starsin));

        Typeface font2 = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
//items of header
        TextView txtg= (TextView) findViewById(R.id.game);
        txtg.setTypeface(font2);
        TextView txtsq1= (TextView) findViewById(R.id.sqbg);
        TextView txtstar= (TextView) findViewById(R.id.star);
        txtstar.setTypeface(font2);
       TextView plus = (TextView) findViewById(R.id.pl);
        plus.setTypeface(font2);
//        txtIconStar = (TextView) findViewById(R.id.txtstar);
//        txtIconStar.setTypeface(font2);
//        txtStarBg = (TextView) findViewById(R.id.txtstarbg);
//        txtStarBg.setTypeface(font2);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        draw(getResources().getStringArray(R.array.type), 5);

        //HT 748
        int winStars= getIntent().getIntExtra("Stars", 0);
        if (winStars!=0)
        {

            CharSequence text = "You Win , " + winStars + " Stars";
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
            toast.show();


        }

        //full screen adsMob start
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                playingGame(vGatTag);
            }
        });

        requestNewInterstitial();
        //full screen adsMob End

    }

    private void draw(String[] strs, int column) {

        int row = strs.length / column;
        int index = 0;

        for (int i = 0; i < row; i++) {
            LinearLayout ll = new LinearLayout(Lev.this);
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.setWeightSum(6);
            ll.setGravity(Gravity.CENTER);

            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(llParams);
            container.addView(ll);
            for (int j = 0; j < column; j++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 5, 5, 5);
                params.weight = 1;
                Button card = new Button(Lev.this);
                Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
                card.setText(strs[index]);
                card.setTypeface(font);
                card.setTextSize(30);
                card.setPadding(5, 5, 5, 5);
                card.setGravity(Gravity.CENTER);
                card.setLayoutParams(params);
                card.setTag(i +""+ j);
                card.setBackgroundResource(R.drawable.circle2);

                ll.addView(card);

                card.setTextColor(R.color.gray);
                card.setOnClickListener(clickListener);
                index++;



            }
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            vGatTag=v.getTag().toString();
            playingGame(vGatTag);

        }
    };

    private void playingGame(String in) {
        multi mlt = new multi();
        int icon;
        mlt.VibrationStart(1,getApplicationContext(),true);
        int levelselect=0;

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
        switch (in) {
            case "00":
                Log.i("goneToGame()", "case 00");
                levelselect=1;
                icon=R.string.icon_plus;

                break;
            case "01":
                Log.i("goneToGame()", "case 01");
                levelselect=2;
                icon=R.string.icon_plus;
                break;
            case "02":
                Log.i("goneToGame()", "case 02");
                levelselect=3;
                icon=R.string.icon_plus;
                break;

            case "10":
                Log.i("goneToGame()", "case 03");
                levelselect=4;
                icon=R.string.icon_plus;
                break;
            case "11":
                Log.i("goneToGame()", "case 04");
                levelselect=5;
                icon=R.string.icon_plus;
                break;
            case "12":
                Log.i("goneToGame()", "case 05");
                levelselect=6;
                icon=R.string.icon_plus;
                break;
            case "20":
                Log.i("goneToGame()", "case 06");
                levelselect=7;
                icon=R.string.icon_plus;
            case "21":
                levelselect=8;
                icon=R.string.icon_plus;
                break;
            case "22":
                levelselect=9;
                icon=R.string.icon_plus;
                break;

            case "31":
                levelselect=10;
                icon=R.string.icon_plus;
                break;
            case "32":
                levelselect=11;
                icon=R.string.icon_plus;
                break;


        }
            
            if (starsin>2 || levelselect==1 )
            {
                if (levelselect!=1)
                {
                    starsin=starsin-2;
                    sp.Set("stars",Integer.toString(starsin));

                }
                Intent intent = new Intent(Lev.this, Game.class);
                levelselect=levelselect*2;
                intent.putExtra("levelNo", (Integer)levelselect);
                startActivity(intent);
            }
                /*
            if(true)
            {
                Intent intent = new Intent(Lev.this, Game.class);
                levelselect=levelselect*2;
                intent.putExtra("levelNo", (Integer)levelselect);

                startActivity(intent);
            }
 */
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Lev.this, NavigationDrawer.class);
        startActivity(intent);
    }
}
