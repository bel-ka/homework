package ru.otus.java;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HelloOtus {

    public static void main(String[] args) {
        HashFunction hash = Hashing.sha256();
        HashCode hashCode = hash.newHasher()
                .putString(HelloOtus.class.getName(), Charsets.UTF_8)
                .hash();
        System.out.println(hashCode);
    }
}
