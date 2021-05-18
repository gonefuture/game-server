package io.github.gonefuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 游戏应用启动类
 *
 * @author gonefuture
 * @version 2021/5/16 14:16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GameApplication    {
    public static void main(String[] args) {
        SpringApplication.run(GameApplication.class, args);
    }
}
