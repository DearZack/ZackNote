package com.zack.zacknote.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zack.zacknote.R;
import com.zack.zacknote.bean.Note;

import java.util.List;

/**
 * Created by Zack Zhou on 2016/5/15.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ItemViewHolder> {

    private Context context;
    private List<Note> notes;
    private LayoutInflater layoutInflater;

    public MyRecyclerViewAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_note, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.title.setText(notes.get(position).getTitle());
        holder.content.setText(notes.get(position).getContent());
        holder.lastModifyTime.setText(notes.get(position).getLastModifyTime());
        if (OnItemClickListener != null) {
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnItemClickListener.onItemClick(holder.more, holder.getLayoutPosition());
                }
            });
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnItemClickListener.onItemClick(holder.root, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title, content, lastModifyTime;
        private ImageButton more;
        private CardView root;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.note_title);
            content = (TextView) itemView.findViewById(R.id.note_content);
            lastModifyTime = (TextView) itemView.findViewById(R.id.note_last_modify_time);
            more = (ImageButton) itemView.findViewById(R.id.note_more);
            root = (CardView) itemView.findViewById(R.id.notes_item_root);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener OnItemClickListener;

    public void OnItemClickListener(OnItemClickListener OnItemClickListener) {
        this.OnItemClickListener = OnItemClickListener;
    }
}
