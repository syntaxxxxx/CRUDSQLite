package com.example.idn.biodatasqlite.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.idn.biodatasqlite.R;
import com.example.idn.biodatasqlite.adapter.BiodataAdapter;
import com.example.idn.biodatasqlite.db.BiodataHelper;
import com.example.idn.biodatasqlite.entity.Biodata;

import java.util.ArrayList;
import java.util.LinkedList;

import static com.example.idn.biodatasqlite.ui.BiodataActivity.EXTRA_POSITION;
import static com.example.idn.biodatasqlite.ui.BiodataActivity.REQUEST_UPDATE;
import static com.example.idn.biodatasqlite.ui.BiodataActivity.RESULT_DELETE;
import static com.example.idn.biodatasqlite.ui.BiodataActivity.RESULT_UPDATE;

public class MainActivity extends AppCompatActivity {

    // deklarasi
    private RecyclerView rvBiodata;
    private ProgressBar progressBar;
    private LinkedList<Biodata> list;
    private BiodataAdapter adapter;
    private BiodataHelper biodataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // kenalin atau casting atau inisialisasi
        progressBar = findViewById(R.id.progrres_bar);
        rvBiodata = findViewById(R.id.rv_biodata);
        rvBiodata.setLayoutManager(new LinearLayoutManager(this));
        rvBiodata.setHasFixedSize(true);

        list = new LinkedList<>();
        adapter = new BiodataAdapter(this);
        adapter.setListBiodata(list);

        // hubungkan dengan adapter
        rvBiodata.setAdapter(adapter);

        // mengakses dan membuka database SQLite dengan membuat instance
        biodataHelper = new BiodataHelper(this);
        biodataHelper.open();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BiodataActivity.class);
                startActivityForResult(intent, BiodataActivity.REQUEST_ADD);
            }
        });

        // menggunakan AsyncTask
        new LoadBiodataAsyntask().execute();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    // method ini untuk meload data dari table dan nampilin ke dalam list
    private class LoadBiodataAsyntask extends AsyncTask<Void, Void, ArrayList<Biodata>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            if (list.size() > 0) {
                list.clear();
            }
        }


        // secara asynchornous
        @Override
        protected ArrayList<Biodata> doInBackground(Void... voids) {
            return biodataHelper.query();
        }


        // jika method doIn setelah dijalankan hasil nya akan dikirim kesini
        @Override
        protected void onPostExecute(ArrayList<Biodata> biodata) {
            super.onPostExecute(biodata);

            progressBar.setVisibility(View.GONE);

            list.addAll(biodata);
            adapter.setListBiodata(list);
            adapter.notifyDataSetChanged();

            // dan menampilkan bahwa proses AsyncTask telah selesai
            if (list.size() == 0) {
                showSnackBar("Data Belum Ada");

            }
        }
    }


    // method untuk menampilkan notif pesan
    private void showSnackBar(String pesan) {
        Snackbar.make(rvBiodata, pesan, Snackbar.LENGTH_LONG).show();
    }


    // class ini adalah class untuk menerima data yang dikirim dari
    // BiodataActivity berdasarkan requestCode dan resultCode nya
    // nilai hasil balik
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // nilai hasil balik jika ada penambahan
        if (requestCode == BiodataActivity.REQUEST_ADD) {
            if (resultCode == BiodataActivity.RESULT_ADD) {
                new LoadBiodataAsyntask().execute();
                rvBiodata.getLayoutManager().smoothScrollToPosition(rvBiodata,
                        new RecyclerView.State(), 0);
                showSnackBar("Biodata Berhasil Ditambahkan");
            }

            // nilai hasil balik jika ada perubahan
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == RESULT_UPDATE) {
                new LoadBiodataAsyntask().execute();
                int position = data.getIntExtra(BiodataActivity.EXTRA_POSITION, 0);
                rvBiodata.getLayoutManager().smoothScrollToPosition(rvBiodata,
                        new RecyclerView.State(), position);
                showSnackBar("Biodata Berhasil Diupdate");

                // nilai hasil balik jika ada penghapusan
            } else if (resultCode == RESULT_DELETE) {
                int position = data.getIntExtra(EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setListBiodata(list);
                adapter.notifyDataSetChanged();
                showSnackBar("Biodata Berhasil Dihapus");
            }
        }
    }


    // nutup akses ke database
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (biodataHelper != null) {
            biodataHelper.close();
        }
    }
}














