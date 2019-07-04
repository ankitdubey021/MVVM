package adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ebiztechnocrats.mvvm.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import database.TaskEntry;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    Context context;
    List<TaskEntry> list;
    OnItemClickListener listener;

    public TaskAdapter(List<TaskEntry> list,OnItemClickListener listener) {
        this.list = list;
        this.listener=listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout,parent,false);

        context=parent.getContext();
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskEntry taskEntry = list.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String updatedAt = dateFormat.format(taskEntry.getUpdatedOn());

        //Set values
        holder.descTV.setText(description);
        holder.dateTV.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityTV.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityTV.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(context, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(context, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(context, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView descTV,dateTV,priorityTV;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            descTV=itemView.findViewById(R.id.desc_et);
            dateTV=itemView.findViewById(R.id.date_et);
            priorityTV=itemView.findViewById(R.id.priority_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        listener.onItemClick(list.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public void addTaskList(List<TaskEntry>list){
        this.list=list;
        notifyDataSetChanged();
    }

    public List<TaskEntry>getTaskList(){return list;}


    public interface OnItemClickListener{
        void onItemClick(int itemId);
    }
}
