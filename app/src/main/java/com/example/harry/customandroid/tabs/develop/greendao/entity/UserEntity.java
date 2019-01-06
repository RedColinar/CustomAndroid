package com.example.harry.customandroid.tabs.develop.greendao.entity;

import com.example.harry.customandroid.tabs.develop.greendao.UUID2BytesConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author panqiang
 */
@Entity(nameInDb = "User")
public class UserEntity {

    @Id
    private Long id;

    @Index(unique = true)
    @Convert(converter = UUID2BytesConverter.class, columnType = byte[].class)
    private UUID pkUser;

    @Property(nameInDb = "Name")
    private String name;

    private boolean married;

    @Generated(hash = 1463275509)
    public UserEntity(Long id, UUID pkUser, String name, boolean married) {
        this.id = id;
        this.pkUser = pkUser;
        this.name = name;
        this.married = married;
    }

    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPkUser() {
        return this.pkUser;
    }

    public void setPkUser(UUID pkUser) {
        this.pkUser = pkUser;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getMarried() {
        return this.married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }
}
