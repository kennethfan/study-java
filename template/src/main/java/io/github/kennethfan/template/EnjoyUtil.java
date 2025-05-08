package io.github.kennethfan.template;

import com.google.common.collect.ImmutableMap;
import com.jfinal.template.Engine;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class EnjoyUtil {

    public static void main(String[] args) {
        List<Map<String, Object>> users = Collections.singletonList(ImmutableMap.of("name", "Lucy", "age", "18"));

//        String template = "<html>\n" +
//                "<body>\n" +
//                "<h1>用户列表</h1>\n" +
//                "<ul>\n" +
//                "    #for(user : users)\n" +
//                "    <li>#(user.name) - 年龄：#(user.age)</li>\n" +
//                "    #end\n" +
//                "</ul>\n" +
//                "</body>\n" +
//                "</html>";
//        String html = Engine.use().getTemplateByString(template)
//                .renderToString(ImmutableMap.of("users", users));
        String path = EnjoyUtil.class.getClassLoader().getResource("user_list.html").getPath();
        log.info("path={}", path);
        String html = Engine.use().getTemplate(path)
                .renderToString(ImmutableMap.of("users", users));
        log.info("html={}", html);
    }
}
