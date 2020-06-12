package com.selesse.compare.filesystem.comparison;

import com.selesse.compare.filesystem.DirectoryRoot;
import com.selesse.compare.filesystem.comparison.primitives.Md5DiffResult;
import com.selesse.compare.workers.Md5Result;
import com.selesse.compare.workers.Md5Service;

import java.nio.file.Path;
import java.util.*;

/**
 * Outputs a structured folder diff based on two folder inputs.
 *
 * Specifically, this diff format assumes that relative paths are important.
 */
public class StructuredFolderDiff implements Diff {
    private final DirectoryRoot rootA;
    private final DirectoryRoot rootB;

    StructuredFolderDiff(DirectoryRoot rootA, DirectoryRoot rootB) {
        this.rootA = rootA;
        this.rootB = rootB;
    }

    public void printOutput() {
        var aResults = new Md5Service(rootA).getMd5Results();
        var bResults = new Md5Service(rootB).getMd5Results();

        Map<Path, Md5DiffResult> diffMap = computeDiffMap(aResults, bResults);

        Set<Md5DiffResult> filesInCommon = new TreeSet<>(Comparator.comparing(Md5DiffResult::getRelativePath));
        Set<Md5DiffResult> filesOnlyInA = new TreeSet<>(Comparator.comparing(Md5DiffResult::getRelativePath));
        Set<Md5DiffResult> filesOnlyInB = new TreeSet<>(Comparator.comparing(Md5DiffResult::getRelativePath));

        for (Md5DiffResult diffResult : diffMap.values()) {
            if (diffResult.getAResult() != null && diffResult.getBResult() != null) {
                filesInCommon.add(diffResult);
            }
            if (diffResult.getAResult() != null && diffResult.getBResult() == null) {
                filesOnlyInA.add(diffResult);
            }
            if (diffResult.getAResult() == null && diffResult.getBResult() != null) {
                filesOnlyInB.add(diffResult);
            }
        }

        if (!filesOnlyInA.isEmpty()) {
            System.out.println("Only in " + rootA.getFolderRoot().getFileName());
            for (Md5DiffResult diffResult : filesOnlyInA) {
                System.out.println("  A - " + diffResult.getAResult().file);
            }
        }
        if (!filesOnlyInB.isEmpty()) {
            System.out.println("Only in " + rootB.getFolderRoot().getFileName());
            for (Md5DiffResult diffResult : filesOnlyInB) {
                System.out.println("  B - " + diffResult.getBResult().file);
            }
        }
        if (filesInCommon.stream().anyMatch(x -> !x.getAResult().md5.equals(x.getBResult().md5))) {
            System.out.println("Files in both folders that differ:");
            for (Md5DiffResult diffResult : filesInCommon) {
                Md5Result aResult = diffResult.getAResult();
                Md5Result bResult = diffResult.getBResult();

                if (!aResult.md5.equals(bResult.md5)) {
                    Path relativePath = rootA.getFolderRoot().relativize(aResult.file);

                    System.out.println("  " + relativePath);
                }
            }
        }
    }

    private Map<Path, Md5DiffResult> computeDiffMap(List<Md5Result> aResults, List<Md5Result> bResults) {
        Map<Path, Md5DiffResult> results = new HashMap<>();
        for (Md5Result firstResult : aResults) {
            Path relativePath = rootA.getFolderRoot().relativize(firstResult.file);
            Md5DiffResult diffResult = new Md5DiffResult(relativePath);
            diffResult.setAResult(firstResult);
            results.put(relativePath, diffResult);
        }
        for (Md5Result secondResult : bResults) {
            Path relativePath = rootB.getFolderRoot().relativize(secondResult.file);
            Md5DiffResult diffResult = results.getOrDefault(relativePath, new Md5DiffResult(relativePath));
            diffResult.setBResult(secondResult);
            results.put(relativePath, diffResult);
        }
        return results;
    }
}
