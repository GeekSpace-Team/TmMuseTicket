package com.android.ticketadmin.Fragment.ui.home;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.ticketadmin.Adapter.TicketAdapter;
import com.android.ticketadmin.Api.APIClient;
import com.android.ticketadmin.Api.ApiInterface;
import com.android.ticketadmin.Common.TicketStatus;
import com.android.ticketadmin.Common.Utils;
import com.android.ticketadmin.Model.Cinema.Cinema;
import com.android.ticketadmin.Model.Home.CinemaId;
import com.android.ticketadmin.Model.Regist.Notif;
import com.android.ticketadmin.Model.Ticket.Ticket;
import com.android.ticketadmin.Model.Ticket.TicketPost;
import com.android.ticketadmin.Model.Universe.Body;
import com.android.ticketadmin.R;
import com.android.ticketadmin.databinding.FragmentHomeBinding;
import com.bruce.pickerview.popwindow.DatePickerPopWin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ProgressDialog progressDialog;
    private ApiInterface apiInterface;
    private Integer cinemaId = null;
    private Integer status = null;
    private String movie_date = null;
    private Integer profile_id = null;
    private Context context;
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private String[] cinemas = new String[]{};
    private String[] cid = new String[]{};
    private Integer[] statuses = new Integer[]{null,TicketStatus.PENDING, TicketStatus.SUCCESS, TicketStatus.ERROR};
    private String[] strStatuses=new String[]{"Status","Täze","Üstünlikli","Gaýtarylan"};
    private Integer day=null;
    private Integer month=null;
    private Integer year = null;
    private ArrayList<Cinema> cnms=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();
        try {
            cinemaId = Integer.parseInt(Utils.getSharedPreference(context, "id"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getTicket(cinemaId, status, movie_date, profile_id);
        getCinema();
        setListener();
        updateNotifToken();

        return root;
    }

    private void getCinema() {
        cnms.clear();
        cnms.add(new Cinema(0,"Filmi sayla",""));
        apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<Body<ArrayList<Cinema>>> call=apiInterface.getCinemaProfile(new CinemaId(""+cinemaId),"Bearer " + Utils.getSharedPreference(context, "token"));
        call.enqueue(new Callback<Body<ArrayList<Cinema>>>() {
            @Override
            public void onResponse(Call<Body<ArrayList<Cinema>>> call, Response<Body<ArrayList<Cinema>>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    if(response.body().getBody()!=null && response.body().getBody().size()>0) {
                        cnms.addAll(response.body().getBody());
                        ArrayList<String> tempList = new ArrayList<>();
                        for(int i=0;i<cnms.size();i++){
                            tempList.add(cnms.get(i).getNameTM());
                        }
                        String[] tmp=new String[tempList.size()];
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, tempList.toArray(tmp));
                        binding.movie.setAdapter(spinnerArrayAdapter);
                    }


                }
            }

            @Override
            public void onFailure(Call<Body<ArrayList<Cinema>>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListener() {
        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.date.setText("Sene saýla");
                binding.movie.setSelection(0);
                binding.status.setSelection(0);
                movie_date=null;
                status=null;
                profile_id=null;
                getTicket(cinemaId,status,movie_date,profile_id);
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, strStatuses);
        binding.status.setAdapter(spinnerArrayAdapter);

        binding.movie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0 && profile_id!=null){
                    profile_id=null;
                    getTicket(cinemaId,status,movie_date,profile_id);
                } else if(position>0) {
                    profile_id=cnms.get(position).getId();
                    getTicket(cinemaId,status,movie_date,profile_id);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0 && status!=null){
                    status=null;
                    getTicket(cinemaId,status,movie_date,profile_id);
                } else if(position>0) {
                    status=statuses[position];
                    getTicket(cinemaId,status,movie_date,profile_id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        year=y;
                        month=m+1;
                        day=d;
                        movie_date = year + "-" + month + "-" + day;
                        binding.date.setText(movie_date);
                        getTicket(cinemaId,status,movie_date,profile_id);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }

    private void getTicket(Integer cinemaId, Integer status, String movie_date, Integer profile_id) {
        binding.list.setVisibility(View.VISIBLE);
        binding.noData.setVisibility(View.GONE);
        progressDialog = Utils.progressDialog(context);
        progressDialog.show();
        tickets.clear();
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        TicketPost post = new TicketPost(cinemaId, status, movie_date, profile_id);
        Call<Body<ArrayList<Ticket>>> call = apiInterface.getTicket(post, "Bearer " + Utils.getSharedPreference(context, "token"));
        call.enqueue(new Callback<Body<ArrayList<Ticket>>>() {
            @Override
            public void onResponse(Call<Body<ArrayList<Ticket>>> call, Response<Body<ArrayList<Ticket>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getBody() != null) {
                        if (response.body().getBody().size() > 0) {
                            tickets = response.body().getBody();
                            setTicketAdapter();
                        } else {
                            emptyPage();
                        }
                    } else {
                        emptyPage();
                    }
                } else {
                    emptyPage();
                    Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Body<ArrayList<Ticket>>> call, Throwable t) {
                emptyPage();
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void emptyPage() {
        tickets.clear();
        binding.list.setVisibility(View.GONE);
        binding.noData.setVisibility(View.VISIBLE);
    }

    private void setTicketAdapter() {
        int span = 1;
        if(Utils.isTablet(context)){
            span=2;
        }
        binding.list.setAdapter(new TicketAdapter(tickets, context, span));
        binding.list.setLayoutManager(new GridLayoutManager(context, span));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateNotifToken(){
        String tkn = "Bearer " + Utils.getSharedPreference(context, "token");
        String notif_tkn = Utils.getSharedPreference(context, "notif_token");
        String id = Utils.getSharedPreference(context, "id");

        Notif notif=new Notif(Integer.parseInt(id),notif_tkn);
        apiInterface=APIClient.getClient().create(ApiInterface.class);
        Call<Body<String>> call=apiInterface.updateNotifToken(notif,tkn);
        call.enqueue(new Callback<Body<String>>() {
            @Override
            public void onResponse(Call<Body<String>> call, Response<Body<String>> response) {
                if(response.isSuccessful()){
                    Log.e("Notif token updated",notif_tkn+" / "+id);
                }
            }

            @Override
            public void onFailure(Call<Body<String>> call, Throwable t) {

            }
        });
    }
}