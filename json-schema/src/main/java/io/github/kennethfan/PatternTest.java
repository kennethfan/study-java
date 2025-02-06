package io.github.kennethfan;

import java.util.regex.Pattern;

public class PatternTest {

    public static void main(String[] args) {

        String message = "proxy: HTTP:107.174.183.43:33080:VRrS0y:1c439fc3503c19ed6d0234ffba237481d4061ff7230940f2fcfb05cf165f2d47, source: HTTP:107.174.183.43:33080:VRrS0y:1c439fc3503c19ed6d0234ffba237481d4061ff7230940f2fcfb05cf165f2d47, method: DELETE, userId: 1332076636733475, httpCode: 403, url: https://onlyfans.com/api2/v2/messages/4145935665755, response: {\"error\":{\"code\":0,\"message\":\"Can't delete message\"}}";
        String pattern = ".+https:\\/\\/onlyfans\\.com\\/api2\\/v2\\/messages\\/\\d+.+";
        pattern = ".*?url:\\s*(https:\\/\\/onlyfans\\.com\\/api2\\/v2[^,\\s]*).*?response:\\s*\\{\"error\":\\{\"code\":\\d+,\"message\":\"Can't delete message\"\\}\\}";
        System.out.println(Pattern.matches(pattern, message));

        message = "proxy: HTTP:192.210.221.144:33080:YJ0RC3:41d58648a31d4dd7a422df5b4b795579b237a52b93a1bb771b9c176da4c66dcf, source: HTTP:192.210.221.144:33080:YJ0RC3:41d58648a31d4dd7a422df5b4b795579b237a52b93a1bb771b9c176da4c66dcf, method: DELETE, userId: 1357726830034967, httpCode: 404, url: https://onlyfans.com/api2/v2/lists/1115885968/users/317466987, response: {\"error\":{\"code\":0,\"message\":\"User not found in user list\"}}";
        pattern = ".+https:\\/\\/onlyfans\\.com\\/api2\\/v2\\/lists\\/\\d+\\/users\\/\\d+.+";
        pattern = ".*?url:\\s*(https:\\/\\/onlyfans\\.com\\/api2\\/v2[^,\\s]*).*?response:\\s*\\{\"error\":\\{\"code\":\\d+,\"message\":\"User not found in user list\"\\}\\}";
        System.out.println(Pattern.matches(pattern, message));

        message = "proxy: HTTP:107.173.45.5:33080:f4U0tD:e7aa012e7e38f50dc7e6dfb4f1436cd0998d00a7c264eaac665cbe5704fab176, source: HTTP:107.173.45.5:33080:f4U0tD:e7aa012e7e38f50dc7e6dfb4f1436cd0998d00a7c264eaac665cbe5704fab176, method: POST, userId: 1481026424275005, httpCode: 400, url: https://onlyfans.com/api2/v2/chats/396833178/messages, response: {\"error\":{\"code\":0,\"message\":\"Please allow 3 seconds.\"}}";
        pattern = ".+https:\\/\\/onlyfans\\.com\\/api2\\/v2\\/chats\\/\\d+\\/messages.+";
        pattern = ".*?url:\\s*(https:\\/\\/onlyfans\\.com\\/api2\\/v2[^,\\s]*).*?response:\\s*\\{\"error\":\\{\"code\":\\d+,\"message\":\"Please allow 3 seconds.\"\\}\\}";
        System.out.println(Pattern.matches(pattern, message));

        message= "proxy: HTTP:107.173.211.148:33080:35BY41:f9e240ba6d92bf99eb4164206964e5b4586f1d865de228b6ec9237e320063525, source: HTTP:107.173.211.148:33080:35BY41:f9e240ba6d92bf99eb4164206964e5b4586f1d865de228b6ec9237e320063525, method: GET, userId: 1452469131280398, httpCode: 404, url: https://onlyfans.com/api2/v2/lists/1120281253, response: {\"error\":{\"code\":0,\"message\":\"User list not found\"}}";
        pattern = ".+https:\\/\\/onlyfans\\.com\\/api2\\/v2\\/lists\\/\\d+.+";
        pattern = ".*?url:\\s*(https:\\/\\/onlyfans\\.com\\/api2\\/v2[^,\\s]*).*?response:\\s*\\{\"error\":\\{\"code\":\\d+,\"message\":\"User list not found\"\\}\\}";
        System.out.println(Pattern.matches(pattern, message));

        message = "proxy: HTTP:198.46.165.205:33080:x0MJzo:da19b9d25a1d441aa6d81205edef4993862d1f0b080fc5855ca0be70080a966d, source: service-autofollow-a-76bd6c6754-swl4c, method: POST, userId: 1530378711662622, httpCode: 400, url: https://onlyfans.com/api2/v2/users/312069336/subscribe, response: {\"error\":{\"code\":0,\"message\":\"Too many requests.\"}}";
        pattern = ".+https:\\/\\/onlyfans\\.com\\/api2\\/v2\\/users\\/\\d+\\/subscribe.+";
        pattern = ".*?url:\\s*(https:\\/\\/onlyfans\\.com\\/api2\\/v2[^,\\s]*).*?response:\\s*\\{\"error\":\\{\"code\":\\d+,\"message\":\"Too many requests.\"\\}\\}";
        System.out.println(Pattern.matches(pattern, message));
    }


}
