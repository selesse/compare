package com.selesse.compare.filesystem.comparison.primitives;

import com.selesse.compare.workers.Md5Result;

import java.nio.file.Path;

public class Md5DiffResult {
    private final Path relativePath;
    private Md5Result aResult;
    private Md5Result bResult;

    public Md5DiffResult(Path relativePath) {
        this.relativePath = relativePath;
    }

    public Path getRelativePath() {
        return relativePath;
    }

    public void setAResult(Md5Result aResult) {
        this.aResult = aResult;
    }

    public void setBResult(Md5Result bResult) {
       this.bResult = bResult;
    }

    public Md5Result getAResult() {
        return aResult;
    }

    public Md5Result getBResult() {
        return bResult;
    }
}
