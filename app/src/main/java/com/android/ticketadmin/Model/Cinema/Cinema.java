package com.android.ticketadmin.Model.Cinema;

public class Cinema {
    private Integer id;
    private String nameTM;
    private String nameRU;

    public Cinema(Integer id, String nameTM, String nameRU) {
        this.id = id;
        this.nameTM = nameTM;
        this.nameRU = nameRU;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
