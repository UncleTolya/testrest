package ru.tolymhlv.testrest.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ElementCollection(targetClass = Visit.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_visits", joinColumns = @JoinColumn(name = "user_id"))
//    private Set<Visit> visits;

    private String ip;
    private String userAgent;

    public User(final String ip, final String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Set<Visit> getVisits() {
//        return visits;
//    }
//
//    public void setVisits(Set<Visit> visits) {
//        this.visits = visits;
//    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
