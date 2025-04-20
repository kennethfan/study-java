package io.github.kennethfan.timezone;

import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.TimeZone;

@Slf4j
public class TimeZoneTest {

    public static void main(String[] args) throws Exception {
        // 从resources目录读取timezone.json
        String jsonStr = new String(Files.readAllBytes(Paths.get(TimeZoneTest.class.getClassLoader().getResource("timezone.json").toURI())), StandardCharsets.UTF_8);
        if (StringUtils.isBlank(jsonStr)) {
            return;
        }

        List<List<String>> configList = JSON.parseObject(jsonStr, new TypeReference<List<List<String>>>() {
        });
        if (CollectionUtils.isEmpty(configList)) {
            return;
        }

        for (List<String> config : configList) {
            if (!"Pacific/Auckland".equals(config.get(1))) {
                continue;
            }
            log.info("timezone.label={}", config.get(0));
            log.info("timezone.name={}", config.get(1));
            ZoneId zoneId = ZoneId.of(config.get(1));
            TimeZone timeZone = TimeZone.getTimeZone(zoneId);
            if (!timeZone.useDaylightTime()) {
                log.info("timezone.dst not support, timezone={}", zoneId);
                continue;
            }
            log.info("timezone.dst support, timezone={}", zoneId);
            ZoneRules zoneRules = zoneId.getRules();
            for (ZoneOffsetTransition transition : zoneRules.getTransitions()) {
                log.info("timezone.dst trans, timezone={}, instant={}, isGap={}, isOverlap={}", zoneId, transition.getInstant(), transition.isGap(), transition.isOverlap());
                LocalDateTime before = transition.getDateTimeBefore();
                LocalDateTime after = transition.getDateTimeAfter();
                if (transition.isGap()) {
                    log.info("timezone.dst begin, timezone={}, instant={}", zoneId, before.atZone(zoneId).toInstant());
                    log.info("timezone.dst end, timezone={}, instant={}", zoneId, after.atZone(zoneId).toInstant());
                } else if (transition.isOverlap()) {
                    log.info("timezone.dst end, timezone={}, instant={}", zoneId, before.atZone(zoneId).toInstant());
                    log.info("timezone.dst begin, timezone={}, instant={}", zoneId, after.atZone(zoneId).toInstant());
                }
            }
            break;
        }
    }
}
