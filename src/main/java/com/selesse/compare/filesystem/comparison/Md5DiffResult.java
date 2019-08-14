package com.selesse.compare.filesystem.comparison;

import com.selesse.compare.workers.Md5Result;

import java.nio.file.Path;

class Md5DiffResult {
    private Path relativePath;
    private Md5Result aResult;
    private Md5Result bResult;

    Md5DiffResult(Path relativePath) {
        this.relativePath = relativePath;
    }

    Path getRelativePath() {
        return relativePath;
    }

    void setAResult(Md5Result aResult) {
        this.aResult = aResult;
    }

    void setBResult(Md5Result bResult) {
       this.bResult = bResult;
    }

    Md5Result getAResult() {
        return aResult;
    }

    Md5Result getBResult() {
        return bResult;
    }
}
