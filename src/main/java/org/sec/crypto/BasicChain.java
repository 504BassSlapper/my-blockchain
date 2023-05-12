package org.sec.crypto;

import org.codehaus.jackson.map.ObjectMapper;
import org.sec.crypto.models.Block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BasicChain {
    private static List<Block> basicBlockChain = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Block firstBlock = new Block("0", "First block");
        System.out.println(firstBlock.getData() + " : " + firstBlock.getHash());
        Block secondBlock = new Block(firstBlock.getHash(), "Second block");
        System.out.println(secondBlock.getData() + " : " + secondBlock.getHash());
        Block thirdBlock = new Block(secondBlock.getHash(), "Third Block");
        System.out.println(thirdBlock.getData() + " : " + thirdBlock.getHash());
        basicBlockChain = List.of(firstBlock, secondBlock, thirdBlock);
        ObjectMapper om = new ObjectMapper();
        String basicBlockChainJson = om.writeValueAsString(basicBlockChain);
        System.out.println(basicBlockChainJson);
        thirdBlock.minedBlock(5);
    }

    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        for (int i = 1; i < basicBlockChain.size(); i++) {
            currentBlock = basicBlockChain.get(i);
            previousBlock = basicBlockChain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return Boolean.FALSE;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
