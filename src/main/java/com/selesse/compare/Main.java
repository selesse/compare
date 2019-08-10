package com.selesse.compare;

import com.selesse.compare.filesystem.DirectoryRoot;

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

        System.out.println("Hello world" + firstRoot);
        System.out.println("Hello world" + secondRoot);
    }
}
