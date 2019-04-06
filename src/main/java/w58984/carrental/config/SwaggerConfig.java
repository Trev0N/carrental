package w58984.carrental.config;

import com.google.common.base.Optional;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("w58984.carrental.controller"))
                .build()
                .apiInfo(apiInfo(model))
                .protocols(protocols())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .genericModelSubstitutes(Optional.class);
    }
    private ApiInfo apiInfo(Model model) {
        return new ApiInfoBuilder()
                .title("Car rental api")
                .description("Car rental rest api")
                .termsOfServiceUrl("github")
                .license("MIT")
                .licenseUrl("https://en.wikipedia.org/wiki/MIT_License")
                .version(model.getParent().getVersion())
                .build();
    }

    private Set<String> protocols() {
        Set<String> protocols = new HashSet<>();
        protocols.add("http");
        return protocols;
    }



    private List<? extends SecurityScheme> securitySchemes() {
        return Collections.<SecurityScheme>singletonList(new ApiKey("Authorization", "Authorization", "header"));
    }
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder().forPaths(PathSelectors.any()).securityReferences(securityReferences()).build());
    }

    private List<SecurityReference> securityReferences() {
        return Collections.singletonList(SecurityReference.builder().reference("Authorization").scopes(new AuthorizationScope[0]).build());
    }
}
