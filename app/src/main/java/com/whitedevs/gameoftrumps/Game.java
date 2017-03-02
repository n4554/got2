package com.whitedevs.gameoftrumps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.whitedevs.gameoftrumps.R.id.adView;


public class Game extends Activity {

	Typeface font;
	TextView txtStarBg;
	TextView txtIconStar;
	//H.T added start
	private static int ROW_COUNT = -1;
	private static int COL_COUNT = -1;
	private Context context;
	private AdView mAdView;

	private int [] [] cards;
	private  int winCard;
	private int size;
	private Card firstCard;
	private Card seconedCard;
	private ButtonListener buttonListener;
	public int[][] rtrn;
	private static Object lock = new Object();
	private int turns;
	private TableLayout mainTable;
	private UpdateCardsHandler handler;
	SharedPre sp;
	int levelNo;
	int icon;
	int sizeIcon;
	private int starsin;

	//H>T added End
	
    protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		Typeface font2=Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
		TextView txtg= (TextView) findViewById(R.id.game);
		txtg.setTypeface(font2);
		TextView txtsq1= (TextView) findViewById(R.id.sqbg);
		TextView txtstar= (TextView) findViewById(R.id.star);
		txtstar.setTypeface(font2);
		sizeIcon=60;
		TextView plus = (TextView) findViewById(R.id.pl);
		plus.setTypeface(font);
//		txtIconStar = (TextView) findViewById(R.id.txtstar);
//		txtIconStar.setTypeface(font2);
//		txtStarBg = (TextView) findViewById(R.id.txtstarbg);
//		txtStarBg.setTypeface(font2);
		//HT added
		handler = new UpdateCardsHandler();
		buttonListener = new ButtonListener();
        font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        levelNo = getIntent().getIntExtra("levelNo", 0);
		Log.i("loadCards()","levelNo=" + levelNo);

