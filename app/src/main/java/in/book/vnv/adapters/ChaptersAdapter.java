package in.book.vnv.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.activitys.Chapters;
import in.book.vnv.entity.ChaptersDataModel;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ChapterViewHolder> {
    private ArrayList<ChaptersDataModel> list;
    private Context context;
    private ChaptersAdapter.RecyclerItemListener listener;

    public ChaptersAdapter(ArrayList<ChaptersDataModel> list, Context context, RecyclerItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapters,parent,false);
        ChaptersAdapter.ChapterViewHolder holder = new ChaptersAdapter.ChapterViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.queFinished.setText(list.get(position).getQueCompleted());
        holder.queTotal.setText("/"+list.get(position).getQueTotal());
        holder.percentageCompleted.setText(list.get(position).getQuestions());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerItemListener {
        void onClick(View view, int adapterPosition);
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView queFinished;
        private TextView queTotal;
        private TextView percentageCompleted;
        private ChaptersAdapter.RecyclerItemListener listenern;

        public ChapterViewHolder(View itemView, final RecyclerItemListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.chapter_title);
            queFinished = itemView.findViewById(R.id.chapters_que_completed);
            queTotal = itemView.findViewById(R.id.chapters_total_questions);
            percentageCompleted = itemView.findViewById(R.id.chapters_completed_percentage);
            listenern = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenern.onClick(view,getAdapterPosition());
                }
            });
        }
    }
}
