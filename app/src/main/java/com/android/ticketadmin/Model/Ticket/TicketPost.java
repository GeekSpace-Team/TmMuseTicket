package com.android.ticketadmin.Model.Ticket;

import androidx.annotation.Nullable;

public class TicketPost {
    @Nullable
    private Integer cinema_id=null;
    @Nullable
    private Integer status=null;
    @Nullable
    private String movie_date=null;
    @Nullable
    private Integer profile_id=null;

    public TicketPost(@Nullable Integer cinema_id, @Nullable Integer status, @Nullable String movie_date, @Nullable Integer profile_id) {
        this.cinema_id = cinema_id;
        this.status = status;
        this.movie_date = movie_date;
        this.profile_id = profile_id;
    }

    @Nullable
    public Integer getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(@Nullable Integer cinema_id) {
        this.cinema_id = cinema_id;
    }

    @Nullable
    public Integer getStatus() {
        return status;
    }

    public void setStatus(@Nullable Integer status) {
        this.status = status;
    }

    @Nullable
    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(@Nullable String movie_date) {
        this.movie_date = movie_date;
    }

    @Nullable
    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(@Nullable Integer profile_id) {
        this.profile_id = profile_id;
    }
}
