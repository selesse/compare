package com.selesse.compare;

import com.selesse.compare.filesystem.DirectoryRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new RuntimeException("Expected 2 arguments, got " + args.length);
        }

        String firstDirectory = args[0];
        String secondDirectory = args[1];

        Path firstPath = new File(firstDirectory).toPath();
        Path secondPath = new File(secondDirectory).toPath();

        DirectoryRoot firstRoot = DirectoryRoot.fromPath(firstPath);
        DirectoryRoot secondRoot = DirectoryRoot.fromPath(secondPath);

        System.out.println("Hello world" + firstRoot);
        System.out.println("Hello world" + secondRoot);
    }
}
