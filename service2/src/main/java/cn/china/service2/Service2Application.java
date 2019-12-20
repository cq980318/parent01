package cn.china.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.StudentService;

@SpringBootApplication
//(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableEurekaClient
@ComponentScan("service")

@RestController
public class Service2Application {

    @Autowired
    StudentService ss;

    @RequestMapping("/getInfo")



    public static void main(String[] args) {
        SpringApplication.run(Service2Application.class, args);
    }

}
