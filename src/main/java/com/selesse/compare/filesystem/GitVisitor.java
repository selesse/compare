package com.selesse.compare.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class GitVisitor implements CompareFileSystemVisitor {
    private final DirectoryRoot directory;

    public GitVisitor(Path root) {
        this.directory = new DirectoryRoot(root);
    }

    @Override
    public DirectoryRoot getFileSystem() {
        String listOfFiles = executeCommandAndGetOutput(new String[] {"git", "ls-files"});
        var sequences = listOfFiles.split("\n");
        Arrays.stream(sequences)
                .map(x -> directory.getFolderRoot().resolve(x))
                .forEach(directory::addFile);
        return directory;
    }

    private String executeCommandAndGetOutput(String[] command) {
        String result = null;
        try (InputStream inputStream = Runtime.getRuntime().exec(command, null, directory.getFolderRoot().toFile()).getInputStream();
             Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
            result = s.hasNext() ? s.next() : null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
