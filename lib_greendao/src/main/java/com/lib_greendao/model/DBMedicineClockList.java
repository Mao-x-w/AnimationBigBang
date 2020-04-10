package com.lib_greendao.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * User: laomao
 * Date: 2019-09-17
 * Time: 15-20
 */
@Entity
public class DBMedicineClockList {
    @Id(autoincrement = true)
    private Long id;
    private long time;
    private String title;
    private String repeat;
    private String json;
    private boolean isOpen;
    @Generated(hash = 1232610860)
    public DBMedicineClockList(Long id, long time, String title, String repeat,
            String json, boolean isOpen) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.repeat = repeat;
        this.json = json;
        this.isOpen = isOpen;
    }
    @Generated(hash = 1043978018)
    public DBMedicineClockList() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getRepeat() {
        return this.repeat;
    }
    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public boolean getIsOpen() {
        return this.isOpen;
    }
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
}
