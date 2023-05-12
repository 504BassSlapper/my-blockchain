package org.sec.crypto;

import org.codehaus.jackson.map.ObjectMapper;
import org.sec.crypto.models.Block;
import org.sec.crypto.utils.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicChain {
    // example for casual machine processing (BTC chain up to ~ 48)
    public static final int NETWORK_DIFFICULTY = 5;
    private static List<Block> basicBlockChain = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Block firstBlock = new Block("0", "First block");
        firstBlock.minedBlock(NETWORK_DIFFICULTY);

        Block secondBlock = new Block(firstBlock.getHash(), "Second block");
        secondBlock.minedBlock(NETWORK_DIFFICULTY);

        Block thirdBlock = new Block(secondBlock.getHash(), "Third Block");
        thirdBlock.minedBlock(NETWORK_DIFFICULTY);

        basicBlockChain = List.of(firstBlock, secondBlock, thirdBlock);
        if (isChainValid()) {
            System.out.println("Chain is valid");
        } else {
            System.out.println("Chain is not valid");
        }
        String basicBlockChainJson = StringUtil.toJson(basicBlockChain);
        System.out.println("Block chain : ");
        System.out.println(basicBlockChainJson);

    }

    public static boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[NETWORK_DIFFICULTY]).replace('\0', '0');

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
            if (!currentBlock.getHash().substring(0, NETWORK_DIFFICULTY).equals(hashTarget)) {
                System.out.println("the block : " + currentBlock.getData() + " has not been mined");
                return false;
            }
        }
        return true;
    }
}
