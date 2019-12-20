package cn.china.web;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableDiscoveryClient//此启动类当controller使用
@RestController
@Configuration//为了实现负载均衡
public class WebApplication {

    @Autowired
    RestTemplate rest;

    @Autowired
    LoadBalancerClient loadBalancerClient;
   @Bean
   @LoadBalanced//负载均衡
    public RestTemplate getRest() {
       return new RestTemplate();
   }

   @Bean//负载均衡随机规则,随机调用集群服务
   public IRule ribbonRule(){
       return  new RandomRule();
   }

    @RequestMapping(value="/getinfo",produces = "application/json;charset=UTF-8")
    public Student getInfo(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"/getInfo";
//        System.out.println(serviceInstance.getHost());
//        System.out.println(serviceInstance.getPort());
          System.out.println(url);
        //路径:restful接口
        Student student = rest.getForObject("http://provider/getInfo", Student.class);
        return student;
    }

    @RequestMapping(value="/insert",produces = "application/json;charset=UTF-8")
    public int insert(){
//        ServiceInstance serviceInstance = loadBalancerClient.choose("provider");
//        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"/getInfo";
//        return rest.getForObject(url,Integer.class);
        return rest.getForObject("http://provider/insert", Integer.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
