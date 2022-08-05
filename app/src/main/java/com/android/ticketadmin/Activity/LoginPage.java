package com.android.ticketadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.ticketadmin.Api.APIClient;
import com.android.ticketadmin.Api.ApiInterface;
import com.android.ticketadmin.Common.Utils;
import com.android.ticketadmin.Model.Regist.SignIn;
import com.android.ticketadmin.Model.Regist.SignInResponse;
import com.android.ticketadmin.Model.Universe.Body;
import com.android.ticketadmin.R;
import com.android.ticketadmin.databinding.ActivityLoginPageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {
    private ActivityLoginPageBinding binding;
    private Context context=this;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //bundle must contain all info sent in "data" field of the notification
            if (bundle.get("ticket_id") != null) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra("id",bundle.get("ticket_id")+"");
                startActivity(intent);
            }
        }
        setListener();
    }

    private void setListener() {
        ProgressDialog progressDialog=Utils.progressDialog(context);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                apiInterface= APIClient.getClient().create(ApiInterface.class);
                SignIn signIn=new SignIn(binding.username.getText().toString(),binding.password.getText().toString());
                Call<Body<SignInResponse>> call=apiInterface.signIn(signIn);
                call.enqueue(new Callback<Body<SignInResponse>>() {
                    @Override
                    public void onResponse(Call<Body<SignInResponse>> call, Response<Body<SignInResponse>> response) {
                        if(response.isSuccessful()){
                            SignInResponse res=response.body().getBody();
                            if(res.getType().equals("Cinema")){
                                Utils.setSharedPreference(context,"token",res.getToken());
                                Utils.setSharedPreference(context,"id",res.getId()+"");
                                Intent intent=new Intent(context,MainActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(context, "Yalnyshlyk yuze cykdy", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Body<SignInResponse>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}