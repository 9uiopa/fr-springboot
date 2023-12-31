package com.uiopa.frspringboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;
    @GetMapping("/profile")
    public String profile(){
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real","real1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains) // profiles의 요소 중 realProfiles에 속한 것만 필터링
                .findAny()
                .orElse(defaultProfile);
    }
}
