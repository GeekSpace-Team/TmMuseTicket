package com.android.ticketadmin.Model.Ticket;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Ticket {
   private Integer id;
   private Integer cinema_id;
   private Integer profile_id;
   private Integer user_id;
   private String movie_date;
   private String movie_time;
   private Double ticket_price;
   private Integer ticket_count;
   private Double ticket_discount;
   private Integer status;
   private String nameTM;
   private String nameRU;
   private String short_descTM;
   private String short_descRU;
   private String descriptionTM;
   private String descriptionRU;
   private String fullname;
   private String phone_number;
   @SerializedName("image")
   private ArrayList<Image> images=new ArrayList<>();
   private String created_at;
   private String updated_at;

    public Ticket(Integer id, Integer cinema_id, Integer profile_id, Integer user_id, String movie_date, String movie_time, Double ticket_price, Integer ticket_count, Double ticket_discount, Integer status, String nameTM, String nameRU, String short_descTM, String short_descRU, String descriptionTM, String descriptionRU, String fullname, String phone_number, ArrayList<Image> images, String created_at, String updated_at) {
        this.id = id;
        this.cinema_id = cinema_id;
        this.profile_id = profile_id;
        this.user_id = user_id;
        this.movie_date = movie_date;
        this.movie_time = movie_time;
        this.ticket_price = ticket_price;
        this.ticket_count = ticket_count;
        this.ticket_discount = ticket_discount;
        this.status = status;
        this.nameTM = nameTM;
        this.nameRU = nameRU;
        this.short_descTM = short_descTM;
        this.short_descRU = short_descRU;
        this.descriptionTM = descriptionTM;
        this.descriptionRU = descriptionRU;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.images = images;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(Integer cinema_id) {
        this.cinema_id = cinema_id;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_time() {
        return movie_time;
    }

    public void setMovie_time(String movie_time) {
        this.movie_time = movie_time;
    }

    public Double getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(Double ticket_price) {
        this.ticket_price = ticket_price;
    }

    public Integer getTicket_count() {
        return ticket_count;
    }

    public void setTicket_count(Integer ticket_count) {
        this.ticket_count = ticket_count;
    }

    public Double getTicket_discount() {
        return ticket_discount;
    }

    public void setTicket_discount(Double ticket_discount) {
        this.ticket_discount = ticket_discount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNameTM() {
        return nameTM;
    }

    public void setNameTM(String nameTM) {
        this.nameTM = nameTM;
    }

    public String getNameRU() {
        return nameRU;
    }

    public void setNameRU(String nameRU) {
        this.nameRU = nameRU;
    }

    public String getShort_descTM() {
        return short_descTM;
    }

    public void setShort_descTM(String short_descTM) {
        this.short_descTM = short_descTM;
    }

    public String getShort_descRU() {
        return short_descRU;
    }

    public void setShort_descRU(String short_descRU) {
        this.short_descRU = short_descRU;
    }

    public String getDescriptionTM() {
        return descriptionTM;
    }

    public void setDescriptionTM(String descriptionTM) {
        this.descriptionTM = descriptionTM;
    }

    public String getDescriptionRU() {
        return descriptionRU;
    }

    public void setDescriptionRU(String descriptionRU) {
        this.descriptionRU = descriptionRU;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
