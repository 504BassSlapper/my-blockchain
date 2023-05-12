package org.sec.crypto;

import org.codehaus.jackson.map.ObjectMapper;
import org.sec.crypto.models.Block;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BasicChain {
    public static final int CHAIN_COMPLEXITY = 5;
    private static List<Block> basicBlockChain = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Block firstBlock = new Block("0", "First block");
        firstBlock.minedBlock(CHAIN_COMPLEXITY);

        Block secondBlock = new Block(firstBlock.getHash(), "Second block");
        secondBlock.minedBlock(CHAIN_COMPLEXITY);

        Block thirdBlock = new Block(secondBlock.getHash(), "Third Block");
        thirdBlock.minedBlock(CHAIN_COMPLEXITY);

        System.out.println(firstBlock.getData() + " mined : " + firstBlock.getHash());
        System.out.println(secondBlock.getData() + " mined : " + secondBlock.getHash());
        System.out.println(thirdBlock.getData() + " minde : " + thirdBlock.getHash());

        basicBlockChain = List.of(firstBlock, secondBlock, thirdBlock);
        ObjectMapper om = new ObjectMapper();
        String basicBlockChainJson = om.writerWithDefaultPrettyPrinter().writeValueAsString(basicBlockChain);
        System.out.println("Block chain : ");
        System.out.println(basicBlockChainJson);

    }

    public Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[CHAIN_COMPLEXITY]).replace('\0', '0');

        for (int i = 1; i < basicBlockChain.size(); i++) {
            currentBlock = basicBlockChain.get(i);
            previousBlock = basicBlockChain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                return false;
            }
            if (!currentBlock.getHash().substring(0, CHAIN_COMPLEXITY).equals(hashTarget)) {
                System.out.println("the block : " + currentBlock.getData() + " has not been mined yet");
                return false;
            }
        }
        return Boolean.TRUE;
    }
}
