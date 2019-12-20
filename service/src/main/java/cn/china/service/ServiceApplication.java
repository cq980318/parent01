package cn.china.service;

import entity.Student;
import mapper.StudentMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableEurekaClient
@ComponentScan("service")
@MapperScan("mapper")
@RestController
public class ServiceApplication {
    @Autowired
    StudentMapper sm;
    @RequestMapping("/insert")
    public int insert() {
        Student student = new Student(123,"xz",28);
        return  sm.insert(student);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
