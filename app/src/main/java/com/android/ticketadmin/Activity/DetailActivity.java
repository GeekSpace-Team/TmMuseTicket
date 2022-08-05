package com.android.ticketadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.ticketadmin.Adapter.TicketAdapter;
import com.android.ticketadmin.Api.APIClient;
import com.android.ticketadmin.Api.ApiInterface;
import com.android.ticketadmin.Common.Constant;
import com.android.ticketadmin.Common.TicketStatus;
import com.android.ticketadmin.Common.Utils;
import com.android.ticketadmin.Model.Ticket.ChangeStatus;
import com.android.ticketadmin.Model.Ticket.Ticket;
import com.android.ticketadmin.Model.Universe.Body;
import com.android.ticketadmin.R;
import com.android.ticketadmin.databinding.ActivityDetailBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private Context context=this;
    private String id;
    private ApiInterface apiInterface;
    private Ticket ticket;
    public TicketAdapter.ViewHolder holder;
    public static Integer pos;
    public static ArrayList<Ticket> tickets = new ArrayList<>();
    public static TicketAdapter ticketAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id=getIntent().getStringExtra("id");
        getCurrentTicket();
    }

    private void getCurrentTicket() {
        ProgressDialog progressDialog=Utils.progressDialog(context);
        progressDialog.show();

        apiInterface= APIClient.getClient().create(ApiInterface.class);
        Call<Body<Ticket>> call=apiInterface.getCurrentTicket(id,"Bearer " + Utils.getSharedPreference(context, "token"));
        call.enqueue(new Callback<Body<Ticket>>() {
            @Override
            public void onResponse(Call<Body<Ticket>> call, Response<Body<Ticket>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    ticket=response.body().getBody();
                    setInfo();
                } else {
                    finish();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Body<Ticket>> call, Throwable t) {
                progressDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pos=null;
        tickets = null;
        ticketAdapter=null;
    }

    private void setInfo() {
        if(ticket.getMovie_date()!=null)
            binding.date.setText("Film senesi: " + ticket.getMovie_date() + " " + ticket.getMovie_time());

        if(ticket.getMovie_time()!=null)
            binding.discount.setText("Petek arzanladyş: " + ticket.getTicket_discount() + "%");

        if(ticket.getTicket_count()!=null)
            binding.count.setText("Petek sany: " + ticket.getTicket_count());

        if(ticket.getTicket_price()!=null)
            binding.price.setText("Petek bahasy: " + ticket.getTicket_price()+" TMT");

        if(ticket.getNameTM()!=null)
            binding.name.setText(ticket.getNameTM() + "");

        if(ticket.getFullname()!=null)
            binding.userFullName.setText("Sargyt ediji: " + ticket.getFullname());

        if(ticket.getCreated_at()!=null) {
            try {
                binding.orderDate.setText("Sargyt senesi: " + Utils.covertStringToDate(ticket.getCreated_at()));
            } catch (ParseException e) {
                e.printStackTrace();
                binding.orderDate.setText("");
            }
        }

        if (!ticket.getStatus().equals(TicketStatus.PENDING)) {
            binding.accept.setVisibility(View.GONE);
            binding.cancel.setVisibility(View.GONE);
        }

        try {
            Double total = ticket.getTicket_price() * ticket.getTicket_count();
            binding.totalPrice.setText("Jem bahasy: " + total+" TMT");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            if (ticket.getImages() != null && ticket.getImages().size() > 0) {
                Glide.with(context)
                        .load(Constant.BASE_URL + ticket.getImages().get(0).getSmall_image())
                        .placeholder(R.drawable.placeholder)
                        .into(binding.img);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

       binding.call.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(ticket.getPhone_number()!=null){
                   Intent intent=new Intent();
                   intent.setAction(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse("tel:"+ticket.getPhone_number()));
                   startActivity(intent);
               }
           }
       });

        binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(TicketStatus.SUCCESS,"Siz bu petegi tassyklamak isleýärsiňizmi?");
            }
        });

        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(TicketStatus.ERROR,"Siz bu petegi ýatyrmak isleýärsiňizmi?");
            }
        });



    }

    private void changeStatus(Integer status,String text){
        MaterialAlertDialogBuilder alert=new MaterialAlertDialogBuilder(context);
        alert.setTitle("Uns berin!");
        alert.setMessage(text);
        alert.setNegativeButton("Yok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("Hawa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ProgressDialog progressDialog=Utils.progressDialog(context);
                progressDialog.show();
                apiInterface=APIClient.getClient().create(ApiInterface.class);
                Call<Body<String>> call=apiInterface.ticketStatusUpdate(id,new ChangeStatus(status +""),"Bearer " + Utils.getSharedPreference(context, "token"));
                call.enqueue(new Callback<Body<String>>() {
                    @Override
                    public void onResponse(Call<Body<String>> call, Response<Body<String>> response) {
                        if(response.isSuccessful()){
                            try {
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                tickets.get(pos).setStatus(status);
                                ticketAdapter.itemChanged(pos);
                                if (status.equals(TicketStatus.ERROR)) {
                                    String message = "Hormatly musderi sizin alan peteginiz yatyrldy!";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(ticket.getPhone_number(), null, message, null, null);
                                    Log.e("Message",message+" / "+ticket.getPhone_number()+" / "+status);
                                }

                                if (status.equals(TicketStatus.SUCCESS)) {
                                    String message = "Hormatly musderi sizin alan peteginiz tassyklandy!";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(ticket.getPhone_number(), null, message, null, null);
                                    Log.e("Message",message+" / "+ticket.getPhone_number()+" / "+status);
                                }
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        } else{
                            Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Body<String>> call, Throwable t) {
                        Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        progressDialog.dismiss();
                    }
                });
            }
        });
        alert.show();
    }
}