package com.osrc.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * startup
 * @author tom
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OsrcStatsCardApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsrcStatsCardApplication.class, args);
	}

}
