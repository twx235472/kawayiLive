package com.example.chat.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索值
     */
    @Transient
    private String searchValue;

    /**
     * 超级管理员用户名
     */
    @Transient
    private String administratorUser;

    /**
     * 超级管理员角色名
     */
    @Transient
    private String administratorRole;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门数据权限
     */
    @Transient
    private String deptDataScope;

    /**
     * 用户数据权限
     */
    @Transient
    private String userDataScope;
    /**
     * 开始时间
     */
    @JsonIgnore
    @Transient
    private String beginTime;

    /**
     * 结束时间
     */
    @JsonIgnore
    @Transient
    private String endTime;

    /**
     * 排序字段，多个以逗号隔开
     */
    @Transient
    private String orderBy;
    /**
     * 顺序 ASC/DESC
     */
    @Transient
    private String order = "desc";
}
