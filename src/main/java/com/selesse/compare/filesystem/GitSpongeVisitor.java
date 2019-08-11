package com.selesse.compare.filesystem;

import com.neva.commons.gitignore.GitIgnore;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class GitSpongeVisitor extends SimpleFileVisitor<Path> {
    private DirectoryRoot directoryRoot;
    private GitIgnore gitIgnore;
    private Path folderRoot;

    GitSpongeVisitor(Path folderRoot) {
        this.directoryRoot = new DirectoryRoot();
        this.gitIgnore = new GitIgnore(folderRoot.toFile());
        this.folderRoot = folderRoot;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (!shouldIgnorePath(dir)) {
            directoryRoot.addDirectory(dir);
        }
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (!shouldIgnorePath(file)) {
            directoryRoot.addFile(file);
        }
        return super.visitFile(file, attrs);
    }

    private boolean shouldIgnorePath(Path file) {
        return isGitFile(file) || gitIgnore.isExcluded(file.toFile());
    }

    private boolean isGitFile(Path path) {
        return folderRoot.relativize(path).startsWith(".git/");
    }

    DirectoryRoot getFileSystem() {
        return directoryRoot;
    }
}
