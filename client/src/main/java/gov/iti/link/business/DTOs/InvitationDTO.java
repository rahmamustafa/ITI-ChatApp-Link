package gov.iti.link.business.DTOs;

import java.io.Serializable;
import java.sql.Date;

public class InvitationDTO implements Serializable {
    private int id;
    private String fromPhone;
    private String toPhone;
    private Date date;

    public int getId() {
        return id;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public String getToPhone() {
        return toPhone;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public InvitationDTO(int id, String fromPhone, String toPhone, Date date) {
        this.id = id;
        this.fromPhone = fromPhone;
        this.toPhone = toPhone;
        this.date = date;
    }

    public InvitationDTO() {

    }

    @Override
    public String toString() {
        return "InvitationDTO [id=" + id + ", fromPhone=" + fromPhone + ", toPhone=" + toPhone + ", date=" + date + "]";
    }

    
}
