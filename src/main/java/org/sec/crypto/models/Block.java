package org.sec.crypto.models;

import lombok.Getter;
import org.sec.crypto.utils.StringUtil;

import java.util.Date;

@Getter
public class Block {
    private String hash;
    private final String previousHash;
    private final String data;
    private final long timestamp;
    private int nonce;

    /**
     * Constructor
     *
     * @param previousHash previousHash
     * @param data         data
     */
    public Block(String previousHash, String data) {
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();

    }

    /**
     * applies SHA256 algorithm to an input String built from
     * previousHash, timestamp and data
     *
     * @return String hash
     */
    public String calculateHash() {
        return StringUtil.applySha256(this.previousHash
                + timestamp + nonce
                + data);

    }

    /**
     * new char[networkDifficulty] creates a new char array of length networkDifficulty.
     * new String(...) creates a new String object using the contents of the char array as the initial value.
     * replace("\0","0") replaces all occurrences of the null character (\0) in the string with the digit 0.
     * <p>
     * Since the newly created char array is filled with null characters,
     * the replace method replaces all of them with the digit 0,
     * resulting in a string consisting of networkDifficulty number of zeros.
     *
     * @param networkDifficulty Integer
     */
    public void minedBlock(int networkDifficulty) {
        String target = new String(new char[networkDifficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while (!hash.substring(0, networkDifficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined  : " + hash);
    }
}
