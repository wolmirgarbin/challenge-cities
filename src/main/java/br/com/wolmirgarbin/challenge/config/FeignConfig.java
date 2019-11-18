package br.com.wolmirgarbin.challenge.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCircuitBreaker
@EnableFeignClients(basePackages = "br.com.wolmirgarbin.challenge.consume")
@Configuration
public class FeignConfig {

    @Bean
    public Client client(CachingSpringLoadBalancerFactory cachingSpringLoadBalancerFactory, SpringClientFactory clientFactory) {
        return new LoadBalancerFeignClient(new ApacheHttpClient(), cachingSpringLoadBalancerFactory, clientFactory);
    }
}
