package com.android.ticketadmin.Model.Ticket;

public class Image {
    private Integer id;
    private String small_image;
    private String large_image;

    public Image(Integer id, String small_image, String large_image) {
        this.id = id;
        this.small_image = small_image;
        this.large_image = large_image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getLarge_image() {
        return large_image;
    }

    public void setLarge_image(String large_image) {
        this.large_image = large_image;
    }
}
