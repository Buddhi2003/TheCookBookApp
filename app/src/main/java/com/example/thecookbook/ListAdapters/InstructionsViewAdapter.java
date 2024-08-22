package com.example.thecookbook.ListAdapters;

import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.InstructionClass;
import com.example.thecookbook.R;

import java.util.List;

public class InstructionsViewAdapter extends RecyclerView.Adapter<InstructionsViewAdapter.ViewHolder>{
    private List<InstructionClass> instructionList;
    private TextToSpeech tts;
    private CountDownTimer countDownTimer;
    private boolean istimerrunning = false;


    public InstructionsViewAdapter(List<InstructionClass> instructionList) {
        this.instructionList = instructionList;
    }
    public void setTexttospeech(TextToSpeech tts) {
        this.tts = tts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InstructionClass instruction = instructionList.get(position);
        holder.InstructionNo.setText(String.valueOf(instruction.getInstructionNumber()));
        holder.Instruction.setText(instruction.getInstructionDetail());
        holder.InstructionTime.setText(String.valueOf(instruction.getTime())+" Min");

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(instruction.getInstructionDetail(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        holder.taptimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!istimerrunning){
                    timershow(holder,instruction.getTime());

                }else{
                    canceltimer(holder);
                }
            }
        });
    }
    private void timershow(ViewHolder holder,int time){
        holder.taptimer.setText("Tap to Stop Timer");
        countDownTimer = new CountDownTimer(time*1000*60,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long timeremaining = millisUntilFinished/1000;
                holder.timertext.setText(String.format("%02d:%02d",timeremaining / 60, timeremaining % 60));
            }

            @Override
            public void onFinish() {
                holder.timertext.setText("00:00");
                istimerrunning = false;
                holder.taptimer.setText("Tap to Start Timer");
            }
        }.start();
        istimerrunning = true;
    }
    private void canceltimer(ViewHolder holder){
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        holder.timertext.setText("00:00");
        istimerrunning = false;
        holder.taptimer.setText("Tap to Start Timer");
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView InstructionNo;
        private TextView Instruction;
        private TextView InstructionTime;
        private TextView timertext;
        private TextView taptimer;
        private ImageButton playButton;
        public ViewHolder(View itemView) {
            super(itemView);
            InstructionNo = itemView.findViewById(R.id.instruction_no);
            Instruction = itemView.findViewById(R.id.instruction_detail);
            InstructionTime = itemView.findViewById(R.id.instruction_time);
            playButton = itemView.findViewById(R.id.speechinstruction);
            timertext = itemView.findViewById(R.id.instruction_timer);
            taptimer = itemView.findViewById(R.id.start_timer);
        }
    }

}
