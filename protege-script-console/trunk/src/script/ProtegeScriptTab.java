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

import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
//import java.io.PrintStream;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import edu.stanford.smi.protege.Application;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protege.widget.AbstractTabWidget;


/**
 * 
 * A tab that allows to run BSF-compliant scripts in a Prot&eacute;g&eacute; environment.
 * 
 * @author Olivier Dameron (dameron@smi.stanford.edu)
 *
 */
public class ProtegeScriptTab extends AbstractTabWidget {
	
	private static transient Logger log = Log.getLogger(ProtegeScriptTab.class);
			
	protected KnowledgeBase kb;
	protected Thread consoleThread;
	protected ScriptConsole console;
	protected RadioSelectorPane languagePane;
	
	
    /**
     * The quasi constructor of the tab widget, which creates the sub components
     * and adds them to the panel.
     */
    public void initialize() {
    	kb = (KnowledgeBase) getKnowledgeBase();

        setLabel("Script Console");
        setShortDescription("A script console tab for programmatically interacting with the knowledge base.");
        setIcon(new ImageIcon(ProtegeScriptTab.class.getResource("icon/script.png")));

        // Create a thread for the console
        console = new ScriptConsole();
        //console.setScriptLanguage("jython");
        consoleThread = new Thread(console, "ConsoleThread");
        consoleThread.start();
        
        String defaultScriptLanguage = "jython";
        try {
        	defaultScriptLanguage = System.getProperty("edu.stanford.smi.protege.script.defaultScriptLanguage", defaultScriptLanguage);
        }
        catch(Exception e) {
        	log.log(Level.SEVERE, e.getMessage(), e);
        }
        
        console.setScriptLanguage(defaultScriptLanguage);
        
        LanguageSelectionListener languageListener = new LanguageSelectionListener(console);
		
		languagePane = new RadioSelectorPane(languageListener);
		languagePane.addButton("Python", "jython", defaultScriptLanguage.equals("jython"));
		languagePane.addButton("Perl", "perl", defaultScriptLanguage.equals("perl"));
		languagePane.addButton("BeanShell", "beanshell", defaultScriptLanguage.equals("beanshell"));
		languagePane.addButton("Groovy", "groovy", defaultScriptLanguage.equals("groovy"));
		languagePane.addButton("Ruby", "jruby", defaultScriptLanguage.equals("jruby"));
        
        add(languagePane, BorderLayout.PAGE_START);
        add(console, BorderLayout.CENTER);
        
        console.declareObject("kb", kb);
        console.declareObject("scriptTab", this);
        console.declareObject("scriptConsole", console);
        
        addFocusListener(new FocusListenerOD(this));
 
        String defaultScriptContent = "";
        String defaultScriptPath = "";
        try {
        	defaultScriptPath = System.getProperty("edu.stanford.smi.protege.script.defaultScriptPath", "protegeDefaultScript.py");
        }
        catch(Exception e) {
        	log.log(Level.WARNING, e.getMessage(), e);
        }
        File defaultCommandScript = new File(defaultScriptPath);
        if (defaultCommandScript.exists()) {
        	console.executeCommand("execfile('" + defaultScriptPath + "')");
        }
    }
    
    
    public void giveFocusToConsole() {
    	console.giveFocusToConsole();
    }
    
    /**
     * Main method to simplify invokation from within a Java IDE.
     * @param args  the command line arguments
     */
    public static void main(String[] args) {
        Application.main(args);
    }

    
    @Override
    public void dispose() {
    	super.dispose();
    	console.dispose();
    	//consoleThread.
    }
}



class FocusListenerOD implements FocusListener {
	
	ProtegeScriptTab scriptTab;
	
	public FocusListenerOD(ProtegeScriptTab protegeScriptTab) {
    	scriptTab = protegeScriptTab;
    }
	
	public void focusGained(FocusEvent e) {
		scriptTab.giveFocusToConsole(); 
	}
	
	public void focusLost(FocusEvent e) {
	}
}
