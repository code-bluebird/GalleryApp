package in.dean.galleryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//this is for the list of the images in recyclerview
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Cell> galleryList;
    private Context context;
    public MyAdapter(Context context,ArrayList<Cell> galleryList)
    {
        this.galleryList=galleryList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell,viewGroup,false);
        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageFromPath(galleryList.get(i).getPath(),viewHolder.img);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what happens when you click on an image
                Toast.makeText(context, ""+galleryList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,PdfActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("title",galleryList.get(i).getTitle());
                intent.putExtra("path",galleryList.get(i).getPath());
                context.startActivity(intent);
                Log.e("Position",i+"");

            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;


        public ViewHolder(@NonNull View view) {
            super(view);
            context=view.getContext();
            img=(ImageView) view.findViewById(R.id.img);

        }
    }
    private void setImageFromPath(String path,ImageView image)
    {
        File imgFile=new File(path);
        if (imgFile.exists())
        {
            Bitmap myBitmap=ImageHelper.decodeSampleBitmapFromPath(imgFile.getAbsolutePath(),200,200);
            image.setImageBitmap(myBitmap);

        }
        else {
            Toast.makeText(context, "Wromng directory", Toast.LENGTH_SHORT).show();
        }
    }
}
