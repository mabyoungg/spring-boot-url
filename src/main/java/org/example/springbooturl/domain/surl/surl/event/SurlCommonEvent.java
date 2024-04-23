package org.example.springbooturl.domain.surl.surl.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springbooturl.domain.surl.surl.dto.SurlDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurlCommonEvent {
    private String eventType;
    private SurlDto surlDto;
}
