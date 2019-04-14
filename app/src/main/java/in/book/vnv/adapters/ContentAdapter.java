package in.book.vnv.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.entity.ContentDataModel;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    private ArrayList<ContentDataModel> list = new ArrayList<>();
    private Context context;
    private ContentAdapter.RecyclerItemListener listener;

    public ContentAdapter(ArrayList<ContentDataModel> list, Context context, RecyclerItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject,parent,false);
        ContentViewHolder holder = new ContentViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.solvedQue.setText(list.get(position).getQuestionFinished());
        holder.totalQue.setText(list.get(position).getQuestionTotal());
        holder.completed.setText(list.get(position).getCompletePercentage());
        holder.title.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerItemListener {
        void onClick(View view, int adapterPosition);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView completed;
        private TextView totalQue;
        private TextView solvedQue;
        private RecyclerItemListener recyclerItemListener;
        public ContentViewHolder(View itemView, RecyclerItemListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.content_title);
            completed = itemView.findViewById(R.id.content_completed);
            totalQue = itemView.findViewById(R.id.content_totalquestion);
            solvedQue = itemView.findViewById(R.id.content_questionfinished);
            recyclerItemListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerItemListener.onClick(view,getAdapterPosition());
        }
    }
}
