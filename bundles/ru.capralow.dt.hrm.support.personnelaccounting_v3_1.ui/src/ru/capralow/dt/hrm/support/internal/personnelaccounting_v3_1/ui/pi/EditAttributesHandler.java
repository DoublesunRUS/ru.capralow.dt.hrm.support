/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.pi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com._1c.g5.v8.dt.bsl.model.Module;
import com._1c.g5.v8.dt.bsl.model.StringLiteral;
import com._1c.g5.v8.dt.bsl.ui.menu.BslHandlerUtil;

import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.BslModelUtils;
import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.PersonnelAccountingUiPlugin;

public class EditAttributesHandler
    extends AbstractHandler
{

    @Override
    public final Object execute(ExecutionEvent event) throws ExecutionException
    {
        IWorkbenchPart part = HandlerUtil.getActivePart(event);
        XtextEditor target = BslHandlerUtil.extractXtextEditor(part);
        if (target != null)
            this.execute(target);

        return null;
    }

    public void execute(XtextEditor target)
    {
        IFileEditorInput input = (IFileEditorInput)target.getEditorInput();
        IFile file = input.getFile();
        if (file == null)
            return;
        IProject project = file.getProject();
        if (project == null)
            return;

        IXtextDocument doc = target.getDocument();

        ITextViewer viewer = BslHandlerUtil.getTextViewer(target);

        int insertPosition = viewer.getSelectedRange().x;
        int insertLength = 0;

        StringLiteral stringLiteral = getStringLiteral(doc, viewer);
        if (stringLiteral != null)
        {
            ICompositeNode node = NodeModelUtils.findActualNodeFor(stringLiteral);
            insertPosition = node.getTotalOffset() + 2;
            insertLength = Integer.max(node.getLength() - 2, 0);
        }

        String currentAttributes = ""; //$NON-NLS-1$
        try
        {
            currentAttributes = doc.get(insertPosition, insertLength);
        }
        catch (BadLocationException e)
        {
            PersonnelAccountingUiPlugin.log(PersonnelAccountingUiPlugin.createErrorStatus(e.getMessage(), e));
        }

        Map<String, String> attributes = PiAttributesList.getSelectableAttributes("СоздатьВТКадровыеДанныеСотрудников"); //$NON-NLS-1$
        List<String> attributesList = new ArrayList<>(attributes.keySet());
        Collections.sort(attributesList);

        Shell shell = target.getShell();
        ListSelectionDialog dialog = new ListSelectionDialog(shell, attributesList, new ArrayContentProvider(),
            new LabelProvider(), "Dialog message");
        dialog.setInitialSelections(currentAttributes.split("[,]")); //$NON-NLS-1$
        dialog.setTitle("Dialog title");
        if (dialog.open() != Window.OK)
            return;

        List<String> selectedAttributesList = new ArrayList<>();
        for (Object resultElement : dialog.getResult())
            selectedAttributesList.add((String)resultElement);

        String newAttributes = String.join(",", selectedAttributesList); //$NON-NLS-1$

        try
        {
            doc.replace(insertPosition, insertLength, newAttributes);
        }
        catch (BadLocationException e)
        {
            PersonnelAccountingUiPlugin.log(PersonnelAccountingUiPlugin.createErrorStatus(e.getMessage(), e));
        }
    }

    private StringLiteral getStringLiteral(IXtextDocument doc, ITextViewer viewer)
    {
        return doc.readOnly(new IUnitOfWork<StringLiteral, XtextResource>()
        {
            @Override
            public StringLiteral exec(XtextResource res) throws Exception
            {
                if (res.getContents() != null && !res.getContents().isEmpty())
                {
                    EObject obj = res.getContents().get(0);
                    if (obj instanceof Module)
                    {
                        Module module = (Module)obj;

                        StringLiteral stringLiteral =
                            BslModelUtils.getNearestStringLiteral(module, viewer.getSelectedRange().x);
                        if (stringLiteral != null)
                            return stringLiteral;
                    }
                }
                return null;
            }
        });
    }
}
