package com.example.thecookbook.UserUI.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.thecookbook.ListAdapters.CommentsViewAdapter;
import com.example.thecookbook.ListAdapters.IngredientViewAdapter;
import com.example.thecookbook.ListAdapters.InstructionsViewAdapter;
import com.example.thecookbook.Classes.CommentClass;
import com.example.thecookbook.Classes.IngredientClass;
import com.example.thecookbook.Classes.InstructionClass;
import com.example.thecookbook.Classes.RecipeClass;
import com.example.thecookbook.DBClass.DBHelper;
import com.example.thecookbook.R;
import com.example.thecookbook.UserUI.Search.InfluencerCheck.InfluencerDetailsActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.blurry.Blurry;


public class RecipeDetailsActivity extends AppCompatActivity {

    TextView RecipeName, Description, InfName, Origin, Category, nocomment,likecounttext,date;
    EditText Commenttext;
    ShapeableImageView RecipeImage, InfPfp,likepress,FavouriteAdd,Bluradd,SendComment,bgimage;
    YouTubePlayerView ytplayer;
    RecyclerView IngredientsRecycler, InstructionsRecycler, CommentsRecycler;
    MaterialButton Subscribe,DeleteRecipe;
    int RecipeID,UserID,InfID;
    String YoutubeLink;
    CommentsViewAdapter adapter;
    List<CommentClass> commentslist;
    boolean isSubscribed=false;
    boolean isLiked=false;
    boolean AdminEntry = false;
    boolean InfluencerEntry = false;
    boolean isFavourite = false;
    CardView secondcard;

    TextToSpeech tts;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_details);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();

        Intent intent = getIntent();
        RecipeID = intent.getIntExtra("RecipeID", -1);
        UserID = intent.getIntExtra("UserID", -1);
        AdminEntry = intent.getBooleanExtra("AdminEntry", false);
        InfluencerEntry = intent.getBooleanExtra("InfluencerEntry", false);
        Log.d("RecipeDetailsActivity", "This is recipe details activity"+InfluencerEntry);
        RecipeName = (TextView) findViewById(R.id.RecipeNameDetail);
        secondcard = (CardView) findViewById(R.id.secondcard);

        Description = (TextView) findViewById(R.id.RecipeDescriptionDetail);
        InfName = (TextView) findViewById(R.id.InfluencerName);
        Origin = (TextView) findViewById(R.id.RecipeOrigin);
        Category = (TextView) findViewById(R.id.RecipeCategory);
        nocomment = (TextView) findViewById(R.id.Nocomments);
        likecounttext = (TextView) findViewById(R.id.likecount);
        date = (TextView) findViewById(R.id.date);
        Commenttext = (EditText) findViewById(R.id.AddCommentText);
        SendComment = (ShapeableImageView) findViewById(R.id.SendCommentbtn);
        ytplayer = (YouTubePlayerView) findViewById(R.id.ytplayer);

        RecipeImage = (ShapeableImageView) findViewById(R.id.RecipeImage);
        bgimage=(ShapeableImageView) findViewById(R.id.backgroundImageView);
        //Bluradd=(ShapeableImageView) findViewById(R.id.blurset);
        InfPfp = (ShapeableImageView) findViewById(R.id.Influencerpfp);
        FavouriteAdd = (ShapeableImageView) findViewById(R.id.favourite);
        likepress = (ShapeableImageView) findViewById(R.id.likebtn);
        Subscribe = (MaterialButton) findViewById(R.id.SubscribeButton);
        DeleteRecipe = (MaterialButton) findViewById(R.id.deleterecipe);
        IngredientsRecycler = (RecyclerView) findViewById(R.id.IngredientsList);
        InstructionsRecycler = (RecyclerView) findViewById(R.id.InstructionList);
        CommentsRecycler = (RecyclerView) findViewById(R.id.Commentsection);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.75f);
                    tts.setPitch(1.2f);
                }
            }
        });
        bgimage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (bgimage.getDrawable() != null) {
                    Blurry.with(RecipeDetailsActivity.this)
                            .radius(25)
                            .sampling(2)
                            .capture(bgimage)
                            .into(bgimage);
                } else {
                    Log.e("Blur Error", "Background image drawable is null");
                }
            }
        });
