package com.example.pmpdomasno2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    ListView lvProdukti;
    ListAdapter adapter;
    String p="";
    public static ArrayList<Produkt> listaProdukti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm;
        FragmentTransaction ft;

            addOsnovenFragment();



    }



    public void addKreirajNovProizvodFragment()
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        KreirajNovProduktFragment vtorfr=new KreirajNovProduktFragment();
        ft.replace(R.id.kreirajNovProduktConatiner,vtorfr,"TAG1");
        ft.commit();
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==0)
        {
            ArrayList<String> novoDodadeni=intent.getStringArrayListExtra("novoDodadeni");

            for(int i=0;i<novoDodadeni.size();i++)
            {
                listaProdukti.add(new Produkt(novoDodadeni.get(i),0, R.drawable.placeholder));
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void addOsnovenFragment()
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        OsnovenFragment prvfr=new OsnovenFragment();
        ft.replace(R.id.osnovenFragmentContainer,prvfr);
        ft.commit();
    }

  /* public void kreirajNovProdukt(View view) throws FileNotFoundException {
        PrintStream ps=new PrintStream(openFileOutput("novoDodadeniProdukti",MODE_APPEND));
        EditText et=(EditText) findViewById(R.id.tbVnesiIme);
        String ime=et.getText().toString();
        ps.println(ime);
        ps.close();
        Produkt p=new Produkt(ime,0,R.drawable.placeholder);
        listaProdukti.add(p);
        et.setText("");
        adapter.notifyDataSetChanged();

    }*/

    /*


    public void otvoriKreirajNovProduktActivity(View view)
  {
      Intent intent =new Intent(this, KreirajNovProizvodActivity.class);

      intent.putParcelableArrayListExtra("listaProduktiIntent",listaProdukti);
      startActivityForResult(intent,0);
  }

   public void updateListProdukti() throws FileNotFoundException {
       File file = getFileStreamPath("novoDodadeniProdukti");
       if (file.exists()) {
           Scanner scan = new Scanner(openFileInput("novoDodadeniProdukti"));
           while (scan.hasNext()) {
               String produkt = scan.nextLine();

                   listaProdukti.add(new Produkt(produkt, 0, R.drawable.placeholder));
               }

           }
       }

       public void dodajProdukt(View view) throws FileNotFoundException {
           PrintStream ps=new PrintStream(openFileOutput("vkupnoProdukti",MODE_APPEND));
           String poraka="Uspeshno dodadeni proizvodi: ";
           for(int i=0;i<listaProdukti.size();i++)
           {
               int countKliknato=listaProdukti.get(i).getCounter();
               for(int j=0;j<countKliknato;j++)
               {
                   ps.println(listaProdukti.get(i).getIme());

               }
               if(countKliknato!=0) {
                   poraka = poraka + "\n" + listaProdukti.get(i).getIme() + " kol: " + countKliknato;
                   listaProdukti.get(i).setCounter(0);
               }
               resetListViewProdukti();
               adapter.notifyDataSetChanged();
           }
           ps.close();

           Toast.makeText(MainActivity.this,poraka,Toast.LENGTH_LONG).show();

       }

       public void prikaziIstorijaProdukti(View view) throws FileNotFoundException {
           Scanner scan=new Scanner(openFileInput("vkupnoProdukti"));
           ArrayList<String> produktiOdDatoteka=new ArrayList<String>();
           String poraka="Istorija na prodadeni produkti: ";
           while(scan.hasNext())
           {
               String produkt=scan.nextLine();
               produktiOdDatoteka.add(produkt);
           }
           for(int i=0;i<listaProdukti.size();i++)
           {
               int k=0;
               for(int j=0;j<produktiOdDatoteka.size();j++)
               {
                   if(listaProdukti.get(i).getIme().equalsIgnoreCase(produktiOdDatoteka.get(j)))
                       k++;
               }
               poraka=poraka+"\n"+listaProdukti.get(i).getIme()+" kol: "+k+" "+p;

           }
           Toast.makeText(MainActivity.this,poraka,Toast.LENGTH_LONG).show();
       }

       public void resetListViewProdukti()
       {
           for(int i=lvProdukti.getFirstVisiblePosition();i<=lvProdukti.getLastVisiblePosition();i++)
           {
               View v=lvProdukti.getChildAt(i-lvProdukti.getFirstVisiblePosition());
               v.setBackgroundColor(getResources().getColor(R.color.white));
               TextView t=(TextView)v.findViewById(R.id.txtCounter);
               t.setTextColor(getResources().getColor(R.color.white));
           }
       }

       public void undoProdukti(View view)
       {

           TextView tv=(TextView)findViewById(R.id.txtCounter);
           //tv.setTextColor(getResources().getColor(R.color.white));
           for(int i=0;i<listaProdukti.size();i++)
           {
               listaProdukti.get(i).setCounter(0);
           }
           resetListViewProdukti();
           adapter.notifyDataSetChanged();

       }*/

   }


