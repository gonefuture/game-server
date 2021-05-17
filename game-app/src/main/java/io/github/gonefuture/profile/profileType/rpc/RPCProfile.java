package io.github.gonefuture.profile.profileType.rpc;

import io.github.gonefuture.profile.BaseProfileType;
import io.github.gonefuture.profile.IProColumn;
import io.github.gonefuture.profile.ProPrinter;
import io.github.gonefuture.profile.Profile;

/**
 * PRC性能统计
 *
 * @author gonefuture
 * @version 2021/4/27 18:01
 */
public abstract class RPCProfile<PK, Column extends Enum<Column> & IProColumn> extends BaseProfileType<PK, Column> {
    public static final RPCProfile<String, RPCProKey> RPC = new RPCProfile<String, RPCProKey>("RPC性能统计", RPCProKey.class) {
        @Override
        protected ProPrinter<String, RPCProKey> createPrinter(Profile<String, RPCProKey> profile) {
            return RPCProKey.printStyle1(profile);
        }
    };


    public RPCProfile(String desc, Class<Column> columnClz) {
        super(desc, columnClz);
    }
}