//        bgimage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                bgimage.getViewTreeObserver().removeOnPreDrawListener(this);
//                if (bgimage.getDrawable() != null && mainLayout.getHeight() > 0) {
//                    Blurry.with(RecipeDetailsActivity.this)
//                            .radius(25)
//                            .sampling(2)
//                            .async()
//                            .capture(bgimage)
//                            .into(bgimage);
//                } else {
//                    bgimage.post(this::onPreDraw); // Call onPreDraw again
//                }
//                return true;
//            }
//        });
//        Glide.with(this)
//                .load(R.drawable.bgrecipe)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        // Handle image load failure
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        // Image is ready, apply blur here
//                        Blurry.with(RecipeDetailsActivity.this)
//                                .radius(25)
//                                .sampling(2)
//                                .async()
//                                .capture(bgimage)
//                                .into(bgimage);
//                        return false; // Let Glide handle the resource setting
//                    }
//                })
//                .into(bgimage);
        SetupRecipeDetails();
        SetupIngedients();
        SetupInstructions();
        SetupComments();
        if(AdminEntry){
            SendComment.setVisibility(View.GONE);
            Commenttext.setVisibility(View.GONE);
            likepress.setImageResource(R.drawable.heartfillled);
            FavouriteAdd.setVisibility(View.GONE);
            DeleteRecipe.setVisibility(View.VISIBLE);

        }else{
            CheckSubscriptionStatus();
            CheckLikedStatus();
            CheckFavouriteStatus();
        }
        if (InfluencerEntry){
            SendComment.setVisibility(View.GONE);
            Commenttext.setVisibility(View.GONE);
        }
        CommentsRecycler.setLayoutManager(new LinearLayoutManager(this));
        IngredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        InstructionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        FavouriteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserID==-1) {
                    Toast.makeText(RecipeDetailsActivity.this, "You are not allowed to add to favourites", Toast.LENGTH_SHORT).show();
                }else{
                    if (isFavourite) {
                        if (dbHelper.RemoveFavourite(RecipeID,UserID)) {
                            isFavourite = false;
                            FavouriteAdd.setImageResource(R.drawable.baseline_turned_in_not_24);
                            Toast.makeText(RecipeDetailsActivity.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecipeDetailsActivity.this, "Removing from favourites failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (dbHelper.AddFavourite(RecipeID,UserID)) {
                            isFavourite = true;
                            FavouriteAdd.setImageResource(R.drawable.baseline_turned_in_24);
                            Toast.makeText(RecipeDetailsActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecipeDetailsActivity.this, "Adding to favourites failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        SendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Commenttext.getText().toString().isEmpty()) {
                    Toast.makeText(RecipeDetailsActivity.this, "Please enter a comment before pressing", Toast.LENGTH_SHORT).show();
                } else {
                    CommentClass comment = new CommentClass(Commenttext.getText().toString(), UserID, RecipeID);
                    if (dbHelper.addComment(comment)) {
                        Commenttext.setText("");
                        CommentsRecycler.setVisibility(View.VISIBLE);
                        SetupComments();
                        Toast.makeText(RecipeDetailsActivity.this, "Comment added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RecipeDetailsActivity.this, "Comment adding failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserID==-1) {
                    Toast.makeText(RecipeDetailsActivity.this, "You are not allowed to subscribe", Toast.LENGTH_SHORT).show();
                }else{
                    if (isSubscribed) {
                        if (dbHelper.UnsubscribefromInfluencer( InfID,UserID)) {
                            isSubscribed = false;
                            Subscribe.setText("Subscribe");
                            Toast.makeText(RecipeDetailsActivity.this, "Unsubscribed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecipeDetailsActivity.this, "Unsubscribing failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (dbHelper.SubscribetoInfluencer(UserID, InfID)) {
                            isSubscribed = true;
                            Subscribe.setText("Unsubscribe");
                            Toast.makeText(RecipeDetailsActivity.this, "Subscribed successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RecipeDetailsActivity.this, "Subscribing failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        DeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(RecipeDetailsActivity.this);
                alertDialog.setTitle("Delete Recipe");
                alertDialog.setMessage("Are you sure you want to delete this recipe?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dbHelper.deleteRecipe(RecipeID,InfID)){
                            Toast.makeText(RecipeDetailsActivity.this, "Deleted successfuly", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }else {
                            Toast.makeText(RecipeDetailsActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RecipeDetailsActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
        likepress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserID==-1){
                    Toast.makeText(RecipeDetailsActivity.this, "You are not allowed to like", Toast.LENGTH_SHORT).show();
                }else{
                    if(isLiked){
                        if(dbHelper.RemoveLike(RecipeID,UserID,InfID)){
                            isLiked = false;
                            likepress.setImageResource(R.drawable.heartnotfilled);
                            likecounttext.setText(String.valueOf(dbHelper.getLikeCount(RecipeID)));
                            Toast.makeText(RecipeDetailsActivity.this, "Unliked", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RecipeDetailsActivity.this, "Unliking failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        if(dbHelper.AddLike(RecipeID,UserID,InfID)){
                            isLiked=true;
                            likecounttext.setText(String.valueOf(dbHelper.getLikeCount(RecipeID)));
                            likepress.setImageResource(R.drawable.heartfillled);
                            Toast.makeText(RecipeDetailsActivity.this, "Liked", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RecipeDetailsActivity.this, "Liking failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        ytplayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(YoutubeLink, 0);
            }
        });
        InfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeDetailsActivity.this, InfluencerDetailsActivity.class);
                if(!AdminEntry){
                    intent.putExtra("UserID",UserID);
                    intent.putExtra("InfID",InfID);
                }else{
                    intent.putExtra("InfID",InfID);
                    intent.putExtra("AdminEntry",true);
                }
                startActivity(intent);
            }
        });
//        secondcard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                secondcard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                Blurry.with(RecipeDetailsActivity.this).radius(25).sampling(2).capture(RecipeImage).into(Bluradd);
//            }
//        });


    }

    private void CheckFavouriteStatus() {
        isFavourite = dbHelper.isFavourite(UserID, RecipeID);
        if(isFavourite){
            FavouriteAdd.setImageResource(R.drawable.baseline_turned_in_24);
        }else {
            FavouriteAdd.setImageResource(R.drawable.baseline_turned_in_not_24);
        }
    }

    private void CheckSubscriptionStatus() {
        Log.d("UserID", String.valueOf(UserID));
        Log.d("InfID", String.valueOf(InfID));
        Log.d("isSubscribed", String.valueOf(isSubscribed));
        isSubscribed = dbHelper.isSubscribed(InfID, UserID);
        if(isSubscribed){
            Subscribe.setText("Unsubscribe");
        }else{
            Subscribe.setText("Subscribe");
        }
    }

    private void CheckLikedStatus(){
        isLiked = dbHelper.isLiked(UserID, RecipeID);
        if(isLiked){
            likepress.setImageResource(R.drawable.heartfillled);
        }else{
            likepress.setImageResource(R.drawable.heartnotfilled);
        }
    }

    private void SetupRecipeDetails() {
        try {
            RecipeClass recipe = dbHelper.getRecipeDetails(RecipeID);
            if (recipe != null) {
                InfID = recipe.getInfID();
                RecipeName.setText(recipe.getRecipeName());
                Description.setText(recipe.getDescription());
                date.setText(recipe.getDate());
                likecounttext.setText(String.valueOf(recipe.getLikeCount()));
                InfName.setText(dbHelper.getInfName(InfID));
                InfPfp.setImageBitmap(dbHelper.getInfPfp(InfID));
                Origin.setText(recipe.getOrigin());
                Category.setText(recipe.getCategory());
                RecipeImage.setImageBitmap(recipe.getRecipeImage());
                YoutubeLink = recipe.getVideo();
            } else {
                Toast.makeText(this, "Error showing details", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception c) {
            c.printStackTrace();
        }
    }

    private void SetupIngedients() {
        List<IngredientClass> ingredients = dbHelper.getIngredients(RecipeID);
        if (ingredients.size() > 0) {
            IngredientViewAdapter adapter = new IngredientViewAdapter(ingredients);
            IngredientsRecycler.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No ingredients found", Toast.LENGTH_SHORT).show();
        }
    }

    private void SetupInstructions() {
        List<InstructionClass> instructions = dbHelper.getInstructions(RecipeID);
        if (instructions.size() > 0) {
            InstructionsViewAdapter adapter = new InstructionsViewAdapter(instructions);
            adapter.setTexttospeech(tts);
            InstructionsRecycler.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No instructions found", Toast.LENGTH_SHORT).show();
        }
    }

    private void SetupComments() {
        commentslist = dbHelper.getComments(RecipeID);
        if (!commentslist.isEmpty()) {
            adapter = new CommentsViewAdapter(commentslist, dbHelper,AdminEntry,InfluencerEntry,this);
            adapter.notifyDataSetChanged();
            nocomment.setVisibility(View.GONE);
            CommentsRecycler.setAdapter(adapter);
        } else {
            nocomment.setVisibility(View.VISIBLE);
        }
    }

}