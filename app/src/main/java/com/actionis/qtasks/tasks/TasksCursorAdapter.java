package com.actionis.qtasks.tasks;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionis.qtasks.R;

import static com.actionis.qtasks.data.TasksContract.TaskEntry;



/**
 * Created by fm on 12/01/17.
 */

/**
 * Adaptador de tasks
 */

public class TasksCursorAdapter extends CursorAdapter {

    public TasksCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_task, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        // Referencias UI.
        TextView nameText = (TextView) view.findViewById(R.id.tv_title);
        TextView summaryText = (TextView) view.findViewById(R.id.tv_date);
        //final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        ImageView statusImage = (ImageView) view.findViewById(R.id.iv_avatar);

        // Get valores.
        String title = cursor.getString(cursor.getColumnIndex(TaskEntry.TITLE));
        String summary = cursor.getString(cursor.getColumnIndex(TaskEntry.DATE));
        String status =  cursor.getString(cursor.getColumnIndex(TaskEntry.DONE));

        // Setup.
        //int did = getR
        //Drawable myDrawable = getResources().getDrawable(<insert your id here>);
        nameText.setText(title);
        summaryText.setText(summary);
        if (status.equals("Yes")){
            statusImage.setImageResource(R.drawable.ic_check_circle_green_24dp);
        } else {
            statusImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
        }

        /*Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + date))
                .asBitmap()
                .error(btn_star_big_on)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });*/

    }


    /*
    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        //DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {

            case R.id.iv_avatar:

                Snackbar.make(v, "Release date ", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText(dataModel.getVersion_number());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
    */

}
