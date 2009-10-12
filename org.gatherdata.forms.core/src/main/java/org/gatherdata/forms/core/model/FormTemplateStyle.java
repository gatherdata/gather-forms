package org.gatherdata.forms.core.model;

public enum FormTemplateStyle {
    ROSA("rosa"),
    ORBEON("orbeon");
    
    private String styleName;

    FormTemplateStyle(String styleName) {
        this.styleName = styleName;
    }

    @Override
    public String toString() {
        return styleName;
    }
    
}
