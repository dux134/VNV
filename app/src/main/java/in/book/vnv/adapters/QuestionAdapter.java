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
import in.book.vnv.activitys.Content;
import in.book.vnv.entity.QuestionDataModel;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private ArrayList<QuestionDataModel> list;
    private Context context;
    private QuestionAdapter.RecyclerItemListener listener;;

    public QuestionAdapter(ArrayList<QuestionDataModel> list, Context context,RecyclerItemListener lv) {
        this.list = list;
        this.context = context;
        this.listener = lv;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questions,parent,false);
        QuestionViewHolder holder = new QuestionViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.question.setText(list.get(position).getQuestion());
        holder.optionA.setText(list.get(position).getOptionA());
        holder.optionB.setText(list.get(position).getOptionB());
        holder.optionC.setText(list.get(position).getOptionC());
        holder.optionD.setText(list.get(position).getOptionD());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerItemListener {
        void onClick(View view, int adapterPosition);
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private TextView optionA;
        private TextView optionB;
        private TextView optionC;
        private TextView optionD;
        private RecyclerItemListener listener1;
        public QuestionViewHolder(View itemView, final RecyclerItemListener listener) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            this.listener1 = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener1.onClick(view,getAdapterPosition());
                }
            });
        }
    }
}
