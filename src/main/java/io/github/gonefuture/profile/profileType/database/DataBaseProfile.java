package io.github.gonefuture.profile.profileType.database;

import io.github.gonefuture.profile.BaseProfileType;
import io.github.gonefuture.profile.IProColumn;
import io.github.gonefuture.profile.ProPrinter;
import io.github.gonefuture.profile.Profile;

/**
 * db性能统计
 *
 * @author gonefuture
 * @version 2021/04/15 11:49
 */
public abstract class DataBaseProfile<PK, Column extends Enum<Column> & IProColumn>  extends BaseProfileType<PK, Column> {

    public DataBaseProfile(String desc, Class<Column> columnClz) {
        super(desc, columnClz);
    }

    public static final DataBaseProfile<String, DataBaseProKey> DB_PRO = new DataBaseProfile<String, DataBaseProKey>("DB性能信息", DataBaseProKey.class) {
        @Override
        protected ProPrinter<String, DataBaseProKey> createPrinter(Profile<String, DataBaseProKey> profile) {
            return DataBaseProKey.printStyle1(profile);
        }
    };


}
