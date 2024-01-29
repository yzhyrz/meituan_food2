package com.hyles.shuimen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("管理人员")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    // 数据库表中对应id_number
    private String idNumber;

    private Integer status;

    /**
     * public enum FieldFill {
     *     DEFAULT(0, "默认不处理"),
     *     INSERT(1, "插入填充字段"),
     *     UPDATE(2, "更新填充字段"),
     *     INSERT_UPDATE(3, "插入和更新填充字段");
     *
     *     ...
     * }
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
