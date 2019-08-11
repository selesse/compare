package com.selesse.compare.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DirectoryRoot {
    private List<Path> directories;
    private List<Path> files;

    DirectoryRoot() {
        this.directories = new ArrayList<>();
        this.files = new ArrayList<>();
    }

    void addDirectory(Path directory) {
        directories.add(directory);
    }

    void addFile(Path file) {
        files.add(file);
    }

    public List<Path> getDirectories() {
        return directories;
    }

    public List<Path> getFiles() {
        return files;
    }

    public static DirectoryRoot fromPath(Path path) throws IOException {
        GitSpongeVisitor filesystemVisitor = new GitSpongeVisitor(path);
        Files.walkFileTree(path, filesystemVisitor);
        return filesystemVisitor.getFileSystem();
    }
}
