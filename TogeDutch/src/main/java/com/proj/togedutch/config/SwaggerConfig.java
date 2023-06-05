package com.proj.togedutch.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  // 스프링 실행 시 설정파일을 읽어들이기 위한 Annotation
@EnableSwagger2 // Swagger2를 사용한다는 Annotation
public class SwaggerConfig {
    //swagger 설정
    public Docket getDocket(String groupName, Predicate<String> predicate) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.proj.togedutch"))
                .paths(predicate)
                .apis(RequestHandlerSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("가치더치 Spring Boot REST API")
                .version("1.0.0")
                .description("JPA 버전 SWAGGER API 입니다.")
                .build();
    }

    /* Path에 파라미터 들어가면 regex 말고 ant 쓰기 */

    @Bean
    public Docket postAPI() {
        return getDocket("공고", Predicates.or(
                PathSelectors.regex("/post.*")));
    }

    @Bean
    public Docket matchingAPI() {
        return getDocket("매칭", Predicates.or(
                PathSelectors.regex("/matching.*")));
    }

    @Bean
    public Docket likePostAPI() {
        return getDocket("관심목록", Predicates.or(
                PathSelectors.ant("/user/{userIdx}/likePost.*")));
    }

    @Bean
    public Docket chatRoomAPI() {
        return getDocket("채팅방", Predicates.or(
                PathSelectors.regex("/chatRoom.*")));
    }

    @Bean
    public Docket chatAPI() {
        return getDocket("채팅", Predicates.or(
                PathSelectors.ant("/chatRoom/{chatRoom_id}.*")));
    }
}
