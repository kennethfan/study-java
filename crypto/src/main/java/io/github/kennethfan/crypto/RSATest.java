package io.github.kennethfan.crypto;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Random;

@Slf4j
public class RSATest {

    public static void main(String[] args) throws Exception {
        // 1. 生成RSA密钥对
        // 获取指定算法的密钥对生成器
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器（指定密钥长度, 使用默认的安全随机数源）
        gen.initialize(512);
        // 随机生成一对密钥（包含公钥和私钥）
        KeyPair keyPair = gen.generateKeyPair();
        // 获取 公钥 和 私钥
        RSAPublicKey pubKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey priKey = (RSAPrivateKey) keyPair.getPrivate();
        // 获得公私钥 和 公共模数
        BigInteger E = pubKey.getPublicExponent();
        BigInteger D = priKey.getPrivateExponent();
        BigInteger N = pubKey.getModulus();
        System.out.println("E:" + E);
        System.out.println("D:" + D);
        System.out.println("N:" + N);

        BigInteger m1 = BigInteger.valueOf(new Random().nextInt(Integer.MAX_VALUE));
        BigInteger m2 = BigInteger.valueOf(new Random().nextInt(Integer.MAX_VALUE));

        // 加密
        BigInteger C1 = m1.modPow(E, N);
        BigInteger C2 = m2.modPow(E, N);
        // 密文相乘
        BigInteger C = C1.multiply(C2).mod(N);
        // 解密
        BigInteger Mc = C.modPow(D, N);
        // 验证
        BigInteger val = m1.multiply(m2);
        System.out.println("原始数据数据m1:" + m1 + "，m2:" + m2);
        System.out.println("m1加密后数据C1:" + C1);
        System.out.println("m2加密后数据C2:" + C2);
        System.out.println("C:" + C);
        System.out.println("Mc:" + Mc);
        System.out.println("Mc:" + val);
        System.out.println("public:" + keyPair.getPublic().toString());
        System.out.println("private:" + keyPair.getPrivate().toString());
    }
}
