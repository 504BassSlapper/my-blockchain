package org.sec.crypto.models;

import lombok.Data;
import org.sec.crypto.utils.StringUtil;

import java.util.Date;

@Data
public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timestamp;
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
                + timestamp+ nonce
                + data);

    }

    /**
     * new char[complexity] creates a new char array of length complexity.
     * new String(...) creates a new String object using the contents of the char array as the initial value.
     * replace("\0","0") replaces all occurrences of the null character (\0) in the string with the digit 0.
     *
     * Since the newly created char array is filled with null characters,
     * the replace method replaces all of them with the digit 0,
     * resulting in a string consisting of complexity number of zeros.
     *
     * @param complexity Integer
     */
    public void minedBlock(int complexity) {
        String target = new String(new char[complexity]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, complexity).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined  : " + hash);
    }
}
