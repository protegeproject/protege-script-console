/*
 * Created on Sep 14, 2004
 */

/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is the Scripting Tab for Protege.
 *
 * The Initial Developer of the Original Code is
 * Olivier Dameron (dameron@smi.stanford.edu).
 * Portions created by the Initial Developer are Copyright (C) 2004
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package script;

import java.io.PipedOutputStream;
import java.io.PrintStream;


/**
 * 
 * Provides a PrintStream for writing on a ScriptConsole.
 * Useful for redirecting System.out to the script console.
 * 
 * @author Olivier Dameron (dameron@smi.stanford.edu)
 *
 */
public class OutputBuffer extends PrintStream {

	protected ScriptConsole console = null;

	/**
	 * 
	 */
	public OutputBuffer(ScriptConsole scriptconsole) {
		super(new PipedOutputStream());
		console = scriptconsole;
	}

	public void write(byte[] b, int off, int len){
		console.print(new String(b, off, len));
	}
	
	public void write(byte b){
		console.print(new String("" + b));
	}
	
	public void flush(){
		//console.flush();
		//console.print("\n");
		super.flush();
	}
	
	public void print(char c){
		console.print("" + c);
	}
	
	public void print(char[] s){
		console.print(s);
	}
	
	public void print(Object obj){
		console.print(obj);
	}
	
	public void print(String text){
		console.print(text);
	}
	
	public void println(Object obj){
		console.println(obj);
	}
	
	public void println(String text){
		console.println(text);
	}
	
	public void println(char[] s){
		console.println(s);
	}
	
}
