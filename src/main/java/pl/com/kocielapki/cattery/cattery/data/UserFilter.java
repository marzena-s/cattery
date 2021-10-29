package pl.com.kocielapki.cattery.cattery.data;

import javax.persistence.*;
import java.time.LocalDateTime;


public class UserFilter {
    private String login;


    public UserFilter() {
    }

    public UserFilter(String login) {
        this.login = login;
    }


    public String getLogin() {
        return login;
    }
}