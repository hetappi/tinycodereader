package tcr.util;

import java.io.IOException;

import com.nttdocomo.fs.File;
import com.nttdocomo.fs.Folder;
import com.nttdocomo.io.FileEntity;
import com.nttdocomo.io.PrintWriter;

public class FileUtil {
	private FileUtil() {
	}

	public static boolean exists(Folder folder, String fileName) {
		if (folder == null || fileName == null) {
			throw new NullPointerException();
		}

		try {
			folder.getFile(fileName);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public static void createNewFile(Folder folder, String fileName,
			String contents) throws IOException {
		if (folder == null || fileName == null) {
			throw new NullPointerException();
		}

		if (FileUtil.exists(folder, fileName)) {
			folder.getFile(fileName).delete();
		}

		File file;
		FileEntity entry = null;
		PrintWriter writer = null;
		try {
			file = folder.createFile(fileName);
			entry = file.open(File.MODE_WRITE_ONLY);
			writer = new PrintWriter(entry.openOutputStream());
			writer.print(contents);
			writer.flush();

		} finally {
			if (writer != null) {
				writer.close();
			}
			if (entry != null) {
				try {
					entry.close();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}
}
