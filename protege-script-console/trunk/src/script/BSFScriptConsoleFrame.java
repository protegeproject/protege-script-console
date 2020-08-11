/*
 * Created on Sep 17, 2004
 *
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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * 
 * A frame for standalone BSH scripting console. 
 * 
 * Todo:
 * - add constructor with window title
 * - add constructor with default language
 * - add methods for adding/removing a language
 * - add method for selecting a language
 * 
 * @author dameron
 *
 */
public class BSFScriptConsoleFrame extends JFrame {
	
	private ScriptConsole consolePane;
	private RadioSelectorPane languagePane;

	/**
	 * Deafult constructor.
	 * 
	 */
	public BSFScriptConsoleFrame() {
		super("BSF Scripting console");
		JFrame.setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		consolePane = new ScriptConsole();
		consolePane.setScriptLanguage("jython");
		consolePane.setPreferredSize(new Dimension(500,350));
        
		LanguageSelectionListener languageListener = new LanguageSelectionListener(consolePane);
		
		languagePane = new RadioSelectorPane(languageListener);
		languagePane.addButton("Python", "jython", true);
		languagePane.addButton("Perl", "perl");
		languagePane.addButton("BeanShell", "beanshell");
		languagePane.addButton("Groovy", "groovy");
		languagePane.addButton("Ruby", "jruby");
        
		getContentPane().add(languagePane, BorderLayout.PAGE_START);
		getContentPane().add(consolePane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		BSFScriptConsoleFrame mainFrame = new BSFScriptConsoleFrame();
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}
