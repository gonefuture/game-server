package io.github.gonefuture.module.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 登录控制器
 *
 * @author QianWeiJian
 * @version 2021/5/1811:24
 */


@RestController
public class LoginController {



    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.just("hello  world !");
    }





}
