package cn.zhku.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务发现启动类
 *
 * @author gonefuture
 * @version 2021/5/16 14:16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryApplication.class, args);
	}
}
