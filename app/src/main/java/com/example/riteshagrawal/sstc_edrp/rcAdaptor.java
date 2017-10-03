package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class rcAdaptor extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<reportcard> animalNamesList = null;

   private ArrayList<reportcard> arraylist;

   public rcAdaptor(Context context, List<reportcard> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<reportcard>();
       this.arraylist.addAll(animalNamesList);


   }

    public class ViewHolder {




        TextView subject;
        TextView percent;
        TextView maxmarks;
        //TextView minmarks;
        ImageView icon;
        TextView value;
        TextView status;
        LinearLayout marks;
        LinearLayout percentage;


   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public reportcard getItem(int position) {
       return animalNamesList.get(position);
   }

   @Override
   public long getItemId(int position) {
       return position;
   }

   public View getView(final int position, View view, ViewGroup parent) {
       final ViewHolder holder;
       if (view == null) {
           holder = new ViewHolder();
           view = inflater.inflate(R.layout.rc_list_details, null);


           holder.subject= (TextView) view.findViewById(R.id.subject);
           holder.maxmarks= (TextView) view.findViewById(R.id.max_marks);
          // holder.minmarks= (TextView) view.findViewById(R.id.min_marks);
           holder.value= (TextView) view.findViewById(R.id.value);
           holder.percent= (TextView) view.findViewById(R.id.percent);
           holder.status= (TextView) view.findViewById(R.id.status);
           holder.icon=(ImageView)view.findViewById(R.id.smiley);
           holder.marks=(LinearLayout)view.findViewById(R.id.marks);
           holder.percentage=(LinearLayout)view.findViewById(R.id.percentage);


           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }


       holder.subject.setText(animalNamesList.get(position).getSubject());
       holder.maxmarks.setText(animalNamesList.get(position).getMaxmarks());
     //  holder.minmarks.setText(animalNamesList.get(position).getMinmarks());
       holder.value.setText(animalNamesList.get(position).getValue());
       holder.percent.setText(animalNamesList.get(position).getPercent());
       holder.status.setText(animalNamesList.get(position).getStatus());
     String test=animalNamesList.get(position).getStatus();
       switch (test){

           case "PASS":
               break;

           case "FAIL" :
               //System.out.print("fail is working");
               holder.icon.setImageResource(R.drawable.sad);
               break;

           case "ABSENT" :
               //System.out.print("absent is working");
               holder.icon.setImageResource(R.drawable.absent);
               holder.percentage.setVisibility(View.GONE);
               holder.marks.setVisibility(View.GONE);
               break;

       }

       return view;
   }




}


