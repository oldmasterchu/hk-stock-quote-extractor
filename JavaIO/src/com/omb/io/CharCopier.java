package com.omb.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class CharCopier {

	public static void copy(Reader in, Writer out) throws IOException {
		synchronized (in) {
			synchronized (out) {
				char[] buffer = new char[256];// TODO make it 256
				while (true) {
					int charsRead = in.read(buffer);
					if (charsRead == -1)
						break;
					out.write(buffer, 0, charsRead);
				}
			}
		}
	}
}
