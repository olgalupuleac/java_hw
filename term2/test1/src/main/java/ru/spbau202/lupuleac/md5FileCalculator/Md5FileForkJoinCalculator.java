package ru.spbau202.lupuleac.md5FileCalculator;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Md5FileForkJoinCalculator extends Md5FileCalculator {
    @Override
    public byte[] calculateHash(@NotNull File file) {
        return new ForkJoinPool().invoke(new RecursiveMd5CalculatorTask(file));
    }

    private static class RecursiveMd5CalculatorTask extends RecursiveTask<byte[]> {
        private File file;

        private RecursiveMd5CalculatorTask(File file){
            this.file = file;
        }

        /**
         * The main computation performed by this task.
         *
         * @return the result of the computation
         */
        @Override
        protected byte[] compute() {
            MessageDigest messageDigest = null;
            try{
                messageDigest = MessageDigest.getInstance("MD5");
                List<RecursiveMd5CalculatorTask> subTasks = new ArrayList<>();
                if(file.isDirectory()){
                    messageDigest.update(file.getName().getBytes());
                    for(File subFile : file.listFiles()){
                        RecursiveMd5CalculatorTask task = new RecursiveMd5CalculatorTask(subFile);
                        task.fork();
                        subTasks.add(task);
                    }
                    for(RecursiveMd5CalculatorTask task : subTasks){
                        messageDigest.update(task.join());
                    }
                }
                else {
                    messageDigest.update(hashFromFile(file, messageDigest));
                }
            }
            catch (Exception ignored){}
            assert messageDigest != null;
            return messageDigest.digest();
        }
    }
}
