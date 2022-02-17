package com.alkemy.ong.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "organizations")
public class OrganizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idOrganization;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column
    private String address;

    @Column
    private Integer phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String welcomeText;

    @Column
    private String aboutUsText;

    //Constructors
    public OrganizationEntity() {
    }

    public OrganizationEntity(long idOrganization, String name, String image, String address,
                              Integer phone, String email, String welcomeText, String aboutUsText) {
        this.idOrganization = idOrganization;
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.welcomeText = welcomeText;
        this.aboutUsText = aboutUsText;
    }

    //Getters and Setters

    public long getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(long idOrganization) {
        this.idOrganization = idOrganization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getAboutUsText() {
        return aboutUsText;
    }

    public void setAboutUsText(String aboutUsText) {
        this.aboutUsText = aboutUsText;
    }


}
