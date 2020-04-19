package com.zhaowenjie.mygp.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "mygp_user")
@ApiModel
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户的id")
    int id;
    @ApiModelProperty(value = "用户名",required = true)
    String username;
    @ApiModelProperty(value = "用户密码",required = true)
    String password;
}
