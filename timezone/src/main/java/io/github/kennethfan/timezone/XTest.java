package io.github.kennethfan.timezone;

public class XTest {
    public static void main(String[] args) {
            String sql = "<script>"
                    + "\nSELECT * FROM"
                    + "\n  ("
                    + "\n    SELECT *, ROW_NUMBER() OVER (PARTITION BY employeeId ORDER BY time DESC) AS row_num"
                    + "\n    FROM " + "fdsajlkfdjsalk"
                    + "\n    WHERE"
                    + "\n      agencyId = #{agencyId}"
                    + "\n      AND employeeId IN "
                    + "\n      <foreach collection=\"employeeIds\" item=\"employeeId\" separator=\",\" open=\"(\", close=\")\">"
                    + "\n        #{employeeId}"
                    + "\n      </foreach>"
                    + "\n  )"
                    + "\nWHERE"
                    + "\n  row_num = 1"
                    + "\n</script>";
            System.out.println(sql);
    }
}
