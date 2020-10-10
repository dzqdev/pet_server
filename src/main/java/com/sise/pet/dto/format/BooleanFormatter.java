package com.sise.pet.dto.format;

import org.springframework.stereotype.Component;

@Component
public class BooleanFormatter {

    public Long toLong(Boolean enable) {
        if (enable) {
            return 1L;
        } else {
            return 0L;
        }
    }

    public Boolean toBoolean(Long value) {
        if (value.equals(1L)) {
            return true;
        } else {
            return false;
        }
    }
}
