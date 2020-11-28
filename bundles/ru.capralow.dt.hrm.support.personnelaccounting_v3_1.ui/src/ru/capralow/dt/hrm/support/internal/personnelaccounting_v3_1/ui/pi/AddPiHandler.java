/**
 * Copyright (c) 2020, Alexander Kapralov
 */
package ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.pi;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com._1c.g5.v8.dt.bsl.model.Module;
import com._1c.g5.v8.dt.bsl.model.Statement;
import com._1c.g5.v8.dt.bsl.ui.menu.BslHandlerUtil;
import com._1c.g5.v8.dt.core.platform.IV8Project;
import com._1c.g5.v8.dt.core.platform.IV8ProjectManager;
import com._1c.g5.v8.dt.metadata.mdclass.Configuration;
import com._1c.g5.v8.dt.metadata.mdclass.ScriptVariant;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import com.google.inject.Inject;

import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.BslModelUtils;
import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.MdUtils;
import ru.capralow.dt.hrm.support.internal.personnelaccounting_v3_1.ui.PersonnelAccountingUiPlugin;

public class AddPiHandler
    extends AbstractHandler
{
    private static final String TEMPLATE_NAME_RU = "templateRU.txt"; //$NON-NLS-1$
    private static String templateContentRu;

    private static final String TEMPLATE_NAME_EN = "templateEN.txt"; //$NON-NLS-1$
    private static String templateContentEn;

    static
    {
        templateContentRu = readContents(getFileInputSupplier(TEMPLATE_NAME_RU), TEMPLATE_NAME_RU);
        templateContentEn = readContents(getFileInputSupplier(TEMPLATE_NAME_EN), TEMPLATE_NAME_EN);
    }

    private static CharSource getFileInputSupplier(String partName)
    {
        return Resources.asCharSource(AddPiHandler.class.getResource("/resources/" + partName), //$NON-NLS-1$
            StandardCharsets.UTF_8);
    }

    private static String readContents(CharSource source, String path)
    {
        try (Reader reader = source.openBufferedStream())
        {
            return CharStreams.toString(reader);
        }
        catch (IOException | NullPointerException e)
        {
            return ""; //$NON-NLS-1$
        }
    }

    @Inject
    private IV8ProjectManager projectManager;

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

        IV8Project v8Project = projectManager.getProject(project);
        Configuration configuration = MdUtils.getConfigurationForProject(v8Project);

        String templateContent;
        if (configuration.getScriptVariant().equals(ScriptVariant.RUSSIAN))
            templateContent = templateContentRu;
        else
            templateContent = templateContentEn;

        ITextViewer viewer = BslHandlerUtil.getTextViewer(target);

        try
        {
            int insertPosition = getInsertHandlerPosition(doc, viewer);
            doc.replace(insertPosition, 0, Strings.newLine() + Strings.newLine() + templateContent);

        }
        catch (BadLocationException e)
        {
            PersonnelAccountingUiPlugin.log(PersonnelAccountingUiPlugin.createErrorStatus(e.getMessage(), e));

        }
    }

    private int getInsertHandlerPosition(IXtextDocument doc, ITextViewer viewer)
    {
        return doc.readOnly(new IUnitOfWork<Integer, XtextResource>()
        {
            @Override
            public Integer exec(XtextResource res) throws Exception
            {
                if (res.getContents() != null && !res.getContents().isEmpty())
                {
                    EObject obj = res.getContents().get(0);
                    if (obj instanceof Module)
                    {
                        Module module = (Module)obj;

                        Statement statement = BslModelUtils.getNearestStatement(module, viewer.getSelectedRange().x);
                        if (statement != null)
                            return NodeModelUtils.findActualNodeFor(statement).getTotalOffset();
                    }
                }
                return viewer.getSelectedRange().x;
            }
        });
    }
}
