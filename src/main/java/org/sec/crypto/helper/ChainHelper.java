package org.sec.crypto.helper;

import org.sec.crypto.models.Block;

import java.util.List;

public class ChainHelper {
    public static boolean isChainValid(Integer networkDifficulty , List<Block> basicBlockChain ) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[networkDifficulty]).replace('\0', '0');

        for (int i = 1; i < basicBlockChain.size(); i++) {
            currentBlock = basicBlockChain.get(i);
            previousBlock = basicBlockChain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("#Current Hashes not equal");
                return false;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.getHash().substring(0, networkDifficulty).equals(hashTarget)) {
                System.out.println("the block : " + currentBlock.getData() + " has not been mined");
                return false;
            }
        }
        return true;
    }
}
