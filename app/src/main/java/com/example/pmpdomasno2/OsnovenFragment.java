package com.example.pmpdomasno2;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OsnovenFragment extends Fragment {

    ListView lvProdukti;
   public static ListAdapter adapter;
    String p="";
    public static ArrayList<Produkt> listaProdukti;

    public OsnovenFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_osnoven, container, false);

        lvProdukti=((MainActivity)getActivity()).lvProdukti;
        adapter=((MainActivity)getActivity()).adapter;
        listaProdukti=((MainActivity)getActivity()).listaProdukti;
        lvProdukti = (ListView) v.findViewById(R.id.lvProdukti);

        Produkt.setProdukti();
        listaProdukti=Produkt.getProdukti();
        try {
            updateListProdukti();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        adapter = new ListAdapter((MainActivity)getActivity(), listaProdukti);
        lvProdukti.setAdapter(adapter);

        lvProdukti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                int i= listaProdukti.get(position).getCounter();
                i++;
                listaProdukti.get(position).setCounter(i);
                TextView tv=(TextView) view.findViewById(R.id.txtCounter);
                tv.setTextColor(getResources().getColor(R.color.slikaPozadinaAccent));
                view.setBackgroundColor(getResources().getColor(R.color.lime));
                adapter.notifyDataSetChanged();



            }
        });

        final ImageButton btnDodajProdukt=(ImageButton) v.findViewById(R.id.btnDodajProdukt);
        btnDodajProdukt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    dodajProdukt();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        final ImageButton btnIstorijaProdukti=(ImageButton)v.findViewById(R.id.btnIsotrijaProdukti);
        btnIstorijaProdukti.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View view)
            {
                try {
                    prikaziIstorijaProdukti();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        final ImageButton btnUndo=(ImageButton) v.findViewById(R.id.btnUndo);
        btnUndo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                undoProdukti();
            }
        });



        final FloatingActionButton fabKreirajNov=(FloatingActionButton) v.findViewById(R.id.fabKreirajNov);
        fabKreirajNov.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(getActivity(), KreirajNovProizvodActivity.class);

                    intent.putParcelableArrayListExtra("listaProduktiIntent", listaProdukti);
                    startActivityForResult(intent, 0);
                }
                else
                {
                    ((MainActivity) getActivity()).addKreirajNovProizvodFragment();
                    FrameLayout fl=getActivity().findViewById(R.id.osnovenFragmentContainer);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) fl.getLayoutParams();
                    layoutParams.weight = 2;
                    fl.setLayoutParams(layoutParams);


                }
            }
        });




        return v;
    }

    public static ArrayList<Produkt> getListProdukt()
    {
        return listaProdukti;
    }


    public void onActivityResult(int requestCode,int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==0)
        {
            //ArrayList <Produkt> l=intent.getParcelableArrayListExtra("proba");
            //listaProdukti.add(l.get(l.size()-1));

            ArrayList<String> novoDodadeni=intent.getStringArrayListExtra("novoDodadeni");

            for(int i=0;i<novoDodadeni.size();i++)
            {
                listaProdukti.add(new Produkt(novoDodadeni.get(i),0, R.drawable.placeholder));
            }
            adapter.notifyDataSetChanged();
        }

    }

    public void updateListProdukti() throws FileNotFoundException {
        File file = getActivity().getFileStreamPath("novoDodadeniProdukti");
        if (file.exists()) {
            Scanner scan = new Scanner(getActivity().openFileInput("novoDodadeniProdukti"));
            while (scan.hasNext()) {
                String produkt = scan.nextLine();

                listaProdukti.add(new Produkt(produkt, 0, R.drawable.placeholder));
            }

        }
    }

    public void dodajProdukt() throws FileNotFoundException {
        PrintStream ps=new PrintStream(getActivity().openFileOutput("vkupnoProdukti",getActivity().MODE_APPEND));
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

        Toast.makeText((MainActivity)getActivity(),poraka,Toast.LENGTH_LONG).show();

    }

    public void prikaziIstorijaProdukti() throws FileNotFoundException {
        Scanner scan=new Scanner(getActivity().openFileInput("vkupnoProdukti"));
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
        Toast.makeText((MainActivity)getActivity(),poraka,Toast.LENGTH_LONG).show();
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

    public void undoProdukti()
    {


        for(int i=0;i<listaProdukti.size();i++)
        {
            listaProdukti.get(i).setCounter(0);
        }
        resetListViewProdukti();
        adapter.notifyDataSetChanged();

    }

}

