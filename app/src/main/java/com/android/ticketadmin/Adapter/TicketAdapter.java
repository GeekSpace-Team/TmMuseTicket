package com.android.ticketadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ticketadmin.Activity.DetailActivity;
import com.android.ticketadmin.Common.Constant;
import com.android.ticketadmin.Common.TicketStatus;
import com.android.ticketadmin.Model.Ticket.Ticket;
import com.android.ticketadmin.Model.Universe.Body;
import com.android.ticketadmin.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private Context context;
    private int span = 1;
    Body<Double> doubleBody = new Body<Double>(false, 1.1);
    Body<ArrayList<String>> arrayListBody = new Body<>(false, new ArrayList<>());


    public TicketAdapter(ArrayList<Ticket> tickets, Context context, int span) {
        this.tickets = tickets;
        this.context = context;
        this.span = span;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_design, parent, false);
        return new TicketAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        if(ticket.getMovie_date()!=null)
            holder.date.setText("Film senesi: " + ticket.getMovie_date() + " " + ticket.getMovie_time());

        if(ticket.getMovie_time()!=null)
            holder.discount.setText("Petek arzanladyş: " + ticket.getTicket_discount() + "%");

        if(ticket.getTicket_count()!=null)
            holder.ticketCount.setText("Petek sany: " + ticket.getTicket_count());

        if(ticket.getTicket_price()!=null)
            holder.price.setText("Petek bahasy: " + ticket.getTicket_price()+" TMT");

        if(ticket.getNameTM()!=null)
            holder.name.setText(ticket.getNameTM() + "");

        if(ticket.getFullname()!=null)
            holder.userFullName.setText("Sargyt ediji: " + ticket.getFullname());


        if(span==1){
            LayoutParams lm=holder.card.getLayoutParams();
            lm.width=LayoutParams.MATCH_PARENT;
            holder.card.setLayoutParams(lm);
        }

        if(ticket.getStatus().equals(TicketStatus.PENDING)){
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.pending));
        } else if(ticket.getStatus().equals(TicketStatus.SUCCESS)){
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.success));
        } else if(ticket.getStatus().equals(TicketStatus.ERROR)){
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.error));
        } else {
            holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

        try {
            Double total = ticket.getTicket_price() * ticket.getTicket_count();
            holder.totalPrice.setText("Jem bahasy: " + total+" TMT");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            if (ticket.getImages() != null && ticket.getImages().size() > 0) {
                Glide.with(context)
                        .load(Constant.BASE_URL + ticket.getImages().get(0).getSmall_image())
                        .placeholder(R.drawable.placeholder)
                        .into(holder.image);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        TicketAdapter ticketAdapter=this;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.ticketAdapter=ticketAdapter;
                DetailActivity.tickets=tickets;
                DetailActivity.pos=position;

                context.startActivity(new Intent(context, DetailActivity.class).putExtra("id",ticket.getId()+""));
            }
        });
    }

    public void itemChanged(Integer pos){
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView card;
        private TextView name, date, ticketCount, price, discount, userFullName, totalPrice;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.card);
            name=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.date);
            ticketCount=itemView.findViewById(R.id.ticketCount);
            price=itemView.findViewById(R.id.price);
            discount=itemView.findViewById(R.id.discount);
            userFullName=itemView.findViewById(R.id.userFullName);
            totalPrice=itemView.findViewById(R.id.totalPrice);
            image=itemView.findViewById(R.id.image);
        }
    }
}
