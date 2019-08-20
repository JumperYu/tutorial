package com.bradypod.cache.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/8/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application implements Serializable {
    private Long id;
    private String appId;
    private String appSecret;
    private Long systemCode;
}
