package w58984.carrental;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

/**
 * Klasa uruchomieniowa
 */
@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class,args);
    }


}


//@Component
//class CustomizationPort implements WebServerFactoryCustomizer< ConfigurableServletWebServerFactory > {
//
//    @Override
//    public void customize(ConfigurableServletWebServerFactory server) {
//        server.setPort(9001);
//    }
//
//}
