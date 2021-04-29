package io.github.gonefuture.profile;

import io.github.gonefuture.profile.profileType.database.DataBaseProKey;
import io.github.gonefuture.profile.profileType.database.DataBaseProfile;
import io.github.gonefuture.profile.profileType.rpc.RPCProKey;
import io.github.gonefuture.profile.profileType.rpc.RPCProfile;
import lombok.SneakyThrows;

import java.io.PrintStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * PRC性能统计
 *
 * @author gonefuture
 * @version 2021/4/27 18:01
 */
public class RPCProfileTest {

    public static void main(String[] args) {
        int[] data= new int[1];

        // 模拟耗时RPC调用
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                // 沉睡一秒;
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ++data[0];
        });
        IntStream.range(1,10).forEach( i -> {
            try {
                long startTime = System.nanoTime();
                Integer result = future.get();
                long useNanoTime = System.nanoTime() - startTime;
                RPCProfile.RPC.createRow(ProfileTest.class.getCanonicalName())
                        .add1(RPCProKey.excCount)
                        .add(RPCProKey.callbackAvg, useNanoTime)
                        .add(RPCProKey.callbackMax, useNanoTime)
                        .submit();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });


        RPCProfile.RPC.printAndRest(new PrintStream(System.out),false);

    }
}
