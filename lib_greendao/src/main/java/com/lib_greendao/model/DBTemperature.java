package com.lib_greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * DBTemperature: laomao
 * Date: 2019-09-06
 * Time: 10-43
 */
@Entity
public class DBTemperature {
    @Id(autoincrement = true)
    private Long id;
    private Long time;
    private String temperature;
    @Generated(hash = 1072692910)
    public DBTemperature(Long id, Long time, String temperature) {
        this.id = id;
        this.time = time;
        this.temperature = temperature;
    }
    @Generated(hash = 1745504018)
    public DBTemperature() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public String getTemperature() {
        return this.temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
