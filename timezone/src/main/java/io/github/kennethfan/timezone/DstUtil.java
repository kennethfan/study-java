package io.github.kennethfan.timezone;

import com.google.common.collect.ImmutableList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneOffsetTransitionRule;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.TimeZone;

public class DstUtil {

    public static List<ZonedDateTime> getDstBeforeAndEnd(ZoneId zoneId, int year) {
        TimeZone timeZone = TimeZone.getTimeZone(zoneId);
        if (!timeZone.useDaylightTime()) {
            return null;
        }
        // 获取当前时区的规则
        ZoneRules rules = zoneId.getRules();

        // 检查历史过渡点
        List<ZoneOffsetTransition> transitions = rules.getTransitions();
        LocalDateTime dstStart = null;
        LocalDateTime dstEnd = null;

        for (ZoneOffsetTransition transition : transitions) {
            LocalDateTime dateTimeBefore = transition.getDateTimeBefore();
            // 判断当前过渡是否与目标年份相关
            if (dateTimeBefore.getYear() == year) {
                if (transition.isGap()) {
                    dstStart = transition.getDateTimeAfter();
                } else if (transition.isOverlap()) {
                    dstEnd = transition.getDateTimeAfter();
                }
            }
        }

        // 如果历史数据中没有相关年份，则使用固定规则推断
        if (dstStart == null || dstEnd == null) {
            List<ZoneOffsetTransitionRule> rulesList = rules.getTransitionRules();
            for (ZoneOffsetTransitionRule rule : rulesList) {
                // 推断特定年份的过渡
                ZoneOffsetTransition transition = rule.createTransition(year);
                if (transition.isGap() && dstStart == null) {
                    dstStart = transition.getDateTimeAfter();
                } else if (transition.isOverlap() && dstEnd == null) {
                    dstEnd = transition.getDateTimeAfter();
                }
            }
        }

        // 输出结果
        if (dstStart != null && dstEnd != null) {
            System.out.println("In " + year + ", DST starts at: " + dstStart + " and ends at: " + dstEnd);
        } else {
            System.out.println("No DST transitions found for year " + year + " in zone " + zoneId);
        }


        return null;
//        return ImmutableList.of(dstStart.getDateTimeBefore().atZone(zoneId), dstEnd.getDateTimeBefore().atZone(zoneId));
    }


}


