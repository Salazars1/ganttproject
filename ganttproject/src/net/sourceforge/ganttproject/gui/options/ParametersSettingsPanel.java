/*
GanttProject is an opensource project management tool.
Copyright (C) 2004-2011 Alexandre Thomas, GanttProject Team

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package net.sourceforge.ganttproject.gui.options;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.sourceforge.ganttproject.GanttProject;
import net.sourceforge.ganttproject.document.HttpDocument;

/**
 * Panel to edit the project properties.
 *
 * @author athomas
 */
public class ParametersSettingsPanel extends GeneralOptionPanel {

    private final JCheckBox cbAutomatic;

    private final JSpinner spLockDAV;

    private final JTextField tfTaskPrefix;

    // private final JSpinner spUndoNumber;

    private final GanttProject appli;

    public ParametersSettingsPanel(GanttProject parent) {
        super(language.getCorrectedLabel("parameters"), language
                .getText("settingsParameters"));

        appli = parent;

        // automatic launch of task properties
        JPanel autoPanel = new JPanel(new BorderLayout());
        autoPanel.add(cbAutomatic = new JCheckBox(), BorderLayout.WEST);
        autoPanel.add(new JLabel(language.getText("automaticLaunch")),
                BorderLayout.CENTER);
        vb.add(autoPanel);
        vb.add(new JPanel());

        // move on the graphic area with mouse option
        // JPanel movePanel = new JPanel(new BorderLayout());
        // movePanel.add(cbDrag = new JCheckBox(), BorderLayout.WEST);
        // movePanel.add(new JLabel(language.getText("dragTime")),
        // BorderLayout.CENTER);
        // vb.add(movePanel);
        // vb.add(new JPanel());


        // webdav time block
        JPanel webDavPanel = new JPanel(new BorderLayout());
        webDavPanel.add(spLockDAV = new JSpinner(new SpinnerNumberModel(240, 1,
                1440, 1)), BorderLayout.WEST);
        webDavPanel.add(new JLabel(language.getText("lockDAV")),
                BorderLayout.CENTER);
        vb.add(webDavPanel);
        vb.add(new JPanel());

        // task name prefix
        JPanel taskPrefixPanel = new JPanel(new BorderLayout());
        taskPrefixPanel.add(new JLabel(language.getText("taskNamePrefix")),
                BorderLayout.WEST);
        taskPrefixPanel.add(tfTaskPrefix = new JTextField(),
                BorderLayout.CENTER);
        vb.add(taskPrefixPanel);
        vb.add(new JPanel());

        // number of undoes
        // JPanel undoNumberPanel = new JPanel(new BorderLayout());
        // undoNumberPanel.add(spUndoNumber = new JSpinner(new
        // SpinnerNumberModel(50, 1, 200, 1)), BorderLayout.WEST);
        // undoNumberPanel.add (new JLabel(language.getText("undoNumber")),
        // BorderLayout.CENTER);
        // vb.add(undoNumberPanel);
        // vb.add(new JPanel());

        applyComponentOrientation(language.getComponentOrientation());
    }

    @Override
    public boolean applyChanges(boolean askForApply) {
        boolean hasChange;
        if (getAutomatic() == appli.getGanttOptions().getAutomatic()
                // && getDragTime() == appli.getOptions().getDragTime()
                && getLockDAVMinutes() == appli.getGanttOptions().getLockDAVMinutes()) {
            hasChange = false;
        } else {
            hasChange = true;
            if (!askForApply || (askForApply && askForApplyChanges())) {
                appli.getGanttOptions().setAutomatic(getAutomatic());

                // WebDAV Locking
                appli.getGanttOptions().setLockDAVMinutes(getLockDAVMinutes());
                // changeUndoNumber ();
                HttpDocument.setLockDAVMinutes(getLockDAVMinutes());
            }
        }
        return hasChange;
    }

    @Override
    public void initialize() {
        cbAutomatic.setSelected(appli.getGanttOptions().getAutomatic());
        spLockDAV.setValue(new Integer(appli.getGanttOptions().getLockDAVMinutes()));
    }

    /** @return the automatic launch value. */
    public boolean getAutomatic() {
        return cbAutomatic.isSelected();
    }

    /** @return the web dav locking value. */
    public int getLockDAVMinutes() {
        return ((Integer) spLockDAV.getValue()).intValue();
    }

    // /** @return the undo number value. */
    // public int getUndoNumber () {
    // return ((Integer) spUndoNumber.getValue()).intValue();
    // }
    // public void changeUndoNumber () {
    // appli.getOptions().setUndoNumber(getUndoNumber());
    // appli.changeUndoNumber ();
    // }

    /** @return the prefix task name. */
    public String getTaskNamePrefix() {
        String res = tfTaskPrefix.getText();
        if (res.equals(language.getText("newTask"))) {
            return null;
        }
        return res;
    }
}
