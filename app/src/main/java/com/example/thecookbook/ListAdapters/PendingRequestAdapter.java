package com.example.thecookbook.ListAdapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecookbook.Classes.UserClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.ViewHolder> {
    List<UserClass> pendingrequestlist;
    Context context;
    DBHelper dbhelper;
    public PendingRequestAdapter(List<UserClass> pendingrequestlist, Context context, DBHelper dbhelper) {
        this.pendingrequestlist = pendingrequestlist;
        this.context = context;
        this.dbhelper = dbhelper;
    }
    @NonNull
    @Override
    public PendingRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_request_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRequestAdapter.ViewHolder holder, int position) {
        UserClass user = pendingrequestlist.get(position);
        holder.UserName.setText(user.getUserName());
        holder.UserProf.setImageBitmap(user.getProfileImage());
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbhelper.confirmPremiumUser(user.getUserID())){
                    setPendingList();
                    Toast.makeText(context, "Request Confirmed", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Error occurred while confirming request", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Decline Request");
                    alertDialog.setMessage("Are you sure you want to decline this request?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(dbhelper.declinePremiumUser(user.getUserID())){
                                setPendingList();
                                Toast.makeText(context, "Decline successfuly", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Failed to decline", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.show();
                }
        });
    }

    @Override
    public int getItemCount() {
        return pendingrequestlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView UserName;
        MaterialButton confirm,decline;
        ShapeableImageView UserProf;
      public ViewHolder(@NonNull View itemView) {
          super(itemView);
          UserName=(TextView)itemView.findViewById(R.id.UserNamePending);
          UserProf=(ShapeableImageView)itemView.findViewById(R.id.UserProfPending);
          confirm=(MaterialButton)itemView.findViewById(R.id.confirmpendngrequestitem);
          decline=(MaterialButton)itemView.findViewById(R.id.declinependingrequestitem);
      }
  }
    public void setPendingList(){
        this.pendingrequestlist = dbhelper.getPendingUsers();
        notifyDataSetChanged();
    }
}
