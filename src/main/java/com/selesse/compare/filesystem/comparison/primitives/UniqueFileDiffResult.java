package com.selesse.compare.filesystem.comparison.primitives;

import java.nio.file.Path;
import java.util.List;

public class UniqueFileDiffResult {
    public final String md5Result;
    public final List<Path> paths;

    public UniqueFileDiffResult(String md5Result, List<Path> paths) {
        this.md5Result = md5Result;
        this.paths = paths;
    }
}
