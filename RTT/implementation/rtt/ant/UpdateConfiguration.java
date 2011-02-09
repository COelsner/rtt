/**
 * <copyright>
 *
 * This program and the accompanying materials are made available under the
 * terms of the BSD 3-clause license which accompanies this distribution.
 *
 * </copyright>
 */
package rtt.ant;

import java.io.*;
import java.util.*;

import org.apache.tools.ant.*;

import rtt.managing.Manager;

/**
 * 
 * This task is intended for adding and changing configurations in an existing archive.<br>
 * For every configuration, several classpath entries can be added.<br>
 * <br>
 * Example:<br>
 * <br>
 * <pre>
 * &lt;updateConfigurations
 * 	archive="path-to-archive"&gt;
 * 	&lt;configuration
 * 		name="configuration-name"
 * 		lexer="lexer-class"
 * 		parser="parser-class"&gt;
 * 		&lt;classpathElement path="classpath-directory"/&gt;
 * 	&lt;/configuration&gt;
 * &lt;/updateConfigurations&gt;
 *</pre>
 * 
 * 
 * 
 * @author Peter Mucha
 * 
 */
public class UpdateConfiguration extends Task {
	public static class Configuration extends Task {
		private String name = null;
		private String lexer = null;
		private String parser = null;
		private boolean owrite = false;
		private List<ClassPathElement> cpes =
			new LinkedList<ClassPathElement>();
		
		public Configuration() {}
		
		public boolean isOverwrite() {return owrite;}
		public void setOverwrite(boolean owrite) {this.owrite = owrite;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		public String getLexer() {return lexer;}
		public void setLexer(String lexer) {this.lexer = lexer;}
		public String getParser() {return parser;}
		public void setParser(String parser) {this.parser = parser;}
		public List<ClassPathElement> getClassPathElements() {return cpes;}
		public void addClassPathElement(ClassPathElement cpe) {
			this.cpes.add(cpe);
		}
	}
	
	public static class ClassPathElement extends Task {
		private String path = "";
		public ClassPathElement() {}
		public String getPath() {return path;}
		public void setPath(String path) {this.path = path;}
	}
	
	private String archive = null;
	private String log = null;
	private List<Configuration> configs = new LinkedList<Configuration>();
	
	public void addConfiguration(Configuration c) {this.configs.add(c);}
	public String getArchive() {return archive;}
	public void setArchive(String archive) {this.archive = archive;}
	public void setLog(String log) {this.log = log;}
	
	public void execute() throws BuildException {
		Manager m = new Manager(new File(archive), true);
		try {
			m.loadArchive();
			System.out.println("Archive loaded");
			
			
			
			for (Configuration c : configs) 
			{
				List<String> cpEntries = new LinkedList<String>(); 
				for (ClassPathElement cpe : c.getClassPathElements())
					cpEntries.add(cpe.getPath());
				
				m.createConfiguration(
						c.getLexer(),
						c.getParser(),
						c.getName(),
						false,
						c.isOverwrite(),
						cpEntries) ;
			}
			
			System.out.println("Save archive to: "+ archive);
			m.saveArchive(new File(archive));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		} finally {
			if (log != null) {
				try {
					m.exportLog(new File(log));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
