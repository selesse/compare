package com.selesse.compare.filesystem.comparison;

import com.selesse.compare.filesystem.DirectoryRoot;
import com.selesse.compare.filesystem.comparison.primitives.UniqueFileDiffResult;
import com.selesse.compare.workers.Md5Result;
import com.selesse.compare.workers.Md5Service;

import java.nio.file.Path;
import java.util.*;

/**
 * Diff strategy that only considers file contents.
 */
public class FileContentDiff implements Diff {
    private final DirectoryRoot[] roots;

    public FileContentDiff(DirectoryRoot... roots) {
        this.roots = roots;
    }

    public void printOutput() {
        List<Md5Result> md5Results = new ArrayList<>();
        for (DirectoryRoot root : roots) {
            md5Results.addAll(new Md5Service(root).getMd5Results());
        }

        List<UniqueFileDiffResult> diffResults = computeDiffs(md5Results);

        for (UniqueFileDiffResult uniqueFile : diffResults) {
            if (uniqueFile.paths.size() > 1) {
                System.out.println("Same file contents found in multiple locations:");
                for (Path associatedPath : uniqueFile.paths) {
                    System.out.println("  " + associatedPath);
                }
                System.out.println("");
            }
        }
    }

    private List<UniqueFileDiffResult> computeDiffs(List<Md5Result> allResults) {
        Map<String, List<Path>> results = new HashMap<>();
        for (Md5Result result : allResults) {
            String md5 = result.md5;
            List<Path> paths = results.getOrDefault(md5, new ArrayList<>());
            paths.add(result.file);
            results.put(md5, paths);
        }
        return mapToSortedDiffResult(results);
    }

    private List<UniqueFileDiffResult> mapToSortedDiffResult(Map<String, List<Path>> results) {
        List<UniqueFileDiffResult> uniqueFileDiffResults = new ArrayList<>();
        for (Map.Entry<String, List<Path>> stringListEntry : results.entrySet()) {
            String md5Result = stringListEntry.getKey();
            List<Path> paths = stringListEntry.getValue();
            paths.sort(Path::compareTo);

            uniqueFileDiffResults.add(new UniqueFileDiffResult(md5Result, paths));
        }
        uniqueFileDiffResults.sort(Comparator.comparing(o -> o.paths.get(0)));
        return uniqueFileDiffResults;
    }
}
