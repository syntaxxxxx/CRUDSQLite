package com.example.idn.biodatasqlite.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.idn.biodatasqlite.CustomOnItemClickListener;
import com.example.idn.biodatasqlite.R;
import com.example.idn.biodatasqlite.entity.Biodata;
import com.example.idn.biodatasqlite.ui.BiodataActivity;

import java.util.LinkedList;

public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.ViewHolder> {

    // data yang akan ditampilkan ke recyclerview
    // berasal dari objek seperti code dibawah ini
    // yaitu listBiodata
    private LinkedList<Biodata> listBiodata;
    private Activity activity;

    // constructorm
    public BiodataAdapter(Activity activity) {
        this.activity = activity;
    }


    public LinkedList<Biodata> getListBiodata() {
        return listBiodata;
    }


    public void setListBiodata(LinkedList<Biodata> listBiodata) {
        this.listBiodata = listBiodata;
    }


    @Override
    public BiodataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // diperlukan untuk menambahkan 1 buah layout didalam activity
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_biodata, parent, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(BiodataAdapter.ViewHolder holder, int position) {

        // set dan get sesuai position pada item_biodata.xml
        holder.tvNama.setText(getListBiodata().get(position).getNama());
        holder.tvUmur.setText(getListBiodata().get(position).getUmur());
        holder.tvGender.setText(getListBiodata().get(position).getGender());
        holder.tvWeight.setText(getListBiodata().get(position).getWeight());
        holder.tvDate.setText(getListBiodata().get(position).getTanggal());

        // lalu disini event click pada cardview
        holder.cvBiodata.setOnClickListener(new CustomOnItemClickListener(position,
                new CustomOnItemClickListener.OnItemClickCallBack() {
                    @Override
                    public void OnItemClicked(View view, int position) {

                        // yang akan putExtra ke BiodataActivity sesuai position nya
                        Intent intent = new Intent(activity, BiodataActivity.class);
                        intent.putExtra(BiodataActivity.EXTRA_POSITION, position);
                        intent.putExtra(BiodataActivity.EXTRA_NOTE, getListBiodata().get(position));
                        activity.startActivityForResult(intent, BiodataActivity.REQUEST_UPDATE);

                    }
                }));
    }


    // menampilkan data nya sesuai dengan jumlah data yang ada
    @Override
    public int getItemCount() {
        return getListBiodata().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // deklarasikan widget yang ada di item_biodata.xml
        TextView tvDate, tvNama, tvUmur, tvGender, tvWeight;
        CardView cvBiodata;

        public ViewHolder(View v) {
            super(v);

            // hubungkan dengan id yang ada pada item_biodata.xml
            tvDate = v.findViewById(R.id.tv_item_date);
            tvNama = v.findViewById(R.id.tv_item_nama);
            tvUmur = v.findViewById(R.id.tv_item_umur);
            tvGender = v.findViewById(R.id.tv_item_gender);
            tvWeight = v.findViewById(R.id.tv_item_weight);
            cvBiodata = v.findViewById(R.id.cv_biodata);

        }
    }
}
