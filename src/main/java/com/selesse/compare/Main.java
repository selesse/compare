package com.selesse.compare;

import com.selesse.compare.filesystem.DirectoryRoot;
import com.selesse.compare.filesystem.comparison.Diff;
import com.selesse.compare.filesystem.comparison.DiffFactory;
import com.selesse.compare.filesystem.comparison.FileContentDiff;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new RuntimeException("Expected arguments, got " + args.length);
        }
        // TODO: smarter argument input
        if (args.length == 1) {
            Diff diff = new FileContentDiff(DirectoryRoot.fromPath(new File(args[0]).toPath()));
            diff.printOutput();
        } else {
            var firstDirectory = args[0];
            var secondDirectory = args[1];

            var firstPath = new File(firstDirectory).toPath();
            var secondPath = new File(secondDirectory).toPath();

            var firstRoot = DirectoryRoot.fromPath(firstPath);
            var secondRoot = DirectoryRoot.fromPath(secondPath);

            System.out.printf("%s => %d directories, %d files%n", firstPath, firstRoot.getDirectories().size(), firstRoot.getFiles().size());
            System.out.printf("%s => %d directories, %d files%n", secondPath, secondRoot.getDirectories().size(), secondRoot.getFiles().size());
            var diff = DiffFactory.create(firstRoot, secondRoot);
            diff.printOutput();
        }
    }
}
