package org.sec.crypto.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.sec.crypto.BasicChain;
import org.sec.crypto.models.Block;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class StringUtil {
    public static String applySha256(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
            // will contain the hash in hexadecimal
            var hexHash = new StringBuilder();
            IntStream.range(0, hash.length).forEach(i -> {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexHash.append("0");
                hexHash.append(hex);
            });
            // returns a 64 caracters String
            return hexHash.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }

    public static String toJson(List<Block> basicBlockChain) throws IOException {
        ObjectMapper om = new ObjectMapper();
        return om.writerWithDefaultPrettyPrinter().writeValueAsString(basicBlockChain);
    }
}
