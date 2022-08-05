package com.android.ticketadmin.Model.Ticket;

import java.util.ArrayList;

public class GetTicket {
    private Boolean error;
    private ArrayList<Ticket> body=new ArrayList<>();

    public GetTicket(Boolean error, ArrayList<Ticket> body) {
        this.error = error;
        this.body = body;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public ArrayList<Ticket> getBody() {
        return body;
    }

    public void setBody(ArrayList<Ticket> body) {
        this.body = body;
    }
}