        mainTable = (TableLayout)findViewById(R.id.TableLayout03);
        context  = mainTable.getContext();
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
        newGame(levelNo);






	}
	// /*
	private void newGame(int levelNo) {


//		String icons ;
		COL_COUNT =  getRowAndCol(levelNo);
		ROW_COUNT =  levelNo/COL_COUNT ;

		int CCount = (ROW_COUNT*COL_COUNT) ;
		final int[] checkPaper = new int[CCount];
		ClassRandom rndm = new ClassRandom();

		switch (levelNo) {
			case 1:
				Log.i("goneToGame()", "case 00");
				icon=R.string.icon_glass;

				break;
			case 2:
				Log.i("goneToGame()", "case 01");
				icon=R.string.icon_globe;
				break;
			case 3:
				Log.i("goneToGame()", "case 02");
				icon=R.string.icon_glass;
				break;
			case 4:
				Log.i("goneToGame()", "case 03");
				icon=R.string.icon_gratipay;
				break;
			case 5:
				Log.i("goneToGame()", "case 04");
				icon=R.string.icon_glass;
				break;
			case 6:
				Log.i("goneToGame()", "case 05");
				icon=R.string.icon_globe;
				break;
			case 7:
				Log.i("goneToGame()", "case 06");
				icon=R.string.icon_gratipay;
			case 8:
				icon=R.string.icon_plus;
				break;
			case 9:
				icon=R.string.icon_plus;
				break;
			case 10:
				icon=R.string.icon_plus;
				break;
			case 11:
				icon=R.string.icon_plus;
				break;
			case 12:
				icon=R.string.icon_plus;
				break;
			case 13:
				icon=R.string.icon_plus;
				break;
			case 14:
				icon=R.string.icon_plus;
				break;
			case 15:
				icon=R.string.icon_plus;
				break;
			case 16:
				icon=R.string.icon_plus;
				break;
			case 17:
				icon=R.string.icon_plus;
				break;
			case 18:
				icon=R.string.icon_plus;
				break;
			case 19:
				icon=R.string.icon_plus;
				break;
			case 20:
				icon=R.string.icon_plus;
				break;
			case 21:
				icon=R.string.icon_plus;
				break;
			case 22:
				icon=R.string.icon_plus;
				break;
			case 23:
				icon=R.string.icon_plus;
				break;
			case 24:
				icon=R.string.icon_plus;
				break;
			case 25:
				icon=R.string.icon_plus;
				break;

		}
		rtrn = rndm.Fisher3(CCount);
		Log.i("newGame()","COL_COUNT=" +rtrn);
		cards = new int [COL_COUNT] [ROW_COUNT];
		mAdView = (AdView) findViewById(adView);

		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);



		TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
		tr.removeAllViews();

		mainTable = new TableLayout(context);
		tr.addView(mainTable);

		for (int y = 0; y < ROW_COUNT; y++) {
			mainTable.addView(createRow(y));
		}

		firstCard=null;
		loadCards();
		//tedad bar sai : turns
		turns=0;



	}
	private int getRowAndCol(int i)
	{
		int a=0;
		int b =0;
		for (int x =2 ; x<8 ; x++)
		{
			if (i>14 && x==2){x=x+1;}
			if (i%x==0)
			{

				a=i/x;
				b=x;
				break;
			}
		}
		Log.i("getRowAndCol()","A=" + a);
		Log.i("getRowAndCol()","B=" + b);
        return b;
    }
    
	private void loadCards(){
		try{
			winCard=0;
			size = ROW_COUNT*COL_COUNT;

			Log.i("loadCards()","size=" + size);

			ArrayList<Integer> list = new ArrayList<Integer>();

			for(int i=0;i<size;i++){
				list.add(new Integer(i));
			}


			Random r = new Random();

			for(int i=size-1;i>=0;i--){
				int t=0;

				if(i>0){
					t = r.nextInt(i);
				}

				t=list.remove(t).intValue();

	    		cards[rtrn[i][0]%COL_COUNT][rtrn[i][0]/COL_COUNT]=rtrn[t][1];

	    		Log.i("loadCards()", "card["+(i%COL_COUNT)+
	    				"]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
			}
/*
			for (int i=0; i<ROW_COUNT ; i++)
			{
				for (int z=0  ; z<COL_COUNT ; z++)
				{
					size=size-1;
					cards[z][i]=rtrn[size][1];
					Log.i("loadCards()", "card["+(z)+
							"]["+(i)+"]=" + cards[z][i]);

				}
			}
			*/

		}
		catch (Exception e) {
			Log.e("loadCards()", e+"");
		}

	}

	private TableRow createRow(int y){
		TableRow row = new TableRow(context);
		row.setHorizontalGravity(Gravity.CENTER);

		for (int x = 0; x < COL_COUNT; x++) {
			row.addView(createButton(x,y));
		}
		return row;
	}

	private View createButton(int x, int y){
		final Button button = new Button(context);
		TextView textView=new TextView(context);
		button.setBackgroundDrawable(null);
		Typeface fontawsome = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
		button.setTypeface(fontawsome);

		button.setTextColor(Color.WHITE);


		//button.setText("\uf04d");

		button.setText(icon);
		button.setBackgroundResource(R.drawable.square);

		//textView.setText("Hi");
		//textView.setTextColor(Color.RED);

		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeIcon);
		button.setId(100*x+y);
		button.setOnClickListener(buttonListener);
		/* H.T E
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				final Animation myAnim = AnimationUtils.loadAnimation(Game.this, R.anim.anim2);
				button.startAnimation(myAnim);

			}
		});
		*/
		return button;
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			synchronized (lock) {
				if(firstCard!=null && seconedCard != null){
					return;
				}
				int id = v.getId();
				int x = id/100;
				int y = id%100;
				Log.i("onClick()", "card["+(x)+
						"]["+(y)+"]=" + cards[x][y]);
				turnCard((Button)v,x,y);
			}

		}

		private void turnCard(Button button,int x, int y) {
			//	button.setBackgroundDrawable(images.get(cards[x][y]));
			Log.i("turndCards()", "card["+(x)+
					"]["+(y)+"]=" + cards[x][y]);
			Typeface fontawsome = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
			button.setTypeface(fontawsome);
			button.setTextColor(Color.parseColor("#f6bb07"));
			String s = Character.toString((char) cards[x][y]);
			button.setText(s);
			button.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeIcon);
			final Animation myAnim = AnimationUtils.loadAnimation(Game.this, R.anim.anim2);
			button.startAnimation(myAnim);


			if(firstCard==null){
				firstCard = new Card(button,x,y);
			}
			else{

				if(firstCard.x == x && firstCard.y == y){
					return; //the user pressed the same card
				}

				seconedCard = new Card(button,x,y);

				turns++;
				//((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);


				TimerTask tt = new TimerTask() {

					@Override
					public void run() {
						try{
							synchronized (lock) {
							  handler.sendEmptyMessage(0);
							}
						}
						catch (Exception e) {
							Log.e("E1", e.getMessage());
						}
					}
				};
				
				  Timer t = new Timer(false);
			        t.schedule(tt, 1300);
			}
		   }
			
		}
    
    class UpdateCardsHandler extends Handler{
    	
    	@Override
    	public void handleMessage(Message msg) {
    		synchronized (lock) {
    			checkCards();
    		}
    	}
    	 public void checkCards(){
    	    	if(cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]){
    				firstCard.button.setVisibility(View.INVISIBLE);
    				seconedCard.button.setVisibility(View.INVISIBLE);
					winCard +=1;
					Log.e("checkCards()", winCard+"");
					int starsin;
					if (winCard==size/2)
					{

						try {
							starsin=Integer.parseInt(sp.Get("stars").toString());
							Log.e("sp.Get(stars)", starsin+"");
							starsin=starsin+getStar();
						} catch(NumberFormatException nfe) {
						//	System.out.println("Could not parse " + nfe);
							starsin=getStar();
						}
						TextView tv1 = (TextView)findViewById(R.id.starsNo);
						tv1.setText(Integer.toString(starsin));
						sp.Set("stars",Integer.toString(starsin));

						Intent intent = new Intent(Game.this, Lev.class);
						intent.putExtra("stars", (Integer)getStar());
						startActivity(intent);


					}

    			}
    			else {
    				//seconedCard.button.setBackgroundDrawable(null);
    				//firstCard.button.setBackgroundDrawable(null);

					seconedCard.button.setTextColor(Color.WHITE);
					seconedCard.button.setText(icon);
					seconedCard.button.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeIcon);


					firstCard.button.setTextColor(Color.WHITE);
					firstCard.button.setText(icon);
					firstCard.button.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeIcon);
    			}
    	    	
    	    	firstCard=null;
    			seconedCard=null;
    	    }

		public int getStar ()
		{
			int sizeDiv2=size/2;

			if (turns<size)
			{return 3;}
			if (turns>=size && turns< (size*((sizeDiv2)-(sizeDiv2-2))))
			{return 2;}
			if (turns>= (size*(sizeDiv2)) &&turns<= (size*((sizeDiv2)-sizeDiv2-3) ))
			{return 1;}
			else
			return 0;
		}
    }


	@Override
	public void onBackPressed() {
		try {
			starsin=Integer.parseInt(sp.Get("stars").toString());
			Log.e("sp.Get(stars)", starsin+"");

		} catch(NumberFormatException nfe) {
			//	System.out.println("Could not parse " + nfe);
			starsin=0;
		}
		TextView tv1 = (TextView)findViewById(R.id.starsNo);
		tv1.setText(Integer.toString(starsin));


		AlertDialog.Builder dialog = new AlertDialog.Builder(Game.this);
		dialog.setCancelable(false);
		dialog.setTitle("Eixt Game");
		dialog.setMessage("Do you want exit game?" );
		dialog.setPositiveButton("Yest", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(Game.this, Lev.class);
				startActivity(intent);
			}
		})
				.setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//Action for "Cancel".
					}
				});

		final AlertDialog alert = dialog.create();
		alert.show();
	}
   
    //*/
}
