package com.kodilla.fileintegrationtask;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

public class FileTransform {
    public String transformFile(String path){
        return getFileNameFromPath(path)+"\n";
    }

    private String getFileNameFromPath(String path) {
        File file = new File(path);
        return file.getName();
    }

}
