package administrador;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bar.Bar;
import bar.R;

public class BarSelectionAdapter extends RecyclerView.Adapter<BarSelectionAdapter.BarHolder> {
    private List<Bar> bares;
    private SparseBooleanArray baresSeleccionados;
    private BarHolder.ClickListener listener;

    public BarSelectionAdapter(List<Bar> bares, BarHolder.ClickListener listener) {
        this.bares = bares;
        baresSeleccionados = new SparseBooleanArray();
        this.listener = listener;

    }

    public String getBarName(int position) {
        return bares.get(position).getNombre();
    }

    @Override
    public BarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.bar_list_item_admin, parent, false);
        return new BarHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(BarHolder holder, int position) {
        Bar b = bares.get(position);
        holder.mNombre.setText(b.getNombre());
        Picasso.with(holder.mImagen.getContext()).load(b.getPrincipal()).into(holder.mImagen);
        holder.selectOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    public void removeBar(int position) {
        bares.remove(position);
        notifyItemRemoved(position);
    }

    public void removeListBares(List<Integer> itemPos) {
        Collections.sort(itemPos);
        Collections.reverse(itemPos);
        for (Integer i : itemPos) {
            bares.remove(i.intValue());
            baresSeleccionados.delete(i.intValue());
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return bares.size();
    }

    public void setBares(List<Bar> bares) {
        this.bares = bares;
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int position) {
        if (baresSeleccionados.get(position, false)) {
            baresSeleccionados.delete(position);
        } else {
            baresSeleccionados.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        baresSeleccionados.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return baresSeleccionados.size();
    }


    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(baresSeleccionados.size());
        for (int i = 0; i < baresSeleccionados.size(); ++i) {
            items.add(baresSeleccionados.keyAt(i));
        }
        return items;
    }

    public static class BarHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView mNombre;
        public ImageView mImagen;
        public View view;
        public View selectOverlay;
        public ClickListener listener;

        public BarHolder(View item, ClickListener listener) {
            super(item);
            this.view = item;
            mNombre = (TextView) item.findViewById(R.id.list_nombre_bar);
            mImagen = (ImageView) item.findViewById(R.id.list_imagen_bar);
            selectOverlay = item.findViewById(R.id.selected_overlay);
            this.listener = listener;
            item.setOnClickListener(this);
            item.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onBarClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onBarLongClicked(getAdapterPosition());
            return true;
        }

        /*@Override
        public void onClick(View v) {
            startActivity(BarActivity.newIntent(SearchBarAdmin.this, mNombre.getText().toString()));
        }

        @Override
        public boolean onLongClick(View view) {
            if (mActionMode == null)
                mActionMode = startSupportActionMode(mActionModeCallback);
            view.setBackgroundColor(Color.parseColor("#e91e63"));
            Log.d("hello", "shiet" + getAdapterPosition());
            for (int i = 0; i < marcado.size(); i++) {
                Log.d("nuse", "nas " + marcado.get(i));
            }
            marcado.put(getAdapterPosition(), true);
            //view.setBackgroundColor(Color.parseColor("#e91e63"));
            return true;
        }*/

        public interface ClickListener {
            public void onBarClicked(int position);

            public boolean onBarLongClicked(int position);
        }

    }

}