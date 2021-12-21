package com.ling.springshiro.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ky2fe
 * @since 2021-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer  id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


}
