package com.selesse.compare.filesystem.comparison;

import com.selesse.compare.filesystem.DirectoryRoot;

public class DiffFactory {
    public static Diff create(DirectoryRoot rootA, DirectoryRoot rootB) {
        if (System.getenv().getOrDefault("DIFF_STRATEGY", "").equals("file")) {
            return new FileContentDiff(rootA, rootB);
        }
        return new StructuredFolderDiff(rootA, rootB);
    }
}
