package com.example.thecookbook.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.CommentClass;
import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Home.RecipeDetailsActivity;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CommentsViewAdapter extends RecyclerView.Adapter<CommentsViewAdapter.ViewHolder> {
    private List<CommentClass> Commentslist;
    private DBHelper dbHelper;
    boolean AdminEntry=false;
    boolean InfluencerEntry=false;
    Context context;

    public CommentsViewAdapter(List<CommentClass> commentslist, DBHelper dbHelper, boolean adminEntry, boolean influencerEntry,Context context) {
        Commentslist = commentslist;
        this.dbHelper = dbHelper;
        AdminEntry = adminEntry;
        InfluencerEntry = influencerEntry;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_section_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentClass comment = Commentslist.get(position);
        UserClass user = dbHelper.getUserdetailsforComment(comment.getUserID());
        if(user!=null){
            holder.userpfp.setImageBitmap(user.getProfileImage());
            holder.username.setText(user.getUserName());
        }
        holder.comment.setText(comment.getComment());
        if (comment.getReply()==null) {
            holder.reply.setVisibility(View.GONE);
            holder.replytitle.setVisibility(View.GONE);
        }
        else{
            holder.reply.setText(comment.getReply());
        }
        if(AdminEntry){
            holder.deletecomment.setVisibility(View.VISIBLE);
        }
        if(InfluencerEntry&&comment.getReply()==null){
           holder.AddReplyBox.setVisibility(View.VISIBLE);
           holder.AddReply.setVisibility(View.VISIBLE);
        }
        if (InfluencerEntry){
            holder.deletecomment.setVisibility(View.VISIBLE);
        }
        holder.deletecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbHelper.DeleteComment(comment.getCommentID(),comment.getRecipeID())){
                    int removeposition = holder.getAdapterPosition();
                    Commentslist.remove(removeposition);
                    notifyItemRemoved(removeposition);
                    Toast.makeText(v.getContext(), "Comment deleted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.AddReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.AddReplyEditText.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please add reply first", Toast.LENGTH_SHORT).show();
                }else{
                    CommentClass newreply = new CommentClass(comment.getCommentID(),holder.AddReplyEditText.getText().toString());
                    if(dbHelper.AddReply(newreply)){
                        Toast.makeText(context, "Reply added successfully", Toast.LENGTH_SHORT).show();
                        holder.reply.setVisibility(View.VISIBLE);
                        holder.replytitle.setVisibility(View.VISIBLE);
                        holder.reply.setText(holder.AddReplyEditText.getText().toString());
                        holder.AddReplyEditText.setText("");
                        holder.AddReplyBox.setVisibility(View.GONE);
                        holder.AddReply.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(context, "Reply not added", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void setCommentsagain(int RecipeID){
        this.Commentslist = dbHelper.getComments(RecipeID);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Commentslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username,comment,reply,replytitle;
        ShapeableImageView userpfp,deletecomment;
        TextInputEditText AddReplyEditText;
        TextInputLayout AddReplyBox;
        ShapeableImageView AddReply;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.UserNameComment);
            comment = itemView.findViewById(R.id.CommentDescriptiont);
            reply = itemView.findViewById(R.id.ReplyDescriptiont);
            userpfp = itemView.findViewById(R.id.Userpfpcomment);
            replytitle = itemView.findViewById(R.id.replytitle);
            deletecomment = itemView.findViewById(R.id.deletecomment);
            AddReplyEditText = itemView.findViewById(R.id.replyaddedittext);
            AddReplyBox = itemView.findViewById(R.id.addreplybox);
            AddReply = itemView.findViewById(R.id.addreplybtn);
        }
    }
}
