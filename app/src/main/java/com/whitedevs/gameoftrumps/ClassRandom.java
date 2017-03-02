
package com.whitedevs.gameoftrumps;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * the fisher function create list of unquie array int
 * Created by hassan on 7/16/2015.
 * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
 */
public class ClassRandom {


    boolean check=true;

    public int[] Getrandomlist(int lenghtN)
    {
     //   scratch=lenghtN;
        int[] solutionArray = new int[lenghtN];
        for (int i=0 ; i<lenghtN ; i++)
        {
            solutionArray[i]=5+i;
        }


        return  solutionArray;

    }


    public int GetRandomInt (int min , int max)
    {
        Random r = new Random();
        int re=r.nextInt((max - min) + 1) + min;
        return re;
    }
    public int[] Fisher(int n)
    {
        ArrayList<Integer> listSource = new ArrayList <Integer> ();
        int[] result = new int[n];

        for (int i=0 ; i<n ; i++){
            listSource.add(i);
            result[i]=i;
        }

       for (int i=1 ; i<n; i++){
           result[i]=GetRandomInt(1,listSource.size());
           if (result[i]==listSource.size())
           {
               result[i]=result[i]-1;
           }
           result[i]=listSource.get(result[i]);
           listSource.remove(Integer.valueOf(result[i]));
       }
        for (int i=0 ; i<n ;i++)
        {
            Log.d("number", "i: " + i + " result[i]: " + result[i]);
        }
        return result;
    }

    public int[][] Fisher2(int n)
    {
        //end:61834
        //start61440
        ArrayList<Integer> listSource = new ArrayList <Integer> ();
        int[][] result = new int[n][2];
        int min=61440;
        int Max=61834;
        int max=Max-((n*2)+20);

        int icon=GetRandomInt(min,max);
        //icon=61617;
        if (((n*2)+icon) > 61834)
        {
            icon=icon-(n*2);
        }

        for (int i=0 ; i<n ; i++){
            listSource.add(i);
          //  Log.d("listSource", "Get i: " + listSource.get(i));
        }

        int in=icon;
        for (int i=0 ; i<n; i++){
        //    Log.d("listSource", "Size: " + listSource.size());
                result[i][0] = GetRandomInt(1, listSource.size());
        //    Log.d("result[i][0]", "SETRESULT VALUE: " + result[i][0]);
            if (result[i][0]==listSource.size())
            {
                result[i][0]=result[i][0]-1;
            }
            result[i][0]=listSource.get(result[i][0]);
           // Log.d("result[i][0]", "listSource.get: " + result[i][0]);
            listSource.remove(Integer.valueOf(result[i][0]));
            //don't remove below line ever
         //   Log.d("result[i][0]", "listSource.remove " + listSource.remove(Integer.valueOf(result[i][0])));

                in=icon+(i*2);
                if (61618<in && in<61632)
                { in =61632+(61632-in);}

               switch (in)
               {
                   //fXXf
                   case 61455:
                   case 61471:
                   case 61487:
                   case 61503:
                   case 61519:
                   case 61535:
                   case 61551:
                   case 61567:
                   case 61583:
                   case 61599:
                   case 61615:
                   case 61631:
                   case 61647:
                   case 61663:
                   case 61679:
                   case 61695:
                   case 61711:
                   case 61727:
                   case 61743:
                   case 61759:
                   case 61775:
                   case 61791:
                   case 61807:
                   case 61823:
                     //  Log.d("While before","in:"+in);
                        in=in+3;
                   //    Log.d("While after","in:"+in);
                       break;
               }
                result[i][1]=in;
             //   Log.d("resul","result: "+result[i][1] + "i:" + i);
                i=i+1;
                result[i][1]=in;
               // Log.d("resul", "result: " + result[i][1] + "i:" + i);


           // Log.d("fisher check","i:"+i+"code"+ result[i]);
        }
        for (int i=0 ; i<n ;i++)
        {
           Log.d("number","i: " + i+ " result[i][0]: " + result[i][0] + " result[i][1]: " + result[i][1] );
        }
        return result;
    }

    public int[][] Fisher3(int n)
    {
        ArrayList<Integer> listSource = new ArrayList <Integer> ();
        int[][] result = new int[n][2];
        int min=61440;
        int Max=61834;
        int max=Max-((n*2)+15);


        int icon=GetRandomInt(min,max);
        //icon=61617;
        if (((n*2)+icon) > 61834)
        {
            icon=icon-(n*2);
        }

        for (int i=0 ; i<n ; i++){
            listSource.add(i);
            result[i][0]=i;
        }
        int in=icon;
        int lastIn=in ;
        for (int i=0 ; i<n; i++){
            result[i][0]=GetRandomInt(1,listSource.size());
            if (result[i][0]==listSource.size())
            {
                result[i][0]=result[i][0]-1;
            }
            result[i][0]=listSource.get(result[i][0]);
            listSource.remove(Integer.valueOf(result[i][0]));



            //new start

            in=icon+(i*2);
            if (61618<in && in<61632)
            { in =61632+(61632-in);}

            switch (in)
            {
                //fXXf
                case 61455:
                case 61471:
                case 61487:
                case 61503:
                case 61519:
                case 61535:
                case 61551:
                case 61567:
                case 61583:
                case 61599:
                case 61615:
                case 61631:
                case 61647:
                case 61663:
                case 61679:
                case 61695:
                case 61711:
                case 61727:
                case 61743:
                case 61759:
                case 61775:
                case 61791:
                case 61807:
                case 61823:
                case 61718:
                    //  Log.d("While before","in:"+in);
                    in=in+3;
                    //    Log.d("While after","in:"+in);
                    break;
                case 0:
                    in=61834;
                    break;
            }
            if (i%2==0 || i==0)
            {result[i][1]=in;}
            else
            {result[i][1]=lastIn;}
            lastIn=in;
            //   Log.d("resul","result: "+result[i][1] + "i:" + i);
           // i=i+1;
          //  result[i][1]=in;
            // Log.d("resul", "result: " + result[i][1] + "i:" + i);

            //new end */
        }
        for (int i=0 ; i<n ;i++)
        {
            Log.d("numberF3", "i: " + i + " result[i][0]: " + result[i][0] +" result[i][1]: "  + result[i][1]);
        }
        return result;
    }

}
