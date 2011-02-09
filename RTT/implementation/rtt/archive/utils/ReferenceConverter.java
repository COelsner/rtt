/**
 * <copyright>
 *
 * This program and the accompanying materials are made available under the
 * terms of the BSD 3-clause license which accompanies this distribution.
 *
 * </copyright>
 */
package rtt.archive.utils;

import rtt.archive.Configuration;
import rtt.archive.Input;
import rtt.archive.LexerOutput;
import rtt.archive.ParserOutput;
import rtt.archive.Testsuit;
import rtt.managing.Archive;

/**
 * Convertes used references into file paths.<br>
 * References are used for example in the testsuite files.<br>
 * The directory structure is created by the reference converter, as it maps for
 * example input file references to the directory "input".<br>
 * 
 * @author Peter Mucha
 * 
 */
public class ReferenceConverter {
	private static ArchiveLoader currentArchiveLoader = null;
	private static Archive currentArchive;

	public static void setCurrentArchive(Archive currentArchive) {
		ReferenceConverter.currentArchive = currentArchive;
	}

	private static String defConfig = null;

	public static void setCurrentArchiveLoader(ArchiveLoader al) {
		currentArchiveLoader = al;
	}

	public static Testsuit loadTestsuitRef(String inputFile) {
		Testsuit t = null;
		try {

			t = ((Testsuit) currentArchiveLoader.unmarshal(Testsuit.class,
					inputFile));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		currentArchiveLoader.setFileFor(t, inputFile);
		return t;

	}

	public static String saveTestsuitRef(Testsuit t) {
		String fileName = currentArchiveLoader.getFileName(t);
		if (fileName == null) {
			System.err.println("cant find filename for testsuitRef");
			return null;
		}

		try {

			currentArchiveLoader.marshal(Testsuit.class, t, fileName, false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return fileName;
	}

	public static String loadDefaultConfig(String defaultConfig) {
		defConfig = defaultConfig;
		// System.out.println("Setting default config:" + defConfig);
		return defaultConfig;

	}

	public static String saveDefaultConfig(String defaultConfig) {
		return defaultConfig;
	}

	public static LexerOutput loadLexerOutputRef(String inputFile) {
		LexerOutput t = new LexerOutput();
		try {
			String cfg = currentArchiveLoader.getCurrentConfiguration();
			if (cfg == null)
				cfg = defConfig;

			Configuration curCfg = currentArchive != null ? currentArchive
					.getConfiguration(cfg) : null;

			if (!(curCfg != null && curCfg.getLexerClass() == null))
				t = ((LexerOutput) currentArchiveLoader.unmarshal(
						LexerOutput.class, "output/" + cfg + "/" + inputFile));
		} catch (Exception e) {
			// e.printStackTrace();
			// file not found because reference was generated by another
			// configuration
			// this is ok
			return null;
		}

		currentArchiveLoader.setFileFor(t, inputFile);
		return t;

	}

	public static String saveLexerOutputRef(LexerOutput t) {

		String fileName = currentArchiveLoader.getFileName(t);
		if (fileName == null) {
			System.err.println("cant find filename for LexerOutputRef");
			return null;
		}

		try {

			String cfg = currentArchiveLoader.getCurrentConfiguration();

			if (cfg == null)
				cfg = defConfig;

			Configuration curCfg = currentArchive != null ? currentArchive
					.getConfiguration(cfg) : null;

			if (curCfg != null && curCfg.getLexerClass() != null)
				currentArchiveLoader.marshal(LexerOutput.class, t, "output/"
						+ cfg + "/" + fileName, false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return fileName;
	}

	public static ParserOutput loadParserOutputRef(String inputFile) {
		ParserOutput t = new ParserOutput();
		try {
			String cfg = currentArchiveLoader.getCurrentConfiguration();
			if (cfg == null)
				cfg = defConfig;

			Configuration curCfg = currentArchive != null ? currentArchive
					.getConfiguration(cfg) : null;
			if (!(curCfg != null && curCfg.getParserClass() == null))
				t = ((ParserOutput) currentArchiveLoader.unmarshal(
						ParserOutput.class, "output/" + cfg + "/" + inputFile));
		} catch (Exception e) {
			// e.printStackTrace();
			// file not found because reference was generated by another
			// configuration
			// this is ok
			return null;
		}

		currentArchiveLoader.setFileFor(t, inputFile);
		return t;

	}

	public static String saveParserOutputRef(ParserOutput t) {
		String fileName = currentArchiveLoader.getFileName(t);
		if (fileName == null) {
			System.err.println("cant find filename for ParserOutputRef");
			return null;
		}

		try {
			String cfg = currentArchiveLoader.getCurrentConfiguration();
			if (cfg == null)
				cfg = defConfig;

			Configuration curCfg = currentArchive != null ? currentArchive
					.getConfiguration(cfg) : null;

			if (curCfg != null && curCfg.getParserClass() != null)
				currentArchiveLoader.marshal(ParserOutput.class, t, "output/"
						+ cfg + "/" + fileName, false);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return fileName;
	}

	public static Input loadInputRef(String inputFile) {
		Input t = new Input();

		try {
			t.setValue(currentArchiveLoader.loadInput("input/" + inputFile));

			// t = ((Input) currentArchiveLoader.unmarshal(Input.class,
			// inputFile));

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		currentArchiveLoader.setFileFor(t, inputFile);
		return t;

	}

	public static String saveInputRef(Input t) {
		String fileName = currentArchiveLoader.getFileName(t);
		if (fileName == null) {
			System.err.println("cant find filename for InputRef");
			return null;
		}

		try {

			// currentArchiveLoader.marshal(Input.class, t, fileName);
			currentArchiveLoader.saveInput("input/" + fileName, t.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return fileName;
	}
}
