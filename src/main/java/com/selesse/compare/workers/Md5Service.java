package com.selesse.compare.workers;

import com.selesse.compare.filesystem.DirectoryRoot;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Md5Service {
    private final DirectoryRoot directoryRoot;

    public Md5Service(DirectoryRoot directoryRoot) {
        this.directoryRoot = directoryRoot;
    }

    public List<Md5Result> getMd5Results() {
        try {
            List<Md5Worker> workers = new ArrayList<>();
            for (Path file : directoryRoot.getFiles()) {
                workers.add(new Md5Worker(file));
            }

            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Future<Md5Result>> futureMd5Results = executorService.invokeAll(workers, 1, TimeUnit.HOURS);

            List<Md5Result> md5Results = new ArrayList<>();
            for (Future<Md5Result> md5ResultFuture : futureMd5Results) {
                Md5Result md5Result = md5ResultFuture.get();
                md5Results.add(md5Result);
            }

            executorService.shutdown();

            return md5Results;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
