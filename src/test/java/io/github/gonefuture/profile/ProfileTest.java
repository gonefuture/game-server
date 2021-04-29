package io.github.gonefuture.profile;



import io.github.gonefuture.profile.profileType.database.DataBaseProKey;
import io.github.gonefuture.profile.profileType.database.DataBaseProfile;

import java.io.PrintStream;
import java.util.stream.IntStream;

/**
 * 性能统计模块测试
 *
 * @author gonefuture
 * @version 2021/04/15 12:26
 */
public class ProfileTest {

    public static void main(String[] args) {

        // 模拟数据库提交次数
        IntStream.range(1,20).forEach(
                i -> {
                    DataBaseProfile.DB_PRO.createRow(ProfileTest.class.getCanonicalName())
                            .add1(DataBaseProKey.submitCount).submit();
                }
        );

        DataBaseProfile.DB_PRO.printAndRest(new PrintStream(System.out),false);

    }


}
