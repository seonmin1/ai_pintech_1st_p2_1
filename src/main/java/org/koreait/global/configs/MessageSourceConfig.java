package org.koreait.global.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Message 설정
 */
@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.addBasenames("messages.commons", "messages.validations", "messages.errors", "messages.pokemon");
        ms.setDefaultEncoding("UTF-8");
        ms.setUseCodeAsDefaultMessage(true); // 문구가 없을 땐 코드 형태로 출력

        return ms;
    }
}
