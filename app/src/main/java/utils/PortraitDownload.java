package utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tlabs.eve.api.character.CharacterSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Boris Lachev on 12/15/2015.
 */
public class PortraitDownload {
    public void downloadImage(Context context, List<CharacterSheet> characterSheets) {
        try {
            URL url = new URL("https://image.eveonline.com/Character/"+ characterSheets.get(0).getCharacterID() + "_128.jpg");
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("portraits", Context.MODE_PRIVATE);
            File myDir = new File(directory, "folder");

            if (!myDir.exists()) {
                myDir.mkdir();
                Log.v("", "inside mkdir");
            }

            String fname = characterSheets.get(0).getCharacterName();
            File file = new File(myDir, fname);
            Log.d("file===========path", "" + file);
            if (file.exists())
                file.delete();
            URLConnection ucon = url.openConnection();
            InputStream inputStream = null;
            HttpURLConnection httpConn = (HttpURLConnection)ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            inputStream = httpConn.getInputStream();

            FileOutputStream fos = new FileOutputStream(file);
            int totalSize = httpConn.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLenght = 0;
            while ((bufferLenght = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLenght);
                downloadedSize += bufferLenght;
                Log.i("Progress: ", "downloaded size:" + downloadedSize + "totalSize:" + totalSize);
            }

            fos.close();
            Log.d("Image saved", "Saved to SDCard");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Bitmap viewimage(Context context, String name) throws FileNotFoundException {
        String path = name+".png";
        ContextWrapper cw = new ContextWrapper(context);

        //path to /data/data/yourapp/app_data/dirName
        File directory = cw.getDir("files", Context.MODE_PRIVATE);

        File mypath=new File(directory,"folder/"+path);

        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(mypath));

        return b;
    }
}
