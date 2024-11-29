package com.project1.HotelManagement.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync(proxyTargetClass = true)  // Bật sử dụng CGLIB Proxy
public class AsyncConfig {
}
