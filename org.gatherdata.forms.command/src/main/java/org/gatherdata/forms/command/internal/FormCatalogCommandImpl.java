package org.gatherdata.forms.command.internal;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.shell.Command;
import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.FormTemplateStyle;
import org.gatherdata.forms.core.model.impl.MutableFormTemplate;
import org.gatherdata.forms.core.spi.FormCatalogService;
import org.joda.time.DateTime;

import com.google.inject.Inject;

public class FormCatalogCommandImpl implements Command {

    public static final String COMMAND_NAME = "form";

    private final Pattern commandPattern = Pattern.compile("^(\\w+)\\s*(\\w+)\\s*(.*)");

    private final Pattern subCommandPattern = Pattern.compile("^(\\S+)\\s+(.*)");

    @Inject
    FormCatalogService formTemplateService;

    private int mockFormCounter = 0;

    public void execute(String argString, PrintStream out, PrintStream err) {
        Matcher argMatcher = commandPattern.matcher(argString);
        if (argMatcher.matches()) {
            String subCommand = argMatcher.group(2);
            String subArguments = argMatcher.group(3);

            if (formTemplateService == null) {
                err.println("FormTemplateService not available");
                return;
            }

            if ("help".equals(subCommand)) {
                out.println("subcommands: list, show, mock");
                out.println("\tlist - show available form templates");
                out.println("\tshow <uid> - show the form template");
                out.println("\tmock - generate and save a form template");
            } else if ("mock".equals(subCommand)) {
                FormTemplate mockEntity = createMockEntity();
                formTemplateService.save(mockEntity);
            } else if ("list".equals(subCommand)) {
                for (FormTemplate savedArchive : formTemplateService.getAll()) {
                    out.println(savedArchive);
                }
            } else if ("show".equals(subCommand)) {
                URI requestedUid = null;
                try {
                    requestedUid = new URI(subArguments);
                    FormTemplate requestedArchive = formTemplateService.get(requestedUid);
                    if (requestedArchive != null) {
                        out.println(requestedArchive.getFormTemplate());
                    } else {
                        err.println("Requested form not found.");
                    }
                } catch (URISyntaxException e) {
                    err.println("Bad form template uid: " + subArguments);
                }
            } else {
                err.println("Don't know how to respond to " + subCommand);
            }
        } else {
            err.println("Sorry, can't parse:" + argString);
        }
    }

    private FormTemplate createMockEntity() {
        MutableFormTemplate mockFormTemplate = new MutableFormTemplate();
        mockFormTemplate.setFormTemplate(createMockTemplate());
        mockFormTemplate.setDateCreated(new DateTime());
        mockFormTemplate.setDateModified(new DateTime());
        mockFormTemplate.setDescription("mocked-up form");
        mockFormTemplate.setFormStyle(FormTemplateStyle.ROSA.toString());
        mockFormTemplate.setFormType("text/xml");
        mockFormTemplate.setName("mock" + mockFormCounter);
        
        try {
            mockFormTemplate.setUid(new URI("http://gatherdata.org/mock/" + mockFormCounter));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mockFormCounter++;
        return mockFormTemplate;
    }

    private String createMockTemplate() {
        StringBuilder formTemplate = new StringBuilder();
        formTemplate.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        formTemplate
                .append("<xhtml:html xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xforms=\"http://www.w3.org/2002/xforms\" xmlns:ev=\"http://www.w3.org/2001/xml-events\" xmlns:jr=\"http://openrosa.org/javarosa\" xmlns:xhtml=\"http://www.w3.org/1999/xhtml\">\n");
        formTemplate.append("<xhtml:head>\n");
        formTemplate.append("<xhtml:title>Simple Form</xhtml:title>\n");
        formTemplate.append("<xforms:model>\n");
        formTemplate.append("<xforms:instance>\n");
        formTemplate.append("<form>\n");
        formTemplate.append("<one-section>\n");
        formTemplate.append("<name/>\n");
        formTemplate.append("</one-section>\n");
        formTemplate.append("</form>\n");
        formTemplate.append("</xforms:instance>\n");
        formTemplate.append("<xforms:bind id=\"name-bind\" nodeset=\"one-section/name\" name=\"name\"/>\n");
        formTemplate.append("<jr:itext>\n");
        formTemplate.append("<jr:translation lang=\"en\">\n");
        formTemplate.append("<jr:text id=\"one-section\">\n");
        formTemplate.append("<jr:value>One Question</jr:value>\n");
        formTemplate.append("</jr:text>\n");
        formTemplate.append("<jr:text id=\"name\">\n");
        formTemplate.append("<jr:value>What is your name?</jr:value>\n");
        formTemplate.append("</jr:text>\n");
        formTemplate.append("</jr:translation>\n");
        formTemplate.append("</jr:itext>\n");
        formTemplate.append("</xforms:model>\n");
        formTemplate.append("</xhtml:head>\n");
        formTemplate.append("<xhtml:body>\n");
        formTemplate.append("<xforms:input bind=\"name-bind\" id=\"name-control\">\n");
        formTemplate.append("<xforms:label ref=\"jr:itext('name')\"/>\n");
        formTemplate.append("</xforms:input>\n");
        formTemplate.append("</xhtml:body>\n");
        formTemplate.append("</xhtml:html>\n");

        return formTemplate.toString();
    }

    public String getName() {
        return COMMAND_NAME;
    }

    public String getShortDescription() {
        return "interacts with " + formTemplateService.getClass().getSimpleName();
    }

    public String getUsage() {
        return COMMAND_NAME + " <sub-command>";
    }

}
