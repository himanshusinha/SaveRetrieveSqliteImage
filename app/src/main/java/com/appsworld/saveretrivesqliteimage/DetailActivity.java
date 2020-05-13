package com.appsworld.saveretrivesqliteimage;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class DetailActivity extends Activity
{

    DatabaseHelper mDatabaseHelper;
    public ListView listView;
    ArrayList<MyData>  listData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        listView = (ListView) findViewById(R.id.myList);
        mDatabaseHelper = new DatabaseHelper(this);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this, R.layout.row_image, listData);
        listView.setAdapter(adapter);
    }

    private void populateView()
    {
        Cursor data = mDatabaseHelper.getdata();
        while(data.moveToNext())
        {
            String name = data.getString(1);
            byte[] image = data.getBlob(2);
            listData.add(new MyData(name,image));

        }
    }

    public class CustomAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        ArrayList<MyData> textList;

        public CustomAdapter(Context context, int layout, ArrayList<MyData> textList) {
            this.context = context;
            this.layout = layout;
            this.textList = textList;
        }

        @Override
        public int getCount() {
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            ImageView imageView1;
            TextView textName;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            View row = view;

            ViewHolder holder;

            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.textName = (TextView) row.findViewById(R.id.textView);
                holder.imageView1 = (ImageView) row.findViewById(R.id.imageView);
                row.setTag(holder);

            } else {
                holder = (ViewHolder) row.getTag();

            }
            final MyData food = textList.get(position);
            holder.textName.setText(food.GetName());
            byte[] foodImage = food.GetImage();
            final Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            holder.imageView1.setImageBitmap(bitmap);

            return row;
        }
    }
}