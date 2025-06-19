package issues7.ex1;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader implements Runnable {
    private String fileURL;
    private String saveDir;
    private final Runnable onSuccess;

    public FileDownloader(String fileURL, String saveDir, Runnable onSuccess) {
        this.fileURL = fileURL;
        this.saveDir = saveDir;
        this.onSuccess = onSuccess;
    }

    @Override
    public void run() {
        downloadFile(fileURL, saveDir);
    }

    private void downloadFile(String fileURL, String saveDir) {
        try (InputStream in = new BufferedInputStream(new URL(fileURL).openStream());
             FileOutputStream out = new FileOutputStream(saveDir)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }

            if (onSuccess != null) onSuccess.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
