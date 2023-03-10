package com.proj.togedutch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // SpringApplication.run으로 내장 WAS를 실행
        // 내장 WAS : 별도로 외부에 WAS를 두지 않고 AP가 실행할 때 내부에서 WAS를 실행하는 것
        // 내장 WAS 사용 -> 항상 서버에 톰캣을 설치할 필요 X + 스프링 부트로 만들어진 JAR 파일로 실행
        // 장점 : 언제 어디서나 같은 환경에서 스프링 부트를 배포 O
        SpringApplication.run(Main.class, args);
    }
}