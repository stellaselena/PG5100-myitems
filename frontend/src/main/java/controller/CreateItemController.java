package controller;

import ejb.ItemEJB;
import validation.ItemTypes;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Stella on 11.08.2017.
 */

@Named
@RequestScoped
public class CreateItemController {

    private String formType;
    private String formTitle;
    private String formText;

    @EJB
    private ItemEJB itemEJB;

    @Inject
    private LoggingController loggingController;

    public String createItem() {

        try {
            itemEJB.createItem(loggingController.getRegisteredUser(), formType, formTitle, formText);
        } catch (Exception e) {
            return "newItem.jsf";
        }

        return "home.jsf";
    }

    public List<String> getItemTypes() {
        return ItemTypes.getTypes();
    }


    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormTitle() {
        return formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }
}
