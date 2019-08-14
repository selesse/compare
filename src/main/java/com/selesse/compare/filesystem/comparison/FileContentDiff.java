package com.selesse.compare.filesystem.comparison;

import com.selesse.compare.filesystem.DirectoryRoot;
import com.selesse.compare.workers.Md5Result;
import com.selesse.compare.workers.Md5Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, List<Path>> diffMap = computeDiffMap(md5Results);

        for (String uniqueFile : diffMap.keySet()) {
            List<Path> associatedPaths = diffMap.get(uniqueFile);
            associatedPaths.sort(Path::compareTo);
            if (associatedPaths.size() > 1) {
                System.out.println("Same file contents found in multiple locations:");
                for (Path associatedPath : associatedPaths) {
                    System.out.println("  " + associatedPath);
                }
                System.out.println("");
            }
        }
    }

    private Map<String, List<Path>> computeDiffMap(List<Md5Result> allResults) {
        Map<String, List<Path>> results = new HashMap<>();
        for (Md5Result result : allResults) {
            String md5 = result.md5;
            List<Path> paths = results.getOrDefault(md5, new ArrayList<>());
            paths.add(result.file);
            results.put(md5, paths);
        }
        return results;
    }
}
