package com.yihenchat.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_user") // 直接与数据库表映射
@Data
public class User {

    @TableId // 数据库表主键
    private Long userId;

    private String password;

    private String nickName;

    private String img;

}
