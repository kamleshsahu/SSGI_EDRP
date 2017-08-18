package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class detailsAdaptor extends BaseAdapter  {

   // Declare Variables

   Context mContext;
   LayoutInflater inflater;
   private List<attend_info_class> animalNamesList = null;

   private ArrayList<attend_info_class> arraylist;

   public detailsAdaptor(Context context, List<attend_info_class> animalNamesList) {
       mContext = context;
       this.animalNamesList = animalNamesList;
       inflater = LayoutInflater.from(mContext);
       this.arraylist = new ArrayList<attend_info_class>();
       this.arraylist.addAll(animalNamesList);


   }

    public class ViewHolder {


      
        
TextView teacher;
TextView subject;
TextView sub_desc;
TextView outOf;
TextView value;


   }

   @Override
   public int getCount() {
       return animalNamesList.size();
   }

   @Override
   public attend_info_class getItem(int position) {
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
           view = inflater.inflate(R.layout.list_details, null);

           holder.teacher= (TextView) view.findViewById(R.id.teacher);
           holder.subject= (TextView) view.findViewById(R.id.subject);
           holder.sub_desc = (TextView) view.findViewById(R.id.sub_desc);
           holder.outOf = (TextView) view.findViewById(R.id.outOf);
           holder.value= (TextView) view.findViewById(R.id.value);



           view.setTag(holder);
       } else {
           holder = (ViewHolder) view.getTag();
       }

        holder.teacher.setText(animalNamesList.get(position).getTeacher());
        holder.subject.setText(animalNamesList.get(position).getSubject());
        holder.sub_desc.setText(animalNamesList.get(position).getSub_Desc());
        holder.outOf.setText(animalNamesList.get(position).getOutOf());
        holder.value.setText(animalNamesList.get(position).getValue());

       
       return view;
   }




}


