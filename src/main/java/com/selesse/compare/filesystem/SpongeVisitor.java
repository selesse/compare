package com.selesse.compare.filesystem;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SpongeVisitor extends SimpleFileVisitor<Path> {
    private DirectoryRoot directoryRoot;

    SpongeVisitor() {
        this.directoryRoot = new DirectoryRoot();
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        directoryRoot.addDirectory(dir);
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        directoryRoot.addFile(file);
        return super.visitFile(file, attrs);
    }

    DirectoryRoot getFileSystem() {
        return directoryRoot;
    }
}
