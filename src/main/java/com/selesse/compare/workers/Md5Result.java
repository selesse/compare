package com.selesse.compare.workers;

import java.nio.file.Path;

public class Md5Result {
    public final Path file;
    public final String md5;

    Md5Result(Path file, String md5) {
        this.file = file;
        this.md5 = md5;
    }
}
