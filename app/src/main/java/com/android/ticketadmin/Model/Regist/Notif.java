package com.android.ticketadmin.Model.Regist;

public class Notif {
    private Integer admin_id;
    private String notif_token;

    public Notif(Integer admin_id, String notif_token) {
        this.admin_id = admin_id;
        this.notif_token = notif_token;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getNotif_token() {
        return notif_token;
    }

    public void setNotif_token(String notif_token) {
        this.notif_token = notif_token;
    }
}
