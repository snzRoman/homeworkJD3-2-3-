import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        GameProgress savedGames1 = new GameProgress(10, 5, 2, 2.0);
        GameProgress savedGames2 = new GameProgress(5, 10, 5, 12.0);
        GameProgress savedGames3 = new GameProgress(1, 11, 11, 42.3);

        saveGame("E://Games/savegames/saveGames1", savedGames1);
        saveGame("E://Games/savegames/saveGames2", savedGames2);
        saveGame("E://Games/savegames/saveGames3", savedGames3);

        List<String> savedGames = new ArrayList<>();
        File dir = new File("E://Games/savegames");
        for (File file : dir.listFiles()) {
            if (file.isFile()){
                savedGames.add(file.getAbsolutePath());
            }
        }

        zipFiles("E://Games/zipFiles.zip", savedGames);

        openZip("E://Games/zipFiles.zip", "E://Games/savegames/");

        System.out.println(openProgress("E://Games/savegames/saveGames1"));

    }

    public static void saveGame(String path, GameProgress gameProgress){
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(gameProgress);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String path, List<String> files){
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))){
            for (String file : files) {
                FileInputStream fis = new FileInputStream(file);
                ZipEntry entry = new ZipEntry(new File(file).getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                zout.write(buffer);
                zout.closeEntry();

                File zipedFile = new File(file);
                zipedFile.delete();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void openZip(String zipPath, String folderPath){
        try(ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))){
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null){
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(folderPath + name);
                for(int c = zis.read(); c != -1; c = zis.read()){
                    fout.write(c);
                }
                fout.flush();
                fout.close();
                zis.closeEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String savedGame){
        GameProgress gameProgress = null;
        try(FileInputStream fis = new FileInputStream(savedGame);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            gameProgress = (GameProgress) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }

}
