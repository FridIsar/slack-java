package io.slack.front;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 16/12/2020
 */
import io.slack.utils.Outils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ImageFilter extends FileFilter {
	private String extension=null;
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		extension = Outils.getExtension(f);
		if (extension != null) {
			if ( extension.equals("jpeg") ||
				extension.equals("jpg")  ||
				extension.equals("png")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return null;
	}
}