package administrador;

import android.support.v7.widget.RecyclerView;
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
    private List<Bar> mBares;
    private SparseBooleanArray mBaresSeleccionados;
    private BarHolder.ClickListener mListener;

    public BarSelectionAdapter(List<Bar> bares, BarHolder.ClickListener listener) {
        this.mBares = bares;
        mBaresSeleccionados = new SparseBooleanArray();
        this.mListener = listener;

    }

    public String getBarName(int position) {
        return mBares.get(position).getNombre();
    }

    @Override
    public BarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.bar_list_item_admin, parent, false);
        return new BarHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(BarHolder holder, int position) {
        Bar b = mBares.get(position);
        holder.mNombre.setText(b.getNombre());
        if (!b.getPrincipal().isEmpty()) {
            Picasso.with(holder.mImagen.getContext()).load(b.getPrincipal()).into(holder.mImagen);
        } else {
            holder.mImagen.setImageDrawable(null);
        }
        holder.selectOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    public void removeBar(int position) {
        mBares.remove(position);
        notifyItemRemoved(position);
    }

    public void removeListBares(List<Integer> itemPos) {
        Collections.sort(itemPos);
        Collections.reverse(itemPos);
        for (Integer i : itemPos) {
            mBares.remove(i.intValue());
            mBaresSeleccionados.delete(i);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mBares.size();
    }

    public void setBares(List<Bar> bares) {
        this.mBares = bares;
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void toggleSelection(int position) {
        if (mBaresSeleccionados.get(position, false)) {
            mBaresSeleccionados.delete(position);
        } else {
            mBaresSeleccionados.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        mBaresSeleccionados.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return mBaresSeleccionados.size();
    }


    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(mBaresSeleccionados.size());
        for (int i = 0; i < mBaresSeleccionados.size(); ++i) {
            items.add(mBaresSeleccionados.keyAt(i));
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

        public interface ClickListener {
            void onBarClicked(int position);

            boolean onBarLongClicked(int position);
        }

    }

}