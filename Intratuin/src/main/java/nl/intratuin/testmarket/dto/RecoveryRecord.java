package nl.intratuin.testmarket.dto;

import nl.intratuin.testmarket.Settings;

import java.time.LocalDateTime;
import java.time.temporal.*;

/**
 * Created by Иван on 07.04.2016.
 */
public class RecoveryRecord {
    private String email;
    private String link;
    private LocalDateTime dt;

    public RecoveryRecord(String email, String link, LocalDateTime dt){
        this.email=email;
        this.link=link;
        this.dt=dt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = dt;
    }

    public boolean isDeprecated(){
        long timePassed=ChronoUnit.SECONDS.between(dt,LocalDateTime.now());
        if(timePassed>= Settings.getDeprecateTimeout())
            return true;
        return false;
    }
}
