/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.pi;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EditAttributesHandlerDialog
    extends TitleAreaDialog
{

    public EditAttributesHandlerDialog(Shell parentShell)
    {
        super(parentShell);
    }

    @Override
    protected Control createDialogArea(Composite parent)
    {
        setTitle("Dialog title");
        setMessage("Dialog message");
        getShell().setText("Dialog text");
        Composite control = (Composite)super.createDialogArea(parent);
        createTableViewer(control);

        return control;
    }

    private void createTableViewer(Composite control)
    {
        Composite composite = new Composite(control, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

        composite = new Composite(control, SWT.NONE);
        TableColumnLayout columnLayout = new TableColumnLayout();
        GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
        composite.setLayout(columnLayout);

    }

}
