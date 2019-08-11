package com.selesse.compare;

import com.selesse.compare.filesystem.DirectoryRoot;
import com.selesse.compare.workers.Md5Result;
import com.selesse.compare.workers.Md5Service;
import com.selesse.compare.workers.Md5Worker;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new RuntimeException("Expected 2 arguments, got " + args.length);
        }

        var firstDirectory = args[0];
        var secondDirectory = args[1];

        var firstPath = new File(firstDirectory).toPath();
        var secondPath = new File(secondDirectory).toPath();

        var firstRoot = DirectoryRoot.fromPath(firstPath);
        var secondRoot = DirectoryRoot.fromPath(secondPath);

        var firstResults = new Md5Service(firstRoot).getMd5Results();
        var secondResults = new Md5Service(secondRoot).getMd5Results();

        for (Md5Result firstResult : firstResults) {
            if (!firstResult.md5.equals(Md5Worker.EMPTY_FILE_SHA)) {
                System.out.printf("%s => %s\n", firstResult.file, firstResult.md5);
            }
        }
        for (Md5Result secondResult : secondResults) {
            if (!secondResult.md5.equals(Md5Worker.EMPTY_FILE_SHA)) {
                System.out.printf("%s => %s\n", secondResult.file, secondResult.md5);
            }
        }

        System.out.printf("%s => %d directories, %d files%n", firstPath, firstRoot.getDirectories().size(), firstRoot.getFiles().size());
        System.out.printf("%s => %d directories, %d files%n", secondPath, secondRoot.getDirectories().size(), secondRoot.getFiles().size());
    }
}
