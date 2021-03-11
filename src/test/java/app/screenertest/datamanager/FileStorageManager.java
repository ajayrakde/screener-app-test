package app.screenertest.datamanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileStorageManager {
	Path path;
	
	
	private List<File> getAllFilesListFromPath(String ext){
				 
        if(!ext.startsWith(".")) {
        	ext = "."+ext;
        }
        final String type = ext;
		try {
            List<File> files = Files.list(Paths.get(this.path.toString()))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(type))
                    .map(Path::toFile)
                    .collect(Collectors.toList());;
            return files;
            
        } catch (IOException e) {
            // Error while reading the directory
        }
		return null;
	}
	public List<File> getAllFilesListFromPath(Path path, String ext){
		this.path=path;
		return this.getAllFilesListFromPath(ext);
	}
	public List<File> getAllFilesListFromPath(String path, String ext){
		this.path=Paths.get(path);
		return this.getAllFilesListFromPath(ext);
	}
	
	
	
	public int getCountOfFilesFromPath(String ext) {
		try {
		List<File> files = getAllFilesListFromPath(ext);
		return files.size();
		}
		catch(Exception e){
			return 0;
		}		
	}
	public int getCountOfFilesFromPath(Path path, String ext) {
		this.path=path;
		return this.getCountOfFilesFromPath(ext);
	}
	public int getCountOfFilesFromPath(String path, String ext) {
		this.path=Paths.get(path);
		return this.getCountOfFilesFromPath(ext);
	}
	
	
	
	
}
