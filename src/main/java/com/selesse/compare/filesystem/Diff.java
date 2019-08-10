package com.selesse.compare.filesystem;

public class Diff {
    private final DirectoryRoot root1;
    private final DirectoryRoot root2;

    public Diff(DirectoryRoot directoryRoot1, DirectoryRoot directoryRoot2) {
        this.root1 = directoryRoot1;
        this.root2 = directoryRoot2;
    }
}
