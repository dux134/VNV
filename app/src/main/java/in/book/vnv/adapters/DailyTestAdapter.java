package in.book.vnv.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import in.book.vnv.R;
import in.book.vnv.entity.DailyScore;

public class DailyTestAdapter extends RecyclerView.Adapter<DailyTestAdapter.DailyTestViewHolder> {
    private ArrayList<DailyScore> list = new ArrayList<>();
    private Context context;

    public DailyTestAdapter(ArrayList<DailyScore> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyTestAdapter.DailyTestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily_score,parent,false);
        DailyTestViewHolder holder = new DailyTestViewHolder(view);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTestAdapter.DailyTestViewHolder holder, int position) {
        holder.score.setText(list.get(position).getCorrect());
        holder.date.setText(list.get(position).getDate());
        holder.id.setText(list.get(position).getId());
        holder.total.setText("/"+list.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DailyTestViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView score;
        private TextView date;
        private TextView total;

        public DailyTestViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.dailyscore_id);
            score = itemView.findViewById(R.id.dailyscore_score);
            date = itemView.findViewById(R.id.dailyscore_date);
            total = itemView.findViewById(R.id.dailyscore_total);
        }
    }
}
