/**
 * <copyright>
 *
 * This program and the accompanying materials are made available under the
 * terms of the BSD 3-clause license which accompanies this distribution.
 *
 * </copyright>
 */
package rtt.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import rtt.managing.Manager;

/**
 * Task for exporting a log and its stylesheet from an archive<br>
 * <br>
 * Example: <br>
 *  
 *  <pre>
 *  &lt;exportLog archive="path-to-archive" destination="directory/"&gt;
 *  </pre>
 * 
 * 
 * 
 * @author Peter Mucha
 * 
 */
public class ExportLog extends Task {
	private String archive = null;
	private String dest = null;
	
	public String getArchive() {return archive;}
	public void setArchive(String archive) {this.archive = archive;}
	public String getDestination() {return dest;}
	public void setDestination(String dest) {this.dest = dest;}
	
	public void execute() throws BuildException {
		if (archive == null || archive.length() == 0)
			throw new BuildException("Parameter <path> is required!");
		if (dest == null)
			dest = ".";
		log("Loading Archive: <" + archive + ">");
		Manager m = new Manager(new File(archive), true);
		try {
			m.loadArchive();
			log("Archive loaded");
			m.exportLog(new File(dest));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e.toString());
		}
	}
}
