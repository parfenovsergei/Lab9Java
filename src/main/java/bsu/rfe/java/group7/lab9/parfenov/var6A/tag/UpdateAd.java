package bsu.rfe.java.group7.lab9.parfenov.var6A.tag;

import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.Ad;
import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.AdList;
import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.AdListHelper;
import bsu.rfe.java.group7.lab9.parfenov.var6A.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Calendar;

public class UpdateAd extends SimpleTagSupport {

    private Ad ad;

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public void doTag() throws JspException, IOException {
        String errorMessage = null;
        AdList adList = (AdList) getJspContext().getAttribute("ads", PageContext.APPLICATION_SCOPE);
        User currentUser = (User) getJspContext().getAttribute("authUser", PageContext.SESSION_SCOPE);

        if (ad.getSubject()==null || ad.getSubject().equals("")) {
            errorMessage = "The title cannot be empty!";

        } else {
            if (currentUser==null || (ad.getId()>0 && ad.getAuthorId()!=currentUser.getId())) {
                errorMessage = "You are trying to change a message that you do not have permission to access!";
            }
        }

        if (errorMessage==null) {
            ad.setLastModified(Calendar.getInstance().getTimeInMillis());

            if (ad.getId()==0) {
                adList.addAd(currentUser, ad);

            } else {
                adList.updateAd(ad);
            }

            AdListHelper.saveAdList(adList);
        }

        getJspContext().setAttribute("errorMessage", errorMessage,PageContext.SESSION_SCOPE);
    }
}
