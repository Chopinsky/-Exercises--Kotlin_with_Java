package com.company;

import com.sun.deploy.nativesandbox.comm.Request;
import com.sun.deploy.nativesandbox.comm.Response;

import java.math.BigInteger;

public class Factorizer {
    private final Computable<BigInteger, BigInteger[]> c =
            new Computable<BigInteger, BigInteger[]>() {
                @Override
                public BigInteger[] compute(BigInteger arg) throws InterruptedException {
                    return new BigInteger[0];
                }
            };

    private final Computable<BigInteger, BigInteger[]> cache = new Memorizer<>(c);

    public void service(Request req, Response resp) {
        try {
            BigInteger i = new BigInteger("1000");
            cache.compute(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
}
