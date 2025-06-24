package issues7.ex1;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader implements Runnable {
    private final String mFileUrl;
    private final String mSaveDir;
    private final Runnable mOnSuccess;

    public FileDownloader(String fileURL, String saveDir, Runnable onSuccess) {
        this.mFileUrl = fileURL;
        this.mSaveDir = saveDir;
        this.mOnSuccess = onSuccess;
    }

    @Override
    public void run() {
        downloadFile(mFileUrl, mSaveDir);
    }

    private void downloadFile(String fileUrl, String saveDir) {
        try (InputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream out = new FileOutputStream(saveDir)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }

            if (mOnSuccess != null) mOnSuccess.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
