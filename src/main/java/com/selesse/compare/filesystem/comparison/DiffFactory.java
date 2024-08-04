package com.selesse.compare.filesystem.comparison;

import com.selesse.compare.filesystem.DirectoryRoot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DiffFactory {
    public static Diff create(String[] args) throws IOException {
        if (Arrays.asList(args).contains("--type=file")) {
            List<String> directories =
                    Stream.of(args).filter(arg -> !arg.equals("--type=file")).toList();
            return new FileContentDiff(directories.stream()
                    .map(dir -> {
                        try {
                            return DirectoryRoot.fromPath(new File(dir).toPath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).toArray(DirectoryRoot[]::new));
        } else if (Arrays.asList(args).contains("--type=directory")) {
            List<String> directories =
                    Stream.of(args).filter(arg -> !arg.equals("--type=directory")).toList();
            if (directories.size() != 2) {
                throw new RuntimeException("Expected 2 directories to compare, got " + directories.size());
            }
            var firstDirectory = directories.get(0);
            var secondDirectory = directories.get(1);

            var firstPath = new File(firstDirectory).toPath();
            var secondPath = new File(secondDirectory).toPath();

            System.out.println("First directory " + LocalDateTime.now());
            var firstRoot = DirectoryRoot.fromPath(firstPath);
            System.out.println("Second directory " + LocalDateTime.now());
            var secondRoot = DirectoryRoot.fromPath(secondPath);
            System.out.println("Done" + LocalDateTime.now());

            System.out.printf("%s => %d directories, %d files%n", firstPath, firstRoot.getDirectories().size(), firstRoot.getFiles().size());
            System.out.printf("%s => %d directories, %d files%n", secondPath, secondRoot.getDirectories().size(), secondRoot.getFiles().size());
            return new StructuredFolderDiff(firstRoot, secondRoot);
        } else {
            throw new RuntimeException("Unspecified diff type, try --type=file or --type=directory");
        }
    }
}
