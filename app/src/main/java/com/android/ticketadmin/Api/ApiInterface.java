package com.android.ticketadmin.Api;

import com.android.ticketadmin.Model.Cinema.Cinema;
import com.android.ticketadmin.Model.Home.CinemaId;
import com.android.ticketadmin.Model.Regist.Notif;
import com.android.ticketadmin.Model.Regist.SignIn;
import com.android.ticketadmin.Model.Regist.SignInResponse;
import com.android.ticketadmin.Model.Ticket.ChangeStatus;
import com.android.ticketadmin.Model.Ticket.GetTicket;
import com.android.ticketadmin.Model.Ticket.Ticket;
import com.android.ticketadmin.Model.Ticket.TicketPost;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("/get-ticket")
    Call<com.android.ticketadmin.Model.Universe.Body<ArrayList<Ticket>>> getTicket(@Body TicketPost body, @Header("Authorization") String token);

    @POST("/sign-in")
    Call<com.android.ticketadmin.Model.Universe.Body<SignInResponse>> signIn(@Body SignIn signIn);

    @POST("/get-cineme-profile")
    Call<com.android.ticketadmin.Model.Universe.Body<ArrayList<Cinema>>> getCinemaProfile(@Body CinemaId body,@Header("Authorization") String token);

    @GET("/get-current-ticket?")
    Call<com.android.ticketadmin.Model.Universe.Body<Ticket>> getCurrentTicket(@Query("id") String id, @Header("Authorization") String token);

    @PUT("/ticket-status-update?")
    Call<com.android.ticketadmin.Model.Universe.Body<String>> ticketStatusUpdate(@Query("id") String id, @Body ChangeStatus body,@Header("Authorization") String token);

    @PUT("/update-notif-token")
    Call<com.android.ticketadmin.Model.Universe.Body<String>> updateNotifToken(@Body Notif body,@Header("Authorization") String token);
}
